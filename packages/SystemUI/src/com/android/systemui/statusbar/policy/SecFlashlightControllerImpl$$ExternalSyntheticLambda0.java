package com.android.systemui.statusbar.policy;

public final /* synthetic */ class SecFlashlightControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecFlashlightControllerImpl f$0;

    public /* synthetic */ SecFlashlightControllerImpl$$ExternalSyntheticLambda0(SecFlashlightControllerImpl secFlashlightControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = secFlashlightControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecFlashlightControllerImpl secFlashlightControllerImpl = this.f$0;
        switch (i) {
            case 0:
                secFlashlightControllerImpl.showUnavailableMessage();
                break;
            default:
                secFlashlightControllerImpl.mSubscreenFlashlightController.finishFlashLightActivity();
                break;
        }
    }
}
