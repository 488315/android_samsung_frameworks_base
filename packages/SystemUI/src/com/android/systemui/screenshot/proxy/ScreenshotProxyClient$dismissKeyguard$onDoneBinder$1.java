package com.android.systemui.screenshot.proxy;

import com.android.systemui.screenshot.proxy.IOnDoneCallback;
import kotlin.Unit;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes2.dex */
public final class ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1 extends IOnDoneCallback.Stub {
    public final /* synthetic */ CompletableDeferred $completion;

    public ScreenshotProxyClient$dismissKeyguard$onDoneBinder$1(CompletableDeferred completableDeferred) {
        this.$completion = completableDeferred;
    }

    @Override // com.android.systemui.screenshot.proxy.IOnDoneCallback
    public final void onDone() {
        ((CompletableDeferredImpl) this.$completion).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
    }
}
