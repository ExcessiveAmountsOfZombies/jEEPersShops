package com.epherical.jeepershops.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerMenu.class)
public interface ACMAccessor {

    @Accessor("remoteSlots")
    NonNullList<ItemStack> remoteSlots();

    @Accessor("lastSlots")
    NonNullList<ItemStack> lastSlots();



}
