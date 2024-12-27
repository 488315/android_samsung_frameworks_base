package com.android.server.display.mode;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RefreshRateModeManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RefreshRateModeManager f$0;

    public /* synthetic */ RefreshRateModeManager$$ExternalSyntheticLambda0(RefreshRateModeManager refreshRateModeManager, int i) {
        this.$r8$classId = i;
        this.f$0 = refreshRateModeManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        RefreshRateModeManager refreshRateModeManager = this.f$0;
        switch (i) {
            case 0:
                synchronized (refreshRateModeManager.mLock) {
                    refreshRateModeManager.getController().onBrightnessChangedLocked();
                }
                return;
            default:
                synchronized (refreshRateModeManager.mLock) {
                    refreshRateModeManager.getController().onBrightnessChangedLocked();
                }
                return;
        }
    }
}
