package net.chemistry.arcane_chemistry.screen;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.FlotationerBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.FlotationerBlockEntity;
import net.chemistry.arcane_chemistry.screen.slot.FluidContainerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FlotationerMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    public final FlotationerBlockEntity blockentity;
    protected int waterMillibuckets;

    public FlotationerMenu(int containerId, Player player, BlockPos pos) {
        super(ModMenuTypes.FLOTATIONER_MENU.get(), containerId);
        FlotationerBlockEntity FlotationerBlockEntity;
        this.pos = pos;

        if (player.level().getBlockEntity(pos) instanceof FlotationerBlockEntity blockentity) {
            FlotationerBlockEntity = blockentity;
            this.addDataSlots(blockentity.data);

            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return FlotationerMenu.this.blockentity.getFluidTankAmount();
                }

                @Override
                public void set(int amount) {
                    FlotationerMenu.this.waterMillibuckets = amount;
                }
            });

            addSlot(new SlotItemHandler(blockentity.getInputItems(), 0, 45, 29));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 1, 45, 53));
            addSlot(new FluidContainerSlot(blockentity.getInputItems(), 2, 149, 54));
            addSlot(new SlotItemHandler(blockentity.getOutputItems(), 0, 114, 41) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            addSlot(new SlotItemHandler(blockentity.getOutputItems(), 1, 90, 41) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });

        } else {
            FlotationerBlockEntity = null;
            System.err.println("Invalid block entity at position: " + pos);
        }

        addPlayerInventory(player.getInventory());
        addPlayerHotbar(player.getInventory());

        this.blockentity = FlotationerBlockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockentity.getLevel(), blockentity.getBlockPos()), player, ModBlocks.FLOTATIONER.get());
    }

    public int getFluidAmount() {
        return blockentity.getFluidTankAmount();
    }

    public int getMaxFluidAmount() {
        return blockentity.getFluidTankCapacity();
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    public boolean isCrafting() {
        return blockentity.getProgress() > 0;
    }

    public int getScaledProgress() {
        int progress = blockentity.data.get(0);
        int maxProgress = blockentity.data.get(1);
        int progressArrowSize = 22;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}