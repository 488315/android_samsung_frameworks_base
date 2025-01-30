package com.android.systemui.dreams;

import androidx.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayService f$0;

    public /* synthetic */ DreamOverlayService$$ExternalSyntheticLambda1(DreamOverlayService dreamOverlayService, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayService dreamOverlayService = this.f$0;
                boolean z = DreamOverlayService.DEBUG;
                dreamOverlayService.getClass();
                dreamOverlayService.mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
                break;
            default:
                DreamOverlayService dreamOverlayService2 = this.f$0;
                boolean z2 = DreamOverlayService.DEBUG;
                dreamOverlayService2.getClass();
                dreamOverlayService2.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
                dreamOverlayService2.resetCurrentDreamOverlayLocked();
                dreamOverlayService2.mDestroyed = true;
                break;
        }
    }
}
