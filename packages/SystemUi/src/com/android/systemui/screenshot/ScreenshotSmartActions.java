package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotSmartActions {
    public final Provider mScreenshotNotificationSmartActionsProviderProvider;

    public ScreenshotSmartActions(Provider provider) {
        this.mScreenshotNotificationSmartActionsProviderProvider = provider;
    }

    public static List getSmartActions(CompletableFuture completableFuture, int i, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider) {
        SystemClock.uptimeMillis();
        try {
            List list = (List) completableFuture.get(i, TimeUnit.MILLISECONDS);
            SystemClock.uptimeMillis();
            ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp = ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS;
            ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS;
            try {
                screenshotNotificationSmartActionsProvider.getClass();
            } catch (Throwable th) {
                Log.e("Screenshot", "Error in notifyScreenshotOp: ", th);
            }
            return list;
        } catch (Throwable th2) {
            SystemClock.uptimeMillis();
            if (th2 instanceof TimeoutException) {
                ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus2 = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS;
            } else {
                ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus3 = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS;
            }
            ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp2 = ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS;
            try {
                screenshotNotificationSmartActionsProvider.getClass();
            } catch (Throwable th3) {
                Log.e("Screenshot", "Error in notifyScreenshotOp: ", th3);
            }
            return Collections.emptyList();
        }
    }

    public static CompletableFuture getSmartActionsFuture(Bitmap bitmap, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, boolean z) {
        if (!z) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        SystemClock.uptimeMillis();
        try {
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null || runningTask.topActivity == null) {
                new ComponentName("", "");
            }
            screenshotNotificationSmartActionsProvider.getClass();
            return CompletableFuture.completedFuture(Collections.emptyList());
        } catch (Throwable unused) {
            SystemClock.uptimeMillis();
            CompletableFuture completedFuture = CompletableFuture.completedFuture(Collections.emptyList());
            ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp = ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS;
            ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus = ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS;
            try {
                screenshotNotificationSmartActionsProvider.getClass();
                return completedFuture;
            } catch (Throwable th) {
                Log.e("Screenshot", "Error in notifyScreenshotOp: ", th);
                return completedFuture;
            }
        }
    }

    public final void notifyScreenshotAction() {
        try {
            ((ScreenshotNotificationSmartActionsProvider) this.mScreenshotNotificationSmartActionsProviderProvider.get()).getClass();
        } catch (Throwable th) {
            Log.e("Screenshot", "Error in notifyScreenshotAction: ", th);
        }
    }
}
