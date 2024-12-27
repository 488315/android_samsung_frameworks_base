package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.screenshot.ICrossProfileService;

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
