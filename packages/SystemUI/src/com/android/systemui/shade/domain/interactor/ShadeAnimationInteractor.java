package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.data.repository.ShadeAnimationRepository;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ShadeAnimationInteractor {
    public final ReadonlyStateFlow isLaunchingActivity;
    public final ShadeAnimationRepository shadeAnimationRepository;

    public ShadeAnimationInteractor(ShadeAnimationRepository shadeAnimationRepository) {
        this.shadeAnimationRepository = shadeAnimationRepository;
        this.isLaunchingActivity = FlowKt.asStateFlow(shadeAnimationRepository.isLaunchingActivity);
    }

    public abstract StateFlow isAnyCloseAnimationRunning();

    public final void setIsLaunchingActivity(boolean z) {
        this.shadeAnimationRepository.isLaunchingActivity.updateState(null, Boolean.valueOf(z));
    }
}
