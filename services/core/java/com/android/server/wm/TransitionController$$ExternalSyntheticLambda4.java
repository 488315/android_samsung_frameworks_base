package com.android.server.wm;

public final /* synthetic */ class TransitionController$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TransitionController$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((TransitionController) obj).tryStartCollectFromQueue();
                break;
            default:
                WindowContainer windowContainer = (WindowContainer) obj;
                if (windowContainer.isVisibleRequested()) {
                    WindowContainer.enforceSurfaceVisible(windowContainer);
                    break;
                }
                break;
        }
    }
}
