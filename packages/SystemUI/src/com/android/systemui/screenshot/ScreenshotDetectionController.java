package com.android.systemui.screenshot;

import android.content.pm.PackageManager;
import android.view.IWindowManager;

/* loaded from: classes2.dex */
public final class ScreenshotDetectionController {
    public final PackageManager packageManager;
    public final IWindowManager windowManager;

    public ScreenshotDetectionController(IWindowManager iWindowManager, PackageManager packageManager) {
        this.windowManager = iWindowManager;
        this.packageManager = packageManager;
    }
}
