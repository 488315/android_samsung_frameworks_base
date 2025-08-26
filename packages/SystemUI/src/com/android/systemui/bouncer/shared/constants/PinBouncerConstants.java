package com.android.systemui.bouncer.shared.constants;

import com.android.systemui.R;

/* loaded from: classes.dex */
public final class PinBouncerConstants {
    public static final int pinDotAvd;
    public static final int pinShapes;

    static {
        new PinBouncerConstants();
        pinShapes = Integer.valueOf(R.array.bouncer_pin_shapes).intValue();
        pinDotAvd = Integer.valueOf(R.drawable.pin_dot_avd).intValue();
        Integer.valueOf(R.drawable.pin_dot_delete_avd).intValue();
    }

    private PinBouncerConstants() {
    }
}
