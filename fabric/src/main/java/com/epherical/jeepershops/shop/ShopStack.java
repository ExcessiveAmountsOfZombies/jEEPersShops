package com.epherical.jeepershops.shop;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ShopStack {


    private ItemStack itemStack;
    private double price;


    public ShopStack(ItemStack item, double price) {
        this.itemStack = item;
        this.price = price;
    }


    public ItemStack displayStack() {
        ItemStack copy = itemStack.copy();
        CompoundTag tag = copy.getOrCreateTag();
        CompoundTag displayTag = new CompoundTag();
        ListTag loreTag = new ListTag();
        loreTag.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal("Price: ").append(String.valueOf(price)))));

        displayTag.put("Lore", loreTag);
        tag.put("display", displayTag);
        copy.setTag(tag);
        return copy;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getPrice() {
        return price;
    }

    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    public boolean attemptPurchase(ServerPlayer player) {
        return player.getInventory().add(itemStack.copy());
    }
}
