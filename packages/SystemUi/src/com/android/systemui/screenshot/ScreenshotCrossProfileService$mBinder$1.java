package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.screenshot.ICrossProfileService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
