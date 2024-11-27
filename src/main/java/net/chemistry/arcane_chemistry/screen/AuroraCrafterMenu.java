package net.chemistry.arcane_chemistry.screen;

import net.chemistry.arcane_chemistry.block.ModBlocks;
import net.chemistry.arcane_chemistry.block.entity.custom.AuroraCrafterBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.AuroraCrafterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AuroraCrafterMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final AuroraCrafterBlockEntity blockentity;
    public AuroraCrafterMenu(int containerId, Player player, BlockPos pos) {
        super(ModMenuTypes.AURORA_CRAFTER_MENU.get(), containerId);
        AuroraCrafterBlockEntity AuroraCrafterBlockEntity;
        this.pos = pos;

        if (player.level().getBlockEntity(pos) instanceof AuroraCrafterBlockEntity blockentity) {
            AuroraCrafterBlockEntity = blockentity;
            this.addDataSlots(blockentity.data);

            addSlot(new SlotItemHandler(blockentity.getInputItems(), 0, 30, 17));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 1, 48, 17));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 2, 66, 17));

            addSlot(new SlotItemHandler(blockentity.getInputItems(), 3, 30, 35));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 4, 48, 35));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 5, 66, 35));

            addSlot(new SlotItemHandler(blockentity.getInputItems(), 6, 30, 53));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 7, 48, 53));
            addSlot(new SlotItemHandler(blockentity.getInputItems(), 8, 66, 53));

            addSlot(new SlotItemHandler(blockentity.getOutputItems(), 0, 124, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });

        } else {
            AuroraCrafterBlockEntity = null;
            System.err.println("Invalid block entity at position: " + pos);
        }

        addPlayerInventory(player.getInventory());
        addPlayerHotbar(player.getInventory());

        this.blockentity = AuroraCrafterBlockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockentity.getLevel(), blockentity.getBlockPos()), player, ModBlocks.AURORA_CRAFTER.get());
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
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
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