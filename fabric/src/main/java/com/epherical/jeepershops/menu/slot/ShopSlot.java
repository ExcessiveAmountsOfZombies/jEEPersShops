package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.ShopMenu;
import com.epherical.jeepershops.shop.Shop;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ShopSlot extends NoOpSlot {

    // We pass the ShopMenu instead of the Shop because slots are created before the ShopMenu, so we have no access to the
    // actual Shop object.
    private final ShopMenu shop;
    public ShopSlot(Container container, int i, int j, int k, ShopMenu shop) {
        super(container, i, j, k);
        this.shop = shop;
    }

    @Override
    public boolean mayPickup(Player player) {
        shop.getShop().confirmPurchaseMenu((ServerPlayer) player, this.getContainerSlot());
        setChanged();
        return false;
    }

    @Override
    public Optional<ItemStack> tryRemove(int i, int j, Player player) {
        return super.tryRemove(i, j, player);
    }
}
