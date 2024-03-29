package com.epherical.jeepershops;

import com.epherical.octoecon.api.Economy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

import static com.epherical.jeepershops.JeepersShopsForge.mod;

public class ForgePlatform extends CommonPlatform<ForgePlatform> {

    @Override
    public ForgePlatform getPlatform() {
        return this;
    }

    @Override
    public boolean isClientEnvironment() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public boolean isServerEnvironment() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    @Override
    public Path getRootConfigPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Economy getEconomy() {
        return mod.getEconomy();
    }

    @Override
    public ShopStorage getStorage() {
        return mod.getStorage();
    }

    @Override
    public ShopManager getManager() {
        return mod.getManager();
    }

}
