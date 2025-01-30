package com.android.systemui.multishade.data.remoteproxy;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MultiShadeInputProxy {
    public final SharedFlowImpl _proxiedTouch;
    public final ReadonlySharedFlow proxiedInput;

    public MultiShadeInputProxy() {
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this._proxiedTouch = MutableSharedFlow$default;
        this.proxiedInput = new ReadonlySharedFlow(MutableSharedFlow$default, null);
    }
}
