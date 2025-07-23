package com.android.internal.view;

import android.util.EventLog;
import android.view.SurfaceHolder;

/* loaded from: classes4.dex */
public class SurfaceCallbackHelper {
    private static final int LOGTAG_SURFACEVIEW_CALLBACK = 60006;
    int mFinishDrawingCollected;
    int mFinishDrawingExpected;
    private Runnable mFinishDrawingRunnable;
    Runnable mRunnable;
    private boolean mSurfaceRedrawImplemented;
    private final String mTag;

    public SurfaceCallbackHelper(Runnable runnable) {
        this(runnable, null);
    }

    public SurfaceCallbackHelper(Runnable runnable, String str) {
        this.mFinishDrawingCollected = 0;
        this.mFinishDrawingExpected = 0;
        this.mFinishDrawingRunnable = new Runnable() { // from class: com.android.internal.view.SurfaceCallbackHelper.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SurfaceCallbackHelper.this) {
                    SurfaceCallbackHelper.this.mFinishDrawingCollected++;
                    if (SurfaceCallbackHelper.this.mFinishDrawingCollected < SurfaceCallbackHelper.this.mFinishDrawingExpected) {
                        return;
                    }
                    SurfaceCallbackHelper.this.mRunnable.run();
                    if (SurfaceCallbackHelper.this.mSurfaceRedrawImplemented && SurfaceCallbackHelper.this.mTag != null) {
                        EventLog.writeEvent(60006, SurfaceCallbackHelper.this.mTag, "surfaceRedrawNeeded implemented");
                    }
                }
            }
        };
        this.mRunnable = runnable;
        this.mTag = str;
        this.mSurfaceRedrawImplemented = false;
    }

    public void dispatchSurfaceRedrawNeededAsync(SurfaceHolder surfaceHolder, SurfaceHolder.Callback[] callbackArr) {
        int i;
        if (callbackArr == null || callbackArr.length == 0) {
            this.mRunnable.run();
            return;
        }
        synchronized (this) {
            this.mFinishDrawingExpected = callbackArr.length;
            this.mFinishDrawingCollected = 0;
        }
        for (SurfaceHolder.Callback callback : callbackArr) {
            if (callback instanceof SurfaceHolder.Callback2) {
                ((SurfaceHolder.Callback2) callback).surfaceRedrawNeededAsync(surfaceHolder, this.mFinishDrawingRunnable);
                this.mSurfaceRedrawImplemented = true;
            } else {
                this.mFinishDrawingRunnable.run();
            }
        }
    }
}
