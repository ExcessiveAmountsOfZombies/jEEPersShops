package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.RemoveItemMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class RemoveSlot extends NoOpSlot {

    private final RemoveItemMenu menu;

    public RemoveSlot(Container container, int i, int j, int k, RemoveItemMenu menu) {
        super(container, i, j, k);
        this.menu = menu;
    }


    @Override
    public boolean mayPickup(Player player) {
        menu.attemptPurchaseFromSlot((ServerPlayer) player);
        menu.getShop().openShop((ServerPlayer) player);
        return super.mayPickup(player);
    }
}
