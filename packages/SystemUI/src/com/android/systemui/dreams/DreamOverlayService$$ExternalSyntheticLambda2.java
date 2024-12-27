package com.android.systemui.dreams;

import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class DreamOverlayService$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayService f$0;

    public /* synthetic */ DreamOverlayService$$ExternalSyntheticLambda2(DreamOverlayService dreamOverlayService, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DreamOverlayService dreamOverlayService = this.f$0;
        switch (i) {
            case 0:
                boolean z = DreamOverlayService.DEBUG;
                dreamOverlayService.getClass();
                dreamOverlayService.mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
                break;
            default:
                boolean z2 = DreamOverlayService.DEBUG;
                dreamOverlayService.getClass();
                dreamOverlayService.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
                dreamOverlayService.resetCurrentDreamOverlayLocked();
                dreamOverlayService.mDestroyed = true;
                break;
        }
    }
}
