package com.android.systemui.statusbar.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FlashlightControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FlashlightControllerImpl f$0;

    public /* synthetic */ FlashlightControllerImpl$$ExternalSyntheticLambda0(FlashlightControllerImpl flashlightControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = flashlightControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.tryInitCamera();
                break;
            case 1:
                this.f$0.tryInitCamera();
                break;
            case 2:
                this.f$0.tryInitCamera();
                break;
            case 3:
                this.f$0.tryInitCamera();
                break;
            default:
                this.f$0.mSubscreenFlashlightController.finishFlashLightActivity();
                break;
        }
    }
}
