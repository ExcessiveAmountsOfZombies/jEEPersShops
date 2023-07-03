package com.epherical.jeepershops;

import com.epherical.jeepershops.shop.Shop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShopManager {

    private final ShopStorage storage;
    private final Map<String, Shop> shopByUserName;
    private final Map<UUID, Shop> shopByUUID;


    public ShopManager(ShopStorage storage) {
        this.storage = storage;
        this.shopByUserName = new HashMap<>();
        this.shopByUUID = new HashMap<>();
    }

    public Shop getOrCreateShop(UUID uuid, String userName) {
        return shopByUUID.getOrDefault(uuid, createShop(uuid, userName));
    }

    public Shop getShop(String userName) {
        return shopByUserName.get(userName);
    }

    public Shop createShop(UUID uuid, String userName) {
        Shop shop;
        try {
            Tag tag = storage.readTagFromFile(storage.resolve(uuid));
            shop = Shop.loadShop((CompoundTag) tag);
        } catch (IOException ignored) {
            shop = new Shop(uuid, userName);
        }

        shopByUUID.put(uuid, shop);
        shopByUserName.put(userName, shop);
        return shop;
    }

    public void saveShopToFile(Shop shop, CompoundTag tag) {
        try {
            storage.writeTagToFile(tag, storage.resolve(shop.getOwner()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
