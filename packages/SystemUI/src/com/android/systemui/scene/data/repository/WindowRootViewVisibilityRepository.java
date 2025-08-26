package com.android.systemui.scene.data.repository;

import com.android.internal.statusbar.IStatusBarService;
import java.util.concurrent.Executor;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class WindowRootViewVisibilityRepository {
    public final StateFlowImpl _isLockscreenOrShadeVisible;
    public final ReadonlyStateFlow isLockscreenOrShadeVisible;
    public final IStatusBarService statusBarService;
    public final Executor uiBgExecutor;

    public WindowRootViewVisibilityRepository(IStatusBarService iStatusBarService, Executor executor) {
        this.statusBarService = iStatusBarService;
        this.uiBgExecutor = executor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isLockscreenOrShadeVisible = stateFlowImplMutableStateFlow;
        this.isLockscreenOrShadeVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }
}
