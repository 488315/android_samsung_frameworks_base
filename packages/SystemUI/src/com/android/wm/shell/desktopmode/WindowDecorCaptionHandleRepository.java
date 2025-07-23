package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.CaptionState;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowDecorCaptionHandleRepository {
    public final SharedFlowImpl _appToWebUsageFlow;
    public final StateFlowImpl _captionStateFlow;
    public final SharedFlowImpl appToWebUsageFlow;
    public final StateFlowImpl captionStateFlow;

    public WindowDecorCaptionHandleRepository() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(CaptionState.NoCaption.INSTANCE);
        this._captionStateFlow = MutableStateFlow;
        this.captionStateFlow = MutableStateFlow;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._appToWebUsageFlow = MutableSharedFlow$default;
        this.appToWebUsageFlow = MutableSharedFlow$default;
    }
}
