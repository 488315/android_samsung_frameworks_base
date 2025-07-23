package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OccludedToAodTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public OccludedToAodTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromOccludedTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromOccludedTransitionInteractor.TO_AOD_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.OCCLUDED, KeyguardState.AOD));
        this.transitionAnimation = m2598setupVtjQ1oo;
        this.deviceEntryBackgroundViewAlpha = m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f);
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new OccludedToAodTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
