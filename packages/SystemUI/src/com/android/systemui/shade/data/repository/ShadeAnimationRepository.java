package com.android.systemui.shade.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeAnimationRepository {
    public final StateFlowImpl isLaunchingActivity = StateFlowKt.MutableStateFlow(Boolean.FALSE);
}
