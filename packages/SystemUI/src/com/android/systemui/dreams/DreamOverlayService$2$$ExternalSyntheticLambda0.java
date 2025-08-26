package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.scene.shared.model.Overlays;
import java.util.Set;

/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayService$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DreamOverlayService$2$$ExternalSyntheticLambda0(DreamOverlayService.AnonymousClass2 anonymousClass2, Boolean bool) {
        this.f$0 = anonymousClass2;
        this.f$1 = bool;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayService.AnonymousClass2 anonymousClass2 = (DreamOverlayService.AnonymousClass2) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                if (anonymousClass2.this$0.mCommunalVisible != bool.booleanValue()) {
                    anonymousClass2.this$0.mCommunalVisible = bool.booleanValue();
                    anonymousClass2.this$0.updateLifecycleStateLocked();
                    break;
                }
                break;
            case 1:
                DreamOverlayService.AnonymousClass3 anonymousClass3 = (DreamOverlayService.AnonymousClass3) this.f$0;
                Boolean bool2 = (Boolean) this.f$1;
                DreamOverlayService dreamOverlayService = anonymousClass3.this$0;
                boolean zBooleanValue = bool2.booleanValue();
                if (dreamOverlayService.mBouncerShowing != zBooleanValue) {
                    dreamOverlayService.mBouncerShowing = zBooleanValue;
                    dreamOverlayService.updateLifecycleStateLocked();
                    dreamOverlayService.updateGestureBlockingLocked();
                    break;
                }
                break;
            default:
                DreamOverlayService.AnonymousClass4 anonymousClass4 = (DreamOverlayService.AnonymousClass4) this.f$0;
                Set set = (Set) this.f$1;
                DreamOverlayService dreamOverlayService2 = anonymousClass4.this$0;
                boolean zContains = set.contains(Overlays.Bouncer);
                if (dreamOverlayService2.mBouncerShowing != zContains) {
                    dreamOverlayService2.mBouncerShowing = zContains;
                    dreamOverlayService2.updateLifecycleStateLocked();
                    dreamOverlayService2.updateGestureBlockingLocked();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ DreamOverlayService$2$$ExternalSyntheticLambda0(DreamOverlayService.AnonymousClass3 anonymousClass3, Boolean bool) {
        this.f$0 = anonymousClass3;
        this.f$1 = bool;
    }

    public /* synthetic */ DreamOverlayService$2$$ExternalSyntheticLambda0(DreamOverlayService.AnonymousClass4 anonymousClass4, Set set) {
        this.f$0 = anonymousClass4;
        this.f$1 = set;
    }
}
