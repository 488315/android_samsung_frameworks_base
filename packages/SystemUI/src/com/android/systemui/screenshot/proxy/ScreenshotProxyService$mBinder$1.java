package com.android.systemui.screenshot.proxy;

import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.proxy.IScreenshotProxy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotProxyService$mBinder$1 extends IScreenshotProxy.Stub {
    public final /* synthetic */ ScreenshotProxyService this$0;

    public ScreenshotProxyService$mBinder$1(ScreenshotProxyService screenshotProxyService) {
        this.this$0 = screenshotProxyService;
    }

    @Override // com.android.systemui.screenshot.proxy.IScreenshotProxy
    public final void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
        CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new ScreenshotProxyService$mBinder$1$dismissKeyguard$1(this.this$0, iOnDoneCallback, null), 6);
    }

    @Override // com.android.systemui.screenshot.proxy.IScreenshotProxy
    public final boolean isNotificationShadeExpanded() {
        boolean z = !this.this$0.mExpansionMgr.isClosed();
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isNotificationShadeExpanded(): ", "ScreenshotProxyService", z);
        return z;
    }
}
