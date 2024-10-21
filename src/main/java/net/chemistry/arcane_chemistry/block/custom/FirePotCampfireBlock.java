package net.chemistry.arcane_chemistry.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.FirePotCampfireBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.FirePotCampfireBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.NickelCompreserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class FirePotCampfireBlock extends Block implements EntityBlock {
    private final String elementName;
    private final int color;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final EnumProperty<ColorType> WATER_COLOR = EnumProperty.create("water_color", ColorType.class);
    public enum ColorType implements StringRepresentable {
        DEFAULT("default"),
        BLUE("blue"),
        RED("red"),
        GREEN("green");

        private final String name;

        ColorType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    static final VoxelShape SHAPE = Shapes.or(
            box(1, 0, 0, 5, 4, 16),
            box(0, 3, 11, 16, 7, 15),
            box(11, 0, 0, 15, 4, 16),
            box(0, 3, 1, 16, 7, 5),
            box(5, 0, 0, 11, 1, 16),
            box(1, 8, 1, 3, 20, 15),
            box(3, 6, 3, 13, 8, 13),
            box(13, 8, 1, 15, 20, 15),
            box(3, 8, 1, 13, 20, 3),
            box(3, 8, 13, 13, 20, 15)
    );

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public FirePotCampfireBlock(Properties properties, String elementName, int color) {
        super(properties);
        this.elementName = elementName;
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(ENABLED, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER_COLOR, ColorType.DEFAULT));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(LIT, false)
                .setValue(ENABLED, false)
                .setValue(WATER_COLOR, ColorType.DEFAULT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
        pBuilder.add(ENABLED);
        pBuilder.add(WATER_COLOR);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTootipComponents, TooltipFlag pTooltipFlag) {
        pTootipComponents.add(Component.literal(elementName).setStyle(Style.EMPTY.withColor(color)));
        super.appendHoverText(pStack, pContext, pTootipComponents, pTooltipFlag);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FirePotCampfireBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof FirePotCampfireBlockEntity tile) {
                tile.tick(pos, state, tile);
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof FirePotCampfireBlockEntity blockEntity) {

            ItemStack singleStack = pStack.copy();
            singleStack.setCount(1);

            if (!pStack.isEmpty()) {
                if (pStack.getItem() == Items.BUCKET) {
                    if (!blockEntity.isInputEmpty(0)) {
                        ItemStack stackOnPedestal = blockEntity.getItem(0);
                        pLevel.setBlock(pPos, pState.setValue(WATER_COLOR, ColorType.DEFAULT), 3);
                        pPlayer.setItemInHand(pHand, stackOnPedestal);
                        blockEntity.clearInput(0);
                        pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }

                for (int i = 0; i < 5; i++) {
                    if (blockEntity.isInputEmpty(i)) {
                        if (singleStack.getItem() != Items.WATER_BUCKET) {
                            ColorType randomColor = getRandomColor();
                            pLevel.setBlock(pPos, pState.setValue(WATER_COLOR, randomColor), 3);
                        } else {
                            pPlayer.setItemInHand(pHand, Items.BUCKET.getDefaultInstance());
                        }

                        blockEntity.setItem(i, singleStack);
                        pStack.shrink(1);
                        pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 5f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            } else {
                for (int i = 1; i < 5; i++) {
                    if (!blockEntity.isInputEmpty(i)) {
                        ItemStack stackOnPedestal = blockEntity.getItem(i);
                        pPlayer.setItemInHand(pHand, stackOnPedestal);
                        blockEntity.clearInput(i);
                        pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }


    private ColorType getRandomColor() {
        ColorType[] colors = ColorType.values();
        Random random = new Random();
        return colors[random.nextInt(colors.length)];
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof FirePotCampfireBlockEntity entity) {
                entity.dropItems();
            }

            dropItemInWorld(level, pos, new ItemStack(ModBlocks.FIRE_POT.get()));

            if (state.getValue(LIT)) {
                level.setBlock(pos, Blocks.CAMPFIRE.defaultBlockState().setValue(BlockStateProperties.LIT, true), 3);
                level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.setBlock(pos, Blocks.CAMPFIRE.defaultBlockState().setValue(BlockStateProperties.LIT, false), 3);
                level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    private void dropItemInWorld(Level level, BlockPos position, ItemStack stack) {
        if (!stack.isEmpty()) {

            ItemEntity itemEntity = new ItemEntity(level, position.getX() + 0.5, position.getY() + 1.1, position.getZ() + 0.5, stack);
            itemEntity.setDeltaMovement(0, 0.2, 0f);
            level.addFreshEntity(itemEntity);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && (state.getValue(ENABLED))) {
            if(level.getBlockEntity(pos) instanceof FirePotCampfireBlockEntity FirePotCampfireBlockEntity && !FirePotCampfireBlockEntity.getInputItems().getStackInSlot(1).isEmpty()) {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, FirePotCampfireBlockEntity.getInputItems().getStackInSlot(1)),
                        0, 0.7, 0, 0.0, 0.0, 0.0);
            }
            level.addParticle(
                    ParticleTypes.BUBBLE,
                    (0.5) * 1.5,
                    1.5,
                    (0.5) * 1.5,
                    0.0,
                    0.0,
                    0.0
            );
        }
    }
}