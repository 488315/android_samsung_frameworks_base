package com.android.systemui.statusbar.pipeline.battery.shared.ui;

import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.PathSvgKt;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryFrame {
    public static final BatteryFrame INSTANCE = new BatteryFrame();
    public static final PathSpec pathSpec;

    static {
        AndroidPath Path = AndroidPath_androidKt.Path();
        PathSvgKt.addSvg(Path, "M17.5 0H2C0.895431 0 0 0.895431 0 2V10C0 11.1046 0.89543 12 2 12H17.5C18.6046 12 19.5 11.1046 19.5 10V8H19.9231C20.5178 8 21 7.51785 21 6.92308V5.07692C21 4.48215 20.5178 4 19.9231 4H19.5V2C19.5 0.895431 18.6046 0 17.5 0Z");
        Dp.Companion companion = Dp.Companion;
        pathSpec = new PathSpec(Path, 21, 12, null);
    }

    private BatteryFrame() {
    }
}
