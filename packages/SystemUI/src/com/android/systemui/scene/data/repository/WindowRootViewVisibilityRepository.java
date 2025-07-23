package com.android.systemui.scene.data.repository;

import com.android.internal.statusbar.IStatusBarService;
import java.util.concurrent.Executor;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WindowRootViewVisibilityRepository {
    public final StateFlowImpl _isLockscreenOrShadeVisible;
    public final ReadonlyStateFlow isLockscreenOrShadeVisible;
    public final IStatusBarService statusBarService;
    public final Executor uiBgExecutor;

    public WindowRootViewVisibilityRepository(IStatusBarService iStatusBarService, Executor executor) {
        this.statusBarService = iStatusBarService;
        this.uiBgExecutor = executor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isLockscreenOrShadeVisible = MutableStateFlow;
        this.isLockscreenOrShadeVisible = FlowKt.asStateFlow(MutableStateFlow);
    }
}
