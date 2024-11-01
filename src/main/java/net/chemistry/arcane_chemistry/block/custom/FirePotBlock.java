package net.chemistry.arcane_chemistry.block.custom;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.NickelCompreserBlockEntity;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.ENABLED;
import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.WATER_COLOR;

public class FirePotBlock extends Block {
    private final String elementName;
    private final int color;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    static VoxelShape SHAPE = Shapes.or(
            box(1, 2, 1, 3, 14, 15),
            box(3, 0, 3, 13, 2, 13),
            box(13, 2, 1, 15, 14, 15),
            box(3, 2, 1, 13, 14, 3),
            box(3, 2, 13, 13, 14, 15)
    );


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public FirePotBlock(Properties properties, String elementName, int color) {
        super(properties);
        this.elementName = elementName;
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos targetPos = context.getClickedPos();
        Level level = context.getLevel();

        BlockState existingBlockState = level.getBlockState(targetPos);

        if (existingBlockState.getBlock() instanceof CampfireBlock) {
            level.setBlockAndUpdate(targetPos, ModBlocks.FIRE_POT_CAMPFIRE.get().defaultBlockState().setValue(ENABLED, false).setValue(WATER_COLOR, FirePotCampfireBlock.ColorType.DEFAULT));
            return null;
        }

        BlockPos belowPos = targetPos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);
        boolean isLit = false;

        if (belowBlockState.getBlock() instanceof CampfireBlock) {
            isLit = belowBlockState.getValue(CampfireBlock.LIT);

            if (isLit) {
                level.setBlockAndUpdate(belowPos, ModBlocks.FIRE_POT_CAMPFIRE.get().defaultBlockState().setValue(ENABLED, false).setValue(WATER_COLOR, FirePotCampfireBlock.ColorType.DEFAULT).setValue(FirePotCampfireBlock.LIT, true));
            } else {
                level.setBlockAndUpdate(belowPos, ModBlocks.FIRE_POT_CAMPFIRE.get().defaultBlockState().setValue(ENABLED, false).setValue(WATER_COLOR, FirePotCampfireBlock.ColorType.DEFAULT).setValue(FirePotCampfireBlock.LIT, false));
            }

            return null;
        }

        return this.defaultBlockState().setValue(LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTootipComponents, TooltipFlag pTooltipFlag) {
        pTootipComponents.add(Component.literal(elementName).setStyle(Style.EMPTY.withColor(color)));
        super.appendHoverText(pStack, pContext, pTootipComponents, pTooltipFlag);
    }
}