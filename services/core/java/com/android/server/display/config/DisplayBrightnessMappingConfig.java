package com.android.server.display.config;

import android.R;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.Spline;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.feature.DisplayManagerFlags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class DisplayBrightnessMappingConfig {
    public static final String DEFAULT_BRIGHTNESS_MAPPING_KEY =
            AutoBrightnessModeName._default.getRawName()
                    + "_"
                    + AutoBrightnessSettingName.normal.getRawName();
    public final float[] mBrightnessLevelsNits;
    public final Map mBrightnessLevelsMap = new HashMap();
    public final Map mBrightnessLevelsLuxMap = new HashMap();

    public DisplayBrightnessMappingConfig(
            Context context,
            DisplayManagerFlags displayManagerFlags,
            AutoBrightness autoBrightness,
            Spline spline) {
        if (displayManagerFlags.mAutoBrightnessModesFlagState.isEnabled()
                && autoBrightness != null
                && autoBrightness.getLuxToBrightnessMapping() != null
                && autoBrightness.getLuxToBrightnessMapping().size() > 0) {
            for (LuxToBrightnessMapping luxToBrightnessMapping :
                    autoBrightness.getLuxToBrightnessMapping()) {
                int size = luxToBrightnessMapping.map.getPoint().size();
                float[] fArr = new float[size];
                float[] fArr2 = new float[size];
                for (int i = 0; i < size; i++) {
                    fArr[i] =
                            spline.interpolate(
                                    ((NonNegativeFloatToFloatPoint)
                                                    luxToBrightnessMapping.map.getPoint().get(i))
                                            .second.floatValue());
                    fArr2[i] =
                            ((NonNegativeFloatToFloatPoint)
                                            luxToBrightnessMapping.map.getPoint().get(i))
                                    .first.floatValue();
                }
                if (size == 0) {
                    throw new IllegalArgumentException(
                            "A display brightness mapping should not be empty");
                }
                if (fArr2[0] != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    throw new IllegalArgumentException(
                            "The first lux value in the display brightness mapping must be 0");
                }
                StringBuilder sb = new StringBuilder();
                AutoBrightnessModeName autoBrightnessModeName = luxToBrightnessMapping.mode;
                sb.append(
                        autoBrightnessModeName == null
                                ? AutoBrightnessModeName._default.getRawName()
                                : autoBrightnessModeName.getRawName());
                sb.append("_");
                AutoBrightnessSettingName autoBrightnessSettingName =
                        luxToBrightnessMapping.setting;
                sb.append(
                        autoBrightnessSettingName == null
                                ? AutoBrightnessSettingName.normal.getRawName()
                                : autoBrightnessSettingName.getRawName());
                String sb2 = sb.toString();
                if (((HashMap) this.mBrightnessLevelsMap).containsKey(sb2)
                        || ((HashMap) this.mBrightnessLevelsLuxMap).containsKey(sb2)) {
                    throw new IllegalArgumentException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "A display brightness mapping with key ",
                                    sb2,
                                    " already exists"));
                }
                ((HashMap) this.mBrightnessLevelsMap).put(sb2, fArr);
                ((HashMap) this.mBrightnessLevelsLuxMap).put(sb2, fArr2);
            }
        }
        HashMap hashMap = (HashMap) this.mBrightnessLevelsMap;
        String str = DEFAULT_BRIGHTNESS_MAPPING_KEY;
        if (hashMap.containsKey(str) && ((HashMap) this.mBrightnessLevelsLuxMap).containsKey(str)) {
            return;
        }
        this.mBrightnessLevelsNits =
                DisplayDeviceConfig.getFloatArray(
                        context.getResources()
                                .obtainTypedArray(R.array.config_minimumBrightnessCurveLux));
        int[] intArray =
                context.getResources().getIntArray(R.array.config_networkSupportedKeepaliveCount);
        float[] fArr3 = new float[intArray.length + 1];
        int i2 = 0;
        while (i2 < intArray.length) {
            int i3 = i2 + 1;
            fArr3[i3] = intArray[i2];
            i2 = i3;
        }
        ((HashMap) this.mBrightnessLevelsLuxMap).put(str, fArr3);
        int[] intArray2 = context.getResources().getIntArray(R.array.config_mobile_tcp_buffers);
        Map map = this.mBrightnessLevelsMap;
        float[] fArr4 = new float[intArray2.length];
        for (int i4 = 0; i4 < intArray2.length; i4++) {
            fArr4[i4] =
                    spline.interpolate(BrightnessSynchronizer.brightnessIntToFloat(intArray2[i4]));
        }
        ((HashMap) map).put(str, fArr4);
    }

    public static String autoBrightnessModeToString(int i) {
        if (i == 0) {
            return AutoBrightnessModeName._default.getRawName();
        }
        if (i == 1) {
            return AutoBrightnessModeName.idle.getRawName();
        }
        if (i == 2) {
            return AutoBrightnessModeName.doze.getRawName();
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown auto-brightness mode: "));
    }

    public static String autoBrightnessPresetToString(int i) {
        if (i == 1) {
            return AutoBrightnessSettingName.bright.getRawName();
        }
        if (i == 2) {
            return AutoBrightnessSettingName.normal.getRawName();
        }
        if (i == 3) {
            return AutoBrightnessSettingName.dim.getRawName();
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "Unknown auto-brightness preset value: "));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry entry : ((HashMap) this.mBrightnessLevelsLuxMap).entrySet()) {
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append(Arrays.toString((float[]) entry.getValue()));
            sb.append(", ");
        }
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        StringBuilder sb2 = new StringBuilder("{");
        for (Map.Entry entry2 : ((HashMap) this.mBrightnessLevelsMap).entrySet()) {
            sb2.append((String) entry2.getKey());
            sb2.append("=");
            sb2.append(Arrays.toString((float[]) entry2.getValue()));
            sb2.append(", ");
        }
        if (sb2.length() > 2) {
            sb2.delete(sb2.length() - 2, sb2.length());
        }
        sb2.append("}");
        return "mBrightnessLevelsNits= "
                + Arrays.toString(this.mBrightnessLevelsNits)
                + ", mBrightnessLevelsLuxMap= "
                + ((Object) sb)
                + ", mBrightnessLevelsMap= "
                + ((Object) sb2);
    }
}
