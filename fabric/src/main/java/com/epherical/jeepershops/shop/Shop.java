package com.epherical.jeepershops.shop;

import com.epherical.jeepershops.BozoFabric;
import com.epherical.jeepershops.ShopManager;
import com.epherical.jeepershops.menu.ShopMenu;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class Shop {

    private UUID owner;
    private String username;

    private SimpleMenuProvider provider;
    private ShopMenu menu;
    private Container container;


    public Shop(UUID owner, String username) {
        this.owner = owner;
        this.username = username;
        MutableComponent empty = Component.literal("Shop Owned By: ").append(username);
        this.provider = new SimpleMenuProvider((i, inventory, player) ->
                this.menu = new ShopMenu(MenuType.GENERIC_9x3, i, inventory, 3), empty);
    }



    public void openShop(ServerPlayer player) {
        player.openMenu(provider);
    }

    public void addItem(ItemStack itemStack) {
        // TODO; add logic
        saveFile(this);
    }

    public void load(CompoundTag tag) {
        // TODO; this is going to be null, the menu is only ever created once the player has opened the menu
        //  for the first time
        ContainerHelper.loadAllItems(tag, menu.getItems());
    }

    public void saveToTag(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, menu.getItems());
    }

    public static void saveFile(Shop shop) {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("uuid", shop.getOwner());
        tag.putString("name", shop.getUsername());
        shop.saveToTag(tag);
        BozoFabric.instance.getManager().saveShopToFile(shop, tag);
    }

    public static Shop loadShop(CompoundTag tag) {
        UUID uuid = tag.getUUID("uuid");
        String username = tag.getString("name");
        Shop shop = new Shop(uuid, username);
        shop.load(tag);

        return shop;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getUsername() {
        return username;
    }
}
