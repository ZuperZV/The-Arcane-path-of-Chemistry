package net.chemistry.arcane_chemistry.block.custom;

import net.chemistry.arcane_chemistry.block.entity.custom.AuroraCrafterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AuroraCrafterBlock extends Block implements EntityBlock {

    public AuroraCrafterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @javax.annotation.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AuroraCrafterBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof AuroraCrafterBlockEntity tile) {
                tile.tick(pos, state, tile);

            }
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            ServerPlayer theplayer = (ServerPlayer) player;
            
            if (entity instanceof AuroraCrafterBlockEntity) {
                theplayer.openMenu((AuroraCrafterBlockEntity) entity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            ServerPlayer theplayer = (ServerPlayer) player;
            
            if (entity instanceof AuroraCrafterBlockEntity) {
                theplayer.openMenu((AuroraCrafterBlockEntity) entity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof AuroraCrafterBlockEntity furnace) {
                furnace.dropItems();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}