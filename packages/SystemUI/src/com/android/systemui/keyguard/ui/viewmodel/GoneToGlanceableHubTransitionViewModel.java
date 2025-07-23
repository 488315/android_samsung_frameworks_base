package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GoneToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardAlpha;

    public GoneToGlanceableHubTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_GLANCEABLE_HUB_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, Edge.INVALID));
        this.keyguardAlpha = m.immediatelyTransitionTo(0.0f);
        Duration.Companion companion = Duration.Companion;
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, DurationKt.toDuration(167, DurationUnit.MILLISECONDS), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, 204);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
