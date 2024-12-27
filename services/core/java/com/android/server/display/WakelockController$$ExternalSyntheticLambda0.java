package com.android.server.display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class WakelockController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WakelockController f$0;

    public /* synthetic */ WakelockController$$ExternalSyntheticLambda0(WakelockController wakelockController, int i) {
        this.$r8$classId = i;
        this.f$0 = wakelockController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WakelockController wakelockController = this.f$0;
        switch (i) {
            case 0:
                if (wakelockController.mOnStateChangedPending) {
                    wakelockController.mOnStateChangedPending = false;
                    wakelockController.mDisplayPowerCallbacks.onStateChanged();
                    wakelockController.mDisplayPowerCallbacks.releaseSuspendBlocker(wakelockController.mSuspendBlockerIdOnStateChanged);
                    break;
                }
                break;
            case 1:
                if (wakelockController.mIsProximityNegativeAcquired) {
                    wakelockController.mIsProximityNegativeAcquired = false;
                    wakelockController.mDisplayPowerCallbacks.onProximityNegative();
                    wakelockController.mDisplayPowerCallbacks.releaseSuspendBlocker(wakelockController.mSuspendBlockerIdProxNegative);
                    break;
                }
                break;
            default:
                if (wakelockController.mIsProximityPositiveAcquired) {
                    wakelockController.mIsProximityPositiveAcquired = false;
                    wakelockController.mDisplayPowerCallbacks.onProximityPositive();
                    wakelockController.mDisplayPowerCallbacks.releaseSuspendBlocker(wakelockController.mSuspendBlockerIdProxPositive);
                    break;
                }
                break;
        }
    }
}
