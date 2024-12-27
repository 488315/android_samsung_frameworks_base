package com.android.systemui.screenshot.scroll;

import android.app.ActivityManager;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ScrollCaptureExecutor {
    public final Executor mainExecutor;

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
