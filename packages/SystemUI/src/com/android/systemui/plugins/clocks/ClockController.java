package com.android.systemui.plugins.clocks;

import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ClockController {
    void dump(PrintWriter printWriter);

    ClockConfig getConfig();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(boolean z, float f, float f2, ClockEventListener clockEventListener);
}
