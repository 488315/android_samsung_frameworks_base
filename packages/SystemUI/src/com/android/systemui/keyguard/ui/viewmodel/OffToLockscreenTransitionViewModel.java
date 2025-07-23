package com.android.systemui.keyguard.ui.viewmodel;

import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
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
public final class OffToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final double alphaStartAt;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public OffToLockscreenTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(300, durationUnit);
        long duration2 = DurationKt.toDuration(633, durationUnit);
        this.alphaStartAt = Duration.m3436divLRDsOJo(duration, Duration.m3441plusLRDsOJo(duration2, duration));
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(Duration.m3441plusLRDsOJo(duration, duration2), KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.OFF, KeyguardState.LOCKSCREEN)), duration2, new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), duration, null, null, null, Interpolators.EMPHASIZED_ACCELERATE, null, 184);
        this.lockscreenAlpha = m2599sharedFlow74qcysc$default;
        this.shortcutsAlpha = m2599sharedFlow74qcysc$default;
        this.deviceEntryParentViewAlpha = m2599sharedFlow74qcysc$default;
        this.deviceEntryBackgroundViewAlpha = m2599sharedFlow74qcysc$default;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
