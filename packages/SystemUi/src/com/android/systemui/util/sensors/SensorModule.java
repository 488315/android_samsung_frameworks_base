package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SensorModule {
    public static ThresholdSensor[] createPostureToSensorMapping(ThresholdSensorImpl.BuilderFactory builderFactory, String[] strArr, int i, int i2) {
        boolean z;
        ThresholdSensorImpl.Builder builder = new ThresholdSensorImpl.Builder(builderFactory.mResources, builderFactory.mSensorManager, builderFactory.mExecution);
        builder.mSensor = null;
        builder.mSensorSet = true;
        builder.mThresholdValue = 0.0f;
        builder.mThresholdSet = true;
        if (!builder.mThresholdLatchValueSet) {
            builder.mThresholdLatchValue = 0.0f;
        }
        ThresholdSensor[] thresholdSensorArr = new ThresholdSensor[5];
        Arrays.fill(thresholdSensorArr, builder.build());
        if (strArr != null && strArr.length != 0) {
            for (String str : strArr) {
                if (!TextUtils.isEmpty(str)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            Log.e("SensorModule", "config doesn't support postures, but attempting to retrieve proxSensorMapping");
            return thresholdSensorArr;
        }
        HashMap hashMap = new HashMap();
        for (int i3 = 0; i3 < strArr.length; i3++) {
            try {
                String str2 = strArr[i3];
                if (hashMap.containsKey(str2)) {
                    thresholdSensorArr[i3] = (ThresholdSensor) hashMap.get(str2);
                } else {
                    ThresholdSensorImpl.Builder builder2 = new ThresholdSensorImpl.Builder(builderFactory.mResources, builderFactory.mSensorManager, builderFactory.mExecution);
                    Resources resources = builder2.mResources;
                    Sensor findSensorByType = builder2.findSensorByType(strArr[i3], true);
                    if (findSensorByType != null) {
                        builder2.mSensor = findSensorByType;
                        builder2.mSensorSet = true;
                    }
                    try {
                        float f = resources.getFloat(i);
                        builder2.mThresholdValue = f;
                        builder2.mThresholdSet = true;
                        if (!builder2.mThresholdLatchValueSet) {
                            builder2.mThresholdLatchValue = f;
                        }
                    } catch (Resources.NotFoundException unused) {
                    }
                    try {
                        builder2.mThresholdLatchValue = resources.getFloat(i2);
                        builder2.mThresholdLatchValueSet = true;
                    } catch (Resources.NotFoundException unused2) {
                    }
                    ThresholdSensorImpl build = builder2.build();
                    thresholdSensorArr[i3] = build;
                    hashMap.put(str2, build);
                }
            } catch (IllegalStateException unused3) {
            }
        }
        return thresholdSensorArr;
    }
}
