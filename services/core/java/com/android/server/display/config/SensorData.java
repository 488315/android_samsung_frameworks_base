package com.android.server.display.config;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.feature.DisplayManagerFlags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SensorData {
    public final float maxRefreshRate;
    public final float minRefreshRate;
    public final String name;
    public final List supportedModes;
    public final String type;

    public SensorData() {
        this(null, null);
    }

    public SensorData(String str, String str2) {
        this(str, str2, FullScreenMagnificationGestureHandler.MAX_SCALE, Float.POSITIVE_INFINITY);
    }

    public SensorData(String str, String str2, float f, float f2) {
        this(str, str2, f, f2, List.of());
    }

    public SensorData(String str, String str2, float f, float f2, List list) {
        this.type = str;
        this.name = str2;
        this.minRefreshRate = f;
        this.maxRefreshRate = f2;
        this.supportedModes = Collections.unmodifiableList(list);
    }

    public static SensorData loadProxSensorConfig(
            DisplayManagerFlags displayManagerFlags, DisplayConfiguration displayConfiguration) {
        SensorData sensorData;
        SensorData sensorData2 = new SensorData();
        if (displayConfiguration.proxSensor == null) {
            displayConfiguration.proxSensor = new ArrayList();
        }
        ArrayList arrayList = (ArrayList) displayConfiguration.proxSensor;
        if (arrayList.isEmpty()) {
            return sensorData2;
        }
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                sensorData = sensorData2;
                break;
            }
            SensorDetails sensorDetails = (SensorDetails) it.next();
            String str = sensorDetails.featureFlag;
            if (displayManagerFlags.mUseFusionProxSensor.isEnabled()
                    && displayManagerFlags.mUseFusionProxSensor.mName.equals(str)) {
                sensorData = loadSensorData(sensorDetails);
                break;
            }
        }
        if (sensorData2 == sensorData) {
            Iterator it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                SensorDetails sensorDetails2 = (SensorDetails) it2.next();
                if (sensorDetails2.featureFlag == null) {
                    sensorData = loadSensorData(sensorDetails2);
                    break;
                }
            }
        }
        if (sensorData2 != sensorData && "".equals(sensorData.name) && "".equals(sensorData.type)) {
            return null;
        }
        return sensorData;
    }

    public static SensorData loadSensorData(SensorDetails sensorDetails) {
        float f;
        float f2;
        RefreshRateRange refreshRateRange = sensorDetails.refreshRate;
        if (refreshRateRange != null) {
            f = refreshRateRange.minimum.floatValue();
            f2 = refreshRateRange.maximum.floatValue();
        } else {
            f = FullScreenMagnificationGestureHandler.MAX_SCALE;
            f2 = Float.POSITIVE_INFINITY;
        }
        return new SensorData(
                sensorDetails.type,
                sensorDetails.name,
                f,
                f2,
                SupportedModeData.load(sensorDetails.supportedModes));
    }

    public final String toString() {
        return "SensorData{type= "
                + this.type
                + ", name= "
                + this.name
                + ", refreshRateRange: ["
                + this.minRefreshRate
                + ", "
                + this.maxRefreshRate
                + "], supportedModes="
                + this.supportedModes
                + '}';
    }
}
