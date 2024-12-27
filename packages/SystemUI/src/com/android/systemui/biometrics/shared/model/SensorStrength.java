package com.android.systemui.biometrics.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
