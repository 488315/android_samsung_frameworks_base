package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.view.IWindowManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

public final class ScreenshotDetectionController {
    public final PackageManager packageManager;
    public final IWindowManager windowManager;

    public ScreenshotDetectionController(IWindowManager iWindowManager, PackageManager packageManager) {
        this.windowManager = iWindowManager;
        this.packageManager = packageManager;
    }

    public final List maybeNotifyOfScreenshot(ScreenshotData screenshotData) {
        if (screenshotData.source == 3) {
            return EmptyList.INSTANCE;
        }
        List notifyScreenshotListeners = this.windowManager.notifyScreenshotListeners(0);
        Intrinsics.checkNotNull(notifyScreenshotListeners);
        List<ComponentName> list = notifyScreenshotListeners;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ComponentName componentName : list) {
            CharSequence loadLabel = this.packageManager.getActivityInfo(componentName, PackageManager.ComponentInfoFlags.of(512L)).loadLabel(this.packageManager);
            if (loadLabel.length() == 0) {
                loadLabel = this.packageManager.getActivityInfo(componentName, PackageManager.ComponentInfoFlags.of(512L)).packageName;
            }
            arrayList.add(loadLabel);
        }
        return arrayList;
    }
}
