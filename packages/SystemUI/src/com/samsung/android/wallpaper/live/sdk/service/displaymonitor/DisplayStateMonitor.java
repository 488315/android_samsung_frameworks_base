package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class DisplayStateMonitor {
    public static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
    public final Context mContext;
    public LiveWallpaperService.BaseEngine.AnonymousClass1 mListener;
    public DisplayState mLastReportedState = DisplayState.NONE;
    public boolean mIsFolded = false;
    public final AnonymousClass1 mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor.1
        public final void onFoldStateChanged(final boolean z) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            DisplayStateMonitor.this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor.1.1
                public boolean mIsWaitingTriggered = false;

                @Override // java.lang.Runnable
                public final void run() {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
                    if (elapsedRealtime2 < 200 && SdkFoldUtils.isFolded(DisplayStateMonitor.this.mContext) != z) {
                        if (DisplayStateMonitor.DEBUG) {
                            SdkLog.i("DisplayStateMonitor", "onFoldStateChanged : LID state not updated yet. will dispatch later. which=" + DisplayStateMonitor.this.getCurrentWhich() + ", folded=" + z);
                        }
                        this.mIsWaitingTriggered = true;
                        DisplayStateMonitor.this.mHandler.postDelayed(this, 10L);
                        return;
                    }
                    if (this.mIsWaitingTriggered) {
                        SdkLog.i("DisplayStateMonitor", "onFoldStateChanged : waiting finished. which=" + DisplayStateMonitor.this.getCurrentWhich() + ", folded=" + z + ", elapsed=" + elapsedRealtime2);
                    }
                    DisplayStateMonitor displayStateMonitor = DisplayStateMonitor.this;
                    boolean z2 = z;
                    displayStateMonitor.mIsFolded = z2;
                    synchronized (displayStateMonitor) {
                        try {
                            if (DisplayStateMonitor.DEBUG) {
                                SdkLog.d("DisplayStateMonitor", "onFoldingStateChanged: which=" + displayStateMonitor.getCurrentWhich() + ", folded=" + z2 + ", getCurrentMode=" + (displayStateMonitor.getCurrentWhich() & 60) + ", isInteractive=" + displayStateMonitor.isInteractive());
                            }
                            if (!z2 && (displayStateMonitor.getCurrentWhich() & 60) == 4 && displayStateMonitor.isInteractive()) {
                                displayStateMonitor.notifyDisplayStateChanged(DisplayState.ON);
                            } else if (z2 && (displayStateMonitor.getCurrentWhich() & 60) == 16 && displayStateMonitor.isInteractive()) {
                                displayStateMonitor.notifyDisplayStateChanged(DisplayState.ON);
                            } else if (displayStateMonitor.isMyDisplayDisabledByFoldState()) {
                                displayStateMonitor.notifyDisplayStateChanged(DisplayState.OFF);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }, 5L);
        }

        public final void onTableModeChanged(boolean z) {
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.wallpaper.live.sdk.service.displaymonitor.DisplayStateMonitor$1] */
    public DisplayStateMonitor(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public final int getCurrentWhich() {
        LiveWallpaperService.BaseEngine.AnonymousClass1 anonymousClass1 = this.mListener;
        if (anonymousClass1 != null) {
            return LiveWallpaperService.BaseEngine.this.getWhich();
        }
        return 0;
    }

    public abstract DisplayState getDisplayState();

    public final boolean isInteractive() {
        Context context = this.mContext;
        Method method = SdkDeviceUtils.sMethodIsDozeAfterScreenOff;
        if (context == null) {
            return false;
        }
        return ((PowerManager) context.getSystemService("power")).isInteractive();
    }

    public final boolean isMyDisplayDisabledByFoldState() {
        int currentWhich = getCurrentWhich() & 60;
        if (currentWhich == 4 || currentWhich == 16) {
            return !((currentWhich == 16) == this.mIsFolded);
        }
        return false;
    }

    public final synchronized void notifyDisplayStateChanged(DisplayState displayState) {
        try {
            DisplayState displayState2 = this.mLastReportedState;
            if (displayState == displayState2) {
                return;
            }
            LiveWallpaperService.BaseEngine.AnonymousClass1 anonymousClass1 = this.mListener;
            if (anonymousClass1 != null) {
                anonymousClass1.onDisplayStateChanged(displayState, displayState2);
            }
            this.mLastReportedState = displayState;
        } catch (Throwable th) {
            throw th;
        }
    }

    public abstract void onCommand(int i, String str);

    public void start(LiveWallpaperService.BaseEngine.AnonymousClass1 anonymousClass1) {
        this.mListener = anonymousClass1;
        this.mIsFolded = SdkFoldUtils.isFolded(this.mContext);
        this.mLastReportedState = getDisplayState();
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public void stop() {
        this.mListener = null;
        this.mLastReportedState = DisplayState.NONE;
        SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
    }
}
