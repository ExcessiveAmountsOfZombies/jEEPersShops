package com.epherical.jeepershops;

import com.epherical.jeepershops.command.ShopCommand;
import com.epherical.octoecon.api.Economy;
import com.epherical.octoecon.api.event.EconomyEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.level.storage.LevelResource;

public class JeepersShopsFabric implements ModInitializer {

    public static JeepersShopsFabric instance;

    private Economy economy;
    private ShopStorage storage;
    private ShopManager manager;


    @Override
    public void onInitialize() {
        instance = this;
        CommonPlatform.create(new FabricPlatform());
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            new ShopCommand(dispatcher);
        });
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            storage = new ShopStorage(LevelResource.ROOT, server, "epherical/shops");
            manager = new ShopManager(storage);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            manager.clear();
        });
        EconomyEvents.ECONOMY_CHANGE_EVENT.register(economy -> {
            this.economy = economy;
        });
    }

    public ShopStorage getStorage() {
        return storage;
    }

    public ShopManager getManager() {
        return manager;
    }

    public Economy getEconomy() {
        return economy;
    }
}
