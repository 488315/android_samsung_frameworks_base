package com.android.server.display.utils;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.Slog;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class DeviceConfigParsingUtils {
    public static float[] ambientBrightnessThresholdsIntToFloat(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = iArr[i];
        }
        return fArr;
    }

    public static float[] displayBrightnessThresholdsIntToFloat(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            if (i2 < 0) {
                fArr[i] = i2;
            } else {
                fArr[i] = BrightnessSynchronizer.brightnessIntToFloat(i2);
            }
        }
        return fArr;
    }

    public static float parseBrightness(String str) {
        float parseFloat = Float.parseFloat(str);
        if (parseFloat < FullScreenMagnificationGestureHandler.MAX_SCALE || parseFloat > 1.0f) {
            throw new IllegalArgumentException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Brightness value out of bounds: ", str));
        }
        return parseFloat;
    }

    public static Map parseDeviceConfigMap(String str, BiFunction biFunction, Function function) {
        if (str == null) {
            return Map.of();
        }
        HashMap hashMap = new HashMap();
        String[] split = str.split(";");
        int length = split.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            String str2 = split[i2];
            String[] split2 = str2.split(",");
            int length2 = split2.length;
            if (length2 < 4) {
                Slog.e(
                        "DeviceConfigParsingUtils",
                        "Invalid dataSet(not enough items):".concat(str2),
                        new Throwable());
                return Map.of();
            }
            String str3 = split2[i];
            try {
                int parseInt = Integer.parseInt(split2[1]);
                int i3 = parseInt * 2;
                if (length2 < i3 + 2 || length2 > i3 + 3) {
                    Slog.e(
                            "DeviceConfigParsingUtils",
                            "Invalid dataSet(wrong number of points):".concat(str2),
                            new Throwable());
                    return Map.of();
                }
                ArrayList arrayList = new ArrayList();
                int i4 = 2;
                int i5 = i;
                while (i5 < parseInt) {
                    int i6 = i4 + 1;
                    String str4 = split2[i4];
                    i4 += 2;
                    String str5 = split2[i6];
                    String[] strArr = split;
                    int i7 = length;
                    Object apply = biFunction.apply(str4, str5);
                    if (apply == null) {
                        StringBuilder m =
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "Invalid dataPoint ,key=",
                                        str4,
                                        ",value=",
                                        str5,
                                        ",dataSet=");
                        m.append(str2);
                        Slog.e("DeviceConfigParsingUtils", m.toString(), new Throwable());
                        return Map.of();
                    }
                    arrayList.add(apply);
                    i5++;
                    split = strArr;
                    length = i7;
                }
                String[] strArr2 = split;
                int i8 = length;
                Object apply2 = function.apply(arrayList);
                if (apply2 == null) {
                    Slog.e(
                            "DeviceConfigParsingUtils",
                            "Invalid dataSetMapped dataPoints=" + arrayList + ",dataSet=" + str2,
                            new Throwable());
                    return Map.of();
                }
                String str6 = i4 < split2.length ? split2[i4] : "default";
                if (((Map)
                                        hashMap.computeIfAbsent(
                                                str3,
                                                new DeviceConfigParsingUtils$$ExternalSyntheticLambda0()))
                                .put(str6, apply2)
                        != null) {
                    Slog.e(
                            "DeviceConfigParsingUtils",
                            BootReceiver$$ExternalSyntheticOutline0.m(
                                    "Duplicate dataSetId=", str6, ",data=", str),
                            new Throwable());
                    return Map.of();
                }
                i2++;
                split = strArr2;
                length = i8;
                i = 0;
            } catch (NumberFormatException e) {
                Slog.e(
                        "DeviceConfigParsingUtils",
                        "Invalid dataSet(invalid number of points):".concat(str2),
                        e);
                return Map.of();
            }
        }
        return hashMap;
    }

    public static int parseThermalStatus(String str) {
        str.getClass();
        switch (str) {
            case "severe":
                return 3;
            case "moderate":
                return 2;
            case "shutdown":
                return 6;
            case "none":
                return 0;
            case "light":
                return 1;
            case "emergency":
                return 5;
            case "critical":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid Thermal Status: ".concat(str));
        }
    }
}
