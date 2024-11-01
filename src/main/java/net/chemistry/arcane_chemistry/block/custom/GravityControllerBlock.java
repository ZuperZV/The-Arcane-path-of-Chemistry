package net.chemistry.arcane_chemistry.block.custom;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.GravityControllerBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.GravityControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class GravityControllerBlock extends Block implements EntityBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final int RADIUS = 30;
    private static final double UPWARD_VELOCITY = 0.5;

    public GravityControllerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(LIT, false)
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT, FACING);
    }

    @Override
    public @javax.annotation.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GravityControllerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity ) -> {
            if (blockEntity instanceof GravityControllerBlockEntity tile) {
                tile.tick(st, (ServerLevel) lvl, pos);

            }
        };
    }

    private static BlockPos[] rotatePositions(BlockPos[] positions, Direction facing) {
        BlockPos[] rotatedPositions = new BlockPos[positions.length];

        for (int i = 0; i < positions.length; i++) {
            rotatedPositions[i] = rotatePosition(positions[i], positions[0], facing);
        }

        return rotatedPositions;
    }

    private static BlockPos rotatePosition(BlockPos position, BlockPos origin, Direction facing) {
        int dx = position.getX() - origin.getX();
        int dz = position.getZ() - origin.getZ();

        switch (facing) {
            case NORTH:
                return origin.offset(-dx, position.getY() - origin.getY(), -dz);
            case EAST:
                return origin.offset(-dz, position.getY() - origin.getY(), dx);
            case WEST:
                return origin.offset(dz, position.getY() - origin.getY(), -dx);
            default:
                return position;
        }
    }

    public static BlockPos[] getBlockPositions(BlockPos pos, Direction facing) {

        BlockPos[] southPositions = new BlockPos[]{
                pos.offset(-1, 0, 0), pos.offset(1, 0, 0),
                pos.offset(2, 0, -1), pos.offset(-2, 0, -1), pos.offset(2, 0, -2),
                pos.offset(-2, 0, -2), pos.offset(2, 0, -3), pos.offset(-2, 0, -3),
                pos.offset(1, 0, -4), pos.offset(-1, 0, -4), pos.offset(0, 0, -4),

                pos.offset(0, -1, -1), pos.offset(1, -1, -1), pos.offset(-1, -1, -1),
                pos.offset(0, -1, -2), pos.offset(1, -1, -2), pos.offset(-1, -1, -2),
                pos.offset(0, -1, -3), pos.offset(1, -1, -3), pos.offset(-1, -1, -3),
                pos.offset(0, 2, -2),

                pos.offset(0, 3, 0), pos.offset(1, 3, 0), pos.offset(-1, 3, 0),
                pos.offset(-2, 3, -1), pos.offset(2, 3, -1), pos.offset(-2, 3, -2),
                pos.offset(2, 3, -2), pos.offset(-2, 3, -3), pos.offset(2, 3, -3),
                pos.offset(0, 3, -4), pos.offset(1, 3, -4), pos.offset(-1, 3, -4)
        };

        return rotatePositions(southPositions, facing);
    }

    private static BlockPos[] getGlasBlockPositions(BlockPos pos, Direction facing) {

        BlockPos[] southPositions = new BlockPos[]{
                pos.offset(0, 1, 0),
                pos.offset(1, 1, 0),
                pos.offset(-1, 1, 0),

                pos.offset(0, 2, -4),
                pos.offset(1, 2, -4),
                pos.offset(-1, 2, -4),

                pos.offset(2, 1, -1),
                pos.offset(-2, 1, -1),
                pos.offset(2, 1, -2),
                pos.offset(-2, 1, -2),
                pos.offset(2, 1, -3),
                pos.offset(-2, 1, -3),

                pos.offset(2, 2, -1),
                pos.offset(-2, 2, -1),
                pos.offset(2, 2, -2),
                pos.offset(-2, 2, -2),
                pos.offset(2, 2, -3),
                pos.offset(-2, 2, -3),
        };

        return rotatePositions(southPositions, facing);
    }

    public static BlockPos[] getGravityBlockPositions(BlockPos pos, Direction facing) {

        BlockPos[] southPositions = new BlockPos[]{
                pos.offset(0, 1, -2)};

        return rotatePositions(southPositions, facing);
    }


    public static boolean arePedestalPositionsNickel(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);

        BlockPos[] blockPositions = getBlockPositions(pos, facing);
        BlockPos[] glasPositions = getGlasBlockPositions(pos, facing);
        BlockPos[] gravityPositions = getGravityBlockPositions(pos, facing);

        for (BlockPos blockPos : blockPositions) {
            if (!level.getBlockState(blockPos).is(ModBlocks.NICKEL_BLOCK)) {
                return false;
            }
        }

        for (BlockPos glassPos : glasPositions) {
            if (!level.getBlockState(glassPos).is(Blocks.GLASS)) {
                return false;
            }
        }

        for (BlockPos gravityPos : gravityPositions) {
            if (!level.getBlockState(gravityPos).is(ModBlocks.GRAVITY)) {
                return false;
            }
        }

        return true;
    }
}
