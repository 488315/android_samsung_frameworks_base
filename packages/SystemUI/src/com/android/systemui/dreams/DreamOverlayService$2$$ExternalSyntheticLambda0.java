package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayService;

public final /* synthetic */ class DreamOverlayService$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Boolean f$1;

    public /* synthetic */ DreamOverlayService$2$$ExternalSyntheticLambda0(DreamOverlayService.AnonymousClass2 anonymousClass2, Boolean bool) {
        this.f$0 = anonymousClass2;
        this.f$1 = bool;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayService.AnonymousClass2 anonymousClass2 = (DreamOverlayService.AnonymousClass2) this.f$0;
                Boolean bool = this.f$1;
                if (anonymousClass2.this$0.mCommunalVisible != bool.booleanValue()) {
                    anonymousClass2.this$0.mCommunalVisible = bool.booleanValue();
                    anonymousClass2.this$0.updateLifecycleStateLocked();
                    break;
                }
                break;
            default:
                DreamOverlayService.AnonymousClass3 anonymousClass3 = (DreamOverlayService.AnonymousClass3) this.f$0;
                Boolean bool2 = this.f$1;
                if (anonymousClass3.this$0.mBouncerShowing != bool2.booleanValue()) {
                    anonymousClass3.this$0.mBouncerShowing = bool2.booleanValue();
                    anonymousClass3.this$0.updateLifecycleStateLocked();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ DreamOverlayService$2$$ExternalSyntheticLambda0(DreamOverlayService.AnonymousClass3 anonymousClass3, Boolean bool) {
        this.f$0 = anonymousClass3;
        this.f$1 = bool;
    }
}
