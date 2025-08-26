package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.runtime.MutableState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $alphaAnimator;
    final /* synthetic */ boolean $checked;
    final /* synthetic */ MutableState<Boolean> $prevChecked$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1(boolean z, Animatable<Float, AnimationVector1D> animatable, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$checked = z;
        this.$alphaAnimator = animatable;
        this.$prevChecked$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1(this.$checked, this.$alphaAnimator, this.$prevChecked$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0073, code lost:
    
        if (r12.snapTo(r13, r10) != r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1 seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.$checked;
            MutableState<Boolean> mutableState = this.$prevChecked$delegate;
            SeslCheckboxDefaults$LeftCheck$AlphaTransition seslCheckboxDefaults$LeftCheck$AlphaTransition = SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE;
            if (z != ((Boolean) mutableState.getValue()).booleanValue()) {
                this.$prevChecked$delegate.setValue(Boolean.valueOf(this.$checked));
                Animatable<Float, AnimationVector1D> animatable = this.$alphaAnimator;
                Float f = new Float(this.$checked ? 1.0f : 0.0f);
                final boolean z2 = this.$checked;
                KeyframesSpec keyframesSpecKeyframes = AnimationSpecKt.keyframes(new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj2;
                        Float fValueOf = Float.valueOf(1.0f);
                        Float fValueOf2 = Float.valueOf(0.0f);
                        if (z2) {
                            keyframesSpecConfig.durationMillis = 132;
                            keyframesSpecConfig.at(0, fValueOf2);
                            keyframesSpecConfig.at(66, fValueOf2);
                            KeyframesSpec.KeyframeEntity keyframeEntityAt = keyframesSpecConfig.at(keyframesSpecConfig.durationMillis, fValueOf);
                            SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE.getClass();
                            keyframeEntityAt.easing = SeslCheckboxDefaults$LeftCheck$AlphaTransition.KEY_POINT_EASING;
                        } else {
                            keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_addWidget;
                            keyframesSpecConfig.at(0, fValueOf2);
                            keyframesSpecConfig.at(166, fValueOf2);
                            keyframesSpecConfig.at(167, fValueOf);
                            KeyframesSpec.KeyframeEntity keyframeEntityAt2 = keyframesSpecConfig.at(keyframesSpecConfig.durationMillis, fValueOf2);
                            SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE.getClass();
                            keyframeEntityAt2.easing = SeslCheckboxDefaults$LeftCheck$AlphaTransition.KEY_POINT_EASING;
                        }
                        return Unit.INSTANCE;
                    }
                });
                this.label = 1;
                seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1 = this;
                if (Animatable.animateTo$default(animatable, f, keyframesSpecKeyframes, null, null, seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1, 12) != coroutineSingletons) {
                    Animatable<Float, AnimationVector1D> animatable2 = seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1.$alphaAnimator;
                    Float f2 = new Float(0.0f);
                    seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1.label = 2;
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1 = this;
        Animatable<Float, AnimationVector1D> animatable22 = seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1.$alphaAnimator;
        Float f22 = new Float(0.0f);
        seslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1.label = 2;
    }
}
