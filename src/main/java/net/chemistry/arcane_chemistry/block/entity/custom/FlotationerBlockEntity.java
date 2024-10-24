package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.screen.FlotationerMenu;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    private final FluidTank fluidTank = new FluidTank(5000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };
    private final Lazy<FluidTank> fluidOptional = Lazy.of(() -> this.fluidTank);

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
        /*if (hasRecipe()) {
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
         */


        ItemStack stack = this.inputItems.getStackInSlot(3);
        if(stack.isEmpty())
            return;

        if(this.fluidTank.getFluidAmount() >= this.fluidTank.getCapacity())
            return;

        IFluidHandlerItem fluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (fluidHandler != null) {
            FluidStack tankFluid = this.fluidTank.getFluid();
            FluidStack itemFluid = fluidHandler.getFluidInTank(0);

            if (!tankFluid.getFluid().isSame(itemFluid.getFluid()) && !this.fluidTank.isEmpty()) {
                return;
            }
            int amountToDrain = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
            FluidStack drainedFluid = fluidHandler.drain(amountToDrain, IFluidHandler.FluidAction.SIMULATE);

            if (drainedFluid.getAmount() > 0) {
                FluidStack filled = fluidHandler.drain(amountToDrain, IFluidHandler.FluidAction.EXECUTE);
                this.fluidTank.fill(filled, IFluidHandler.FluidAction.EXECUTE);

                if (drainedFluid.getAmount() <= amountToDrain) {
                    this.inputItems.setStackInSlot(3, fluidHandler.getContainer());
                }
            }
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

    /*private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;


        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<FlotationerRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FLOTATION_RECIPE_TYPE.get(), getRecipeInput(inventory, fluidInTank), level);


        System.out.println("Items in inventory: " + inventory.getItem(0) + ", " + inventory.getItem(1));

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
    }

    private FluidRecipeInput getRecipeInput(SimpleContainer inventory, FluidStack fluidStack) {
        System.out.println("Fluid being passed to recipe: " + fluidStack);
        return new RecipeInput(fluidStack) {
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
     */


    private boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.getMaxStackSize() > outputStack.getCount();
    }

    private boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.isEmpty() || (outputStack.getItem() == stack.getItem() && outputStack.getCount() < stack.getMaxStackSize());
    }

    /*private void craftItem() {
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
        }

    }
     */


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress", this.progress);
        tag.putInt("max_progress", this.maxProgress);

        tag.put("inputs", inputItems.serializeNBT(registries));
        tag.put("outputs", outputItems.serializeNBT(registries));

        CompoundTag fluidTankTag = new CompoundTag();
        this.fluidTank.writeToNBT(registries, fluidTankTag);
        tag.put("FluidTank", fluidTankTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("max_progress");

        inputItems.deserializeNBT(registries, tag.getCompound("inputs"));
        outputItems.deserializeNBT(registries, tag.getCompound("outputs"));

        if (tag.contains("FluidTank", Tag.TAG_COMPOUND)) {
            this.fluidTank.readFromNBT(registries, tag.getCompound("FluidTank"));
        }
    }


    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
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

    public Lazy<FluidTank> getFluidOptional() {
        return this.fluidOptional;
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }
}