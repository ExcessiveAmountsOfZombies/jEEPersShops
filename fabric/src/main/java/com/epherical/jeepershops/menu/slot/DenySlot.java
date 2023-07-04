package com.epherical.jeepershops.menu.slot;

import com.epherical.jeepershops.menu.ConfirmPurchaseMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class DenySlot extends NoOpSlot {

    private ConfirmPurchaseMenu menu;

    public DenySlot(Container container, int i, int j, int k, ConfirmPurchaseMenu menu) {
        super(container, i, j, k);
        this.menu = menu;
    }


    @Override
    public boolean mayPickup(Player player) {
        System.out.println("OWWW");
        return super.mayPickup(player);
    }
}
