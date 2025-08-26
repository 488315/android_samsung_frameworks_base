package com.android.systemui.dreams.homecontrols.shared;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public abstract class IHomeControlsRemoteProxyExtKt {
    public static final Flow getControlsSettings(IHomeControlsRemoteProxy iHomeControlsRemoteProxy) {
        return FlowConflatedKt.conflatedCallbackFlow(new IHomeControlsRemoteProxyExtKt$controlsSettings$1(iHomeControlsRemoteProxy, null));
    }
}
