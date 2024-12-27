package com.android.systemui.plugins.clocks;

import android.content.res.Resources;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ClockController {
    void dump(PrintWriter printWriter);

    ClockConfig getConfig();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(Resources resources, float f, float f2);
}
