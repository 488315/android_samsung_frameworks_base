package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.data.repository.ShadeAnimationRepository;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
