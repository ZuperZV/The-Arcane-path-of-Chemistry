package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.FlotationerRecipe;
import net.chemistry.arcane_chemistry.recipes.FluidRecipeInput;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.FlotationerRecipe;
import net.chemistry.arcane_chemistry.screen.FlotationerMenu;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.chemistry.arcane_chemistry.block.custom.FlotationerBlock.LIT;

public class FlotationerBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler inputItems = createItemHandler(4);
    private final ItemStackHandler outputItems = createItemHandler(1);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private int progress = 0;
    private int maxProgress = 500;

    public final ContainerData data;
    private FluidStack neededFluidStack = FluidStack.EMPTY;

    private final FluidTank tank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    public FlotationerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLOTATIONER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FlotationerBlockEntity.this.progress;
                    case 1 -> FlotationerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FlotationerBlockEntity.this.progress = pValue;
                    case 1 -> FlotationerBlockEntity.this.maxProgress = pValue;
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

    public void tick(BlockPos pos, BlockState state, FlotationerBlockEntity blockEntity) {
        boolean isLit = state.getValue(LIT);

        if (!inputItems.getStackInSlot(3).isEmpty() && inputItems.getStackInSlot(3).getItem() == Items.WATER_BUCKET) {
            if (tank.getFluidAmount() < tank.getCapacity()) {
                int filled = tank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
                inputItems.setStackInSlot(3, new ItemStack(Items.BUCKET));
                System.out.println("Amount of water filled: " + filled);
                System.out.println("Fluid in tank after fill: " + tank.getFluid());
            }
        }



        if (hasRecipe()) {
            increaseCraftingProcess();

            if (!isLit) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, true));
            }

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();

            if (isLit) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            }
        }
    }

    public FluidStack getFluid() {
        return tank.getFluid();
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

        if (tank.getFluidAmount() < 1000) {
            return false;
        }

        Optional<RecipeHolder<FlotationerRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FLOTATION_RECIPE_TYPE.get(), getRecipeInput(inventory, fluidInTank), level);


        System.out.println("Fluid in tank: " + fluidInTank);
        System.out.println("Items in inventory: " + inventory.getItem(0) + ", " + inventory.getItem(1));

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
    }

    FluidStack fluidInTank = tank.getFluid();

    private FluidRecipeInput getRecipeInput(SimpleContainer inventory, FluidStack fluidStack) {
        System.out.println("Fluid being passed to recipe: " + fluidStack);
        return new FluidRecipeInput(fluidStack) {
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

        Optional<RecipeHolder<FlotationerRecipe>> alcheRecipeOptionalrecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FLOTATION_RECIPE_TYPE.get(), getRecipeInput(inventory, fluidInTank), level);


        FlotationerRecipe recipe;
        if (alcheRecipeOptionalrecipe.isPresent()) {
            recipe = alcheRecipeOptionalrecipe.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());

            ItemStack outputStack = outputItems.getStackInSlot(0);
            if (outputStack.isEmpty()) {
                outputItems.setStackInSlot(0, result.copy());
            } else if (outputStack.getItem() == result.getItem()) {
                outputStack.grow(result.getCount());
            }

            inputItems.extractItem(0, 1, false);
            inputItems.extractItem(1, 1, false);
            inputItems.extractItem(2, 1, false);
            inputItems.extractItem(3, 1, false);

            tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        }

    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("my_block_entity.progress", this.progress);
        tag.putInt("my_block_entity.max_progress", this.maxProgress);CompoundTag water = new CompoundTag();
        getTank().writeToNBT(registries, water);
        tag.put("Tank", water);
        tag.put("my_block_entity.inputs", inputItems.serializeNBT(registries));
        tag.put("my_block_entity.outputs", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("my_block_entity.progress");
        maxProgress = tag.getInt("my_block_entity.max_progress");
        if (tag.contains("Tank"))
            getTank().readFromNBT(registries, tag.getCompound("Tank"));
        inputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.inputs"));
        outputItems.deserializeNBT(registries, tag.getCompound("my_block_entity.outputs"));
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (slot == 4) {
                    if (!inputItems.getStackInSlot(slot).isEmpty() &&
                            inputItems.getStackInSlot(slot).getItem() == Items.BUCKET &&
                            tank.getFluidAmount() < tank.getCapacity()) {

                        tank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
                        inputItems.setStackInSlot(slot, new ItemStack(Items.BUCKET, 1));
                    }
                }
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (slot == 4) {
                    return stack.getItem() == Items.WATER_BUCKET;
                }
                return super.isItemValid(slot, stack);
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
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public FluidTank getTank() {
        return tank;
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
        return new FlotationerMenu(containerId, player, this.getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.arcane_chemistry.flotationer");
    }
}