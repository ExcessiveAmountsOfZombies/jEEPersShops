package com.epherical.jeepershops;

import com.epherical.jeepershops.command.ShopCommand;
import com.epherical.octoecon.api.Economy;
import com.epherical.octoecon.api.event.EconomyChangeEvent;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("jeepers_shops")
public class JeepersShopsForge {

    public static JeepersShopsForge mod;

    private Economy economy;
    private ShopStorage storage;
    private ShopManager manager;

    public JeepersShopsForge() {
        mod = this;
        CommonPlatform.create(new ForgePlatform());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonInit);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonInit(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void economyChange(EconomyChangeEvent event) {
        this.economy = event.getEconomy();
    }

    @SubscribeEvent
    public void serverStarting(ServerStartingEvent event) {
        storage = new ShopStorage(LevelResource.ROOT, event.getServer(), "epherical/shops");
        manager = new ShopManager(storage);
    }

    @SubscribeEvent
    public void serverStopping(ServerStoppingEvent event) {
        manager.clear();
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        new ShopCommand(event.getDispatcher());
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
