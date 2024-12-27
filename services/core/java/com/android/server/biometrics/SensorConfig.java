package com.android.server.biometrics;

public final class SensorConfig {
    public final int id;
    public final int modality;
    public final int strength;

    public SensorConfig(String str) {
        String[] split = str.split(":");
        this.id = Integer.parseInt(split[0]);
        this.modality = Integer.parseInt(split[1]);
        this.strength = Integer.parseInt(split[2]);
    }
}
