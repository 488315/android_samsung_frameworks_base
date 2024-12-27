package com.android.server.wm;

import android.content.Context;
import android.os.Handler;
import android.util.Slog;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;

import com.android.server.wm.SystemPerformancePointerEventListener.PerformaneGestureDetector;

import com.samsung.android.os.SemDvfsManager;

public final class SystemPerformancePointerEventListener
        implements WindowManagerPolicyConstants.PointerEventListener {
    public final Context mContext;
    public AnonymousClass1 mGestureDetector;
    public final Handler mHandler;
    public long mLastAcquireTime;
    public final SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
            mScrollAcquireRunnable;
    public final SemDvfsManager mScrollDvfsManager;
    public final SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
            mScrollReleaseRunnable;

    /* renamed from: com.android.server.wm.SystemPerformancePointerEventListener$1, reason: invalid class name */
    public final class AnonymousClass1 extends GestureDetector {}

    public final class PerformaneGestureDetector extends GestureDetector.SimpleOnGestureListener {
        public PerformaneGestureDetector() {}

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(
                MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
                    systemPerformancePointerEventListener$$ExternalSyntheticLambda0;
            long currentTimeMillis = System.currentTimeMillis();
            if (SystemPerformancePointerEventListener.this.mContext.getDisplay().getRefreshRate()
                    < 90.0f) {
                return true;
            }
            SystemPerformancePointerEventListener systemPerformancePointerEventListener =
                    SystemPerformancePointerEventListener.this;
            if (systemPerformancePointerEventListener.mScrollDvfsManager == null
                    || currentTimeMillis - systemPerformancePointerEventListener.mLastAcquireTime
                            < 500
                    || (systemPerformancePointerEventListener$$ExternalSyntheticLambda0 =
                                    systemPerformancePointerEventListener.mScrollReleaseRunnable)
                            == null
                    || systemPerformancePointerEventListener.mScrollAcquireRunnable == null) {
                return true;
            }
            systemPerformancePointerEventListener.mHandler.removeCallbacks(
                    systemPerformancePointerEventListener$$ExternalSyntheticLambda0);
            SystemPerformancePointerEventListener systemPerformancePointerEventListener2 =
                    SystemPerformancePointerEventListener.this;
            systemPerformancePointerEventListener2.mHandler.post(
                    systemPerformancePointerEventListener2.mScrollAcquireRunnable);
            SystemPerformancePointerEventListener systemPerformancePointerEventListener3 =
                    SystemPerformancePointerEventListener.this;
            systemPerformancePointerEventListener3.mLastAcquireTime = currentTimeMillis;
            systemPerformancePointerEventListener3.mHandler.postDelayed(
                    systemPerformancePointerEventListener3.mScrollReleaseRunnable, 1000L);
            return true;
        }
    }

    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0] */
    public SystemPerformancePointerEventListener(Context context) {
        this.mContext = context;
        Handler handler = new Handler(context.getMainLooper());
        this.mHandler = handler;
        SemDvfsManager createInstance =
                SemDvfsManager.createInstance(context, "WM_SCROLL_DETECTED");
        this.mScrollDvfsManager = createInstance;
        if (createInstance != null) {
            int[] supportedFrequency = createInstance.getSupportedFrequency(268443651);
            if (supportedFrequency == null || supportedFrequency.length <= 0) {
                Slog.e(
                        "SystemPerformancePointerEventListener",
                        "SemDvfsManager Not Initialized...");
                this.mScrollDvfsManager = null;
            } else {
                createInstance.addResourceValue(268443651, supportedFrequency[0]);
                this.mLastAcquireTime = 0L;
                final int i = 0;
                this.mScrollReleaseRunnable =
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
                            public final /* synthetic */ SystemPerformancePointerEventListener f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i2 = i;
                                SystemPerformancePointerEventListener
                                        systemPerformancePointerEventListener = this.f$0;
                                switch (i2) {
                                    case 0:
                                        SemDvfsManager semDvfsManager =
                                                systemPerformancePointerEventListener
                                                        .mScrollDvfsManager;
                                        if (semDvfsManager != null) {
                                            semDvfsManager.release();
                                            systemPerformancePointerEventListener.mLastAcquireTime =
                                                    0L;
                                            break;
                                        }
                                        break;
                                    case 1:
                                        SemDvfsManager semDvfsManager2 =
                                                systemPerformancePointerEventListener
                                                        .mScrollDvfsManager;
                                        if (semDvfsManager2 != null) {
                                            semDvfsManager2.acquire();
                                            break;
                                        }
                                        break;
                                    default:
                                        systemPerformancePointerEventListener.getClass();
                                        systemPerformancePointerEventListener.mGestureDetector =
                                                new SystemPerformancePointerEventListener
                                                        .AnonymousClass1(
                                                        systemPerformancePointerEventListener
                                                                .mContext,
                                                        systemPerformancePointerEventListener
                                                        .new PerformaneGestureDetector(),
                                                        systemPerformancePointerEventListener
                                                                .mHandler);
                                        break;
                                }
                            }
                        };
                final int i2 = 1;
                this.mScrollAcquireRunnable =
                        new Runnable(
                                this) { // from class:
                                        // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
                            public final /* synthetic */ SystemPerformancePointerEventListener f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                int i22 = i2;
                                SystemPerformancePointerEventListener
                                        systemPerformancePointerEventListener = this.f$0;
                                switch (i22) {
                                    case 0:
                                        SemDvfsManager semDvfsManager =
                                                systemPerformancePointerEventListener
                                                        .mScrollDvfsManager;
                                        if (semDvfsManager != null) {
                                            semDvfsManager.release();
                                            systemPerformancePointerEventListener.mLastAcquireTime =
                                                    0L;
                                            break;
                                        }
                                        break;
                                    case 1:
                                        SemDvfsManager semDvfsManager2 =
                                                systemPerformancePointerEventListener
                                                        .mScrollDvfsManager;
                                        if (semDvfsManager2 != null) {
                                            semDvfsManager2.acquire();
                                            break;
                                        }
                                        break;
                                    default:
                                        systemPerformancePointerEventListener.getClass();
                                        systemPerformancePointerEventListener.mGestureDetector =
                                                new SystemPerformancePointerEventListener
                                                        .AnonymousClass1(
                                                        systemPerformancePointerEventListener
                                                                .mContext,
                                                        systemPerformancePointerEventListener
                                                        .new PerformaneGestureDetector(),
                                                        systemPerformancePointerEventListener
                                                                .mHandler);
                                        break;
                                }
                            }
                        };
            }
        }
        final int i3 = 2;
        handler.post(
                new Runnable(
                        this) { // from class:
                                // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
                    public final /* synthetic */ SystemPerformancePointerEventListener f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i3;
                        SystemPerformancePointerEventListener
                                systemPerformancePointerEventListener = this.f$0;
                        switch (i22) {
                            case 0:
                                SemDvfsManager semDvfsManager =
                                        systemPerformancePointerEventListener.mScrollDvfsManager;
                                if (semDvfsManager != null) {
                                    semDvfsManager.release();
                                    systemPerformancePointerEventListener.mLastAcquireTime = 0L;
                                    break;
                                }
                                break;
                            case 1:
                                SemDvfsManager semDvfsManager2 =
                                        systemPerformancePointerEventListener.mScrollDvfsManager;
                                if (semDvfsManager2 != null) {
                                    semDvfsManager2.acquire();
                                    break;
                                }
                                break;
                            default:
                                systemPerformancePointerEventListener.getClass();
                                systemPerformancePointerEventListener.mGestureDetector =
                                        new SystemPerformancePointerEventListener.AnonymousClass1(
                                                systemPerformancePointerEventListener.mContext,
                                                systemPerformancePointerEventListener
                                                .new PerformaneGestureDetector(),
                                                systemPerformancePointerEventListener.mHandler);
                                break;
                        }
                    }
                });
    }

    public final void onPointerEvent(MotionEvent motionEvent) {
        SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
                systemPerformancePointerEventListener$$ExternalSyntheticLambda0;
        if (this.mGestureDetector != null && motionEvent.isTouchEvent()) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if ((actionMasked != 1 && actionMasked != 3)
                || this.mScrollDvfsManager == null
                || (systemPerformancePointerEventListener$$ExternalSyntheticLambda0 =
                                this.mScrollReleaseRunnable)
                        == null) {
            return;
        }
        this.mHandler.removeCallbacks(
                systemPerformancePointerEventListener$$ExternalSyntheticLambda0);
        this.mScrollDvfsManager.release();
        this.mLastAcquireTime = 0L;
    }
}
