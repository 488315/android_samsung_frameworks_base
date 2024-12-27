package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telecom.Logging.Session;

/* loaded from: classes5.dex */
public class Utils {
    public static float asNan(int v) {
        return Float.intBitsToFloat((-8388608) | v);
    }

    public static int idFromNan(float value) {
        int b = Float.floatToRawIntBits(value);
        return 1048575 & b;
    }

    public static float getActualValue(float lr) {
        return 0.0f;
    }

    static String trimString(String str, int n) {
        if (str.length() > n) {
            return str.substring(0, n - 3) + Session.TRUNCATE_STRING;
        }
        return str;
    }

    public static String floatToString(float idvalue, float value) {
        if (Float.isNaN(idvalue)) {
            return NavigationBarInflaterView.SIZE_MOD_START + idFromNan(idvalue) + NavigationBarInflaterView.SIZE_MOD_END + floatToString(value);
        }
        return floatToString(value);
    }

    public static String floatToString(float value) {
        if (Float.isNaN(value)) {
            return NavigationBarInflaterView.SIZE_MOD_START + idFromNan(value) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(value);
    }

    public static void log(String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        System.out.println(NavigationBarInflaterView.KEY_CODE_START + s.getFileName() + ":" + s.getLineNumber() + ")." + str);
    }

    public static void logStack(String str, int n) {
        StackTraceElement[] st = new Throwable().getStackTrace();
        for (int i = 1; i < n + 1; i++) {
            StackTraceElement s = st[i];
            String space = new String(new char[i]).replace((char) 0, ' ');
            System.out.println(space + NavigationBarInflaterView.KEY_CODE_START + s.getFileName() + ":" + s.getLineNumber() + ")." + str);
        }
    }

    public static boolean isVariable(float v) {
        if (!Float.isNaN(v)) {
            return false;
        }
        int id = idFromNan(v);
        return id > 40 || id < 10;
    }

    public static String colorInt(int color) {
        String str = "000000000000" + Integer.toHexString(color);
        return "0x" + str.substring(str.length() - 8);
    }

    public static int interpolateColor(int c1, int c2, float t) {
        if (Float.isNaN(t) || t == 0.0f) {
            return c1;
        }
        if (t == 1.0f) {
            return c2;
        }
        int a = (c1 >> 24) & 255;
        int r = (c1 >> 16) & 255;
        int g = (c1 >> 8) & 255;
        int b = c1 & 255;
        float f_r = (float) Math.pow(r / 255.0f, 2.2d);
        float f_g = (float) Math.pow(g / 255.0f, 2.2d);
        float f_b = (float) Math.pow(b / 255.0f, 2.2d);
        float c1fa = a / 255.0f;
        int a2 = (c2 >> 24) & 255;
        int r2 = (c2 >> 16) & 255;
        int g2 = (c2 >> 8) & 255;
        int b2 = c2 & 255;
        float f_r2 = (float) Math.pow(r2 / 255.0f, 2.2d);
        float c2fa = a2 / 255.0f;
        float f_g2 = f_g + ((((float) Math.pow(g2 / 255.0f, 2.2d)) - f_g) * t);
        float f_b2 = f_b + ((((float) Math.pow(b2 / 255.0f, 2.2d)) - f_b) * t);
        float f_a = c1fa + ((c2fa - c1fa) * t);
        int outr = clamp((int) (((float) Math.pow(f_r + ((f_r2 - f_r) * t), 0.45454545454545453d)) * 255.0f));
        int outg = clamp((int) (((float) Math.pow(f_g2, 0.45454545454545453d)) * 255.0f));
        int outb = clamp((int) (((float) Math.pow(f_b2, 0.45454545454545453d)) * 255.0f));
        int outa = clamp((int) (255.0f * f_a));
        return (outa << 24) | (outr << 16) | (outg << 8) | outb;
    }

    public static int clamp(int c) {
        int c2 = (c & (~(c >> 31))) - 255;
        return (c2 & (c2 >> 31)) + 255;
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        int h = (int) (hue * 6.0f);
        float f = (6.0f * hue) - h;
        int p = (int) ((value * 255.0f * (1.0f - saturation)) + 0.5f);
        int q = (int) ((value * 255.0f * (1.0f - (f * saturation))) + 0.5f);
        int t = (int) ((value * 255.0f * (1.0f - ((1.0f - f) * saturation))) + 0.5f);
        int v = (int) ((255.0f * value) + 0.5f);
        switch (h) {
            case 0:
                return (-16777216) | ((v << 16) + (t << 8) + p);
            case 1:
                return (-16777216) | ((q << 16) + (v << 8) + p);
            case 2:
                return (-16777216) | ((p << 16) + (v << 8) + t);
            case 3:
                return (-16777216) | ((p << 16) + (q << 8) + v);
            case 4:
                return (-16777216) | ((t << 16) + (p << 8) + v);
            case 5:
                return (-16777216) | ((v << 16) + (p << 8) + q);
            default:
                return 0;
        }
    }
}
