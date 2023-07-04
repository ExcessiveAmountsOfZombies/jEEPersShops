package com.epherical.jeepershops.shop;

import com.epherical.jeepershops.BozoFabric;
import com.epherical.jeepershops.ShopStorage;
import com.epherical.jeepershops.menu.ConfirmPurchaseMenu;
import com.epherical.jeepershops.menu.ShopMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
            ShopMenu shopMenu = new ShopMenu(MenuType.GENERIC_9x3, i, inventory, 3, Shop.this);
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

    public void confirmPurchaseMenu(ServerPlayer player, int slot) {
        MutableComponent title = Component.literal("Confirm Purchase");
        SimpleMenuProvider provider = new SimpleMenuProvider((i, inventory, player1) -> {
            ConfirmPurchaseMenu menu = new ConfirmPurchaseMenu(MenuType.GENERIC_9x1, i, inventory, 1, this, slot);
            ShopStack stack = this.items.get(slot);
            for (int j = 0; j < 4; ++j) {
                menu.getContainer().setItem(j, new ItemStack(Items.GREEN_STAINED_GLASS_PANE).setHoverName(Component.nullToEmpty("Purchase for " + stack.getPrice())));
            }
            for (int j = 5; j < 9; ++j) {
                menu.getContainer().setItem(j, new ItemStack(Items.RED_STAINED_GLASS_PANE).setHoverName(Component.nullToEmpty("Cancel")));
            }
            menu.getContainer().setItem(4, stack.getItemStack());
            return menu;
        }, title);
        player.openMenu(provider);
    }

    public void attemptPurchase(int slot, ServerPlayer player) {
        ShopStack stack = this.items.get(slot);
        if (stack.attemptPurchase(player)) {
            player.sendSystemMessage(Component.literal("You have purchased: ").append(stack.getItemStack().getHoverName()).append(" For " + stack.getPrice()));
            this.items.remove(slot);
        } else {
            player.sendSystemMessage(Component.literal("Purchase could not be completed."));
        }
    }
}
