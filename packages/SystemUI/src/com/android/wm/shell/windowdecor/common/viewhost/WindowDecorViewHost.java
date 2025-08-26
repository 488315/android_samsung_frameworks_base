package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public interface WindowDecorViewHost {
    SurfaceControl getSurfaceControl();

    void release(SurfaceControl.Transaction transaction);

    void updateView(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, SurfaceControl.Transaction transaction);

    void updateViewAsync(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region);
}
