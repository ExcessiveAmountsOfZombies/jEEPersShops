package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.ConfirmPurchaseMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class ConfirmSlot extends NoOpSlot {

    private final ConfirmPurchaseMenu menu;

    public ConfirmSlot(Container container, int i, int j, int k, ConfirmPurchaseMenu menu) {
        super(container, i, j, k);
        this.menu = menu;
    }


    @Override
    public boolean mayPickup(Player player) {
        menu.attemptPurchaseFromSlot((ServerPlayer) player, false);
        menu.getShop().openShop((ServerPlayer) player);
        return super.mayPickup(player);
    }
}
