package com.epherical.jeepershops;

import com.epherical.epherolib.data.WorldBasedStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.util.UUID;

public class ShopStorage extends WorldBasedStorage {


    public ShopStorage(LevelResource resource, MinecraftServer server, String path) {
        super(resource, server, path);
    }

    @Override
    public Gson buildGson(GsonBuilder builder) {
        return builder.create();
    }

    public Path resolve(UUID uuid) {
        return basePath.resolve(uuid.toString() + ".json");
    }

}
