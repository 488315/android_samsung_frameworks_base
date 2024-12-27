package com.android.systemui.shade;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeControllerImpl f$0;

    public /* synthetic */ ShadeControllerImpl$$ExternalSyntheticLambda0(ShadeControllerImpl shadeControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ShadeControllerImpl shadeControllerImpl = this.f$0;
        switch (i) {
            case 0:
                shadeControllerImpl.animateCollapseShade(1.0f, 0, true, false);
                break;
            case 1:
                shadeControllerImpl.animateCollapseShade(0);
                break;
            case 2:
                shadeControllerImpl.collapseShadeInternal();
                break;
            case 3:
                shadeControllerImpl.runPostCollapseActions();
                break;
            default:
                shadeControllerImpl.postAnimateForceCollapseShade();
                break;
        }
    }
}
