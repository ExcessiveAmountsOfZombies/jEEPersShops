package com.epherical.jeepershops.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

public class ConfirmSlot extends NoOpSlot {
    public ConfirmSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }


    @Override
    public boolean mayPickup(Player player) {
        boolean pickup = super.mayPickup(player);

        return pickup;

    }
}
