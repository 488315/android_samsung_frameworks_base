package com.android.systemui.biometrics.udfps;

import kotlin.enums.EnumEntriesKt;

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
