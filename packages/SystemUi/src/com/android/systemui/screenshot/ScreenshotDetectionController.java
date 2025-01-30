package com.android.systemui.screenshot;

import android.content.pm.PackageManager;
import android.view.IWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotDetectionController {
    public final PackageManager packageManager;
    public final IWindowManager windowManager;

    public ScreenshotDetectionController(IWindowManager iWindowManager, PackageManager packageManager) {
        this.windowManager = iWindowManager;
        this.packageManager = packageManager;
    }
}
