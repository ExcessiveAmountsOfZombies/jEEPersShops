package com.epherical.jeepershops.menu;

import com.epherical.jeepershops.menu.slot.ShopSlot;
import com.epherical.jeepershops.shop.Shop;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class ShopMenu extends ExpandedMenu {

    private final Shop shop;
    public ShopMenu(MenuType<ChestMenu> menuType, int containerId, Inventory inventory, int rows, Shop shop) {
        super(menuType, containerId, inventory, rows);
        this.shop = shop;
    }

    @Override
    public SlotBuilder createSlot() {
        return (container, slotID, xPos, yPos) -> new ShopSlot(container, slotID, xPos, yPos, shop);
    }
}
