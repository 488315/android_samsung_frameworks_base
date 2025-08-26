package com.android.systemui.shade.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class ShadeAnimationRepository {
    public final StateFlowImpl isLaunchingActivity = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
