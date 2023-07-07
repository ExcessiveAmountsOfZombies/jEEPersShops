package com.epherical.jeepershops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class WorldBasedStorage {

    protected final Gson GSON;

    protected final Path basePath;

    public WorldBasedStorage(LevelResource resource, MinecraftServer server, String path) {
        this(server.getWorldPath(resource).resolve(path));
    }

    public WorldBasedStorage(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        basePath = path;
        this.GSON = buildGson(new GsonBuilder());
    }

    protected abstract Gson buildGson(GsonBuilder builder);

    public Path getBasePath() {
        return basePath;
    }

    public void writeTagToFile(Tag tag, Path path) throws IOException {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            GSON.toJson(NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, tag), writer);
        }
    }

    public Tag readTagFromFile(Path path) throws IOException {
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonElement jsonElement = GSON.fromJson(reader, JsonElement.class);
            return JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, jsonElement);
        }
    }

    public Stream<Path> getFiles() throws IOException {
        return Files.walk(basePath);
    }

}
