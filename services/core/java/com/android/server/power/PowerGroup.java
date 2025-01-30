package com.android.server.power;

import android.hardware.display.DisplayManagerInternal;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Trace;
import com.android.internal.util.LatencyTracker;

/* loaded from: classes3.dex */
public class PowerGroup {
    public static final String TAG = "PowerGroup";
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
    public SuspendBlockerMonitor mSuspendBlockerMonitor;
    public int mUserActivitySummary;
    public int mWakeLockSummary;
    public int mWakefulness;
    public final PowerGroupListener mWakefulnessListener;

    public interface PowerGroupListener {
        void onWakefulnessChangedLocked(int i, int i2, long j, int i3, int i4, int i5, String str, String str2);
    }

    public PowerGroup(int i, PowerGroupListener powerGroupListener, Notifier notifier, DisplayManagerInternal displayManagerInternal, int i2, boolean z, boolean z2, long j) {
        this.mDisplayPowerRequest = new DisplayManagerInternal.DisplayPowerRequest();
        this.mSuspendBlockerMonitor = new SuspendBlockerMonitor();
        this.mGroupId = i;
        this.mWakefulnessListener = powerGroupListener;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = i2;
        this.mReady = z;
        this.mSupportsSandman = z2;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    public PowerGroup(int i, PowerGroupListener powerGroupListener, Notifier notifier, DisplayManagerInternal displayManagerInternal, long j) {
        this.mDisplayPowerRequest = new DisplayManagerInternal.DisplayPowerRequest();
        this.mSuspendBlockerMonitor = new SuspendBlockerMonitor();
        this.mGroupId = 0;
        this.mWakefulnessListener = powerGroupListener;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = i;
        this.mReady = false;
        this.mSupportsSandman = true;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    public long getLastWakeTimeLocked() {
        return this.mLastWakeTime;
    }

    public long getLastSleepTimeLocked() {
        return this.mLastSleepTime;
    }

    public int getWakefulnessLocked() {
        return this.mWakefulness;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public boolean setWakefulnessLocked(int i, long j, int i2, int i3, int i4, String str, String str2) {
        int i5 = this.mWakefulness;
        if (i5 == i) {
            return false;
        }
        if (i == 1) {
            setLastPowerOnTimeLocked(j);
            setIsPoweringOnLocked(true);
            this.mLastWakeTime = j;
        } else if (PowerManagerInternal.isInteractive(i5) && !PowerManagerInternal.isInteractive(i)) {
            this.mLastSleepTime = j;
        }
        this.mWakefulness = i;
        this.mWakefulnessListener.onWakefulnessChangedLocked(this.mGroupId, i, j, i3, i2, i4, str, str2);
        return true;
    }

    public boolean isReadyLocked() {
        return this.mReady;
    }

    public boolean setReadyLocked(boolean z) {
        if (this.mReady == z) {
            return false;
        }
        this.mReady = z;
        return true;
    }

    public long getLastPowerOnTimeLocked() {
        return this.mLastPowerOnTime;
    }

    public void setLastPowerOnTimeLocked(long j) {
        this.mLastPowerOnTime = j;
    }

    public boolean isPoweringOnLocked() {
        return this.mPoweringOn;
    }

    public void setIsPoweringOnLocked(boolean z) {
        this.mPoweringOn = z;
    }

    public boolean isSandmanSummonedLocked() {
        return this.mIsSandmanSummoned;
    }

    public void setSandmanSummonedLocked(boolean z) {
        this.mIsSandmanSummoned = z;
    }

    public void wakeUpLocked(long j, int i, String str, int i2, String str2, int i3, LatencyTracker latencyTracker) {
        Trace.traceBegin(131072L, "wakePowerGroup" + this.mGroupId);
        try {
            Slog.m76i(TAG, "Waking up power group from " + PowerManagerInternal.wakefulnessToString(this.mWakefulness) + " (groupId=" + this.mGroupId + ", uid=" + i2 + ", reason=" + PowerManager.wakeReasonToString(i) + ", details=" + str + ")...");
            Trace.asyncTraceBegin(131072L, "Screen turning on", this.mGroupId);
            latencyTracker.onActionStart(5, String.valueOf(this.mGroupId));
            setWakefulnessLocked(1, j, i2, i, i3, str2, str);
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public boolean dreamLocked(long j, int i, boolean z) {
        if (j < this.mLastWakeTime) {
            return false;
        }
        if (!z && this.mWakefulness != 1) {
            return false;
        }
        Trace.traceBegin(131072L, "dreamPowerGroup" + getGroupId());
        try {
            Slog.m76i(TAG, "Napping power group (groupId=" + getGroupId() + ", uid=" + i + ")...");
            setSandmanSummonedLocked(true);
            setWakefulnessLocked(2, j, i, 0, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public boolean dozeLocked(long j, int i, int i2) {
        Trace.traceBegin(131072L, "powerOffDisplay");
        try {
            int min = Math.min(25, Math.max(i2, 0));
            long max = j - Math.max(this.mLastUserActivityTimeNoChangeLights, this.mLastUserActivityTime);
            Slog.m76i(TAG, "Powering off display group due to " + PowerManager.sleepReasonToString(min) + " (groupId= " + getGroupId() + ", uid= " + i + ", millisSinceLastUserActivity=" + max + ", lastUserActivityEvent=" + PowerManager.userActivityEventToString(this.mLastUserActivityEvent) + ")...");
            setSandmanSummonedLocked(true);
            setWakefulnessLocked(3, j, i, min, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public boolean sleepLocked(long j, int i, int i2) {
        Trace.traceBegin(131072L, "sleepPowerGroup");
        try {
            Slog.m76i(TAG, "Sleeping power group (groupId=" + getGroupId() + ", uid=" + i + ", reason=" + PowerManager.sleepReasonToString(i2) + ")...");
            setSandmanSummonedLocked(true);
            setWakefulnessLocked(0, j, i, i2, 0, null, null);
            return true;
        } finally {
            Trace.traceEnd(131072L);
        }
    }

    public long getLastUserActivityTimeLocked() {
        return this.mLastUserActivityTime;
    }

    public void setLastUserActivityTimeLocked(long j, int i) {
        this.mLastUserActivityTime = j;
        this.mLastUserActivityEvent = i;
    }

    public long getLastUserActivityTimeNoChangeLightsLocked() {
        return this.mLastUserActivityTimeNoChangeLights;
    }

    public void setLastUserActivityTimeNoChangeLightsLocked(long j, int i) {
        this.mLastUserActivityTimeNoChangeLights = j;
        this.mLastUserActivityEvent = i;
    }

    public int getUserActivitySummaryLocked() {
        return this.mUserActivitySummary;
    }

    public boolean isPolicyBrightLocked() {
        return this.mDisplayPowerRequest.policy == 3;
    }

    public boolean isPolicyDimLocked() {
        return this.mDisplayPowerRequest.policy == 2;
    }

    public boolean isBrightOrDimLocked() {
        return this.mDisplayPowerRequest.isBrightOrDim();
    }

    public void setUserActivitySummaryLocked(int i) {
        this.mUserActivitySummary = i;
    }

    public int getWakeLockSummaryLocked() {
        return this.mWakeLockSummary;
    }

    public String getSuspendBlockerMonitorInfo() {
        if (!this.mSuspendBlockerMonitor.hasGroupEvent()) {
            return "";
        }
        return "    [Group] Id = " + String.valueOf(this.mGroupId) + this.mSuspendBlockerMonitor.toString();
    }

    public boolean hasWakeLockKeepingScreenOnLocked() {
        return (this.mWakeLockSummary & 38) != 0;
    }

    public void setWakeLockSummaryLocked(int i) {
        this.mWakeLockSummary = i;
    }

    public DisplayManagerInternal.DisplayPowerRequest getDisplayPowerRequestLocked() {
        return this.mDisplayPowerRequest;
    }

    public int getLastGoToSleepReasonLocked() {
        return this.mLastGoToSleepReason;
    }

    public void setLastGoToSleepReasonLocked(int i) {
        this.mLastGoToSleepReason = i;
    }

    public boolean supportsSandmanLocked() {
        return this.mSupportsSandman;
    }

    public boolean needSuspendBlockerLocked(boolean z, boolean z2, int i) {
        int i2;
        this.mSuspendBlockerMonitor.clearGroupEvent();
        if (isBrightOrDimLocked() && !z) {
            this.mSuspendBlockerMonitor.setEvent(16);
            return true;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mDisplayPowerRequest;
        int i3 = displayPowerRequest.policy;
        if (i3 == 1 && displayPowerRequest.dozeScreenState == 2) {
            this.mSuspendBlockerMonitor.setEvent(32);
            return true;
        }
        if ((PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI || (PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY && displayPowerRequest.dualScreenPolicy == 1)) && i3 == 1 && (i2 = displayPowerRequest.dozeScreenState) != 1 && i2 != 6) {
            this.mSuspendBlockerMonitor.setEvent(64);
            return true;
        }
        if (i != 2) {
            return false;
        }
        this.mSuspendBlockerMonitor.setEvent(128);
        return true;
    }

    public int getDesiredScreenPolicyLocked(boolean z, boolean z2, boolean z3, boolean z4) {
        return getDesiredScreenPolicyLocked(z, z2, z3, z4, -1L);
    }

    public int getDesiredScreenPolicyLocked(boolean z, boolean z2, boolean z3, boolean z4, long j) {
        int wakefulnessLocked = getWakefulnessLocked();
        int wakeLockSummaryLocked = getWakeLockSummaryLocked();
        if (wakefulnessLocked == 0 || z) {
            return 0;
        }
        if (wakefulnessLocked == 3) {
            if (this.mGroupId == 2) {
                return 0;
            }
            if ((wakeLockSummaryLocked & 64) != 0) {
                return 1;
            }
            if (z2) {
                return 0;
            }
        }
        if ((wakeLockSummaryLocked & 2) != 0 || !z3 || (1 & getUserActivitySummaryLocked()) != 0 || z4) {
            return 3;
        }
        if (j == 0 && (this.mWakeLockSummary & 4) == 0 && this.mDisplayPowerRequest.isBrightOrDim()) {
            return this.mDisplayPowerRequest.policy;
        }
        return 2;
    }

    public boolean updateLocked(float f, boolean z, boolean z2, int i, float f2, boolean z3, PowerSaveState powerSaveState, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, long j, float f3) {
        this.mDisplayPowerRequest.policy = getDesiredScreenPolicyLocked(z4, z5, z6, z7, j);
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mDisplayPowerRequest;
        displayPowerRequest.screenBrightnessOverride = f;
        displayPowerRequest.useProximitySensor = z;
        displayPowerRequest.boostScreenBrightness = z2;
        if (displayPowerRequest.policy == 1) {
            displayPowerRequest.dozeScreenState = i;
            if ((getWakeLockSummaryLocked() & 128) != 0 && !z3) {
                DisplayManagerInternal.DisplayPowerRequest displayPowerRequest2 = this.mDisplayPowerRequest;
                if (displayPowerRequest2.dozeScreenState == 4) {
                    displayPowerRequest2.dozeScreenState = 3;
                }
                if (displayPowerRequest2.dozeScreenState == 6) {
                    displayPowerRequest2.dozeScreenState = 2;
                }
            }
            this.mDisplayPowerRequest.dozeScreenBrightness = f2;
        } else {
            displayPowerRequest.dozeScreenState = 0;
            displayPowerRequest.dozeScreenBrightness = Float.NaN;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest3 = this.mDisplayPowerRequest;
        displayPowerRequest3.lowPowerMode = powerSaveState.batterySaverEnabled;
        displayPowerRequest3.screenLowPowerBrightnessFactor = f3;
        boolean requestPowerState = this.mDisplayManagerInternal.requestPowerState(this.mGroupId, displayPowerRequest3, z8);
        this.mNotifier.onScreenPolicyUpdate(this.mGroupId, this.mDisplayPowerRequest.policy);
        return requestPowerState;
    }

    public boolean shouldEnableInteractiveModeLocked(boolean z, boolean z2) {
        if (isBrightOrDimLocked()) {
            return (this.mWakefulness == 0 || z) ? false : true;
        }
        DisplayManagerInternal.DisplayPowerRequest displayPowerRequest = this.mDisplayPowerRequest;
        return displayPowerRequest.policy == 1 && displayPowerRequest.dozeScreenState == 2 && z2;
    }
}
