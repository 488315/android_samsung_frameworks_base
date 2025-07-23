package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.common.AudioChannelLayout;
import android.telecom.Logging.Session;

/* loaded from: classes6.dex */
public class Utils {
    public static int clamp(int i) {
        int i2 = (i & (~(i >> 31))) - 255;
        return (i2 & (i2 >> 31)) + 255;
    }

    public static int hsvToRgb(float f, float f2, float f3) {
        float f4 = f * 6.0f;
        int i = (int) f4;
        float f5 = f4 - i;
        float f6 = f3 * 255.0f;
        int i2 = (int) (((1.0f - f2) * f6) + 0.5f);
        int i3 = (int) (((1.0f - (f5 * f2)) * f6) + 0.5f);
        int i4 = (int) (((1.0f - ((1.0f - f5) * f2)) * f6) + 0.5f);
        int i5 = (int) (f6 + 0.5f);
        if (i == 0) {
            return ((i5 << 16) + (i4 << 8) + i2) | (-16777216);
        }
        if (i == 1) {
            return ((i3 << 16) + (i5 << 8) + i2) | (-16777216);
        }
        if (i == 2) {
            return ((i2 << 16) + (i5 << 8) + i4) | (-16777216);
        }
        if (i == 3) {
            return ((i2 << 16) + (i3 << 8) + i5) | (-16777216);
        }
        if (i == 4) {
            return ((i4 << 16) + (i2 << 8) + i5) | (-16777216);
        }
        if (i != 5) {
            return 0;
        }
        return ((i5 << 16) + (i2 << 8) + i3) | (-16777216);
    }

    public static long idFromLong(long j) {
        return j - 4294967296L;
    }

    public static int toARGB(float f, float f2, float f3, float f4) {
        return (((int) ((f * 255.0f) + 0.5f)) << 24) | (((int) ((f2 * 255.0f) + 0.5f)) << 16) | (((int) ((f3 * 255.0f) + 0.5f)) << 8) | ((int) ((f4 * 255.0f) + 0.5f));
    }

    public static float asNan(int i) {
        return Float.intBitsToFloat(i | (-8388608));
    }

    public static int idFromNan(float f) {
        return Float.floatToRawIntBits(f) & AudioChannelLayout.INDEX_MASK_22;
    }

    public static long longIdFromNan(float f) {
        return idFromNan(f) + 4294967296L;
    }

    public static String idStringFromNan(float f) {
        return idString(Float.floatToRawIntBits(f) & AudioChannelLayout.INDEX_MASK_22);
    }

    public static String idString(int i) {
        if (i > 1048575) {
            return "A_" + (i & 1048575);
        }
        return "" + i;
    }

    public static String trimString(String str, int i) {
        if (str.length() <= i) {
            return str;
        }
        return str.substring(0, i - 3) + Session.TRUNCATE_STRING;
    }

    public static String floatToString(float f, float f2) {
        if (Float.isNaN(f)) {
            if (idFromNan(f2) == 0) {
                return "NaN";
            }
            return NavigationBarInflaterView.SIZE_MOD_START + idFromNan(f) + NavigationBarInflaterView.SIZE_MOD_END + floatToString(f2);
        }
        return floatToString(f2);
    }

    public static String floatToString(float f) {
        if (Float.isNaN(f)) {
            if (idFromNan(f) == 0) {
                return "NaN";
            }
            return NavigationBarInflaterView.SIZE_MOD_START + idFromNan(f) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(f);
    }

    public static void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        System.out.println(NavigationBarInflaterView.KEY_CODE_START + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "). " + stackTraceElement.getMethodName() + "() " + str);
    }

    public static void logStack(String str, int i) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (int i2 = 1; i2 < i + 1; i2++) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            String replace = new String(new char[i2]).replace((char) 0, ' ');
            System.out.println(replace + NavigationBarInflaterView.KEY_CODE_START + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")." + str);
        }
    }

    public static boolean isVariable(float f) {
        int idFromNan;
        if (!Float.isNaN(f) || (idFromNan = idFromNan(f)) == 0) {
            return false;
        }
        return idFromNan > 40 || idFromNan < 10;
    }

    public static String colorInt(int i) {
        return "0x" + ("000000000000" + Integer.toHexString(i)).substring(r2.length() - 8);
    }

    public static int interpolateColor(int i, int i2, float f) {
        if (Float.isNaN(f) || f == 0.0f) {
            return i;
        }
        if (f == 1.0f) {
            return i2;
        }
        int i3 = (i >> 24) & 255;
        float pow = (float) Math.pow(((i >> 16) & 255) / 255.0f, 2.2d);
        float pow2 = (float) Math.pow(((i >> 8) & 255) / 255.0f, 2.2d);
        float pow3 = (float) Math.pow((i & 255) / 255.0f, 2.2d);
        float f2 = i3 / 255.0f;
        float pow4 = (float) Math.pow(((i2 >> 16) & 255) / 255.0f, 2.2d);
        float pow5 = pow2 + ((((float) Math.pow(((i2 >> 8) & 255) / 255.0f, 2.2d)) - pow2) * f);
        float f3 = f2 + (f * ((((i2 >> 24) & 255) / 255.0f) - f2));
        return clamp((int) (((float) Math.pow(pow3 + ((((float) Math.pow((i2 & 255) / 255.0f, 2.2d)) - pow3) * f), 0.45454545454545453d)) * 255.0f)) | (clamp((int) (((float) Math.pow(pow + ((pow4 - pow) * f), 0.45454545454545453d)) * 255.0f)) << 16) | (clamp((int) (f3 * 255.0f)) << 24) | (clamp((int) (((float) Math.pow(pow5, 0.45454545454545453d)) * 255.0f)) << 8);
    }

    public static float getHue(int i) {
        float f = ((i >> 16) & 255) / 255.0f;
        float f2 = ((i >> 8) & 255) / 255.0f;
        float f3 = (i & 255) / 255.0f;
        float max = Math.max(f, Math.max(f2, f3));
        float min = Math.min(f, Math.min(f2, f3));
        float f4 = max - min;
        float f5 = ((max == min ? 0.0f : max == f ? ((f2 - f3) / f4) % 6.0f : max == f2 ? ((f3 - f) / f4) + 2.0f : ((f - f2) / f4) + 4.0f) * 60.0f) % 360.0f;
        if (f5 < 0.0f) {
            f5 += 360.0f;
        }
        return f5 / 360.0f;
    }

    public static float getSaturation(int i) {
        float f = ((i >> 16) & 255) / 255.0f;
        float f2 = ((i >> 8) & 255) / 255.0f;
        float f3 = (i & 255) / 255.0f;
        float max = Math.max(f, Math.max(f2, f3));
        float min = Math.min(f, Math.min(f2, f3));
        float f4 = max - min;
        float f5 = (max + min) / 2.0f;
        if (max == min) {
            return 0.0f;
        }
        return f4 / (1.0f - Math.abs((f5 * 2.0f) - 1.0f));
    }

    public static float getBrightness(int i) {
        float f = ((i >> 16) & 255) / 255.0f;
        float f2 = ((i >> 8) & 255) / 255.0f;
        float f3 = (i & 255) / 255.0f;
        return (Math.max(f, Math.max(f2, f3)) + Math.min(f, Math.min(f2, f3))) / 2.0f;
    }
}
