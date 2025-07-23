package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SensorStrength {
    public static final /* synthetic */ SensorStrength[] $VALUES;
    public static final SensorStrength CONVENIENCE;
    public static final SensorStrength STRONG;
    public static final SensorStrength WEAK;

    static {
        SensorStrength sensorStrength = new SensorStrength("CONVENIENCE", 0);
        CONVENIENCE = sensorStrength;
        SensorStrength sensorStrength2 = new SensorStrength("WEAK", 1);
        WEAK = sensorStrength2;
        SensorStrength sensorStrength3 = new SensorStrength("STRONG", 2);
        STRONG = sensorStrength3;
        SensorStrength[] sensorStrengthArr = {sensorStrength, sensorStrength2, sensorStrength3};
        $VALUES = sensorStrengthArr;
        EnumEntriesKt.enumEntries(sensorStrengthArr);
    }

    private SensorStrength(String str, int i) {
    }

    public static SensorStrength valueOf(String str) {
        return (SensorStrength) Enum.valueOf(SensorStrength.class, str);
    }

    public static SensorStrength[] values() {
        return (SensorStrength[]) $VALUES.clone();
    }
}
