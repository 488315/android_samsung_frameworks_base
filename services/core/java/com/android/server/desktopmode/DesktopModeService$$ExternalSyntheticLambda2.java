package com.android.server.desktopmode;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
