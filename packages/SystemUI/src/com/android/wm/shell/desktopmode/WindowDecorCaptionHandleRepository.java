package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.CaptionState;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class WindowDecorCaptionHandleRepository {
    public final SharedFlowImpl _appToWebUsageFlow;
    public final StateFlowImpl _captionStateFlow;
    public final SharedFlowImpl appToWebUsageFlow;
    public final StateFlowImpl captionStateFlow;

    public WindowDecorCaptionHandleRepository() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(CaptionState.NoCaption.INSTANCE);
        this._captionStateFlow = stateFlowImplMutableStateFlow;
        this.captionStateFlow = stateFlowImplMutableStateFlow;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._appToWebUsageFlow = sharedFlowImplMutableSharedFlow$default;
        this.appToWebUsageFlow = sharedFlowImplMutableSharedFlow$default;
    }
}
