package net.chemistry.arcane_chemistry.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BioHarvester extends FarmBlock {
    public static final MapCodec<FarmBlock> CODEC = simpleCodec(FarmBlock::new);
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    public static final int MAX_MOISTURE = 7;

    @Override
    public MapCodec<FarmBlock> codec() {
        return CODEC;
    }

    public BioHarvester(BlockBehaviour.Properties properties) {
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
    protected BlockState updateShape(BlockState p_53276_, Direction p_53277_, BlockState p_53278_, LevelAccessor p_53279_, BlockPos p_53280_, BlockPos p_53281_) {
        if (p_53277_ == Direction.UP && !p_53276_.canSurvive(p_53279_, p_53280_)) {
            p_53279_.scheduleTick(p_53280_, this, 1);
        }

        return super.updateShape(p_53276_, p_53277_, p_53278_, p_53279_, p_53280_, p_53281_);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(MOISTURE) > 0;
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource randomSource) {
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

            for (ItemStack drop : drops) {
                if (!insertIntoChestBelow(world, pos, drop)) {
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
}
