package com.epherical.jeepershops.shop;

import com.epherical.jeepershops.BozoFabric;
import com.epherical.jeepershops.ShopStorage;
import com.epherical.jeepershops.menu.ShopMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class Shop {

    private UUID owner;
    private String username;
    private NonNullList<ShopStack> items = NonNullList.createWithCapacity(27);


    public Shop(UUID owner, String username) {
        this.owner = owner;
        this.username = username;
    }


    public void openShop(ServerPlayer serverPlayer) {
        MutableComponent empty = Component.literal("Shop Owned By: ").append(username);
        SimpleMenuProvider provider = new SimpleMenuProvider((i, inventory, player) -> {
            ShopMenu shopMenu = new ShopMenu(MenuType.GENERIC_9x3, i, inventory, 3, this);
            int size = items.size();
            for (int item = 0; item < size; item++) {
                shopMenu.getContainer().setItem(item, items.get(item).displayStack());
            }
            return shopMenu;
        }, empty);
        serverPlayer.openMenu(provider);
    }

    public boolean addItem(ItemStack itemStack, double price) {
        if (items.size() >= 27) {
            return false;
        } else {
            ShopStack stack = new ShopStack(itemStack.copyAndClear(), price);
            items.add(stack);
            saveFile(this);
        }
        return true;
    }

    public void loadFromTag(CompoundTag tag) {
        ShopStorage.loadAllItems(tag, items);
    }

    public void saveToTag(CompoundTag tag) {
        tag.putUUID("uuid", getOwner());
        tag.putString("name", getUsername());
        ShopStorage.saveAllItems(tag, items);
    }

    public static void saveFile(Shop shop) {
        CompoundTag tag = new CompoundTag();
        shop.saveToTag(tag);
        BozoFabric.instance.getManager().saveShopToFile(shop, tag);
    }

    public static Shop loadShop(CompoundTag tag) {
        UUID uuid = tag.getUUID("uuid");
        String username = tag.getString("name");
        Shop shop = new Shop(uuid, username);
        shop.loadFromTag(tag);

        return shop;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getUsername() {
        return username;
    }
}
