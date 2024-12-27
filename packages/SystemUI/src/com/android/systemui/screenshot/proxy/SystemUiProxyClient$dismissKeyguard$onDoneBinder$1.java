package com.android.systemui.screenshot.proxy;

import com.android.systemui.screenshot.IOnDoneCallback;
import kotlin.Unit;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SystemUiProxyClient$dismissKeyguard$onDoneBinder$1 extends IOnDoneCallback.Stub {
    public final /* synthetic */ CompletableDeferred $completion;

    public SystemUiProxyClient$dismissKeyguard$onDoneBinder$1(CompletableDeferred completableDeferred) {
        this.$completion = completableDeferred;
    }

    @Override // com.android.systemui.screenshot.IOnDoneCallback
    public final void onDone(boolean z) {
        ((CompletableDeferredImpl) this.$completion).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
    }
}
