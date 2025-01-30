package com.android.server.display;

import android.hardware.display.DisplayManagerInternal;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class WakelockController {
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

    public WakelockController(int i, DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks) {
        this.mDisplayId = i;
        this.mTag = "WakelockController[" + i + "]";
        this.mDisplayPowerCallbacks = displayPowerCallbacks;
        this.mSuspendBlockerIdUnfinishedBusiness = "[" + i + "]unfinished business";
        this.mSuspendBlockerIdOnStateChanged = "[" + i + "]on state changed";
        this.mSuspendBlockerIdProxPositive = "[" + i + "]prox positive";
        this.mSuspendBlockerIdProxNegative = "[" + i + "]prox negative";
        this.mSuspendBlockerIdProxDebounce = "[" + i + "]prox debounce";
        this.mSuspendBlockerIdRefreshRate = "[" + i + "]vrr ramp animation";
        this.mSuspendBlockerIdEarlyWakeup = "[" + i + "]early wakeup";
    }

    public boolean acquireWakelock(int i) {
        return acquireWakelockInternal(i);
    }

    public boolean releaseWakelock(int i) {
        return releaseWakelockInternal(i);
    }

    public void releaseAll() {
        for (int i = 1; i <= 7; i++) {
            releaseWakelockInternal(i);
        }
    }

    public final boolean acquireWakelockInternal(int i) {
        switch (i) {
            case 1:
                return acquireProxPositiveSuspendBlocker();
            case 2:
                return acquireProxNegativeSuspendBlocker();
            case 3:
                return acquireProxDebounceSuspendBlocker();
            case 4:
                return acquireStateChangedSuspendBlocker();
            case 5:
                return acquireUnfinishedBusinessSuspendBlocker();
            case 6:
                return acquireRefreshRateSuspendBlocker();
            case 7:
                return acquireEarlyWakeUpSuspendBlocker();
            default:
                throw new RuntimeException("Invalid wakelock attempted to be acquired");
        }
    }

    public final boolean releaseWakelockInternal(int i) {
        switch (i) {
            case 1:
                return releaseProxPositiveSuspendBlocker();
            case 2:
                return releaseProxNegativeSuspendBlocker();
            case 3:
                return releaseProxDebounceSuspendBlocker();
            case 4:
                return releaseStateChangedSuspendBlocker();
            case 5:
                return releaseUnfinishedBusinessSuspendBlocker();
            case 6:
                return releaseRefreshRateSuspendBlocker();
            case 7:
                return releaseEarlyWakeUpSuspendBlocker();
            default:
                throw new RuntimeException("Invalid wakelock attempted to be released");
        }
    }

    public final boolean acquireProxPositiveSuspendBlocker() {
        if (this.mIsProximityPositiveAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        this.mIsProximityPositiveAcquired = true;
        return true;
    }

    public final boolean acquireStateChangedSuspendBlocker() {
        if (this.mOnStateChangedPending) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        this.mOnStateChangedPending = true;
        return true;
    }

    public final boolean releaseStateChangedSuspendBlocker() {
        if (!this.mOnStateChangedPending) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        this.mOnStateChangedPending = false;
        return true;
    }

    public final boolean acquireUnfinishedBusinessSuspendBlocker() {
        if (this.mUnfinishedBusiness) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
        this.mUnfinishedBusiness = true;
        return true;
    }

    public final boolean releaseUnfinishedBusinessSuspendBlocker() {
        if (!this.mUnfinishedBusiness) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
        this.mUnfinishedBusiness = false;
        return true;
    }

    public final boolean releaseProxPositiveSuspendBlocker() {
        if (!this.mIsProximityPositiveAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        this.mIsProximityPositiveAcquired = false;
        return true;
    }

    public final boolean acquireProxNegativeSuspendBlocker() {
        if (this.mIsProximityNegativeAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        this.mIsProximityNegativeAcquired = true;
        return true;
    }

    public final boolean releaseProxNegativeSuspendBlocker() {
        if (!this.mIsProximityNegativeAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        this.mIsProximityNegativeAcquired = false;
        return true;
    }

    public final boolean acquireProxDebounceSuspendBlocker() {
        if (this.mHasProximityDebounced) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
        this.mHasProximityDebounced = true;
        return true;
    }

    public final boolean releaseProxDebounceSuspendBlocker() {
        if (!this.mHasProximityDebounced) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
        this.mHasProximityDebounced = false;
        return true;
    }

    public final boolean acquireRefreshRateSuspendBlocker() {
        if (this.mIsRefreshRateRequested) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdRefreshRate);
        this.mIsRefreshRateRequested = true;
        return true;
    }

    public final boolean releaseRefreshRateSuspendBlocker() {
        if (!this.mIsRefreshRateRequested) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdRefreshRate);
        this.mIsRefreshRateRequested = false;
        return true;
    }

    public final boolean acquireEarlyWakeUpSuspendBlocker() {
        if (this.mIsEarlyWakeupRequested) {
            return false;
        }
        this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdEarlyWakeup);
        this.mIsEarlyWakeupRequested = true;
        return true;
    }

    public final boolean releaseEarlyWakeUpSuspendBlocker() {
        if (!this.mIsEarlyWakeupRequested) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdEarlyWakeup);
        this.mIsEarlyWakeupRequested = false;
        return true;
    }

    public Runnable getOnProximityPositiveRunnable() {
        return new Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WakelockController.this.lambda$getOnProximityPositiveRunnable$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnProximityPositiveRunnable$0() {
        if (this.mIsProximityPositiveAcquired) {
            this.mIsProximityPositiveAcquired = false;
            this.mDisplayPowerCallbacks.onProximityPositive();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        }
    }

    public Runnable getOnStateChangedRunnable() {
        return new Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WakelockController.this.lambda$getOnStateChangedRunnable$1();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnStateChangedRunnable$1() {
        if (this.mOnStateChangedPending) {
            this.mOnStateChangedPending = false;
            this.mDisplayPowerCallbacks.onStateChanged();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        }
    }

    public Runnable getOnProximityNegativeRunnable() {
        return new Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                WakelockController.this.lambda$getOnProximityNegativeRunnable$2();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnProximityNegativeRunnable$2() {
        if (this.mIsProximityNegativeAcquired) {
            this.mIsProximityNegativeAcquired = false;
            this.mDisplayPowerCallbacks.onProximityNegative();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        }
    }

    public void dumpLocal(PrintWriter printWriter) {
        printWriter.println("WakelockController State:");
        printWriter.println("  mDisplayId=" + this.mDisplayId);
        printWriter.println("  mUnfinishedBusiness=" + hasUnfinishedBusiness());
        printWriter.println("  mOnStateChangePending=" + isOnStateChangedPending());
        printWriter.println("  mOnProximityPositiveMessages=" + isProximityPositiveAcquired());
        printWriter.println("  mOnProximityNegativeMessages=" + isProximityNegativeAcquired());
        printWriter.println("  mIsRefreshRateRequested=" + isRefreshRateRequested());
        printWriter.println("  mIsEarlyWakeUpRequested=" + isEarlyWakeupRequested());
    }

    public String getSuspendBlockerUnfinishedBusinessId() {
        return this.mSuspendBlockerIdUnfinishedBusiness;
    }

    public String getSuspendBlockerOnStateChangedId() {
        return this.mSuspendBlockerIdOnStateChanged;
    }

    public String getSuspendBlockerProxPositiveId() {
        return this.mSuspendBlockerIdProxPositive;
    }

    public String getSuspendBlockerProxNegativeId() {
        return this.mSuspendBlockerIdProxNegative;
    }

    public String getSuspendBlockerProxDebounceId() {
        return this.mSuspendBlockerIdProxDebounce;
    }

    public boolean hasUnfinishedBusiness() {
        return this.mUnfinishedBusiness;
    }

    public boolean isOnStateChangedPending() {
        return this.mOnStateChangedPending;
    }

    public boolean isProximityPositiveAcquired() {
        return this.mIsProximityPositiveAcquired;
    }

    public boolean isProximityNegativeAcquired() {
        return this.mIsProximityNegativeAcquired;
    }

    public boolean hasProximitySensorDebounced() {
        return this.mHasProximityDebounced;
    }

    public boolean isRefreshRateRequested() {
        return this.mIsRefreshRateRequested;
    }

    public boolean isEarlyWakeupRequested() {
        return this.mIsEarlyWakeupRequested;
    }
}
