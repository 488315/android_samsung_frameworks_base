package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.screenshot.ICrossProfileService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
