package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.FirePotCampfireBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.FirePotRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;

import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.Optional;

public class FirePotCampfireBlockEntity extends BlockEntity {

    private final ItemStackHandler inputItems = createItemHandler(1);
    private final FluidTank inputFluidTank = createFluidHandler(1000);
    private final FluidTank outputFluidTank = createFluidHandler(1000);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));

    public FirePotCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLOTATIONER_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FirePotCampfireBlockEntity blockEntity) {
        if (blockEntity.hasRecipe() && blockEntity.hasEnoughFluid()) {
            blockEntity.craftItemAndFluid();
            level.setBlockAndUpdate(pos, state.setValue(FirePotCampfireBlock.LIT, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(FirePotCampfireBlock.LIT, false));
        }
    }

    public void insertItem(ItemStack stack) {
        if (stack.isEmpty()) return;

        ItemStack remaining = inputItems.insertItem(0, stack, false);
        if (remaining.isEmpty()) {
            this.markUpdated();
        }
    }

    public void insertFluid(FlowingFluid fluid) {
        int amountToInsert = 1000;

        FluidStack fluidStack = new FluidStack(fluid, amountToInsert);

        int filledAmount = inputFluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

        if (filledAmount > 0) {
            this.markUpdated();
        }
    }

    public FluidStack extractFluid() {
        return outputFluidTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
    }

    public void insertFluid(FluidStack fluid) {
        outputFluidTank.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
    }

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<FirePotRecipe>> alcheRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FIRE_POT_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        return alcheRecipe.isPresent();
    }

    private boolean hasEnoughFluid() {
        return inputFluidTank.getFluidAmount() >= 1000;
    }

    private void craftItemAndFluid() {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<FirePotRecipe>> alcheRecipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.FIRE_POT_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (alcheRecipeOptional.isPresent()) {
            FirePotRecipe recipe = alcheRecipeOptional.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());

            inputFluidTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            outputFluidTank.fill(recipe.getFluidOutput(), IFluidHandler.FluidAction.EXECUTE);

            inputItems.extractItem(0, 1, false); // Extract the input item
        }
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

    private FluidTank createFluidHandler(int capacity) {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void dowse() {
        if (this.level != null) {
            this.markUpdated();
        }
    }
}
