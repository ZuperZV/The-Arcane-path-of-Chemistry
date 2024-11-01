package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.GravityBlock;
import net.chemistry.arcane_chemistry.block.custom.NickelCompreserBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.FirePotRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.NickelCompreserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.ENABLED;
import static net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock.LIT;
import static net.chemistry.arcane_chemistry.block.custom.NickelCompreserBlock.EXTENDED;

public class GravityBlockEntity extends BlockEntity {

    private int progress = 0;
    private int maxProgress = 50;
    private int useCooldown = 0;
    private int useCooldown2 = 0;

    private static final int RADIUS = 30;
    private static final double UPWARD_VELOCITY = 0.05;
    private static final double DOWNWARD_VELOCITY = 0.2;
    private static final int NORMAL_TICK_DELAY = 1;
    private static final int FLOAT_DURATION = 40;

    public final ContainerData data;
    private Random random;

    private int floatTimer = 0;
    private boolean isFloating = false;

    public GravityBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRAVITY_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GravityBlockEntity.this.progress;
                    case 1 -> GravityBlockEntity.this.maxProgress;
                    case 2 -> GravityBlockEntity.this.useCooldown;
                    case 3 -> GravityBlockEntity.this.useCooldown2;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GravityBlockEntity.this.progress = pValue;
                    case 1 -> GravityBlockEntity.this.maxProgress = pValue;
                    case 2 -> GravityBlockEntity.this.useCooldown = pValue;
                    case 3 -> GravityBlockEntity.this.useCooldown2 = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos) {
        boolean isLit = state.getValue(GravityBlock.LIT);
        boolean shouldBeLit = GravityBlock.arePedestalPositionsNickel(level, pos, state) ||
                GravityBlock.arePedestal2PositionsNickel(level, pos, state) ||
                GravityBlock.arePedestal3PositionsNickel(level, pos, state) ||
                GravityBlock.arePedestal4PositionsNickel(level, pos, state);

        if (shouldBeLit != isLit) {
            level.setBlockAndUpdate(pos, state.setValue(LIT, shouldBeLit));
        }

        AABB areaOfEffect = new AABB(pos).inflate(RADIUS);
        List<Entity> entities = level.getEntities(null, areaOfEffect);

        this.random = new Random();

        if (!(shouldBeLit)) {
            if (useCooldown <= NORMAL_TICK_DELAY) {
                for (Entity entity : entities) {
                    if (!(entity instanceof ItemEntity)) {
                        if (random.nextInt(4500) == 1) {
                            entity.setDeltaMovement(entity.getDeltaMovement().x, -UPWARD_VELOCITY, entity.getDeltaMovement().z);
                            useCooldown = 13;
                        } else {
                            entity.setDeltaMovement(entity.getDeltaMovement().x, UPWARD_VELOCITY, entity.getDeltaMovement().z);
                        }
                    }
                }
            } else {
                useCooldown--;
            }
        } else if (useCooldown2 <= NORMAL_TICK_DELAY) {
            for (Entity entity : entities) {
                if (!(entity instanceof ItemEntity)) {
                    if (random.nextInt(500) == 1) {
                        entity.setDeltaMovement(entity.getDeltaMovement().x, -UPWARD_VELOCITY, entity.getDeltaMovement().z);
                        useCooldown2 = 16;
                    } else {
                        entity.setDeltaMovement(entity.getDeltaMovement().x, UPWARD_VELOCITY, entity.getDeltaMovement().z);
                    }
                }
            }
        } else {
            useCooldown2--;
        }
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("my_block_entity.progress", this.progress);
        tag.putInt("my_block_entity.max_progress", this.maxProgress);
        tag.putInt("my_block_entity.use_cooldown", this.useCooldown);
        tag.putInt("my_block_entity.use_cooldown2", this.useCooldown2);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("my_block_entity.progress");
        maxProgress = tag.getInt("my_block_entity.max_progress");
        useCooldown = tag.getInt("my_block_entity.use_cooldown");
        useCooldown2 = tag.getInt("my_block_entity.use_cooldown2");
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    private void markForUpdate() {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}