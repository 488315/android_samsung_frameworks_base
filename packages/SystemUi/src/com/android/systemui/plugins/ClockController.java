package com.android.systemui.plugins;

import android.content.res.Resources;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ClockController {
    void dump(PrintWriter printWriter);

    ClockConfig getConfig();

    ClockEvents getEvents();

    ClockFaceController getLargeClock();

    ClockFaceController getSmallClock();

    void initialize(Resources resources, float f, float f2);
}
