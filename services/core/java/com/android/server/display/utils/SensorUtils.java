package com.android.server.display.utils;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.TextUtils;
import com.android.server.display.config.SensorData;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SensorUtils {
    public static Sensor findSensor(SensorManager sensorManager, String str, String str2, int i) {
        if (sensorManager == null) {
            return null;
        }
        boolean z = !TextUtils.isEmpty(str2);
        boolean z2 = !TextUtils.isEmpty(str);
        if (z || z2) {
            for (Sensor sensor : sensorManager.getSensorList(-1)) {
                if (!z || str2.equals(sensor.getName())) {
                    if (!z2 || str.equals(sensor.getStringType())) {
                        return sensor;
                    }
                }
            }
        }
        if (i != 0) {
            return sensorManager.getDefaultSensor(i);
        }
        return null;
    }

    public static int getSensorTemperatureType(SensorData sensorData) {
        if (sensorData.type.equalsIgnoreCase("DISPLAY")) {
            return 11;
        }
        String str = sensorData.type;
        if (str.equalsIgnoreCase("SKIN")) {
            return 3;
        }
        throw new IllegalArgumentException("tempSensor doesn't support type: " + str);
    }
}
