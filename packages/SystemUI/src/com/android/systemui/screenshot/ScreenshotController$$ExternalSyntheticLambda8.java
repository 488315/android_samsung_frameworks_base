package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ScreenshotController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenshotController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda8(ScreenshotController screenshotController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = screenshotController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenshotController screenshotController = this.f$0;
                screenshotController.mViewProxy.setChipIntents((ScreenshotController.SavedImageData) this.f$1);
                break;
            default:
                this.f$0.mViewProxy.addQuickShareChip(((ScreenshotController.QuickShareData) this.f$1).quickShareAction);
                break;
        }
    }
}
