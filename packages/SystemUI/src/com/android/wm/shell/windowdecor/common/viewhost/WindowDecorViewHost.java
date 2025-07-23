package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WindowDecorViewHost {
    SurfaceControl getSurfaceControl();

    void release(SurfaceControl.Transaction transaction);

    void updateView(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, SurfaceControl.Transaction transaction);

    void updateViewAsync(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region);
}
