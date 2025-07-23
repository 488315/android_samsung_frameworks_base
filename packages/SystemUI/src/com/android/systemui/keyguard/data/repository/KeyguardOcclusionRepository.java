package com.android.systemui.keyguard.data.repository;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardOcclusionRepository {
    public final StateFlowImpl showWhenLockedActivityInfo = StateFlowKt.MutableStateFlow(new ShowWhenLockedActivityInfo(false, null, 2, null));
}
