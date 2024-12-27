package com.android.systemui.screenshot;

public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenshotController f$0;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda0(ScreenshotController screenshotController, int i) {
        this.$r8$classId = i;
        this.f$0 = screenshotController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ScreenshotController screenshotController = this.f$0;
        switch (i) {
            case 0:
                screenshotController.mViewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT);
                break;
            default:
                screenshotController.removeWindow();
                break;
        }
    }
}
