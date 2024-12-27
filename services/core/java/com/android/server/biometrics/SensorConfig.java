package com.android.server.biometrics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
