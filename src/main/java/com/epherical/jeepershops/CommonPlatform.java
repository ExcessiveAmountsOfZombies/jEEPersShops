package com.epherical.jeepershops;

import com.epherical.octoecon.api.Economy;

import java.nio.file.Path;

public abstract class CommonPlatform<T> {
    public static CommonPlatform<?> platform;

    protected CommonPlatform() {
        platform = this;
    }

    public static <T> void create(CommonPlatform<T> value) {
        platform = value;
    }

    public abstract T getPlatform();

    public abstract boolean isClientEnvironment();

    public abstract boolean isServerEnvironment();

    public abstract Path getRootConfigPath();

    public abstract Economy getEconomy();

    public abstract ShopStorage getStorage();

    public abstract ShopManager getManager();

}
