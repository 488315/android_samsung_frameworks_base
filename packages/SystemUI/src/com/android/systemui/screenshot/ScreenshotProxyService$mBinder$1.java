package com.android.systemui.screenshot;

import androidx.lifecycle.LifecycleOwnerKt;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.IScreenshotProxy;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenshotProxyService$mBinder$1 extends IScreenshotProxy.Stub {
    public final /* synthetic */ ScreenshotProxyService this$0;

    public ScreenshotProxyService$mBinder$1(ScreenshotProxyService screenshotProxyService) {
        this.this$0 = screenshotProxyService;
    }

    @Override // com.android.systemui.screenshot.IScreenshotProxy
    public final void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), EmptyCoroutineContext.INSTANCE, null, new ScreenshotProxyService$mBinder$1$dismissKeyguard$$inlined$launch$default$1("IScreenshotProxy#dismissKeyguard", null, this.this$0, iOnDoneCallback), 2);
    }

    @Override // com.android.systemui.screenshot.IScreenshotProxy
    public final boolean isNotificationShadeExpanded() {
        boolean z = !this.this$0.mExpansionMgr.isClosed();
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isNotificationShadeExpanded(): ", "ScreenshotProxyService", z);
        return z;
    }
}
