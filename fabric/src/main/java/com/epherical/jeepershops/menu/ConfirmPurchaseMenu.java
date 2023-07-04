package com.epherical.jeepershops.menu;

import com.epherical.jeepershops.menu.slot.ConfirmSlot;
import com.epherical.jeepershops.menu.slot.DenySlot;
import com.epherical.jeepershops.menu.slot.NoOpSlot;
import com.epherical.jeepershops.shop.Shop;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class ConfirmPurchaseMenu extends ExpandedMenu {

    private final Shop shop;

    // 0 1 2 3 -4- 5 6 7 8
    public ConfirmPurchaseMenu(MenuType<ChestMenu> menuType, int containerId, Inventory inventory, int rows, Shop shop) {
        super(menuType, containerId, inventory, rows);
        this.shop = shop;
    }

    @Override
    public SlotBuilder createSlot() {
        return (container, slotID, xPos, yPos) -> {
            if (slotID >= 0 && slotID < 4) {
                return new ConfirmSlot(container, slotID, xPos, yPos);
            } else if (slotID == 4) {
                return new NoOpSlot(container, slotID, xPos, yPos);
            } else {
                return new DenySlot(container, slotID, xPos, yPos, this);
            }
        };
    }
}