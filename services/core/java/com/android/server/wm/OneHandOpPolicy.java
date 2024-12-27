package com.android.server.wm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Slog;
import android.view.IInputFilter;
import android.view.MagnificationSpec;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.onehandop.IOneHandOpWatcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OneHandOpPolicy {
    public boolean mBootCompleted;
    public Context mContext;
    public Handler mHandler;
    public boolean mHasOneHandOpSpec;
    public boolean mIsOneHandOpEnabled;
    public OneHandOpMonitor mOneHandOpMonitor;
    public int mReasonToStart;
    public OneHandOpPolicy$$ExternalSyntheticLambda0 mRestartRunnable;
    public WindowManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OneHandOpMonitor implements IBinder.DeathRecipient {
        public IOneHandOpWatcher mWatcher;

        public OneHandOpMonitor() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z;
            try {
                WindowManagerGlobalLock windowManagerGlobalLock =
                        OneHandOpPolicy.this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        IOneHandOpWatcher iOneHandOpWatcher = this.mWatcher;
                        if (iOneHandOpWatcher != null) {
                            iOneHandOpWatcher.asBinder().unlinkToDeath(this, 0);
                            this.mWatcher = null;
                            Slog.e("OneHandOpPolicy", "OneHandOp service has died unexpectedly");
                            MagnificationSpec magnificationSpec = new MagnificationSpec();
                            magnificationSpec.initialize(
                                    1.0f,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                            z = true;
                            OneHandOpPolicy.this.changeDisplayScaleLocked(
                                    magnificationSpec, true, null);
                        } else {
                            z = false;
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                if (z) {
                    Settings.System.putInt(
                            OneHandOpPolicy.this.mContext.getContentResolver(),
                            "any_screen_running",
                            0);
                    OneHandOpPolicy oneHandOpPolicy = OneHandOpPolicy.this;
                    if (oneHandOpPolicy.mIsOneHandOpEnabled) {
                        oneHandOpPolicy.startService(3);
                        OneHandOpPolicy oneHandOpPolicy2 = OneHandOpPolicy.this;
                        oneHandOpPolicy2.mHandler.postDelayed(
                                oneHandOpPolicy2.mRestartRunnable, 60000L);
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public static String startReasonToString(int i) {
        return i != -1
                ? i != 0
                        ? i != 1
                                ? i != 2
                                        ? i != 3
                                                ? Integer.toString(i)
                                                : "RESTART_ONE_HAND_OP_SERVICE"
                                        : "HOME_KEY_DOUBLE"
                                : "SETTING_OBSERVER"
                        : "SYSTEM_BOOTED"
                : "NONE";
    }

    public final void changeDisplayScaleLocked(
            MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Slog.d(
                        "OneHandOpPolicy",
                        "changeDisplayScale, mHasOneHandOpSpec="
                                + this.mHasOneHandOpSpec
                                + ", scale="
                                + magnificationSpec.scale
                                + ", offsetX="
                                + magnificationSpec.offsetX
                                + ", offsetY="
                                + magnificationSpec.offsetY
                                + ", registerInput="
                                + z
                                + ", filter="
                                + iInputFilter);
                DisplayContent defaultDisplayContentLocked =
                        this.mService.getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked == null) {
                    Slog.d("OneHandOpPolicy", "changeDisplayScale, display is null");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                float f = magnificationSpec.scale;
                if (FullScreenMagnificationGestureHandler.MAX_SCALE >= f || f == 1.0f) {
                    if (this.mHasOneHandOpSpec) {
                        this.mService.applyMagnificationSpecLocked(
                                defaultDisplayContentLocked.mDisplayId, magnificationSpec);
                        this.mHasOneHandOpSpec = false;
                        this.mService.mInputManager.setInputFilter(null);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
                MagnificationSpec magnificationSpec2 =
                        defaultDisplayContentLocked.mMagnificationSpec;
                if (magnificationSpec2 != null) {
                    if (!this.mHasOneHandOpSpec) {
                        Slog.d(
                                "OneHandOpPolicy",
                                "changeDisplayScale, other requested spec exists");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    boolean z2 = magnificationSpec2.scale != f;
                    boolean z3 =
                            (magnificationSpec2.offsetX == magnificationSpec.offsetX
                                            && magnificationSpec2.offsetY
                                                    == magnificationSpec.offsetY)
                                    ? false
                                    : true;
                    if (!z2 && !z3) {
                        Slog.d(
                                "OneHandOpPolicy",
                                "changeDisplayScale, the requested scale & offset are same");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                }
                this.mHasOneHandOpSpec = true;
                this.mService.applyMagnificationSpecLocked(
                        defaultDisplayContentLocked.mDisplayId, magnificationSpec);
                if (z) {
                    this.mService.mInputManager.setInputFilter(iInputFilter);
                }
                this.mService.scheduleAnimationLocked();
                defaultDisplayContentLocked.mInputMonitor.updateInputWindowsLw(true);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void startService(final int i) {
        Slog.d(
                "OneHandOpPolicy",
                "startService() reason="
                        + startReasonToString(i)
                        + ", caller="
                        + Debug.getCallers(3));
        this.mReasonToStart = i;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.OneHandOpPolicy$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        OneHandOpPolicy oneHandOpPolicy = OneHandOpPolicy.this;
                        int i2 = i;
                        oneHandOpPolicy.getClass();
                        try {
                            Intent intent = new Intent("com.samsung.action.EASYONEHAND_SERVICE");
                            intent.setComponent(
                                    new ComponentName(
                                            "com.sec.android.easyonehand",
                                            "com.sec.android.easyonehand.EasyOneHandService"));
                            intent.putExtra("StartByHomeKey", i2 == 2);
                            oneHandOpPolicy.mContext.startService(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
