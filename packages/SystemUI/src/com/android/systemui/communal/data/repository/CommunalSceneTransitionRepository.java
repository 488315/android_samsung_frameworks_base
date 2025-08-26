package com.android.systemui.communal.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class CommunalSceneTransitionRepository {
    public final StateFlowImpl nextLockscreenTargetState = StateFlowKt.MutableStateFlow(null);
}
