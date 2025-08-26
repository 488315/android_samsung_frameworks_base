package com.android.systemui.plugins.clocks;

import java.io.PrintWriter;

/* loaded from: classes2.dex */
public interface ClockController {
    void dump(PrintWriter printWriter);

    ClockConfig getConfig();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(boolean z, float f, float f2, ClockEventListener clockEventListener);
}
