package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.AtomicNucleusConstructorBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.AtomicNucleusConstructorRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.screen.AtomicNucleusConstructorMenu;
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

public class AtomicNucleusConstructorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inputItems = createItemHandler(11);
    private final ItemStackHandler outputItems = createItemHandler(2);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private int progress = 0;
    private int maxProgress = 300;

    public final ContainerData data;

    public AtomicNucleusConstructorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ATOMIC_NUCLEUS_CONSTRUCTOR_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AtomicNucleusConstructorBlockEntity.this.progress;
                    case 1 -> AtomicNucleusConstructorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AtomicNucleusConstructorBlockEntity.this.progress = pValue;
                    case 1 -> AtomicNucleusConstructorBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AtomicNucleusConstructorBlockEntity blockEntity) {
        boolean dirty = false;


        if (blockEntity.hasRecipe()) {
            blockEntity.progress++;
            level.setBlockAndUpdate(pos, state.setValue(AtomicNucleusConstructorBlock.LIT, true));
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

            level.setBlockAndUpdate(pos, state.setValue(AtomicNucleusConstructorBlock.LIT, false));
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

        Optional<RecipeHolder<AtomicNucleusConstructorRecipe>> alcheRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ATOMIC_NUCLEUS_CONSTRUCTOR_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

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
        ItemStack outputStack2 = outputItems.getStackInSlot(1);

        boolean canInsertInto1Slot = outputStack1.isEmpty() || (outputStack1.getCount() < outputStack1.getMaxStackSize());
        boolean canInsertInto2Slot = outputStack2.isEmpty() || (outputStack2.getCount() < outputStack2.getMaxStackSize());

        return canInsertInto1Slot && canInsertInto2Slot;
    }

    private void craftItem(AtomicNucleusConstructorBlockEntity serverLevel) {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<AtomicNucleusConstructorRecipe>> alcheRecipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ATOMIC_NUCLEUS_CONSTRUCTOR_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (alcheRecipeOptional.isPresent()) {
            AtomicNucleusConstructorRecipe recipe = alcheRecipeOptional.get().value();

            ItemStack result = recipe.getResultItem(level.registryAccess());
            ItemStack outputStack = outputItems.getStackInSlot(0);

            if (outputStack.isEmpty()) {
                outputItems.setStackInSlot(0, result.copy());
            } else if (outputStack.getItem() == result.getItem()) {
                outputStack.grow(result.getCount());
            }

            inputItems.extractItem(0, 1, false);

            recipe.ingredient1.ifPresent(ing -> inputItems.extractItem(1, calculateAmountToExtract(1, recipe), false));
            recipe.ingredient2.ifPresent(ing -> inputItems.extractItem(2, calculateAmountToExtract(2, recipe), false));
            recipe.ingredient3.ifPresent(ing -> inputItems.extractItem(3, calculateAmountToExtract(3, recipe), false));
            recipe.ingredient4.ifPresent(ing -> inputItems.extractItem(4, calculateAmountToExtract(4, recipe), false));
            recipe.ingredient5.ifPresent(ing -> inputItems.extractItem(5, calculateAmountToExtract(5, recipe), false));
            recipe.ingredient6.ifPresent(ing -> inputItems.extractItem(6, calculateAmountToExtract(6, recipe), false));
            recipe.ingredient6.ifPresent(ing -> inputItems.extractItem(7, calculateAmountToExtract(7, recipe), false));
            recipe.ingredient6.ifPresent(ing -> inputItems.extractItem(8, calculateAmountToExtract(8, recipe), false));
            recipe.ingredient6.ifPresent(ing -> inputItems.extractItem(9, calculateAmountToExtract(9, recipe), false));
            recipe.ingredient6.ifPresent(ing -> inputItems.extractItem(10, calculateAmountToExtract(10, recipe), false));
        }
    }

    public int calculateAmountToExtract(int slot, AtomicNucleusConstructorRecipe recipe) {
        int currentStack = recipe.getCountForIngredient(slot);
        int maxStackSize = inputItems.getSlotLimit(slot);
        int availableSpace = maxStackSize - currentStack;
        int amountToExtract = Math.min(availableSpace, currentStack);

        return amountToExtract;
    }


    private boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(null) > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress", this.progress);
        tag.putInt("maxProgress", this.maxProgress);

        tag.put("inputItems", inputItems.serializeNBT(registries));
        tag.put("outputItems", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getInt("progress");
        this.maxProgress = tag.getInt("maxProgress");

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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AtomicNucleusConstructorMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.atomic_nucleus_constructor");
    }

    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }
}
