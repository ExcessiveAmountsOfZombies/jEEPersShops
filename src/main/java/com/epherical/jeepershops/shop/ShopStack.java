package com.epherical.jeepershops.shop;

import com.epherical.jeepershops.CommonPlatform;
import com.epherical.jeepershops.exception.PurchaseException;
import com.epherical.octoecon.api.Economy;
import com.epherical.octoecon.api.transaction.Transaction;
import com.epherical.octoecon.api.user.UniqueUser;
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

    public boolean attemptPurchase(ServerPlayer player, Shop shop, boolean forced) throws PurchaseException {
        Economy economy = CommonPlatform.platform.getEconomy();
        if (player.getInventory().getFreeSlot() == -1) {
            throw new PurchaseException("No free slots to make purchase");
        }

        if (forced) {
            return player.getInventory().add(itemStack.copy());
        }

        if (economy == null) {
            throw new PurchaseException("No Economy Mod Installed.");
        }
        UniqueUser purchaser = economy.getOrCreatePlayerAccount(player.getUUID());
        UniqueUser seller = economy.getOrCreatePlayerAccount(shop.getOwner());
        if (purchaser != null && seller != null) {
            if (purchaser.hasAmount(economy.getDefaultCurrency(), price)) {
                Transaction transaction = purchaser.sendTo(seller, economy.getDefaultCurrency(), price);
                if (transaction.getTransactionResponse() == Transaction.Response.SUCCESS) {
                    return player.getInventory().add(itemStack.copy());
                }
            }
        } else {
            throw new PurchaseException("User accounts do not exist");
        }
        return false;
    }
}
