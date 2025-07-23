package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GoneToDozingTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public GoneToDozingTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_DOZING_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.DOZING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.ContentToState(sceneKey, keyguardState)));
        this.transitionAnimation = m;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, DurationKt.toDuration(500, durationUnit), new DozingToGoneTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, 204);
        this.notificationAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, DurationKt.toDuration(500, durationUnit), new GoneToDozingTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, 220);
        this.deviceEntryBackgroundViewAlpha = m.immediatelyTransitionTo(0.0f);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new GoneToDozingTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
