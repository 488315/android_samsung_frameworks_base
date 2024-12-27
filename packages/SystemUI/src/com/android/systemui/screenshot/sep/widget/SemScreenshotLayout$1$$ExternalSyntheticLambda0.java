package com.android.systemui.screenshot.sep.widget;

import com.android.systemui.screenshot.ScreenshotController;

public final /* synthetic */ class SemScreenshotLayout$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SemScreenshotLayout$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SemScreenshotLayout.this.mCallback.finishAnimation();
                break;
            default:
                ScreenshotController.this.finishDismiss();
                break;
        }
    }
}
