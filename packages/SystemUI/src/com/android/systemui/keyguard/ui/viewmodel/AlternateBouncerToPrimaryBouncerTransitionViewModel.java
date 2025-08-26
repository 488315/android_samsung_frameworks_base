package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.scene.shared.model.Overlays;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class AlternateBouncerToPrimaryBouncerTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 alphaFlow;
    public final AlternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0 alphaForAnimationStep;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final EmptyFlow lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final EmptyFlow notificationBlurRadius;
    public final ChannelLimitedFlowMerge windowBlurRadius;

    public AlternateBouncerToPrimaryBouncerTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, final BlurConfig blurConfig, ShadeDependentFlows shadeDependentFlows) {
        FromAlternateBouncerTransitionInteractor.Companion.getClass();
        long j = FromAlternateBouncerTransitionInteractor.TO_PRIMARY_BOUNCER_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.ALTERNATE_BOUNCER;
        OverlayKey overlayKey = Overlays.Bouncer;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2615setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, overlayKey)));
        AlternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0 alternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0 = new AlternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0();
        this.alphaForAnimationStep = alternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2616sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilderM, j, alternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        this.alphaFlow = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2616sharedFlow74qcysc$default;
        EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
        this.lockscreenAlpha = emptyFlow;
        this.notificationAlpha = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2616sharedFlow74qcysc$default;
        this.notificationBlurRadius = emptyFlow;
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
        this.windowBlurRadius = shadeDependentFlows.transitionFlow(flowBuilderM.immediatelyTransitionTo(blurConfig.minBlurRadiusPx), KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilderM, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                float fFloatValue = ((Float) obj).floatValue();
                BlurConfig blurConfig2 = blurConfig;
                float f = blurConfig2.minBlurRadiusPx;
                this.f$0.getClass();
                return Float.valueOf(MathUtils.lerp(f, blurConfig2.maxBlurRadiusPx, fFloatValue));
            }
        }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(blurConfig.maxBlurRadiusPx);
            }
        }, null, null, 220));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
