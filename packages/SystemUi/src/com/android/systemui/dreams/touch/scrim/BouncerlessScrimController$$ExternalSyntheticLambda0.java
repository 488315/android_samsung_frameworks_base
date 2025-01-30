package com.android.systemui.dreams.touch.scrim;

import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerlessScrimController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BouncerlessScrimController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BouncerlessScrimController$$ExternalSyntheticLambda0(BouncerlessScrimController bouncerlessScrimController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bouncerlessScrimController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BouncerlessScrimController bouncerlessScrimController = this.f$0;
                bouncerlessScrimController.mCallbacks.add((DreamOverlayContainerViewController.C12711) this.f$1);
                break;
            default:
                BouncerlessScrimController bouncerlessScrimController2 = this.f$0;
                final ShadeExpansionChangeEvent shadeExpansionChangeEvent = (ShadeExpansionChangeEvent) this.f$1;
                bouncerlessScrimController2.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.scrim.BouncerlessScrimController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ShadeExpansionChangeEvent shadeExpansionChangeEvent2 = ShadeExpansionChangeEvent.this;
                        DreamOverlayContainerViewController.C12711 c12711 = (DreamOverlayContainerViewController.C12711) obj;
                        c12711.getClass();
                        DreamOverlayContainerViewController.m1531$$Nest$mupdateTransitionState(DreamOverlayContainerViewController.this, shadeExpansionChangeEvent2.fraction);
                    }
                });
                break;
        }
    }
}
