package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.TungstenCompreserBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.TungstenCompreserRecipe;
import net.chemistry.arcane_chemistry.screen.TungstenCompreserMenu;
import net.chemistry.arcane_chemistry.util.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.chemistry.arcane_chemistry.block.custom.TungstenCompreserBlock.EXTENDED;
import static net.chemistry.arcane_chemistry.block.custom.TungstenCompreserBlock.LIT;

public class TungstenCompreserBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inputItems = createItemHandler(5);
    private final ItemStackHandler outputItems = createItemHandler(2);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private int progress = 0;
    private int maxProgress = 200;

    private final CustomEnergyStorage energy = new CustomEnergyStorage(1000, 100, 0, 0);
    private final Lazy<CustomEnergyStorage> energyHandler = Lazy.of(() -> this.energy);

    private int burnTime = 0, maxBurnTime = 0;

    public final ContainerData data;

    public Lazy<CustomEnergyStorage> getEnergyOptional() {
        return this.energyHandler;
    }

    public CustomEnergyStorage getEnergy() {
        return this.energy;
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        this.energyHandler.invalidate();
    }

    public TungstenCompreserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TUNGSTEN_COMPRESER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> TungstenCompreserBlockEntity.this.progress;
                    case 1 -> TungstenCompreserBlockEntity.this.maxProgress;
                    case 2 -> TungstenCompreserBlockEntity.this.energy.getEnergyStored();
                    case 3 -> TungstenCompreserBlockEntity.this.energy.getMaxEnergyStored();
                    case 4 -> TungstenCompreserBlockEntity.this.burnTime;
                    case 5 -> TungstenCompreserBlockEntity.this.maxBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> TungstenCompreserBlockEntity.this.progress = pValue;
                    case 1 -> TungstenCompreserBlockEntity.this.maxProgress = pValue;
                    case 2 -> TungstenCompreserBlockEntity.this.energy.setEnergy(pValue);
                    case 4 -> TungstenCompreserBlockEntity.this.burnTime = pValue;
                    case 5 -> TungstenCompreserBlockEntity.this.maxBurnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), TungstenCompreserBlock.UPDATE_ALL);
        }
    }

    public void tick(BlockPos pos, BlockState state, TungstenCompreserBlockEntity blockEntity) {
        boolean isExtended = state.getValue(EXTENDED);
        boolean isLit = state.getValue(LIT);

        boolean shouldBeExtended = TungstenCompreserBlock.arePedestalPositionsTungsten(level, pos, state);
        if (shouldBeExtended != isExtended) {
            level.setBlockAndUpdate(pos, state.setValue(EXTENDED, shouldBeExtended));
        }

        if (hasRecipe() && state.getValue(EXTENDED)) {
            if (hasRecipe() && state.getValue(EXTENDED)) {
                increaseCraftingProcess();

                if (!isLit) {
                    level.setBlockAndUpdate(pos, state.setValue(LIT, true));
                }

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            }
        } else if (!hasRecipe()){
            blockEntity.progress = Math.max(blockEntity.progress - 2, 0);

            if (isLit) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            }
        }

        if (this.energy.getEnergyStored() < this.energy.getMaxEnergyStored()) {
            if (this.burnTime <= 0) {
                if (canBurn(this.inputItems.getStackInSlot(3))) {
                    this.burnTime = this.maxBurnTime = getBurnTime(this.inputItems.getStackInSlot(3));
                    this.inputItems.getStackInSlot(3).shrink(1);
                    setChanged();
                }
            } else {
                this.burnTime--;
                this.energy.addEnergy(1);
                setChanged();
            }
        }
    }

    public int getBurnTime(ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING);
    }

    public boolean canBurn(ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<TungstenCompreserRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.TUNGSTEN_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
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
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.getMaxStackSize() > outputStack.getCount();
    }

    private boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.isEmpty() || (outputStack.getItem() == stack.getItem() && outputStack.getCount() < stack.getMaxStackSize());
    }

    private void craftItem() {

        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        RecipeType<TungstenCompreserRecipe> recipeType = ModRecipes.TUNGSTEN_RECIPE_TYPE.get();

        Optional<RecipeHolder<TungstenCompreserRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(recipeType, getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            TungstenCompreserRecipe recipe = recipeOptional.get().value();
            ItemStack outputStack = recipe.getResultItem(level.registryAccess());
            ItemStack existingOutput = outputItems.getStackInSlot(0);

            int maxStackSize = outputStack.getMaxStackSize();
            int availableSpace = maxStackSize - existingOutput.getCount();
            int amountToAdd = Math.min(availableSpace, outputStack.getCount());

            if (existingOutput.isEmpty()) {
                outputItems.setStackInSlot(0, new ItemStack(outputStack.getItem(), amountToAdd));
            } else if (existingOutput.getItem() == outputStack.getItem()) {
                existingOutput.grow(amountToAdd);
            }

            if (recipe.ingredient0.test(inventory.getItem(0))) {
                inputItems.extractItem(0, 1, false);
            }
            if (recipe.ingredient1.isPresent() && recipe.ingredient1.get().test(inventory.getItem(1))) {
                inputItems.extractItem(1, 1, false);
            }
            if (recipe.ingredient2.isPresent() && recipe.ingredient2.get().test(inventory.getItem(2))) {
                inputItems.extractItem(2, 1, false);
            }
            outputStack.shrink(amountToAdd);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("my_block_entity.progress", this.progress);
        tag.putInt("my_block_entity.max_progress", this.maxProgress);
        tag.put("my_block_entity.inputs", inputItems.serializeNBT(registries));
        tag.put("my_block_entity.outputs", outputItems.serializeNBT(registries));

        tag.put("my_block_entity.Energy", this.energy.serializeNBT(registries));

        tag.putInt("my_block_entity.burntime", this.burnTime);
        tag.putInt("my_block_entity.maxburntime", this.maxBurnTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("my_block_entity.progress");
        maxProgress = tag.getInt("my_block_entity.max_progress");

        inputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.inputs"));
        outputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.outputs"));

        if (tag.contains("my_block_entity.energy", Tag.TAG_COMPOUND)) {
            this.energy.deserializeNBT(registries, tag.getCompound("my_block_entity.energy"));
        }

        if (tag.contains("my_block_entity.burntime", Tag.TAG_COMPOUND)) {
            this.burnTime = tag.getInt("my_block_entity.burntime");
        }
        if (tag.contains("my_block_entity.maxburntime", Tag.TAG_COMPOUND)) {
            this.burnTime = tag.getInt("my_block_entity.maxburntime");
        }
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

    @Nullable
    @Override
    public @javax.annotation.Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TungstenCompreserMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.Tungsten_compreser");
    }

    public CustomEnergyStorage getEnergyStorage() {
        return this.energyHandler.get();
    }
}