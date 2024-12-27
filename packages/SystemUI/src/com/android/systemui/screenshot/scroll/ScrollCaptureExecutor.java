package com.android.systemui.screenshot.scroll;

import android.app.ActivityManager;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScrollCaptureExecutor {
    public final Executor mainExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScrollCaptureExecutor(ActivityManager activityManager, ScrollCaptureClient scrollCaptureClient, ScrollCaptureController scrollCaptureController, LongScreenshotData longScreenshotData, Executor executor) {
        this.mainExecutor = executor;
        activityManager.isLowRamDevice();
    }
}
