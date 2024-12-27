package com.android.server.power;

import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.PowerManagerInternal;
import android.os.SystemClock;

import com.android.server.input.InputManagerService;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerGroup {
    public final DisplayManagerInternal mDisplayManagerInternal;
    final DisplayManagerInternal.DisplayPowerRequest mDisplayPowerRequest;
    public final int mGroupId;
    public boolean mIsSandmanSummoned;
    public int mLastGoToSleepReason;
    public long mLastPowerOnTime;
    public long mLastSleepTime;
    public int mLastUserActivityEvent;
    public long mLastUserActivityTime;
    public long mLastUserActivityTimeNoChangeLights;
    public long mLastWakeTime;
    public final Notifier mNotifier;
    public boolean mPoweringOn;
    public boolean mReady;
    public final boolean mSupportsSandman;
    public final SuspendBlockerMonitor mSuspendBlockerMonitor;
    public int mUserActivitySummary;
    public int mWakeLockSummary;
    public int mWakefulness;
    public final PowerManagerService.AnonymousClass1 mWakefulnessListener;

    public PowerGroup(
            int i,
            PowerManagerService.AnonymousClass1 anonymousClass1,
            Notifier notifier,
            DisplayManagerInternal displayManagerInternal,
            boolean z,
            long j) {
        this.mDisplayPowerRequest = new DisplayManagerInternal.DisplayPowerRequest();
        this.mSuspendBlockerMonitor = new SuspendBlockerMonitor();
        this.mGroupId = i;
        this.mWakefulnessListener = anonymousClass1;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = 1;
        this.mReady = false;
        this.mSupportsSandman = z;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    public PowerGroup(
            PowerManagerService.AnonymousClass1 anonymousClass1,
            Notifier notifier,
            DisplayManagerInternal displayManagerInternal,
            long j) {
        this.mDisplayPowerRequest = new DisplayManagerInternal.DisplayPowerRequest();
        this.mSuspendBlockerMonitor = new SuspendBlockerMonitor();
        this.mGroupId = 0;
        this.mWakefulnessListener = anonymousClass1;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = 1;
        this.mReady = false;
        this.mSupportsSandman = true;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    public int getDesiredScreenPolicyLocked(
            boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return getDesiredScreenPolicyLocked(z, z2, z3, z4, z5, -1L);
    }

    public int getDesiredScreenPolicyLocked(
            boolean z, boolean z2, boolean z3, boolean z4, boolean z5, long j) {
        int i = this.mWakefulness;
        int i2 = this.mWakeLockSummary;
        if (i == 0 || z) {
            return 0;
        }
        if (i == 3) {
            if (this.mGroupId == 2) {
                return 0;
            }
            if ((i2 & 64) != 0) {
                return 1;
            }
            if (z2) {
                return 0;
            }
            if (z5) {
                return 3;
            }
        }
        if ((i2 & 2) != 0 || !z3 || (1 & this.mUserActivitySummary) != 0 || z4) {
            return 3;
        }
        if (j == 0 && (i2 & 4) == 0 && this.mDisplayPowerRequest.isBrightOrDim()) {
            return this.mDisplayPowerRequest.policy;
        }
        return 2;
    }

    public final String getSuspendBlockerMonitorInfo() {
        SuspendBlockerMonitor suspendBlockerMonitor = this.mSuspendBlockerMonitor;
        if ((suspendBlockerMonitor.mEvents & 240) == 0) {
            return "";
        }
        return "    [Group] Id = "
                + String.valueOf(this.mGroupId)
                + suspendBlockerMonitor.toString();
    }

    public final void setWakefulnessLocked(
            int i, long j, int i2, int i3, int i4, String str, String str2) {
        PowerManagerService.AnonymousClass1 anonymousClass1;
        int i5 = this.mWakefulness;
        if (i5 != i) {
            if (i == 1) {
                this.mLastPowerOnTime = j;
                this.mPoweringOn = true;
                this.mLastWakeTime = j;
            } else if (PowerManagerInternal.isInteractive(i5)
                    && !PowerManagerInternal.isInteractive(i)) {
                this.mLastSleepTime = j;
            }
            this.mWakefulness = i;
            PowerManagerService.AnonymousClass1 anonymousClass12 = this.mWakefulnessListener;
            PowerManagerService powerManagerService = PowerManagerService.this;
            powerManagerService.mWakefulnessChanging = true;
            powerManagerService.mDirty |= 2;
            int i6 = this.mGroupId;
            if (i == 1) {
                powerManagerService.userActivityNoUpdateLocked(
                        (PowerGroup) powerManagerService.mPowerGroups.get(i6),
                        j,
                        0,
                        i3 == 13 ? 1 : 0,
                        i2);
            }
            PowerManagerService powerManagerService2 = PowerManagerService.this;
            ScreenTimeoutOverridePolicy screenTimeoutOverridePolicy =
                    powerManagerService2.mScreenTimeoutOverridePolicy;
            if (screenTimeoutOverridePolicy != null
                    && i6 == 0
                    && (powerManagerService2.mWakeLockSummary & 256) != 0
                    && i != 1) {
                screenTimeoutOverridePolicy.releaseAllWakeLocks(1);
            }
            powerManagerService2.mDirty |=
                    EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            if (i6 == 0) {
                InputManagerService.this.mNative.setInteractiveForInternalDisplay(
                        PowerManagerInternal.isInteractive(i));
                if (powerManagerService2.mIsDualViewMode
                        && !PowerManagerInternal.isInteractive(i)
                        && i3 == 4) {
                    Handler handler = powerManagerService2.mHandlerPmsMisc;
                    PowerManagerService$LocalService$$ExternalSyntheticLambda0
                            powerManagerService$LocalService$$ExternalSyntheticLambda0 =
                                    new PowerManagerService$LocalService$$ExternalSyntheticLambda0(
                                            2, anonymousClass12);
                    powerManagerService2.mClock.getClass();
                    handler.postAtTime(
                            powerManagerService$LocalService$$ExternalSyntheticLambda0,
                            SystemClock.uptimeMillis());
                }
                if (powerManagerService2.mSmartStayEnabledSetting) {
                    SmartStayController smartStayController =
                            powerManagerService2.mSmartStayController;
                    smartStayController.mWakefulness = i;
                    if (i != 1) {
                        smartStayController.mFaceDetectRequested.set(false);
                        smartStayController.mFaceDetected = false;
                    }
                }
                ScreenCurtainController screenCurtainController =
                        powerManagerService2.mScreenCurtainController;
                if (screenCurtainController != null) {
                    screenCurtainController.mWakefulness = i;
                }
                ScreenOnKeeper screenOnKeeper = powerManagerService2.mScreenOnKeeper;
                if (screenOnKeeper != null) {
                    Slog.d(
                            "ScreenOnKeeper",
                            "onWakefulnessChangedLocked: wakefulness="
                                    + PowerManagerInternal.wakefulnessToString(i));
                    screenOnKeeper.mWakefulness = i;
                    if (screenOnKeeper.mIsScreenOnKeeperEnabled
                            && !PowerManagerInternal.isInteractive(i)) {
                        screenOnKeeper.disableScreenOnKeeper();
                        screenOnKeeper.notifyScreenOnKeeperDisabledLocked(0);
                    }
                }
            }
            powerManagerService2.mNotifier.onGroupWakefulnessChangeStarted(i6, i, i3, j);
            if (PowerManagerUtil.SEC_FEATURE_DEX_DUAL_VIEW
                    && i6 == 0
                    && i == 1
                    && powerManagerService2.mIsDualViewMode
                    && powerManagerService2.mPowerGroups.contains(2)) {
                anonymousClass1 = anonymousClass12;
                powerManagerService2.wakePowerGroupLocked(
                        (PowerGroup) powerManagerService2.mPowerGroups.get(2),
                        j,
                        11,
                        str2,
                        i2,
                        str,
                        i4,
                        false);
                powerManagerService2.userActivityNoUpdateLocked(
                        (PowerGroup) powerManagerService2.mPowerGroups.get(2), j, 0, 0, i2);
            } else {
                anonymousClass1 = anonymousClass12;
            }
            if (CoreRune.CARLIFE_DISPLAY_GROUP
                    && i6 == 0
                    && powerManagerService2.mPowerGroups.contains(4)) {
                powerManagerService2.wakePowerGroupLocked(
                        (PowerGroup) powerManagerService2.mPowerGroups.get(4),
                        j,
                        11,
                        str2,
                        i2,
                        str,
                        i4,
                        false);
                powerManagerService2.userActivityNoUpdateLocked(
                        (PowerGroup) powerManagerService2.mPowerGroups.get(4), j, 0, 0, i2);
            }
            PowerManagerService.this.updateGlobalWakefulnessLocked(i3, i2, str, str2, i4, j);
            powerManagerService2.updatePowerStateLocked();
        }
    }
}
