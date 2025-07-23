package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrimaryBouncerToAodTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final BlurConfig blurConfig;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public PrimaryBouncerToAodTransitionViewModel(BlurConfig blurConfig, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.blurConfig = blurConfig;
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_AOD_DURATION;
        Edge.Companion companion = Edge.Companion;
        OverlayKey overlayKey = Overlays.Bouncer;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.PRIMARY_BOUNCER, keyguardState, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.ContentToState(overlayKey, keyguardState)));
        this.transitionAnimation = m;
        this.deviceEntryBackgroundViewAlpha = m.immediatelyTransitionTo(0.0f);
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, j, new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new PrimaryBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.windowBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToAodTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                float floatValue = ((Float) obj).floatValue();
                BlurConfig blurConfig2 = PrimaryBouncerToAodTransitionViewModel.this.blurConfig;
                return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, floatValue));
            }
        }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(PrimaryBouncerToAodTransitionViewModel.this.blurConfig.minBlurRadiusPx);
            }
        }, null, null, 220);
        this.notificationBlurRadius = m.immediatelyTransitionTo(0.0f);
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
