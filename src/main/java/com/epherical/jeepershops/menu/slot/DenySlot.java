package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.ShopBack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class DenySlot<T extends ShopBack> extends NoOpSlot {

    private final T menu;

    public DenySlot(Container container, int i, int j, int k, T menu) {
        super(container, i, j, k);
        this.menu = menu;
    }


    @Override
    public boolean mayPickup(Player player) {
        menu.getShop().openShop((ServerPlayer) player);
        return super.mayPickup(player);
    }
}
