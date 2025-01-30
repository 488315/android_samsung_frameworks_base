package com.android.systemui.screenshot;

import androidx.lifecycle.LifecycleOwnerKt;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.IScreenshotProxy;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotProxyService$mBinder$1 extends IScreenshotProxy.Stub {
    public final /* synthetic */ ScreenshotProxyService this$0;

    public ScreenshotProxyService$mBinder$1(ScreenshotProxyService screenshotProxyService) {
        this.this$0 = screenshotProxyService;
    }

    @Override // com.android.systemui.screenshot.IScreenshotProxy
    public final void dismissKeyguard(IOnDoneCallback iOnDoneCallback) {
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this.this$0), null, null, new ScreenshotProxyService$mBinder$1$dismissKeyguard$1(this.this$0, iOnDoneCallback, null), 3);
    }

    @Override // com.android.systemui.screenshot.IScreenshotProxy
    public final boolean isNotificationShadeExpanded() {
        boolean z = !(this.this$0.mExpansionMgr.state == 0);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isNotificationShadeExpanded(): ", z, "ScreenshotProxyService");
        return z;
    }
}
