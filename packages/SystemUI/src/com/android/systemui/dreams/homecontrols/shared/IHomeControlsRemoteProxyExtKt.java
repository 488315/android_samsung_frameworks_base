package com.android.systemui.dreams.homecontrols.shared;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class IHomeControlsRemoteProxyExtKt {
    public static final Flow getControlsSettings(IHomeControlsRemoteProxy iHomeControlsRemoteProxy) {
        return FlowConflatedKt.conflatedCallbackFlow(new IHomeControlsRemoteProxyExtKt$controlsSettings$1(iHomeControlsRemoteProxy, null));
    }
}
