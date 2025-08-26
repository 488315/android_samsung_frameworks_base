package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.runtime.MutableState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $checked;
    final /* synthetic */ MutableState<Boolean> $prevChecked$delegate;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $scaleAnimator;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1(boolean z, Animatable<Float, AnimationVector1D> animatable, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$checked = z;
        this.$scaleAnimator = animatable;
        this.$prevChecked$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1(this.$checked, this.$scaleAnimator, this.$prevChecked$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslCheckboxDefaults$Common$ScaleTransition$animateScale$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableState<Boolean> mutableState = this.$prevChecked$delegate;
            SeslCheckboxDefaults$Common$ScaleTransition seslCheckboxDefaults$Common$ScaleTransition = SeslCheckboxDefaults$Common$ScaleTransition.INSTANCE;
            boolean zBooleanValue = ((Boolean) mutableState.getValue()).booleanValue();
            boolean z = this.$checked;
            if (zBooleanValue != z) {
                this.$prevChecked$delegate.setValue(Boolean.valueOf(z));
                Animatable<Float, AnimationVector1D> animatable = this.$scaleAnimator;
                Float f = new Float(1.0f);
                KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = new KeyframesSpec.KeyframesSpecConfig();
                keyframesSpecConfig.durationMillis = IKnoxCustomManager.Stub.TRANSACTION_getLoadingLogoPath;
                keyframesSpecConfig.at(0, Float.valueOf(1.0f));
                keyframesSpecConfig.at(133, Float.valueOf(0.8f)).easing = SeslCheckboxDefaults$Common$ScaleTransition.easing;
                keyframesSpecConfig.at(keyframesSpecConfig.durationMillis, Float.valueOf(1.0f)).easing = SeslCheckboxDefaults$Common$ScaleTransition.easingReverse;
                Unit unit = Unit.INSTANCE;
                KeyframesSpec keyframesSpec = new KeyframesSpec(keyframesSpecConfig);
                this.label = 1;
                if (Animatable.animateTo$default(animatable, f, keyframesSpec, null, null, this, 12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
