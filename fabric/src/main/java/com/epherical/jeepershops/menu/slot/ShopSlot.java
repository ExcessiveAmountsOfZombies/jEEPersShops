package com.epherical.jeepershops.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShopSlot extends Slot {
    public ShopSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPickup(Player player) {
        setChanged();
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        setChanged();
        return false;
    }
}
