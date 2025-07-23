package com.android.systemui.bouncer.shared.constants;

import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
