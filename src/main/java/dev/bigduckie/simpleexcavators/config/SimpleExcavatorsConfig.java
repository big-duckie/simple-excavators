package dev.bigduckie.simpleexcavators.config;

import draylar.omegaconfig.api.Config;

public class SimpleExcavatorsConfig implements Config {

    public boolean enableExtraMaterials = true;
    public int durabilityModifier = 5;
    public double breakSpeedMultiplier = 1.0;

    @Override
    public String getName() {
        return "simple-excavators";
    }
}