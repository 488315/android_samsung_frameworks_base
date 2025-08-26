package com.android.systemui.statusbar.events.data.repository;

import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class SystemStatusEventAnimationRepositoryImpl implements SystemStatusEventAnimationRepository {
    public final ReadonlyStateFlow animationState;

    public SystemStatusEventAnimationRepositoryImpl(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.animationState = ((SystemStatusAnimationSchedulerImpl) systemStatusAnimationScheduler).animationState;
    }
}
