package com.android.server.power;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PowerManagerService$2$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ PowerManagerService.AnonymousClass1 f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PowerManagerService$2$$ExternalSyntheticLambda0(
            PowerManagerService.AnonymousClass1 anonymousClass1, boolean z) {
        this.f$0 = anonymousClass1;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PowerManagerService.AnonymousClass1 anonymousClass1 = this.f$0;
        boolean z = this.f$1;
        synchronized (PowerManagerService.this.mLock) {
            try {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mUseAdaptiveScreenOffTimeout != z) {
                    powerManagerService.mUseAdaptiveScreenOffTimeout = z;
                    if (z) {
                        powerManagerService.mForegroundPackageObserver.addObserver(
                                powerManagerService.mAdaptiveScreenOffTimeoutController);
                    } else {
                        powerManagerService.mForegroundPackageObserver.deleteObserver(
                                powerManagerService.mAdaptiveScreenOffTimeoutController);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
