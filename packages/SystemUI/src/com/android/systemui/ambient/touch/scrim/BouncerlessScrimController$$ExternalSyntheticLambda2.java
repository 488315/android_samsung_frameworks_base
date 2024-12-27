package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import java.util.function.Consumer;

public final /* synthetic */ class BouncerlessScrimController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BouncerlessScrimController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BouncerlessScrimController$$ExternalSyntheticLambda2(BouncerlessScrimController bouncerlessScrimController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bouncerlessScrimController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BouncerlessScrimController bouncerlessScrimController = this.f$0;
                bouncerlessScrimController.mCallbacks.remove((DreamOverlayContainerViewController.AnonymousClass1) this.f$1);
                break;
            case 1:
                BouncerlessScrimController bouncerlessScrimController2 = this.f$0;
                bouncerlessScrimController2.mCallbacks.add((DreamOverlayContainerViewController.AnonymousClass1) this.f$1);
                break;
            default:
                BouncerlessScrimController bouncerlessScrimController3 = this.f$0;
                final ShadeExpansionChangeEvent shadeExpansionChangeEvent = (ShadeExpansionChangeEvent) this.f$1;
                bouncerlessScrimController3.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.ambient.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = ShadeExpansionChangeEvent.this;
                        DreamOverlayContainerViewController.AnonymousClass1 anonymousClass1 = (DreamOverlayContainerViewController.AnonymousClass1) obj;
                        anonymousClass1.getClass();
                        DreamOverlayContainerViewController.m1931$$Nest$mupdateTransitionState(DreamOverlayContainerViewController.this, shadeExpansionChangeEvent2.fraction);
                    }
                });
                break;
        }
    }
}
