package com.android.systemui.dreams;

import androidx.lifecycle.Lifecycle;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;

/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DreamOverlayService$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = DreamOverlayService.DEBUG;
                ((DreamOverlayService) obj).mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
                break;
            case 1:
                DreamOverlayService dreamOverlayService = (DreamOverlayService) obj;
                boolean z2 = DreamOverlayService.DEBUG;
                dreamOverlayService.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
                dreamOverlayService.mDestroyed = true;
                dreamOverlayService.mResetHandler.reset(new DreamOverlayService$$ExternalSyntheticLambda2(), "destroying");
                break;
            default:
                PowerInteractor powerInteractor = DreamOverlayService.this.mPowerInteractor;
                if (powerInteractor.statusBarStateController.isDreaming()) {
                    ((PowerRepositoryImpl) powerInteractor.repository).wakeUp(16, "pickupGesture");
                    break;
                }
                break;
        }
    }
}
