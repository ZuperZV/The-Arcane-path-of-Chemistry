package net.chemistry.arcane_chemistry.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemistry.arcane_chemistry.block.entity.custom.FirePotCampfireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nullable;
import java.util.Optional;

public class FirePotCampfireBlock extends BaseEntityBlock {
    public static final MapCodec<FirePotCampfireBlock> CODEC = RecordCodecBuilder.mapCodec(
            p_308808_ -> p_308808_.group(
                            Codec.BOOL.fieldOf("spawn_particles").forGetter(p_304361_ -> p_304361_.spawnParticles),
                            Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter(p_304360_ -> p_304360_.fireDamage),
                            propertiesCodec()
                    )
                    .apply(p_308808_, FirePotCampfireBlock::new)
    );
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    private static final int SMOKE_DISTANCE = 5;
    private final boolean spawnParticles;
    private final int fireDamage;
    private static final float RAIN_FILL_CHANCE = 0.05F;
    private static final float POWDER_SNOW_FILL_CHANCE = 0.1F;

    @Override
    public MapCodec<FirePotCampfireBlock> codec() {
        return CODEC;
    }

    public FirePotCampfireBlock(boolean p_51236_, int p_51237_, BlockBehaviour.Properties p_51238_) {
        super(p_51238_);
        this.spawnParticles = p_51236_;
        this.fireDamage = p_51237_;
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(LIT, Boolean.valueOf(true))
                        .setValue(SIGNAL_FIRE, Boolean.valueOf(false))
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack heldItem = player.getItemInHand(interactionHand);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if (blockEntity instanceof FirePotCampfireBlockEntity firePotCampfire) {

            if (heldItem.is(Items.BUCKET)) {
                FluidStack fluidStack = firePotCampfire.extractFluid();

                if (!fluidStack.isEmpty()) {
                    ItemStack filledBucket = new ItemStack(fluidStack.getFluid().getBucket(), 1);
                    player.addItem(filledBucket);

                    firePotCampfire.extractFluid();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (heldItem.is(Items.WATER_BUCKET)) {
                firePotCampfire.insertFluid(Fluids.WATER);

                if (!player.isCreative()) {
                    player.setItemInHand(interactionHand, new ItemStack(Items.BUCKET));
                }
                return ItemInteractionResult.SUCCESS;

            } else if (!heldItem.isEmpty()) {
                firePotCampfire.insertItem(heldItem);

                if (!player.isCreative()) {
                    heldItem.shrink(1);

                    if (heldItem.isEmpty()) {
                        player.setItemInHand(interactionHand, ItemStack.EMPTY);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }



    @Override
    public BlockEntity newBlockEntity(BlockPos p_152759_, BlockState p_152760_) {
        return new FirePotCampfireBlockEntity(p_152759_, p_152760_);
    }

    @Override
    protected void entityInside(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_) {
        if (p_51269_.getValue(LIT) && p_51272_ instanceof LivingEntity) {
            p_51272_.hurt(p_51270_.damageSources().campfire(), (float)this.fireDamage);
        }

        super.entityInside(p_51269_, p_51270_, p_51271_, p_51272_);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();

        return this.defaultBlockState()
                .setValue(SIGNAL_FIRE, Boolean.valueOf(this.isSmokeSource(levelaccessor.getBlockState(blockpos.below()))))
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState p_51298_, Direction p_51299_, BlockState p_51300_, LevelAccessor p_51301_, BlockPos p_51302_, BlockPos p_51303_) {

        return p_51299_ == Direction.DOWN
                ? p_51298_.setValue(SIGNAL_FIRE, Boolean.valueOf(this.isSmokeSource(p_51300_)))
                : super.updateShape(p_51298_, p_51299_, p_51300_, p_51301_, p_51302_, p_51303_);
    }

    private boolean isSmokeSource(BlockState p_51324_) {
        return p_51324_.is(Blocks.HAY_BLOCK);
    }

    @Override
    protected VoxelShape getShape(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
        if (p_220918_.getValue(LIT)) {
            if (p_220921_.nextInt(10) == 0) {
                p_220919_.playLocalSound(
                        (double)p_220920_.getX() + 0.5,
                        (double)p_220920_.getY() + 0.5,
                        (double)p_220920_.getZ() + 0.5,
                        SoundEvents.CAMPFIRE_CRACKLE,
                        SoundSource.BLOCKS,
                        0.5F + p_220921_.nextFloat(),
                        p_220921_.nextFloat() * 0.7F + 0.6F,
                        false
                );
            }

            if (this.spawnParticles && p_220921_.nextInt(5) == 0) {
                for (int i = 0; i < p_220921_.nextInt(1) + 1; i++) {
                    p_220919_.addParticle(
                            ParticleTypes.LAVA,
                            (double)p_220920_.getX() + 0.5,
                            (double)p_220920_.getY() + 0.5,
                            (double)p_220920_.getZ() + 0.5,
                            (double)(p_220921_.nextFloat() / 2.0F),
                            5.0E-5,
                            (double)(p_220921_.nextFloat() / 2.0F)
                    );
                }
            }
        }
    }

    public static void dowse(@Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
        if (p_152751_.isClientSide()) {
            for (int i = 0; i < 20; i++) {
                makeParticles((Level)p_152751_, p_152752_, p_152753_.getValue(SIGNAL_FIRE), true);
            }
        }

        BlockEntity blockentity = p_152751_.getBlockEntity(p_152752_);
        if (blockentity instanceof FirePotCampfireBlockEntity) {
            ((FirePotCampfireBlockEntity)blockentity).dowse();
        }

        p_152751_.gameEvent(p_152750_, GameEvent.BLOCK_CHANGE, p_152752_);
    }

    @Override
    protected void onProjectileHit(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
        BlockPos blockpos = p_51246_.getBlockPos();
        if (!p_51244_.isClientSide
                && p_51247_.isOnFire()
                && p_51247_.mayInteract(p_51244_, blockpos)
                && !p_51245_.getValue(LIT)) {
            p_51244_.setBlock(blockpos, p_51245_.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
        }
    }

    public static void makeParticles(Level p_51252_, BlockPos p_51253_, boolean p_51254_, boolean p_51255_) {
        RandomSource randomsource = p_51252_.getRandom();
        SimpleParticleType simpleparticletype = p_51254_ ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        p_51252_.addAlwaysVisibleParticle(
                simpleparticletype,
                true,
                (double)p_51253_.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                (double)p_51253_.getY() + randomsource.nextDouble() + randomsource.nextDouble(),
                (double)p_51253_.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        if (p_51255_) {
            p_51252_.addParticle(
                    ParticleTypes.SMOKE,
                    (double)p_51253_.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    (double)p_51253_.getY() + 0.4,
                    (double)p_51253_.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );
        }
    }

    public static boolean isSmokeyPos(Level p_51249_, BlockPos p_51250_) {
        for (int i = 1; i <= 5; i++) {
            BlockPos blockpos = p_51250_.below(i);
            BlockState blockstate = p_51249_.getBlockState(blockpos);
            if (isLitCampfire(blockstate)) {
                return true;
            }

            boolean flag = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(p_51249_, blockpos, CollisionContext.empty()), BooleanOp.AND); // FORGE: Fix MC-201374
            if (flag) {
                BlockState blockstate1 = p_51249_.getBlockState(blockpos.below());
                return isLitCampfire(blockstate1);
            }
        }

        return false;
    }

    public static boolean isLitCampfire(BlockState p_51320_) {
        return p_51320_.hasProperty(LIT) && p_51320_.is(BlockTags.CAMPFIRES) && p_51320_.getValue(LIT);
    }

    @Override
    protected FluidState getFluidState(BlockState p_51318_) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    protected BlockState rotate(BlockState p_51295_, Rotation p_51296_) {
        return p_51295_.setValue(FACING, p_51296_.rotate(p_51295_.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState p_51292_, Mirror p_51293_) {
        return p_51292_.rotate(p_51293_.getRotation(p_51292_.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51305_) {
        p_51305_.add(LIT, SIGNAL_FIRE, FACING);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof FirePotCampfireBlockEntity tile) {
                tile.tick(level, pos, state, tile);
            }
        };
    }

    @Override
    protected boolean isPathfindable(BlockState p_51264_, PathComputationType p_51267_) {
        return false;
    }

    public static boolean canLight(BlockState p_51322_) {
        return p_51322_.is(BlockTags.CAMPFIRES, p_51262_ -> p_51262_.hasProperty(LIT))
                && !p_51322_.getValue(LIT);
    }


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        level.setBlock(pos, Blocks.CAMPFIRE.defaultBlockState(), 3);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
