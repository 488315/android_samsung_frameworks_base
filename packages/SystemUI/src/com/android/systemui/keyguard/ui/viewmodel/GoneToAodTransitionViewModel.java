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
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class GoneToAodTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 enterFromTopAnimationAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final PowerInteractor powerInteractor;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public GoneToAodTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, PowerInteractor powerInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.powerInteractor = powerInteractor;
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_AOD_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.ContentToState(sceneKey, keyguardState)));
        this.transitionAnimation = flowBuilderM;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.notificationAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(200, durationUnit), new GoneToAodTransitionViewModel$$ExternalSyntheticLambda2(), 0L, null, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, 220);
        this.enterFromTopAnimationAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(400, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, durationUnit), null, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode);
        this.deviceEntryBackgroundViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new GoneToAodTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
