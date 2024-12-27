package com.android.systemui.screenshot;

import android.os.Process;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.sep.SnackbarController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda2 implements SnackbarController.DismissedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenshotController f$0;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda2(ScreenshotController screenshotController, int i) {
        this.$r8$classId = i;
        this.f$0 = screenshotController;
    }

    public void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.logSuccessOnActionsReady(savedImageData);
                break;
            default:
                ScreenshotController screenshotController = this.f$0;
                screenshotController.logSuccessOnActionsReady(savedImageData);
                TimeoutHandler timeoutHandler = screenshotController.mScreenshotHandler;
                timeoutHandler.removeMessages(2);
                timeoutHandler.sendMessageDelayed(timeoutHandler.obtainMessage(2), ((AccessibilityManager) timeoutHandler.mContext.getSystemService("accessibility")).getRecommendedTimeoutMillis(timeoutHandler.mDefaultTimeout, 4));
                if (savedImageData.uri != null) {
                    if (!savedImageData.owner.equals(Process.myUserHandle())) {
                        Log.d("Screenshot", "Screenshot saved to user " + savedImageData.owner + " as " + savedImageData.uri);
                    }
                    timeoutHandler.post(new ScreenshotController$$ExternalSyntheticLambda8(screenshotController, savedImageData, 0));
                    break;
                }
                break;
        }
    }
}
