package com.epherical.jeepershops;

import com.epherical.octoecon.api.Economy;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatform extends CommonPlatform<FabricPlatform> {


    @Override
    public FabricPlatform getPlatform() {
        return this;
    }

    @Override
    public boolean isClientEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public boolean isServerEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public Path getRootConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("BOZO_ID");
    }

    @Override
    public Economy getEconomy() {
        return JeepersShopsFabric.instance.getEconomy();
    }

    @Override
    public ShopStorage getStorage() {
        return JeepersShopsFabric.instance.getStorage();
    }

    @Override
    public ShopManager getManager() {
        return JeepersShopsFabric.instance.getManager();
    }

}
