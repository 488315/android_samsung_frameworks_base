package com.android.server.display;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Display;

import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

public final class EarlyWakeUpManager {
    public boolean mAppliedLocked;
    public final AutomaticBrightnessController mAutomaticBrightnessController;
    public final DisplayBlanker mBlanker;
    public final DisplayPowerController.AnonymousClass4 mCallbacks;
    public final int mDisplayId;
    public boolean mEarlyDisplayEnabled;
    public boolean mEarlyLightSensorEnabled;
    public final EarlyWakeUpHandler mHandler;
    public boolean mHoldingSuspendBlocker;
    public boolean mIsRequestInvalidated;
    public long mLastEnableRequestedTime;
    public final String mTag;
    public final WakelockController mWakelockController;
    public final Object mEarlyWakeUpLock = new Object();
    public boolean mEarlyLightSensorReadyLocked = true;
    public boolean mEarlyDisplayReadyLocked = true;
    public final AnonymousClass1 mEarlyLightSensorReadyListener =
            new Runnable() { // from class: com.android.server.display.EarlyWakeUpManager.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (EarlyWakeUpManager.this.mEarlyWakeUpLock) {
                        EarlyWakeUpManager earlyWakeUpManager = EarlyWakeUpManager.this;
                        earlyWakeUpManager.mEarlyLightSensorReadyLocked = true;
                        earlyWakeUpManager.updateSuspendBlockerLocked();
                    }
                }
            };

    public final class EarlyWakeUpHandler extends Handler {
        public EarlyWakeUpHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            EarlyWakeUpManager earlyWakeUpManager = EarlyWakeUpManager.this;
            Slog.d(earlyWakeUpManager.mTag, "[ew] MSG_EARLY_WAKEUP_TIMEOUT");
            DisplayPowerController.this.sendUpdatePowerState();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.display.EarlyWakeUpManager$1] */
    public EarlyWakeUpManager(
            String str,
            int i,
            DisplayPowerController.AnonymousClass4 anonymousClass4,
            DisplayBlanker displayBlanker,
            AutomaticBrightnessController automaticBrightnessController,
            WakelockController wakelockController) {
        this.mTag = str;
        this.mDisplayId = i;
        this.mCallbacks = anonymousClass4;
        this.mBlanker = displayBlanker;
        this.mAutomaticBrightnessController = automaticBrightnessController;
        this.mWakelockController = wakelockController;
        HandlerThread handlerThread = new HandlerThread(str, -4);
        handlerThread.start();
        this.mHandler = new EarlyWakeUpHandler(handlerThread.getLooper());
    }

    public final boolean isEarlyLightSensorEnabled() {
        return this.mEarlyLightSensorEnabled;
    }

    public final void setEarlyDisplayEnabledLocked(int i, final boolean z) {
        if (z == this.mEarlyDisplayEnabled) {
            return;
        }
        if (z
                && (Display.isDozeState(i)
                        || PowerManagerUtil.SECURITY_FINGERPRINT_IN_DISPLAY
                        || PowerManagerUtil.SEC_FEATURE_SUPPORT_AOD_LIVE_CLOCK)) {
            return;
        }
        this.mEarlyDisplayEnabled = z;
        this.mEarlyDisplayReadyLocked = false;
        this.mHandler.post(
                new Runnable() { // from class:
                    // com.android.server.display.EarlyWakeUpManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EarlyWakeUpManager earlyWakeUpManager = EarlyWakeUpManager.this;
                        boolean z2 = z;
                        earlyWakeUpManager.getClass();
                        int i2 = z2 ? 2 : 0;
                        DisplayBlanker displayBlanker = earlyWakeUpManager.mBlanker;
                        int i3 = earlyWakeUpManager.mDisplayId;
                        DisplayManagerService.AnonymousClass1 anonymousClass1 =
                                (DisplayManagerService.AnonymousClass1) displayBlanker;
                        synchronized (anonymousClass1) {
                            synchronized (DisplayManagerService.this.mRequestDisplayStateLock) {
                                try {
                                    synchronized (DisplayManagerService.this.mSyncRoot) {
                                        int indexOfKey =
                                                DisplayManagerService.this.mDisplayStateOverrides
                                                        .indexOfKey(i3);
                                        if (DisplayManagerService.this.mDisplayStateOverrides
                                                        .valueAt(indexOfKey)
                                                != i2) {
                                            Slog.d(
                                                    "DisplayManagerService",
                                                    "setDisplayStateOverrideForEarlyWakeUp :"
                                                        + " stateOverride="
                                                            + Display.stateToString(i2)
                                                            + " displayId="
                                                            + i3);
                                            DisplayManagerService.this.mDisplayStateOverrides
                                                    .setValueAt(indexOfKey, i2);
                                            DisplayManagerService displayManagerService =
                                                    DisplayManagerService.this;
                                            Runnable updateDisplayStateLocked =
                                                    displayManagerService.updateDisplayStateLocked(
                                                            displayManagerService
                                                                    .mLogicalDisplayMapper
                                                                    .getDisplayLocked(i3, true)
                                                                    .mPrimaryDisplayDevice);
                                            if (updateDisplayStateLocked != null) {
                                                updateDisplayStateLocked.run();
                                            }
                                        } else {
                                            Slog.d(
                                                    "DisplayManagerService",
                                                    "setDisplayStateOverrideForEarlyWakeUp:"
                                                        + " sameRequest "
                                                            + Display.stateToString(i2));
                                        }
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                        synchronized (earlyWakeUpManager.mEarlyWakeUpLock) {
                            earlyWakeUpManager.mEarlyDisplayReadyLocked = true;
                            earlyWakeUpManager.updateSuspendBlockerLocked();
                        }
                    }
                });
    }

    public final void setEarlyLightSensorEnabledLocked(boolean z, boolean z2, boolean z3) {
        AutomaticBrightnessController automaticBrightnessController =
                this.mAutomaticBrightnessController;
        if (automaticBrightnessController == null) {
            return;
        }
        if ((z && z2 && !z3) || z == this.mEarlyLightSensorEnabled) {
            return;
        }
        this.mEarlyLightSensorEnabled = z;
        this.mEarlyLightSensorReadyLocked = false;
        if (z != automaticBrightnessController.mShouldApplyEarlyWakeUp) {
            automaticBrightnessController.mShouldApplyEarlyWakeUp = z;
            automaticBrightnessController.mPendingEarlyLightSensorReadyListener =
                    this.mEarlyLightSensorReadyListener;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002d A[Catch: all -> 0x0042, TryCatch #0 {all -> 0x0042, blocks: (B:4:0x0005, B:6:0x000e, B:8:0x0012, B:10:0x0018, B:19:0x002d, B:20:0x0045, B:22:0x0049, B:24:0x005c, B:26:0x006b, B:27:0x0077, B:28:0x0063, B:29:0x0079, B:30:0x007e), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(int r12, boolean r13, boolean r14, boolean r15) {
        /*
            r11 = this;
            java.lang.String r0 = "[ew] "
            java.lang.Object r1 = r11.mEarlyWakeUpLock
            monitor-enter(r1)
            long r2 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L42
            r4 = 2
            r5 = 1
            r6 = 0
            if (r12 == r4) goto L44
            boolean r4 = r11.mIsRequestInvalidated     // Catch: java.lang.Throwable -> L42
            if (r4 != 0) goto L44
            boolean r4 = r11.mAppliedLocked     // Catch: java.lang.Throwable -> L42
            r7 = 3000(0xbb8, double:1.482E-320)
            if (r4 == 0) goto L22
            long r9 = r11.mLastEnableRequestedTime     // Catch: java.lang.Throwable -> L42
            long r9 = r9 + r7
            int r4 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r4 < 0) goto L20
            goto L22
        L20:
            r4 = r5
            goto L23
        L22:
            r4 = r6
        L23:
            if (r13 != 0) goto L2a
            if (r4 == 0) goto L28
            goto L2a
        L28:
            r4 = r6
            goto L2b
        L2a:
            r4 = r5
        L2b:
            if (r13 == 0) goto L45
            r11.mLastEnableRequestedTime = r2     // Catch: java.lang.Throwable -> L42
            java.lang.String r13 = r11.mTag     // Catch: java.lang.Throwable -> L42
            java.lang.String r2 = "[ew] resetEnableRequestTimeout +"
            com.android.server.power.Slog.d(r13, r2)     // Catch: java.lang.Throwable -> L42
            com.android.server.display.EarlyWakeUpManager$EarlyWakeUpHandler r13 = r11.mHandler     // Catch: java.lang.Throwable -> L42
            r13.removeMessages(r5)     // Catch: java.lang.Throwable -> L42
            long r2 = r11.mLastEnableRequestedTime     // Catch: java.lang.Throwable -> L42
            long r2 = r2 + r7
            r13.sendEmptyMessageAtTime(r5, r2)     // Catch: java.lang.Throwable -> L42
            goto L45
        L42:
            r11 = move-exception
            goto L80
        L44:
            r4 = r6
        L45:
            boolean r13 = r11.mAppliedLocked     // Catch: java.lang.Throwable -> L42
            if (r4 == r13) goto L79
            java.lang.String r13 = r11.mTag     // Catch: java.lang.Throwable -> L42
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L42
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L42
            r2.append(r4)     // Catch: java.lang.Throwable -> L42
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L42
            com.android.server.power.Slog.d(r13, r0)     // Catch: java.lang.Throwable -> L42
            if (r4 == 0) goto L63
            r11.setEarlyLightSensorEnabledLocked(r5, r14, r15)     // Catch: java.lang.Throwable -> L42
            r11.setEarlyDisplayEnabledLocked(r12, r5)     // Catch: java.lang.Throwable -> L42
            goto L69
        L63:
            r11.setEarlyLightSensorEnabledLocked(r6, r14, r15)     // Catch: java.lang.Throwable -> L42
            r11.setEarlyDisplayEnabledLocked(r12, r6)     // Catch: java.lang.Throwable -> L42
        L69:
            if (r4 != 0) goto L77
            java.lang.String r12 = r11.mTag     // Catch: java.lang.Throwable -> L42
            java.lang.String r13 = "[ew] clearEnableRequestTimeout -"
            com.android.server.power.Slog.d(r12, r13)     // Catch: java.lang.Throwable -> L42
            com.android.server.display.EarlyWakeUpManager$EarlyWakeUpHandler r12 = r11.mHandler     // Catch: java.lang.Throwable -> L42
            r12.removeMessages(r5)     // Catch: java.lang.Throwable -> L42
        L77:
            r11.mAppliedLocked = r4     // Catch: java.lang.Throwable -> L42
        L79:
            r11.mIsRequestInvalidated = r6     // Catch: java.lang.Throwable -> L42
            r11.updateSuspendBlockerLocked()     // Catch: java.lang.Throwable -> L42
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L42
            return
        L80:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L42
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.display.EarlyWakeUpManager.update(int,"
                    + " boolean, boolean, boolean):void");
    }

    public final void updateSuspendBlockerLocked() {
        boolean z = this.mAppliedLocked;
        WakelockController wakelockController = this.mWakelockController;
        String str = this.mTag;
        if (!z && this.mEarlyDisplayReadyLocked && this.mEarlyLightSensorReadyLocked) {
            if (this.mHoldingSuspendBlocker) {
                Slog.d(str, "[ew] releaseSuspendBlocker: -");
                this.mHoldingSuspendBlocker = false;
                wakelockController.releaseWakelockInternal(7);
                return;
            }
            return;
        }
        if (this.mHoldingSuspendBlocker) {
            return;
        }
        Slog.d(str, "[ew] acquireSuspendBlocker: +");
        wakelockController.acquireWakelock(7);
        this.mHoldingSuspendBlocker = true;
    }
}
