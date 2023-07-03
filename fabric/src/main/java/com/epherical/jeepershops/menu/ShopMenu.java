package com.epherical.jeepershops.menu;

import com.epherical.jeepershops.menu.slot.ShopSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class ShopMenu extends ExpandedMenu {
    public ShopMenu(MenuType<ChestMenu> menuType, int containerId, Inventory inventory, int rows) {
        super(menuType, containerId, inventory, rows);
    }

    @Override
    public SlotBuilder createSlot() {
        return ShopSlot::new;
    }
}
