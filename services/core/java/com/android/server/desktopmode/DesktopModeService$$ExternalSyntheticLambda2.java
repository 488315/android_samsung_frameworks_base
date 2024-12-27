package com.android.server.desktopmode;


public final /* synthetic */ class DesktopModeService$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DesktopModeService$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DesktopModeService desktopModeService = (DesktopModeService) obj;
                desktopModeService.startHome(
                        ((StateManager) desktopModeService.mStateManager).getState());
                break;
            default:
                ((DesktopModeService) ((DesktopModeService.AnonymousClass1) obj).this$0)
                                .mAllowPogoInitialDialog =
                        true;
                break;
        }
    }
}
