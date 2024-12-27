package com.android.server.biometrics.sensors.face.aidl;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AidlConversionUtils {
    public static int convertAidlToFrameworkFeature(byte b) {
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return 2;
        }
        if (b == 2) {
            return 3;
        }
        Slog.e("AidlConversionUtils", "Unsupported feature : " + ((int) b));
        throw new IllegalArgumentException();
    }

    public static byte convertFrameworkToAidlFeature(int i) {
        if (i == 1) {
            return (byte) 0;
        }
        if (i == 2) {
            return (byte) 1;
        }
        Slog.e("AidlConversionUtils", "Unsupported feature : " + i);
        throw new IllegalArgumentException();
    }

    public static int toFrameworkAcquiredInfo(byte b) {
        switch (b) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 9;
            case 11:
                return 10;
            case 12:
                return 11;
            case 13:
                return 12;
            case 14:
                return 13;
            case 15:
                return 14;
            case 16:
                return 15;
            case 17:
                return 16;
            case 18:
                return 17;
            case 19:
                return 18;
            case 20:
                return 19;
            case 21:
                return 20;
            case 22:
                return 21;
            case 23:
                return 22;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            default:
                return 23;
        }
    }
}
