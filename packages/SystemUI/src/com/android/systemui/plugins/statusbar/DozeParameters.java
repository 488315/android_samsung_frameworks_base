package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public interface DozeParameters {
    public static final int VERSION = 1;

    boolean shouldControlScreenOff();
}
