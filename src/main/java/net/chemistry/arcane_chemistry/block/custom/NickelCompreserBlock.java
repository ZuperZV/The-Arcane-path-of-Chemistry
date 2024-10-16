package net.chemistry.arcane_chemistry.block.custom;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.NickelCompreserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NickelCompreserBlock extends Block implements EntityBlock {
    private final String elementName;
    private final int color;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;

    public NickelCompreserBlock(Properties properties, String elementName, int color) {
        super(properties);
        this.elementName = elementName;
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(LIT, false)
                .setValue(EXTENDED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, EXTENDED);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTootipComponents, TooltipFlag pTooltipFlag) {
        pTootipComponents.add(Component.literal(elementName).setStyle(Style.EMPTY.withColor(color)));
        super.appendHoverText(pStack, pContext, pTootipComponents, pTooltipFlag);
    }

    @Override
    public @javax.annotation.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NickelCompreserBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof NickelCompreserBlockEntity tile) {
                tile.tick(pos, state, tile);

            }
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            ServerPlayer theplayer = (ServerPlayer) player;

            if (state.getValue(EXTENDED)) {
                if (entity instanceof NickelCompreserBlockEntity) {
                    theplayer.openMenu((NickelCompreserBlockEntity) entity, pos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            ServerPlayer theplayer = (ServerPlayer) player;

            if (state.getValue(EXTENDED)) {
                if (entity instanceof NickelCompreserBlockEntity) {
                    theplayer.openMenu((NickelCompreserBlockEntity) entity, pos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof NickelCompreserBlockEntity furnace) {
                furnace.dropItems();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && (state.getValue(EXTENDED))) {
            double d0 = (double)pos.getX() + 0.5;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            double xPos = (double)pos.getX() + 0.5;
            double yPos = pos.getY();
            double zPos = (double)pos.getZ() + 0.5;

            Direction direction = state.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52;
            double d4 = random.nextDouble() * 0.6 - 0.3;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d4;
            double d6 = random.nextDouble() * 6.0 / 16.0;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d4;

            double defaultOffset = random.nextDouble() * 0.6 - 0.3;
            double xOffsets = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : defaultOffset;
            double yOffset = random.nextDouble() * 6.0 / 8.0;
            double zOffset = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : defaultOffset;

            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);

            if(level.getBlockEntity(pos) instanceof NickelCompreserBlockEntity NickelCompreserBlockEntity && !NickelCompreserBlockEntity.getInputItems().getStackInSlot(0).isEmpty()) {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, NickelCompreserBlockEntity.getInputItems().getStackInSlot(0)),
                        xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);
            }
        }
    }

    private static BlockPos[] getBlockPositions(BlockPos pos, Direction facing) {
        switch (facing) {
            case SOUTH:
                return new BlockPos[]{
                        pos.offset(1, 0, 0),
                        pos.offset(-1, 0, 0),
                        pos.offset(0, 0, -1),
                        pos.offset(-1, 0, -1),
                        pos.offset(1, 0, -1),
                        pos.offset(0, 0, -2),
                        pos.offset(-1, 0, -2),
                        pos.offset(1, 0, -2),

                        pos.offset(-1, 1, -1),
                        pos.offset(1, 1, -1),
                        pos.offset(0, 1, -2),
                        pos.offset(-1, 1, -2),
                        pos.offset(1, 1, -2),

                        pos.offset(0, 2, 0),
                        pos.offset(-1, 2, 0),
                        pos.offset(1, 2, 0),
                        pos.offset(-1, 2, -1),
                        pos.offset(1, 2, -1),
                        pos.offset(0, 2, -2),
                        pos.offset(-1, 2, -2),
                        pos.offset(1, 2, -2)
                };
            case NORTH:
                return new BlockPos[]{
                        pos.offset(-1, 0, 0),
                        pos.offset(1, 0, 0),
                        pos.offset(0, 0, 1),
                        pos.offset(1, 0, 1),
                        pos.offset(-1, 0, 1),
                        pos.offset(0, 0, 2),
                        pos.offset(1, 0, 2),
                        pos.offset(-1, 0, 2),

                        pos.offset(1, 1, 1),
                        pos.offset(-1, 1, 1),
                        pos.offset(0, 1, 2),
                        pos.offset(1, 1, 2),
                        pos.offset(-1, 1, 2),

                        pos.offset(0, 2, 0),
                        pos.offset(-1, 2, 0),
                        pos.offset(1, 2, 0),
                        pos.offset(1, 2, 1),
                        pos.offset(-1, 2, 1),
                        pos.offset(0, 2, 2),
                        pos.offset(1, 2, 2),
                        pos.offset(-1, 2, 2)
                };
            case EAST:
                return new BlockPos[]{
                        pos.offset(0, 0, 1),
                        pos.offset(0, 0, -1),
                        pos.offset(-1, 0, 0),
                        pos.offset(-1, 0, -1),
                        pos.offset(-1, 0, 1),
                        pos.offset(-2, 0, 0),
                        pos.offset(-2, 0, -1),
                        pos.offset(-2, 0, 1),

                        pos.offset(-1, 1, -1),
                        pos.offset(-1, 1, 1),
                        pos.offset(-2, 1, 0),
                        pos.offset(-2, 1, -1),
                        pos.offset(-2, 1, 1),

                        pos.offset(0, 2, 0),
                        pos.offset(0, 2, 1),
                        pos.offset(0, 2, -1),
                        pos.offset(-1, 2, 1),
                        pos.offset(-1, 2, -1),
                        pos.offset(-2, 2, 0),
                        pos.offset(-2, 2, 1),
                        pos.offset(-2, 2, -1)
                };
            case WEST:
                return new BlockPos[]{
                        pos.offset(0, 0, -1),
                        pos.offset(0, 0, 1),
                        pos.offset(1, 0, 0),
                        pos.offset(1, 0, 1),
                        pos.offset(1, 0, -1),
                        pos.offset(2, 0, 0),
                        pos.offset(2, 0, 1),
                        pos.offset(2, 0, -1),

                        pos.offset(1, 1, 1),
                        pos.offset(1, 1, -1),
                        pos.offset(2, 1, 0),
                        pos.offset(2, 1, 1),
                        pos.offset(2, 1, -1),

                        pos.offset(0, 2, 0),
                        pos.offset(0, 2, 1),
                        pos.offset(0, 2, -1),
                        pos.offset(1, 2, 1),
                        pos.offset(1, 2, -1),
                        pos.offset(2, 2, 0),
                        pos.offset(2, 2, 1),
                        pos.offset(2, 2, -1)
                };
            default:
                return new BlockPos[0];
        }
    }

    private static BlockPos[] getGlasBlockPositions(BlockPos pos, Direction facing) {
        switch (facing) {
            case SOUTH:
                return new BlockPos[]{
                        pos.offset(1, 1, 0),
                        pos.offset(-1, 1, 0),
                        pos.offset(0, 1, 0),
                };
            case NORTH:
                return new BlockPos[]{
                        pos.offset(-1, 1, 0),
                        pos.offset(1, 1, 0),
                        pos.offset(0, 1, 0),
                };
            case EAST:
                return new BlockPos[]{
                        pos.offset(0, 1, 1),
                        pos.offset(0, 1, -1),
                        pos.offset(0, 1, 0),
                };
            case WEST:
                return new BlockPos[]{
                        pos.offset(0, 1, -1),
                        pos.offset(0, 1, 1),
                        pos.offset(0, 1, 0),
                };
            default:
                return new BlockPos[0];
        }
    }


    public static boolean arePedestalPositionsNickel(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);

        BlockPos[] blockPositions = getBlockPositions(pos, facing);
        BlockPos[] glasPositions = getGlasBlockPositions(pos, facing);

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

        return true;
    }
}