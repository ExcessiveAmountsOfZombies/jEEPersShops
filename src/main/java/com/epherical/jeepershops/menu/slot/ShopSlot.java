package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.ShopMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
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
        if (index < 27) {
            if (shop.getShop().isOwner(player.getUUID())) {
                shop.getShop().removeItemMenu((ServerPlayer) player, index);
            } else {
                shop.getShop().confirmPurchaseMenu((ServerPlayer) player, index);
            }
        }
        setChanged();
        return false;
    }

    @Override
    public Optional<ItemStack> tryRemove(int i, int j, Player player) {
        return super.tryRemove(i, j, player);
    }
}
