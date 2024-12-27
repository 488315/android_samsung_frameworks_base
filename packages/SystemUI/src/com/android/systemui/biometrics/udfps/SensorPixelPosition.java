package com.android.systemui.biometrics.udfps;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SensorPixelPosition {
    public static final /* synthetic */ SensorPixelPosition[] $VALUES;
    public static final SensorPixelPosition OUTSIDE;
    public static final SensorPixelPosition SENSOR;
    public static final SensorPixelPosition TARGET;

    static {
        SensorPixelPosition sensorPixelPosition = new SensorPixelPosition("OUTSIDE", 0);
        OUTSIDE = sensorPixelPosition;
        SensorPixelPosition sensorPixelPosition2 = new SensorPixelPosition("SENSOR", 1);
        SENSOR = sensorPixelPosition2;
        SensorPixelPosition sensorPixelPosition3 = new SensorPixelPosition("TARGET", 2);
        TARGET = sensorPixelPosition3;
        SensorPixelPosition[] sensorPixelPositionArr = {sensorPixelPosition, sensorPixelPosition2, sensorPixelPosition3};
        $VALUES = sensorPixelPositionArr;
        EnumEntriesKt.enumEntries(sensorPixelPositionArr);
    }

    private SensorPixelPosition(String str, int i) {
    }

    public static SensorPixelPosition valueOf(String str) {
        return (SensorPixelPosition) Enum.valueOf(SensorPixelPosition.class, str);
    }

    public static SensorPixelPosition[] values() {
        return (SensorPixelPosition[]) $VALUES.clone();
    }
}
