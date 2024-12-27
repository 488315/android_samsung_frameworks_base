package com.android.server.dreams;

public final /* synthetic */ class DreamManagerService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamManagerService f$0;

    public /* synthetic */ DreamManagerService$$ExternalSyntheticLambda1(
            DreamManagerService dreamManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DreamManagerService dreamManagerService = this.f$0;
        switch (i) {
            case 0:
                dreamManagerService.writePulseGestureEnabled();
                synchronized (dreamManagerService.mLock) {
                    dreamManagerService.stopDreamLocked("user switched", false);
                }
                return;
            default:
                dreamManagerService.mAtmInternal.notifyActiveDreamChanged(null);
                return;
        }
    }
}
