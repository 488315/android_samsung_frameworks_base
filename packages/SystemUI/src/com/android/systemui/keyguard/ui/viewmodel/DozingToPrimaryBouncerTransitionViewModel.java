package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.scene.shared.model.Overlays;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class DozingToPrimaryBouncerTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final BlurConfig blurConfig;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final EmptyFlow lockscreenAlpha;
    public final EmptyFlow notificationAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public DozingToPrimaryBouncerTransitionViewModel(BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.blurConfig = blurConfig;
        FromDozingTransitionInteractor.Companion.getClass();
        long j = FromDozingTransitionInteractor.TO_PRIMARY_BOUNCER_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.DOZING;
        OverlayKey overlayKey = Overlays.Bouncer;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, overlayKey)));
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
        this.windowBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                float fFloatValue = ((Float) obj).floatValue();
                BlurConfig blurConfig2 = this.f$0.blurConfig;
                return Float.valueOf(MathUtils.lerp(blurConfig2.minBlurRadiusPx, blurConfig2.maxBlurRadiusPx, fFloatValue));
            }
        }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(this.f$0.blurConfig.maxBlurRadiusPx);
            }
        }, null, null, 220);
        EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
        this.lockscreenAlpha = emptyFlow;
        this.notificationAlpha = emptyFlow;
        this.notificationBlurRadius = flowBuilderM.immediatelyTransitionTo(0.0f);
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
