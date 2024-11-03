package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.AuroraCrafterRecipe;
import net.chemistry.arcane_chemistry.screen.AuroraCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
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

public class AuroraCrafterBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inputItems = createItemHandler(9);
    private final ItemStackHandler outputItems = createItemHandler(1);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private int progress = 0;
    private int maxProgress = 200;

    public final ContainerData data;

    public AuroraCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AURORA_CRAFTER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AuroraCrafterBlockEntity.this.progress;
                    case 1 -> AuroraCrafterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AuroraCrafterBlockEntity.this.progress = pValue;
                    case 1 -> AuroraCrafterBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
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

    private boolean hasAddedCraftItem = false;

    public void tick(BlockPos pos, BlockState state, AuroraCrafterBlockEntity blockEntity) {
        if (!hasRecipe()) {
            outputItems.extractItem(0, 1, false);
        }
        else if (hasRecipe() && outputItems.getStackInSlot(0).isEmpty()){
            if (!hasAddedCraftItem) {
                addCraftItem();
                hasAddedCraftItem = true;
            }
        }

        if (hasAddedCraftItem && outputItems.getStackInSlot(0).isEmpty()) {
            extractItems();
            hasAddedCraftItem = false;
        }
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

        Optional<RecipeHolder<AuroraCrafterRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.AURORA_CRAFTER_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

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

    private void extractItems() {
        hasAddedCraftItem = false;

        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        RecipeType<AuroraCrafterRecipe> recipeType = ModRecipes.AURORA_CRAFTER_RECIPE_TYPE.get();
        Optional<RecipeHolder<AuroraCrafterRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(recipeType, getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            AuroraCrafterRecipe recipe = recipeOptional.get().value();
            if (recipe.ingredient0.isPresent() && recipe.ingredient0.get().test(inventory.getItem(0))) {
                inputItems.extractItem(0, 1, false);
            }
            if (recipe.ingredient1.isPresent() && recipe.ingredient1.get().test(inventory.getItem(1))) {
                inputItems.extractItem(1, 1, false);
            }
            if (recipe.ingredient2.isPresent() && recipe.ingredient2.get().test(inventory.getItem(2))) {
                inputItems.extractItem(2, 1, false);
            }
            if (recipe.ingredient3.isPresent() && recipe.ingredient3.get().test(inventory.getItem(3))) {
                inputItems.extractItem(3, 1, false);
            }
            if (recipe.ingredient4.isPresent() && recipe.ingredient4.get().test(inventory.getItem(4))) {
                inputItems.extractItem(4, 1, false);
            }
            if (recipe.ingredient5.isPresent() && recipe.ingredient5.get().test(inventory.getItem(5))) {
                inputItems.extractItem(5, 1, false);
            }
            if (recipe.ingredient6.isPresent() && recipe.ingredient6.get().test(inventory.getItem(6))) {
                inputItems.extractItem(6, 1, false);
            }
            if (recipe.ingredient7.isPresent() && recipe.ingredient7.get().test(inventory.getItem(7))) {
                inputItems.extractItem(7, 1, false);
            }
            if (recipe.ingredient8.isPresent() && recipe.ingredient8.get().test(inventory.getItem(8))) {
                inputItems.extractItem(8, 1, false);
            }
        }
    }

    private void addCraftItem() {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        RecipeType<AuroraCrafterRecipe> recipeType = ModRecipes.AURORA_CRAFTER_RECIPE_TYPE.get();
        Optional<RecipeHolder<AuroraCrafterRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(recipeType, getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            ItemStack outputStackTest = outputItems.getStackInSlot(0);
            if (outputStackTest.isEmpty()) {
                AuroraCrafterRecipe recipe = recipeOptional.get().value();
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

                outputStack.shrink(amountToAdd);
            }
        }

        hasAddedCraftItem = true;
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("my_block_entity.progress", this.progress);
        tag.putInt("my_block_entity.max_progress", this.maxProgress);
        tag.put("my_block_entity.inputs", inputItems.serializeNBT(registries));
        tag.put("my_block_entity.outputs", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("my_block_entity.progress");
        maxProgress = tag.getInt("my_block_entity.max_progress");
        inputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.inputs"));
        outputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.outputs"));
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
        return new AuroraCrafterMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.nickel_compreser");
    }
}