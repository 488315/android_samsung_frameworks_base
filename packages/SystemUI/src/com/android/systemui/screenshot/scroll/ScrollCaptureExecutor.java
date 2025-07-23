package com.android.systemui.screenshot.scroll;

import android.app.ActivityManager;
import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScrollCaptureExecutor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean isLowRamDevice;
    public CallbackToFutureAdapter.SafeFuture lastScrollCaptureRequest;
    public ScrollCaptureResponse lastScrollCaptureResponse;
    public CallbackToFutureAdapter.SafeFuture longScreenshotFuture;
    public final LongScreenshotData longScreenshotHolder;
    public final Executor mainExecutor;
    public final ScrollCaptureClient scrollCaptureClient;
    public final ScrollCaptureController scrollCaptureController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ScrollTransitionReady {
    }

    static {
        new Companion(null);
    }

    public ScrollCaptureExecutor(ActivityManager activityManager, ScrollCaptureClient scrollCaptureClient, ScrollCaptureController scrollCaptureController, LongScreenshotData longScreenshotData, Executor executor) {
        this.scrollCaptureClient = scrollCaptureClient;
        this.scrollCaptureController = scrollCaptureController;
        this.longScreenshotHolder = longScreenshotData;
        this.mainExecutor = executor;
        this.isLowRamDevice = activityManager.isLowRamDevice();
    }

    public final void close() {
        CallbackToFutureAdapter.SafeFuture safeFuture = this.lastScrollCaptureRequest;
        if (safeFuture != null) {
            safeFuture.cancel(true);
        }
        this.lastScrollCaptureRequest = null;
        ScrollCaptureResponse scrollCaptureResponse = this.lastScrollCaptureResponse;
        if (scrollCaptureResponse != null) {
            scrollCaptureResponse.close();
        }
        this.lastScrollCaptureResponse = null;
        CallbackToFutureAdapter.SafeFuture safeFuture2 = this.longScreenshotFuture;
        if (safeFuture2 != null) {
            safeFuture2.cancel(true);
        }
    }
}
