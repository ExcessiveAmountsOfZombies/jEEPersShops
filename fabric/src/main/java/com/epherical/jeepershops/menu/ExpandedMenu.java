package com.epherical.jeepershops.menu;

import com.epherical.jeepershops.mixin.ACMAccessor;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public abstract class ExpandedMenu extends ChestMenu {

    public ExpandedMenu(MenuType<ChestMenu> menuType, int containerId, Inventory inventory, int rows) {
        super(menuType, containerId, inventory, new SimpleContainer(9 * rows), rows);

        ACMAccessor acmAccessor = (ACMAccessor) this;
        acmAccessor.remoteSlots().clear();
        acmAccessor.lastSlots().clear();
        this.slots.clear();
        int playerInvModifier = (getRowCount() - 4) * 18;

        // real deal
        for(int row = 0; row < getRowCount(); ++row) {
            for(int slot = 0; slot < 9; ++slot) {
                this.addSlot(createSlot().buildSlot(getContainer(), slot + row * 9, 8 + slot * 18, 18 + row * 18));
            }
        }

        // inventory
        for(int row = 0; row < 3; ++row) {
            for(int slot = 0; slot < 9; ++slot) {
                this.addSlot(createSlot().buildSlot(inventory, slot + row * 9 + 9, 8 + slot * 18, 103 + row * 18 + playerInvModifier));
            }
        }

        // hotbar
        for(int slot = 0; slot < 9; ++slot) {
            this.addSlot(createSlot().buildSlot(inventory, slot, 8 + slot * 18, 161 + playerInvModifier));
        }
    }

    public abstract SlotBuilder createSlot();


    public interface SlotBuilder {
        Slot buildSlot(Container container, int slotID, int xPos, int yPos);
    }

}
