package com.android.server.display.mode;

public final /* synthetic */ class RefreshRateModeManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RefreshRateModeManager f$0;

    public /* synthetic */ RefreshRateModeManager$$ExternalSyntheticLambda0(
            RefreshRateModeManager refreshRateModeManager, int i) {
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
