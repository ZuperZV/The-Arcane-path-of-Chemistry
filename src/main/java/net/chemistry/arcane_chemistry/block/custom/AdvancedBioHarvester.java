package net.chemistry.arcane_chemistry.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class AdvancedBioHarvester extends FarmBlock {
    public static final MapCodec<FarmBlock> CODEC = simpleCodec(FarmBlock::new);
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    public static final int MAX_MOISTURE = 7;

    @Override
    public MapCodec<FarmBlock> codec() {
        return CODEC;
    }

    public AdvancedBioHarvester(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MOISTURE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    @Override
    protected boolean canSurvive(BlockState p_53272_, LevelReader p_53273_, BlockPos p_53274_) {
        BlockState blockstate = p_53273_.getBlockState(p_53274_.above());
        return !blockstate.isSolid() || blockstate.getBlock() instanceof FenceGateBlock || blockstate.getBlock() instanceof MovingPistonBlock;
    }

    @Override
    public void fallOn(Level p_153227_, BlockState p_153228_, BlockPos p_153229_, Entity p_153230_, float p_153231_) {
        if (!p_153227_.isClientSide
                && net.neoforged.neoforge.common.CommonHooks.onFarmlandTrample(p_153227_, p_153229_, Blocks.DIRT.defaultBlockState(), p_153231_, p_153230_)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(p_153230_, p_153228_, p_153227_, p_153229_);
        }

        super.fallOn(p_153227_, p_153228_, p_153229_, p_153230_, p_153231_);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(MOISTURE) > 0;
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource randomSource) {
        if (!blockState.canSurvive(world, pos)) {
        }

        int moisture = blockState.getValue(MOISTURE);

        if (!isNearWater(world, pos) && !world.isRainingAt(pos.above())) {
            if (moisture > 0) {
                world.setBlock(pos, blockState.setValue(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < MAX_MOISTURE) {
            world.setBlock(pos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

        BlockPos abovePos = pos.above();
        BlockState aboveState = world.getBlockState(abovePos);

        if (isCropReadyToHarvest(aboveState)) {
            List<ItemStack> drops = Block.getDrops(aboveState, world, abovePos, null);
            world.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);

            boolean replanted = false;
            for (ItemStack drop : drops) {
                if (!replanted && replantCrop(world, abovePos, drop)) {
                    replanted = true;
                } else if (!insertIntoChestBelow(world, pos, drop)) {
                    Block.popResource(world, pos.above(), drop);
                }
            }
        }
    }

    public static void turnToDirt(@Nullable Entity p_270981_, BlockState p_270402_, Level p_270568_, BlockPos p_270551_) {
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource randomSource) {
        int moisture = blockState.getValue(MOISTURE);

        if (!isNearWater(world, pos) && !world.isRainingAt(pos.above())) {
            if (moisture > 0) {
                world.setBlock(pos, blockState.setValue(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < MAX_MOISTURE) {
            world.setBlock(pos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

        BlockPos abovePos = pos.above();
        BlockState aboveState = world.getBlockState(abovePos);

        if (isCropReadyToHarvest(aboveState)) {
            List<ItemStack> drops = Block.getDrops(aboveState, world, abovePos, null);
            world.setBlock(abovePos, Blocks.AIR.defaultBlockState(), 3);

            boolean replanted = false;
            for (ItemStack drop : drops) {
                if (!replanted && replantCrop(world, abovePos, drop)) {
                    replanted = true;
                } else if (!insertIntoChestBelow(world, pos, drop)) {
                    Block.popResource(world, pos.above(), drop);
                }
            }
        }
    }


    private boolean isCropReadyToHarvest(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof CropBlock) {
            CropBlock crop = (CropBlock) block;
            return crop.isMaxAge(state);
        }
        return false;
    }

    private boolean replantCrop(ServerLevel world, BlockPos pos, ItemStack seed) {
        Block seedBlock = Block.byItem(seed.getItem());
        if (seedBlock instanceof CropBlock) {
            BlockState newState = seedBlock.defaultBlockState();
            if (world.getBlockState(pos).isAir()) {
                world.setBlock(pos, newState, 3);
                seed.shrink(1);
                return true;
            }
        }
        return false;
    }

    private static boolean isUnderCrops(BlockGetter blockGetter, BlockPos pos) {
        BlockState plant = blockGetter.getBlockState(pos.above());
        BlockState state = blockGetter.getBlockState(pos);
        return plant.getBlock() instanceof CropBlock && state.is(Blocks.FARMLAND);
    }

    private static boolean isNearWater(LevelReader levelReader, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (levelReader.getFluidState(blockpos).isSource()) {
                return true;
            }
        }
        return false;
    }

    private boolean insertIntoChestBelow(ServerLevel world, BlockPos pos, ItemStack drop) {
        BlockEntity blockEntity = world.getBlockEntity(pos.below());
        if (blockEntity instanceof ChestBlockEntity) {
            ChestBlockEntity chestEntity = (ChestBlockEntity) blockEntity;
            if (HopperBlockEntity.addItem(null, chestEntity, drop, null).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldMaintainFarmland(BlockGetter p_279219_, BlockPos p_279209_) {
        return p_279219_.getBlockState(p_279209_.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }
}
