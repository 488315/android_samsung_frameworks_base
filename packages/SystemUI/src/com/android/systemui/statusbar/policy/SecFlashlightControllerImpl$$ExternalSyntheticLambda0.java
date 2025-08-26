package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class SecFlashlightControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecFlashlightControllerImpl$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SecFlashlightControllerImpl) obj).showUnavailableMessage();
                break;
            case 1:
                ((SecFlashlightControllerImpl) obj).mSubscreenFlashlightController.finishFlashLightActivity();
                break;
            default:
                ((SecFlashlightControllerImpl.AnonymousClass2) obj).this$0.mIsFlashlightTaskInStack.set(false);
                break;
        }
    }
}
