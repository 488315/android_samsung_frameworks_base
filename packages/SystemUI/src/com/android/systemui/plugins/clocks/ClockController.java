package com.android.systemui.plugins.clocks;

import android.content.res.Resources;
import java.io.PrintWriter;

public interface ClockController {
    void dump(PrintWriter printWriter);

    ClockConfig getConfig();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(Resources resources, float f, float f2);
}
