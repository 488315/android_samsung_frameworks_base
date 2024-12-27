package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.screenshot.ICrossProfileService;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenshotCrossProfileService$mBinder$1 extends ICrossProfileService.Stub {
    public final /* synthetic */ ScreenshotCrossProfileService this$0;

    public ScreenshotCrossProfileService$mBinder$1(ScreenshotCrossProfileService screenshotCrossProfileService) {
        this.this$0 = screenshotCrossProfileService;
    }

    @Override // com.android.systemui.screenshot.ICrossProfileService
    public final void launchIntent(Intent intent, Bundle bundle) {
        this.this$0.startActivity(intent, bundle);
    }
}
