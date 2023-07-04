package com.epherical.jeepershops;

import com.epherical.epherolib.data.WorldBasedStorage;
import com.epherical.jeepershops.shop.ShopStack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.util.UUID;

public class ShopStorage extends WorldBasedStorage {


    public ShopStorage(LevelResource resource, MinecraftServer server, String path) {
        super(resource, server, path);
    }

    @Override
    protected Gson buildGson(GsonBuilder builder) {
        return builder.create();
    }

    public Path resolve(UUID uuid) {
        return basePath.resolve(uuid.toString() + ".json");
    }


    public static CompoundTag saveAllItems(CompoundTag tag, NonNullList<ShopStack> list) {
        return saveAllItems(tag, list, true);
    }

    public static CompoundTag saveAllItems(CompoundTag tag, NonNullList<ShopStack> list, boolean force) {
        ListTag listOfItems = new ListTag();

        for(int i = 0; i < list.size(); ++i) {
            ShopStack itemStack = list.get(i);
            if (!itemStack.isEmpty()) {
                CompoundTag slottedItem = new CompoundTag();
                slottedItem.putByte("Slot", (byte)i);
                itemStack.getItemStack().save(slottedItem);
                slottedItem.putDouble("price", itemStack.getPrice());
                listOfItems.add(slottedItem);
            }
        }

        if (!listOfItems.isEmpty() || force) {
            tag.put("Items", listOfItems);
        }

        return tag;
    }

    public static void loadAllItems(CompoundTag compoundTag, NonNullList<ShopStack> nonNullList) {
        ListTag items = compoundTag.getList("Items", 10);

        for(int i = 0; i < items.size(); ++i) {
            CompoundTag slottedItem = items.getCompound(i);
            int slot = slottedItem.getByte("Slot") & 255;
            if (slot < nonNullList.size()) {
                ShopStack stack = new ShopStack(ItemStack.of(slottedItem), slottedItem.getDouble("price"));
                nonNullList.set(slot, stack);
            }
        }

    }

}
