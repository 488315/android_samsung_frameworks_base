package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.ambient.touch.BouncerSwipeTouchHandler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScrimManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimManager f$0;
    public final /* synthetic */ BouncerSwipeTouchHandler.AnonymousClass1 f$1;

    public /* synthetic */ ScrimManager$$ExternalSyntheticLambda1(ScrimManager scrimManager, BouncerSwipeTouchHandler.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = scrimManager;
        this.f$1 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScrimManager scrimManager = this.f$0;
                scrimManager.mCallbacks.remove(this.f$1);
                break;
            default:
                ScrimManager scrimManager2 = this.f$0;
                scrimManager2.mCallbacks.add(this.f$1);
                break;
        }
    }
}
