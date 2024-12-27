package com.android.server.display;

import android.hardware.display.DisplayManagerInternal;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.utils.DebugUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WakelockController {
    public static final boolean DEBUG = DebugUtils.isDebuggable("WakelockController");
    static final int WAKE_LOCK_MAX = 7;
    public final int mDisplayId;
    public final DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
    public boolean mHasProximityDebounced;
    public boolean mIsEarlyWakeupRequested;
    public boolean mIsProximityNegativeAcquired;
    public boolean mIsProximityPositiveAcquired;
    public boolean mIsRefreshRateRequested;
    public boolean mOnStateChangedPending;
    public final String mSuspendBlockerIdEarlyWakeup;
    public final String mSuspendBlockerIdOnStateChanged;
    public final String mSuspendBlockerIdProxDebounce;
    public final String mSuspendBlockerIdProxNegative;
    public final String mSuspendBlockerIdProxPositive;
    public final String mSuspendBlockerIdRefreshRate;
    public final String mSuspendBlockerIdUnfinishedBusiness;
    public final String mTag;
    public boolean mUnfinishedBusiness;

    public WakelockController(
            int i, DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks) {
        this.mDisplayId = i;
        this.mTag =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "WakelockController[", "]");
        this.mDisplayPowerCallbacks = displayPowerCallbacks;
        this.mSuspendBlockerIdUnfinishedBusiness =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "[", "]unfinished business");
        this.mSuspendBlockerIdOnStateChanged =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "[", "]on state changed");
        this.mSuspendBlockerIdProxPositive =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "[", "]prox positive");
        this.mSuspendBlockerIdProxNegative =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "[", "]prox negative");
        this.mSuspendBlockerIdProxDebounce =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "[", "]prox debounce");
        this.mSuspendBlockerIdRefreshRate =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "[", "]vrr ramp animation");
        this.mSuspendBlockerIdEarlyWakeup =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "[", "]early wakeup");
    }

    public final boolean acquireWakelock(int i) {
        String str = this.mTag;
        boolean z = DEBUG;
        switch (i) {
            case 1:
                if (!this.mIsProximityPositiveAcquired) {
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdProxPositive);
                    this.mIsProximityPositiveAcquired = true;
                    break;
                } else {
                    return false;
                }
            case 2:
                if (!this.mIsProximityNegativeAcquired) {
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdProxNegative);
                    this.mIsProximityNegativeAcquired = true;
                    break;
                } else {
                    return false;
                }
            case 3:
                if (!this.mHasProximityDebounced) {
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdProxDebounce);
                    this.mHasProximityDebounced = true;
                    break;
                } else {
                    return false;
                }
            case 4:
                if (!this.mOnStateChangedPending) {
                    if (z) {
                        Slog.d(str, "State Changed...");
                    }
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdOnStateChanged);
                    this.mOnStateChangedPending = true;
                    break;
                } else {
                    return false;
                }
            case 5:
                if (!this.mUnfinishedBusiness) {
                    if (z) {
                        Slog.d(str, "Unfinished business...");
                    }
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdUnfinishedBusiness);
                    this.mUnfinishedBusiness = true;
                    break;
                } else {
                    return false;
                }
            case 6:
                if (!this.mIsRefreshRateRequested) {
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdRefreshRate);
                    this.mIsRefreshRateRequested = true;
                    break;
                } else {
                    return false;
                }
            case 7:
                if (!this.mIsEarlyWakeupRequested) {
                    this.mDisplayPowerCallbacks.acquireSuspendBlocker(
                            this.mSuspendBlockerIdEarlyWakeup);
                    this.mIsEarlyWakeupRequested = true;
                    break;
                } else {
                    return false;
                }
            default:
                throw new RuntimeException("Invalid wakelock attempted to be acquired");
        }
        return true;
    }

    public String getSuspendBlockerOnStateChangedId() {
        return this.mSuspendBlockerIdOnStateChanged;
    }

    public String getSuspendBlockerProxDebounceId() {
        return this.mSuspendBlockerIdProxDebounce;
    }

    public String getSuspendBlockerProxNegativeId() {
        return this.mSuspendBlockerIdProxNegative;
    }

    public String getSuspendBlockerProxPositiveId() {
        return this.mSuspendBlockerIdProxPositive;
    }

    public String getSuspendBlockerUnfinishedBusinessId() {
        return this.mSuspendBlockerIdUnfinishedBusiness;
    }

    public boolean hasProximitySensorDebounced() {
        return this.mHasProximityDebounced;
    }

    public boolean hasUnfinishedBusiness() {
        return this.mUnfinishedBusiness;
    }

    public boolean isEarlyWakeupRequested() {
        return this.mIsEarlyWakeupRequested;
    }

    public boolean isOnStateChangedPending() {
        return this.mOnStateChangedPending;
    }

    public boolean isProximityNegativeAcquired() {
        return this.mIsProximityNegativeAcquired;
    }

    public boolean isProximityPositiveAcquired() {
        return this.mIsProximityPositiveAcquired;
    }

    public boolean isRefreshRateRequested() {
        return this.mIsRefreshRateRequested;
    }

    public final boolean releaseWakelockInternal(int i) {
        switch (i) {
            case 1:
                if (!this.mIsProximityPositiveAcquired) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdProxPositive);
                this.mIsProximityPositiveAcquired = false;
                return true;
            case 2:
                if (!this.mIsProximityNegativeAcquired) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdProxNegative);
                this.mIsProximityNegativeAcquired = false;
                return true;
            case 3:
                if (!this.mHasProximityDebounced) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdProxDebounce);
                this.mHasProximityDebounced = false;
                return true;
            case 4:
                if (!this.mOnStateChangedPending) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdOnStateChanged);
                this.mOnStateChangedPending = false;
                return true;
            case 5:
                if (!this.mUnfinishedBusiness) {
                    return false;
                }
                if (DEBUG) {
                    Slog.d(this.mTag, "Finished business...");
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdUnfinishedBusiness);
                this.mUnfinishedBusiness = false;
                return true;
            case 6:
                if (!this.mIsRefreshRateRequested) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdRefreshRate);
                this.mIsRefreshRateRequested = false;
                return true;
            case 7:
                if (!this.mIsEarlyWakeupRequested) {
                    return false;
                }
                this.mDisplayPowerCallbacks.releaseSuspendBlocker(
                        this.mSuspendBlockerIdEarlyWakeup);
                this.mIsEarlyWakeupRequested = false;
                return true;
            default:
                throw new RuntimeException("Invalid wakelock attempted to be released");
        }
    }
}
