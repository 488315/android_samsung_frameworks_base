package com.android.systemui.keyguard.ui.transitions;

import android.util.MathUtils;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class GlanceableHubBlurProvider {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 enterBlurRadius;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 exitBlurRadius;

    public GlanceableHubBlurProvider(KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder, final BlurConfig blurConfig) {
        final int i = 0;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i2 = i;
                float fFloatValue = ((Float) obj).floatValue();
                switch (i2) {
                    case 0:
                        BlurConfig blurConfig2 = blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, fFloatValue));
                    default:
                        BlurConfig blurConfig3 = blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig3.minBlurRadiusPx, blurConfig3.maxBlurRadiusPx, fFloatValue));
                }
            }
        };
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        };
        final int i2 = 1;
        Function0 function02 = new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        };
        final int i3 = 2;
        this.exitBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilder, 0L, function1, 0L, function0, function02, new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        }, null, null, 197);
        final int i4 = 1;
        Function1 function12 = new Function1() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i22 = i4;
                float fFloatValue = ((Float) obj).floatValue();
                switch (i22) {
                    case 0:
                        BlurConfig blurConfig2 = blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, fFloatValue));
                    default:
                        BlurConfig blurConfig3 = blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig3.minBlurRadiusPx, blurConfig3.maxBlurRadiusPx, fFloatValue));
                }
            }
        };
        final int i5 = 3;
        Function0 function03 = new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        };
        final int i6 = 4;
        Function0 function04 = new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        };
        final int i7 = 5;
        this.enterBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilder, 0L, function12, 0L, function03, function04, new Function0() { // from class: com.android.systemui.keyguard.ui.transitions.GlanceableHubBlurProvider$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 1:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                    case 2:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    case 3:
                        blurConfig.getClass();
                        return Unit.INSTANCE;
                    case 4:
                        return Float.valueOf(blurConfig.minBlurRadiusPx);
                    default:
                        return Float.valueOf(blurConfig.maxBlurRadiusPx);
                }
            }
        }, null, null, 197);
    }
}
