package com.will;

import org.bukkit.block.Biome;

import java.util.Random;

public record DropConfig(String block, Biome biome, String item, double probability) {
    private static final Random random = new Random();

    public boolean shouldDrop() {
        return random.nextDouble() < probability;
    }
}
