package com.android.systemui.statusbar.events.data.repository;

import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemStatusEventAnimationRepositoryImpl implements SystemStatusEventAnimationRepository {
    public final ReadonlyStateFlow animationState;

    public SystemStatusEventAnimationRepositoryImpl(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.animationState = ((SystemStatusAnimationSchedulerImpl) systemStatusAnimationScheduler).animationState;
    }
}
