package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.HardOvenBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.HardOvenRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.HardOvenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HardOvenBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {

    private final ItemStackHandler inputItems = createItemHandler(3);
    private final ItemStackHandler outputItems = createItemHandler(1);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1};
    private static final int[] SLOTS_FOR_DOWN = new int[]{3};
    private static final int[] SLOTS_FOR_SIDES = new int[]{2};

    private int progress = 0;
    private int maxProgress = 300;

    private int fuelBurnTime = 0;
    private int maxFuelBurnTime = 1000;

    public final ContainerData data;

    public HardOvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HARD_OVEN_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> HardOvenBlockEntity.this.progress;
                    case 1 -> HardOvenBlockEntity.this.maxProgress;
                    case 2 -> HardOvenBlockEntity.this.fuelBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> HardOvenBlockEntity.this.progress = pValue;
                    case 1 -> HardOvenBlockEntity.this.maxProgress = pValue;
                    case 2 -> HardOvenBlockEntity.this.fuelBurnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HardOvenBlockEntity blockEntity) {
        boolean dirty = false;

        if (blockEntity.fuelBurnTime > 0) {
            blockEntity.fuelBurnTime--;
        }

        if (blockEntity.fuelBurnTime == 0 && blockEntity.hasRecipe() && blockEntity.canConsumeFuel()) {
            blockEntity.fuelBurnTime = blockEntity.getFuelBurnTime(blockEntity.inputItems.getStackInSlot(2));
            if (blockEntity.fuelBurnTime > 0) {
                dirty = true;
                ItemStack fuelStack = blockEntity.inputItems.extractItem(2, 1, false);
            }
        }

        if (blockEntity.fuelBurnTime > 0 && blockEntity.hasRecipe()) {
            blockEntity.progress++;
            level.setBlockAndUpdate(pos, state.setValue(HardOvenBlock.LIT, true));
            if (blockEntity.progress >= blockEntity.maxProgress) {
                blockEntity.craftItem(blockEntity);
                blockEntity.progress = 0;
                dirty = true;
            }
            dirty = true;
        } else {
            if (blockEntity.progress != 0) {
                blockEntity.progress = Math.max(blockEntity.progress - 2, 0);
                dirty = true;
            }

            level.setBlockAndUpdate(pos, state.setValue(HardOvenBlock.LIT, false));
        }

        if (dirty) {
            blockEntity.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        }
    }
    private boolean canConsumeFuel() {
        return isFuel(this.inputItems.getStackInSlot(2));
    }

    private int getFuelBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return stack.getBurnTime(null);
        }
    }

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<HardOvenRecipe>> alcheRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.HARD_OVEN_RECIPE_TYPE.get(), getRecipeInput(inventory), level);


        return (alcheRecipe.isPresent() && canInsertAmountIntoOutputSlot(inventory)) ;
    }

    private RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
    }

    private boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        ItemStack outputStack1 = outputItems.getStackInSlot(0);

        boolean canInsertIntoFirstSlot = outputStack1.isEmpty() || (outputStack1.getCount() < outputStack1.getMaxStackSize());

        return canInsertIntoFirstSlot;
    }

    private void craftItem(HardOvenBlockEntity serverLevel) {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<HardOvenRecipe>> alcheRecipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.HARD_OVEN_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (alcheRecipeOptional.isPresent()) {
            HardOvenRecipe recipe = alcheRecipeOptional.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());

            ItemStack outputStack = outputItems.getStackInSlot(0);
            if (outputStack.isEmpty()) {
                outputItems.setStackInSlot(0, result.copy());
            } else if (outputStack.getItem() == result.getItem()) {
                outputStack.grow(result.getCount());
            }

            inputItems.extractItem(0, 1, false);
            inputItems.extractItem(1, 1, false);
        }
    }

    private boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(null) > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress", this.progress);
        tag.putInt("maxProgress", this.maxProgress);
        tag.putInt("fuelBurnTime", this.fuelBurnTime);
        tag.put("inputItems", inputItems.serializeNBT(registries));
        tag.put("outputItems", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("progress");
        this.maxProgress = tag.getInt("maxProgress");
        this.fuelBurnTime = tag.getInt("fuelBurnTime");
        inputItems.deserializeNBT(registries, tag.getCompound("inputItems"));
        outputItems.deserializeNBT(registries, tag.getCompound("outputItems"));
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

    public Lazy<IItemHandler> getItemHandler() {
        return itemHandler;
    }

    public ItemStackHandler getInputItems() {
        return inputItems;
    }

    public ItemStackHandler getOutputItems() {
        return outputItems;
    }

    public Lazy<IItemHandler> getInputItemHandler() {
        return inputItemHandler;
    }

    public Lazy<IItemHandler> getOutputItemHandler() {
        return outputItemHandler;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getMaxFuelBurnTime() {
        return maxFuelBurnTime;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new HardOvenMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.hard_oven");
    }

    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    @Override
    public int[] getSlotsForFace(Direction p_58363_) {
        if (p_58363_ == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return p_58363_ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int p_58336_, ItemStack p_58337_, @javax.annotation.Nullable Direction p_58338_) {
        return this.canPlaceItem(p_58336_, p_58337_);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
        return p_58394_ == Direction.DOWN && p_58392_ == 1 ? p_58393_.is(Items.WATER_BUCKET) || p_58393_.is(Items.BUCKET) : true;
    }

    @Override
    public int getContainerSize() {
        return itemHandler.get().getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inputItems.getSlots(); i++) {
            if (!inputItems.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        for (int i = 0; i < outputItems.getSlots(); i++) {
            if (!outputItems.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slotIndex) {
        if (slotIndex < inputItems.getSlots()) {
            return inputItems.getStackInSlot(slotIndex);
        }

        int adjustedIndex = slotIndex - inputItems.getSlots();

        if (adjustedIndex < outputItems.getSlots()) {
            return outputItems.getStackInSlot(adjustedIndex);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slotIndex, int count) {
        int adjustedIndex = slotIndex - inputItems.getSlots();

        if (adjustedIndex < outputItems.getSlots()) {
            ItemStack stackInSlot = outputItems.getStackInSlot(adjustedIndex);
            if (!stackInSlot.isEmpty()) {
                return outputItems.extractItem(adjustedIndex, count, false);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slotIndex) {
        if (slotIndex < inputItems.getSlots()) {
            ItemStack stackInSlot = inputItems.getStackInSlot(slotIndex);

            if (!stackInSlot.isEmpty()) {
                inputItems.setStackInSlot(slotIndex, ItemStack.EMPTY);
                return stackInSlot;
            }
        }
        int adjustedIndex = slotIndex - inputItems.getSlots();
        if (adjustedIndex < outputItems.getSlots()) {
            ItemStack stackInSlot = outputItems.getStackInSlot(adjustedIndex);

            if (!stackInSlot.isEmpty()) {
                outputItems.setStackInSlot(adjustedIndex, ItemStack.EMPTY);
                return stackInSlot;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slotIndex, ItemStack stack) {
        if (slotIndex < inputItems.getSlots()) {
            inputItems.setStackInSlot(slotIndex, stack);
            return;
        }
        int adjustedIndex = slotIndex - inputItems.getSlots();
        if (adjustedIndex < outputItems.getSlots()) {
            outputItems.setStackInSlot(adjustedIndex, stack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        final double MAX_DISTANCE = 64.0;
        double distanceSquared = player.distanceToSqr(this.worldPosition.getX() + 0.5,
                this.worldPosition.getY() + 0.5,
                this.worldPosition.getZ() + 0.5);
        return distanceSquared <= MAX_DISTANCE;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inputItems.setStackInSlot(i, ItemStack.EMPTY);
        }

        for (int i = 0; i < outputItems.getSlots(); i++) {
            outputItems.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
