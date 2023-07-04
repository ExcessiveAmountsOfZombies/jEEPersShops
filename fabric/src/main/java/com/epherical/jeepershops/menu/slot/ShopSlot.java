package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.shop.Shop;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ShopSlot extends Slot {

    private final Shop shop;
    public ShopSlot(Container container, int i, int j, int k, Shop shop) {
        super(container, i, j, k);
        this.shop = shop;
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

    @Override
    public Optional<ItemStack> tryRemove(int i, int j, Player player) {
        return super.tryRemove(i, j, player);
    }
}
