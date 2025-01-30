package com.samsung.android.core;

import android.graphics.Rect;
import android.hardware.input.KeyboardLayout;

/* loaded from: classes5.dex */
public class CompatUtils {
    public static final float SCALE_72 = 0.72f;
    private static final float SCALE_DECIMAL_PLACE = 100.0f;

    public static float adjustRoundScale(float scale) {
        return Math.round(scale * 100.0f) / 100.0f;
    }

    public static float adjustCeilScale(float scale) {
        return ((int) Math.ceil(scale * 100.0f)) / 100.0f;
    }

    public static float adjustFloorScale(float scale) {
        return ((int) Math.floor(scale * 100.0f)) / 100.0f;
    }

    public static int applyScale(int value, float scale) {
        return (int) (value * scale);
    }

    public static void getScaledBounds(Rect inOutBounds, float scale) {
        getScaledBounds(inOutBounds, scale, false);
    }

    public static void getScaledBounds(Rect inOutBounds, float scale, boolean useAspectRatio) {
        int width;
        int height;
        int width2 = inOutBounds.width();
        int height2 = inOutBounds.height();
        if (!useAspectRatio) {
            inOutBounds.set(0, 0, applyScale(width2, scale), applyScale(height2, scale));
            return;
        }
        float aspectRatio = computeAspectRatio(width2, height2);
        if (width2 > height2) {
            height = applyScale(height2, scale);
            width = (int) (height / aspectRatio);
        } else {
            width = applyScale(width2, scale);
            height = (int) (width / aspectRatio);
        }
        inOutBounds.set(0, 0, width, height);
    }

    public static int getConfigurationOrientation(int width, int height) {
        return width > height ? 2 : 1;
    }

    private static float computeAspectRatio(int width, int height) {
        if (width == 0 || height == 0) {
            return 1.0f;
        }
        return Math.max(width, height) / Math.min(width, height);
    }

    public static void adjustBoundsToCenter(Rect sourceBounds, Rect inOutBounds) {
        inOutBounds.offsetTo(sourceBounds.left + ((sourceBounds.width() - inOutBounds.width()) >> 1), sourceBounds.top + ((sourceBounds.height() - inOutBounds.height()) >> 1));
    }

    public static float getCompatScale(Rect containingBounds, Rect containerBounds) {
        int containingW = containingBounds.width();
        int containingH = containingBounds.height();
        int containerW = containerBounds.width();
        int containerH = containerBounds.height();
        if (containingW <= containerW && containingH <= containerH) {
            return 1.0f;
        }
        return Math.min(containerW / containingW, containerH / containingH);
    }

    public static String orientationToString(int orientation) {
        switch (orientation) {
            case 0:
                return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            case 1:
                return "portrait";
            case 2:
                return "landscape";
            default:
                return Integer.toString(orientation);
        }
    }
}
