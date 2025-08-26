package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1 extends IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver.Stub {
    public final /* synthetic */ CompletableDeferred $result;

    public GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1(CompletableDeferred completableDeferred) {
        this.$result = completableDeferred;
    }

    @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver
    public final void onResult(boolean z) {
        ((CompletableDeferredImpl) this.$result).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Boolean.valueOf(z));
    }
}
