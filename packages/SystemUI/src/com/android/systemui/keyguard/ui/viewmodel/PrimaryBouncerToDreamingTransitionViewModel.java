package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.scene.shared.model.Overlays;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrimaryBouncerToDreamingTransitionViewModel implements PrimaryBouncerTransition {
    public final EmptyFlow notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public PrimaryBouncerToDreamingTransitionViewModel(final BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_DREAMING_DURATION;
        Edge.Companion companion = Edge.Companion;
        OverlayKey overlayKey = Overlays.Bouncer;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.PRIMARY_BOUNCER, keyguardState, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.ContentToState(overlayKey, keyguardState)));
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToDreamingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                float floatValue = ((Float) obj).floatValue();
                BlurConfig blurConfig2 = blurConfig;
                float f = blurConfig2.maxBlurRadiusPx;
                PrimaryBouncerToDreamingTransitionViewModel.this.getClass();
                return Float.valueOf(MathUtils.lerp(f, blurConfig2.minBlurRadiusPx, floatValue));
            }
        };
        final int i = 0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToDreamingTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    default:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                }
            }
        };
        final int i2 = 1;
        this.windowBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, 0L, function1, 0L, function0, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToDreamingTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    default:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                }
            }
        }, null, null, IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber);
        this.notificationBlurRadius = EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
