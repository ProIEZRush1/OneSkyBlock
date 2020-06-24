package me.proiezrush.oneskyblock.utils;

import org.bukkit.*;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.Random;

@SuppressWarnings("deprecation")
public class Worlds {

    public World createVoidWorld(String name) {
        WorldCreator creator = new WorldCreator(name);
        creator.generator(new ChunkGenerator() {
            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];
            }
        });
        World world = creator.createWorld();
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setSpawnFlags(true, true);
        world.setPVP(false);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(2147483647);
        world.setAutoSave(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setSpawnLocation(0, 0, 0);
        return world;
    }

    public void deleteWorld(String name, File path) {
        Bukkit.unloadWorld(name, false);
        String[]entries = path.list();
        if (entries != null) {
            for(String s : entries){
                File currentFile = new File(path.getPath(),s);
                currentFile.delete();
            }
        }
    }
}
