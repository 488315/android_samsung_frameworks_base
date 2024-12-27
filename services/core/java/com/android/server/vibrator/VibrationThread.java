package com.android.server.vibrator;

import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.vibrator.Vibration;
import com.android.server.vibrator.VibrationStats;
import com.android.server.vibrator.VibratorManagerService;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibrationThread extends Thread {
    public VibrationStepConductor mExecutingConductor;
    public VibrationStepConductor mRequestedActiveConductor;
    public final VibratorManagerService.VibrationThreadCallbacks mVibratorManagerHooks;
    public final PowerManager.WakeLock mWakeLock;
    public final Object mLock = new Object();
    public boolean mCalledVibrationCompleteCallback = false;

    public VibrationThread(PowerManager.WakeLock wakeLock, VibratorManagerService.VibrationThreadCallbacks vibrationThreadCallbacks) {
        this.mWakeLock = wakeLock;
        this.mVibratorManagerHooks = vibrationThreadCallbacks;
    }

    public final void clientVibrationCompleteIfNotAlready(Vibration.EndInfo endInfo) {
        if (this.mCalledVibrationCompleteCallback) {
            return;
        }
        this.mCalledVibrationCompleteCallback = true;
        VibratorManagerService.VibrationThreadCallbacks vibrationThreadCallbacks = this.mVibratorManagerHooks;
        long j = this.mExecutingConductor.mVibration.id;
        vibrationThreadCallbacks.getClass();
        Slog.d("VibratorManagerService", "Vibration " + j + " finished with " + endInfo);
        synchronized (((VibratorManagerService) vibrationThreadCallbacks.this$0).mLock) {
            try {
                VibratorManagerService vibratorManagerService = (VibratorManagerService) vibrationThreadCallbacks.this$0;
                VibrationStepConductor vibrationStepConductor = vibratorManagerService.mCurrentVibration;
                if (vibrationStepConductor != null && vibrationStepConductor.mVibration.id == j) {
                    VibratorManagerService.m1032$$Nest$mreportFinishedVibrationLocked(vibratorManagerService, endInfo);
                }
            } finally {
            }
        }
    }

    public boolean isRunningVibrationId(long j) {
        boolean z;
        synchronized (this.mLock) {
            try {
                VibrationStepConductor vibrationStepConductor = this.mRequestedActiveConductor;
                z = vibrationStepConductor != null && vibrationStepConductor.mVibration.id == j;
            } finally {
            }
        }
        return z;
    }

    public final void playVibration() {
        Trace.traceBegin(8388608L, "playVibration");
        try {
            this.mExecutingConductor.prepareToStart();
            while (!this.mExecutingConductor.isFinished()) {
                if (this.mExecutingConductor.waitUntilNextStepIsDue()) {
                    this.mExecutingConductor.runNextStep();
                }
                if (!this.mCalledVibrationCompleteCallback) {
                    VibrationStepConductor vibrationStepConductor = this.mExecutingConductor;
                    vibrationStepConductor.getClass();
                    if (Build.IS_DEBUGGABLE) {
                        VibrationStepConductor.expectIsVibrationThread(true);
                    }
                    Vibration.EndInfo endInfo = vibrationStepConductor.mCancelledVibrationEndInfo;
                    if (endInfo == null) {
                        if (vibrationStepConductor.mPendingVibrateSteps <= 0 && vibrationStepConductor.mRemainingStartSequentialEffectSteps <= 0) {
                            endInfo = vibrationStepConductor.mSuccessfulVibratorOnSteps > 0 ? new Vibration.EndInfo(Vibration.Status.FINISHED, null) : new Vibration.EndInfo(Vibration.Status.IGNORED_UNSUPPORTED, null);
                        }
                        endInfo = null;
                    }
                    if (endInfo != null) {
                        clientVibrationCompleteIfNotAlready(endInfo);
                    }
                }
            }
            Trace.traceEnd(8388608L);
        } catch (Throwable th) {
            Trace.traceEnd(8388608L);
            throw th;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        VibrationStepConductor vibrationStepConductor;
        Process.setThreadPriority(-8);
        while (true) {
            synchronized (this.mLock) {
                VibrationStepConductor vibrationStepConductor2 = this.mRequestedActiveConductor;
                if (vibrationStepConductor2 != null) {
                    this.mExecutingConductor = vibrationStepConductor2;
                    this.mCalledVibrationCompleteCallback = false;
                    Vibration.Status status = Vibration.Status.FINISHED_UNEXPECTED;
                    this.mWakeLock.setWorkSource(new WorkSource(this.mExecutingConductor.mVibration.callerInfo.uid));
                    this.mWakeLock.acquire();
                    try {
                        try {
                            runCurrentVibrationWithWakeLockAndDeathLink();
                            clientVibrationCompleteIfNotAlready(new Vibration.EndInfo(status, null));
                            this.mWakeLock.release();
                            this.mWakeLock.setWorkSource(null);
                            if (!this.mExecutingConductor.isFinished()) {
                                Slog.wtf("VibrationThread", "VibrationThread terminated with unfinished vibration");
                            }
                            synchronized (this.mLock) {
                                this.mRequestedActiveConductor = null;
                            }
                            VibratorManagerService.VibrationThreadCallbacks vibrationThreadCallbacks = this.mVibratorManagerHooks;
                            long j = this.mExecutingConductor.mVibration.id;
                            vibrationThreadCallbacks.getClass();
                            Slog.d("VibratorManagerService", "VibrationThread released after finished vibration");
                            synchronized (((VibratorManagerService) vibrationThreadCallbacks.this$0).mLock) {
                                try {
                                    Slog.d("VibratorManagerService", "Processing VibrationThread released callback");
                                    if (Build.IS_DEBUGGABLE && (vibrationStepConductor = ((VibratorManagerService) vibrationThreadCallbacks.this$0).mCurrentVibration) != null) {
                                        long j2 = vibrationStepConductor.mVibration.id;
                                        if (j2 != j) {
                                            Slog.wtf("VibratorManagerService", TextUtils.formatSimple("VibrationId mismatch on release. expected=%d, released=%d", new Object[]{Long.valueOf(j2), Long.valueOf(j)}));
                                        }
                                    }
                                    VibratorManagerService vibratorManagerService = (VibratorManagerService) vibrationThreadCallbacks.this$0;
                                    VibrationStepConductor vibrationStepConductor3 = vibratorManagerService.mCurrentVibration;
                                    if (vibrationStepConductor3 != null) {
                                        VibratorFrameworkStatsLogger vibratorFrameworkStatsLogger = vibratorManagerService.mFrameworkStatsLogger;
                                        HalVibration halVibration = vibrationStepConductor3.mVibration;
                                        long uptimeMillis = SystemClock.uptimeMillis();
                                        int i = halVibration.isRepeating() ? 2 : 1;
                                        Vibration.CallerInfo callerInfo = halVibration.callerInfo;
                                        vibratorFrameworkStatsLogger.writeVibrationReportedAsync(new VibrationStats.StatsInfo(callerInfo.uid, i, callerInfo.attrs.getUsage(), halVibration.mStatus, halVibration.stats, uptimeMillis));
                                        ((VibratorManagerService) vibrationThreadCallbacks.this$0).mCurrentVibration = null;
                                    }
                                    VibratorManagerService vibratorManagerService2 = (VibratorManagerService) vibrationThreadCallbacks.this$0;
                                    VibrationStepConductor vibrationStepConductor4 = vibratorManagerService2.mNextVibration;
                                    if (vibrationStepConductor4 != null) {
                                        vibratorManagerService2.mNextVibration = null;
                                        Vibration.EndInfo startVibrationOnThreadLocked = vibratorManagerService2.startVibrationOnThreadLocked(vibrationStepConductor4);
                                        if (startVibrationOnThreadLocked != null) {
                                            ((VibratorManagerService) vibrationThreadCallbacks.this$0).endVibrationLocked(vibrationStepConductor4.mVibration, startVibrationOnThreadLocked, true);
                                        }
                                    }
                                    VibratorManagerService vibratorManagerService3 = (VibratorManagerService) vibrationThreadCallbacks.this$0;
                                    if (vibratorManagerService3.getDefaultVibratorController() != null) {
                                        VibratorController defaultVibratorController = vibratorManagerService3.getDefaultVibratorController();
                                        synchronized (defaultVibratorController.mLock) {
                                            defaultVibratorController.notifyListenerOnVibrating(false);
                                        }
                                    }
                                } finally {
                                }
                            }
                            synchronized (this.mLock) {
                                this.mLock.notifyAll();
                            }
                            this.mExecutingConductor = null;
                        } finally {
                        }
                    } catch (Throwable th) {
                        this.mWakeLock.release();
                        this.mWakeLock.setWorkSource(null);
                        throw th;
                    }
                } else {
                    try {
                        this.mLock.wait();
                    } catch (InterruptedException unused) {
                        Slog.w("VibrationThread", "VibrationThread interrupted waiting to start, continuing");
                    }
                }
            }
        }
    }

    public final void runCurrentVibrationWithWakeLockAndDeathLink() {
        VibrationStepConductor vibrationStepConductor = this.mExecutingConductor;
        IBinder iBinder = vibrationStepConductor.mVibration.callerToken;
        try {
            iBinder.linkToDeath(vibrationStepConductor, 0);
            try {
                playVibration();
                try {
                    iBinder.unlinkToDeath(this.mExecutingConductor, 0);
                } catch (NoSuchElementException e) {
                    Slog.wtf("VibrationThread", "Failed to unlink token", e);
                }
            } catch (Throwable th) {
                try {
                    iBinder.unlinkToDeath(this.mExecutingConductor, 0);
                } catch (NoSuchElementException e2) {
                    Slog.wtf("VibrationThread", "Failed to unlink token", e2);
                }
                throw th;
            }
        } catch (RemoteException e3) {
            Slog.e("VibrationThread", "Error linking vibration to token death", e3);
            clientVibrationCompleteIfNotAlready(new Vibration.EndInfo(Vibration.Status.IGNORED_ERROR_TOKEN, null));
        }
    }

    public final boolean waitForThreadIdle(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = j + elapsedRealtime;
        synchronized (this.mLock) {
            while (this.mRequestedActiveConductor != null) {
                try {
                    if (elapsedRealtime >= j2) {
                        return false;
                    }
                    try {
                        this.mLock.wait(j2 - elapsedRealtime);
                    } catch (InterruptedException unused) {
                        Slog.w("VibrationThread", "VibrationThread interrupted waiting to stop, continuing");
                    }
                    elapsedRealtime = SystemClock.elapsedRealtime();
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }
}
