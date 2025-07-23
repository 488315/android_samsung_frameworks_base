package com.android.systemui.screenshot;

import android.content.pm.PackageManager;
import android.view.IWindowManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotDetectionController {
    public final PackageManager packageManager;
    public final IWindowManager windowManager;

    public ScreenshotDetectionController(IWindowManager iWindowManager, PackageManager packageManager) {
        this.windowManager = iWindowManager;
        this.packageManager = packageManager;
    }
}
