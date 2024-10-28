package net.chemistry.arcane_chemistry.block.entity.custom;

import com.google.gson.internal.bind.JsonTreeReader;
import net.chemistry.arcane_chemistry.block.custom.LatexBowlBlock;
import net.chemistry.arcane_chemistry.block.custom.LatexBowlBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.CustomItemHandler;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.item.ModItems;
import net.chemistry.arcane_chemistry.recipes.CentrifugeRecipe;
import net.chemistry.arcane_chemistry.recipes.LatexBowlRecipe;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class LatexBowlBlockEntity extends BlockEntity {

    private final ItemStackHandler inputItems = createItemHandler(1);
    private final ItemStackHandler outputItems = createItemHandler(1);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private float rotation;
    private int progress = 0;
    private int maxProgress = 2000;

    public final ContainerData data;
    private Random random;

    public LatexBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LATEX_BOWL_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> LatexBowlBlockEntity.this.progress;
                    case 1 -> LatexBowlBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> LatexBowlBlockEntity.this.progress = pValue;
                    case 1 -> LatexBowlBlockEntity.this.maxProgress = pValue;
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

    public void tick(BlockPos pos, BlockState state, LatexBowlBlockEntity blockEntity) {
        boolean hasLatexOutput = blockEntity.outputItems.getStackInSlot(0).getItem() == ModItems.LATEX_BALL.get();
        assert level != null;
        BlockState oldState = level.getBlockState(pos);

        BlockPos northPos = pos.north();
        BlockPos southPos = pos.south();
        BlockPos eastPos = pos.east();
        BlockPos westPos = pos.west();
        BlockState northBlockState = level.getBlockState(northPos);
        BlockState southBlockState = level.getBlockState(southPos);
        BlockState eastBlockState = level.getBlockState(eastPos);
        BlockState westBlockState = level.getBlockState(westPos);
        ItemStack northBlockItemStack = new ItemStack(northBlockState.getBlock().asItem());
        ItemStack southBlockItemStack = new ItemStack(southBlockState.getBlock().asItem());
        ItemStack eastBlockItemStack = new ItemStack(eastBlockState.getBlock().asItem());
        ItemStack westBlockItemStack = new ItemStack(westBlockState.getBlock().asItem());
        boolean isNorthBlockInRecipe = blockIsInRecipe(northBlockItemStack);
        boolean isSouthBlockInRecipe = blockIsInRecipe(southBlockItemStack);
        boolean isEastBlockInRecipe = blockIsInRecipe(eastBlockItemStack);
        boolean isWestBlockInRecipe = blockIsInRecipe(westBlockItemStack);
        boolean isAnyBlockInRecipe = false;
        isAnyBlockInRecipe = blockIsInRecipe(northBlockItemStack) || blockIsInRecipe(southBlockItemStack) || blockIsInRecipe(eastBlockItemStack) || blockIsInRecipe(westBlockItemStack);

        BlockState LATEXfalse= oldState.setValue(LatexBowlBlock.LATEX, false);
        BlockState LATEXtrue= oldState.setValue(LatexBowlBlock.LATEX, true);
        BlockState LITfalse= oldState.setValue(LatexBowlBlock.LIT, false);
        BlockState LITtrue= oldState.setValue(LatexBowlBlock.LIT, true);
        BlockState newStateSOUTH = oldState.setValue(LatexBowlBlock.FACING, Direction.SOUTH);
        BlockState newStateNORTH = oldState.setValue(LatexBowlBlock.FACING, Direction.NORTH);
        BlockState newStateWEST = oldState.setValue(LatexBowlBlock.FACING, Direction.WEST);
        BlockState newStateEAST= oldState.setValue(LatexBowlBlock.FACING, Direction.EAST);

        ItemStack existingInput = inputItems.getStackInSlot(0);
        this.random = new Random();

        if (isAnyBlockInRecipe) {
            increaseCraftingProcess();
            if (hasProgressFinished()) {
                if (isNorthBlockInRecipe) {
                    if (existingInput.isEmpty()) {
                        craftItem(0, northBlockItemStack);
                    }
                } else if (isSouthBlockInRecipe) {
                    if (existingInput.isEmpty()) {
                        craftItem(0, southBlockItemStack);
                    }
                } else if (isEastBlockInRecipe) {
                    if (existingInput.isEmpty()) {
                        craftItem(0, eastBlockItemStack);
                    }
                } else if (isWestBlockInRecipe) {
                    if (existingInput.isEmpty()) {
                        craftItem(0, westBlockItemStack);
                    }
                }
                resetProgress();
            }
        } else {
            blockEntity.progress = Math.max(blockEntity.progress - 2, 0);
        }

        BlockState newBlockState = level.getBlockState(pos);

        if (isNorthBlockInRecipe) {
            newBlockState = newStateSOUTH;
        } else if (isSouthBlockInRecipe) {
            newBlockState = newStateNORTH;
        } else if (isEastBlockInRecipe) {
            newBlockState = newStateWEST;
        } else if (isWestBlockInRecipe) {
            newBlockState = newStateEAST;
        }

        newBlockState = newBlockState.setValue(LatexBowlBlock.LATEX, hasLatexOutput);

        newBlockState = newBlockState.setValue(LatexBowlBlock.LIT, progress > 0);

        level.setBlockAndUpdate(pos, newBlockState);
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

    private boolean blockIsInRecipe(ItemStack block) {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(block);
        inventory.setItem(0, block);

        Optional<RecipeHolder<LatexBowlRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.LATEX_BOWL_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().value().output.copy().getItem().getDefaultInstance());
    }

    private void craftItem(int slotIndex, ItemStack block) {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(block);
        inventory.setItem(0, block);

        Optional<RecipeHolder<LatexBowlRecipe>> recipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.LATEX_BOWL_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (recipeOptional.isPresent()) {
            LatexBowlRecipe recipe = recipeOptional.get().value();
            ItemStack outputStack = recipe.getResultItem(level.registryAccess());
            ItemStack existingOutput = outputItems.getStackInSlot(0);

            int maxStackSize = outputStack.getMaxStackSize();
            int availableSpace = maxStackSize - existingOutput.getCount();
            int amountToAdd = Math.min(availableSpace, outputStack.getCount());

            if (existingOutput.isEmpty()) {
                outputItems.setStackInSlot(0, new ItemStack(outputStack.getItem(), amountToAdd));
            } else if (existingOutput.getItem() == outputStack.getItem()) {
                if (existingOutput.getCount() <= 4) {
                    existingOutput.grow(amountToAdd);
                }
            }

            inputItems.extractItem(slotIndex, 1, false);
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

    private boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.getMaxStackSize() > outputStack.getCount();
    }

    private boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        ItemStack outputStack = outputItems.getStackInSlot(0);
        return outputStack.isEmpty() || (outputStack.getItem() == stack.getItem() && outputStack.getCount() < stack.getMaxStackSize());
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

    public boolean isInputEmpty(int slot) {
        return inputItems.getStackInSlot(slot).isEmpty();
    }

    public boolean isOutputEmpty(int slot) {
        return outputItems.getStackInSlot(slot).isEmpty();
    }

    public void clearInput(int slot) {
        inputItems.setStackInSlot(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void clearOutput(int slot) {
        outputItems.setStackInSlot(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public Lazy<IItemHandler> getItems() {
        return itemHandler;
    }

    public ItemStack getItem(int pSlot) {
        if (pSlot < 1) {
            return inputItems.getStackInSlot(pSlot);
        } else {
            return outputItems.getStackInSlot(pSlot - 2);
        }
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 1) {
            inputItems.setStackInSlot(slot, stack);
        } else {
            outputItems.setStackInSlot(slot - 2, stack);
        }
        setChanged();
        if (!level.isClientSide) {
            markForUpdate();
        }
    }

    private void markForUpdate() {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}