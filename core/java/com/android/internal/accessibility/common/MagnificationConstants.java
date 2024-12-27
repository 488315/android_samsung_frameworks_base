package com.android.internal.accessibility.common;

import android.os.SystemProperties;

/* loaded from: classes5.dex */
public final class MagnificationConstants {
    public static final float PERSISTED_SCALE_MIN_VALUE = 1.0f;
    public static final float SCALE_MAX_VALUE =
            Float.parseFloat(SystemProperties.get("ro.config.max_magnification_scale", "8.0"));
    public static final float SCALE_MIN_VALUE = 1.0f;

    private MagnificationConstants() {}
}
