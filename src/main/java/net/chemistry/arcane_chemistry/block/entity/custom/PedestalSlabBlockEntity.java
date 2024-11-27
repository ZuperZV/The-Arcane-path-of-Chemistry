package net.chemistry.arcane_chemistry.block.entity.custom;

import net.chemistry.arcane_chemistry.block.custom.SlabBlock;
import net.chemistry.arcane_chemistry.block.entity.ItemHandler.ContainerRecipeInput;
import net.chemistry.arcane_chemistry.block.entity.ModBlockEntities;
import net.chemistry.arcane_chemistry.recipes.ModRecipes;
import net.chemistry.arcane_chemistry.recipes.PedestalSlabAuroraRecipe;
import net.chemistry.arcane_chemistry.recipes.PedestalSlabClayRecipe;
import net.chemistry.arcane_chemistry.recipes.PedestalSlabRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PedestalSlabBlockEntity extends BlockEntity implements Container, WorldlyContainer {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private float rotation;
    private int progessTickCounter = 0;

    public PedestalSlabBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SLAB_BE.get(), pPos, pBlockState);
    }

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    public static void tick(Level level, BlockPos pos, PedestalSlabBlockEntity pedestal) {
        if (level.isClientSide) {
            return;
        }

        ItemStack inputStack = pedestal.getItem(0);

        if (inputStack.isEmpty()) {
            return;
        }
        boolean hasAuroraWire = SlabBlock.arePedestalPositionsAuroraWire(level, pos);
        boolean hasClayWire = SlabBlock.arePedestalPositionsClayWire(level, pos);

        SimpleContainer container = new SimpleContainer(1);
        container.setItem(0, inputStack);
        RecipeInput recipeInput = new ContainerRecipeInput(container);

        Optional<RecipeHolder<PedestalSlabAuroraRecipe>> pedestalSlabAuroraRecipeOptional = Optional.empty();
        Optional<RecipeHolder<PedestalSlabRecipe>> pedestalSlabRecipeOptional;
        Optional<RecipeHolder<PedestalSlabClayRecipe>> pedestalSlabClayRecipeOptional = Optional.empty();

        if (hasAuroraWire) {
            pedestalSlabAuroraRecipeOptional = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.PEDESTAL_SLAB_AURORA_RECIPE_TYPE.get(), recipeInput, level);
            pedestalSlabClayRecipeOptional = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.PEDESTAL_SLAB_CLAY_RECIPE_TYPE.get(), recipeInput, level);
        }
        if (hasClayWire) {
            pedestalSlabClayRecipeOptional = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.PEDESTAL_SLAB_CLAY_RECIPE_TYPE.get(), recipeInput, level);
        }
            pedestalSlabRecipeOptional = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.PEDESTAL_SLAB_RECIPE_TYPE.get(), recipeInput, level);

        Recipe<?> recipe = null;
        if (pedestalSlabAuroraRecipeOptional.isPresent()) {
            recipe = pedestalSlabAuroraRecipeOptional.get().value();

        } else if (pedestalSlabRecipeOptional.isPresent()) {
            recipe = pedestalSlabRecipeOptional.get().value();

        } else if (pedestalSlabClayRecipeOptional.isPresent()) {
            recipe = pedestalSlabClayRecipeOptional.get().value();
        }

        if (recipe != null) {
            ItemStack outputStack = recipe.getResultItem(level.registryAccess());

            pedestal.progessTickCounter++;
            if (pedestal.progessTickCounter >= 50) {
                pedestal.setItem(0, outputStack);
                pedestal.progessTickCounter = 0;
            }

            pedestal.setChanged();
        }
    }


    @Override
    public ItemStack getItem(int pSlot) {
        return inventory.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack stack = inventory.get(pSlot);
        if (stack.getCount() <= pAmount) {
            inventory.set(pSlot, ItemStack.EMPTY);
            return stack;
        } else {
            stack = stack.split(pAmount);
            inventory.set(pSlot, stack);
            return stack;
        }
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        ItemStack stack = inventory.get(pSlot);
        inventory.set(pSlot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        setChanged();
        if (pStack.isEmpty()) {
            inventory.set(pSlot, ItemStack.EMPTY);
        } else {
            inventory.set(pSlot, pStack.copy());
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        inventory.clear();
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        ContainerHelper.saveAllItems(pTag, inventory, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        ContainerHelper.loadAllItems(pTag, inventory, pRegistries);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    public NonNullList<ItemStack> getInputItems() {
        return inventory;
    }

    public NonNullList<ItemStack> getOutputItems() {
        return inventory;
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
}
