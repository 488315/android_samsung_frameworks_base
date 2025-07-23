package com.android.systemui.shade;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
