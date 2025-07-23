package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes6.dex */
public abstract class Easing {
    public static final int CUBIC_ACCELERATE = 2;
    public static final int CUBIC_ANTICIPATE = 5;
    public static final int CUBIC_CUSTOM = 11;
    public static final int CUBIC_DECELERATE = 3;
    public static final int CUBIC_LINEAR = 4;
    public static final int CUBIC_OVERSHOOT = 6;
    public static final int CUBIC_STANDARD = 1;
    public static final int EASE_OUT_BOUNCE = 13;
    public static final int EASE_OUT_ELASTIC = 14;
    public static final int SPLINE_CUSTOM = 12;
    int mType;

    public abstract float get(float f);

    public abstract float getDiff(float f);

    public int getType() {
        return this.mType;
    }

    public static String getString(int i) {
        switch (i) {
            case 1:
                return "CUBIC_STANDARD";
            case 2:
                return "CUBIC_ACCELERATE";
            case 3:
                return "CUBIC_DECELERATE";
            case 4:
                return "CUBIC_LINEAR";
            case 5:
                return "CUBIC_ANTICIPATE";
            case 6:
                return "CUBIC_OVERSHOOT";
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                return "INVALID_CURVE_TYPE[" + i + NavigationBarInflaterView.SIZE_MOD_END;
            case 11:
                return "CUBIC_CUSTOM";
            case 12:
                return "SPLINE_CUSTOM";
            case 13:
                return "EASE_OUT_BOUNCE";
            case 14:
                return "EASE_OUT_ELASTIC";
        }
    }
}
