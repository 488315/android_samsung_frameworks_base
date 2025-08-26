package com.android.compose.animation.scene;

import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.animation.core.VectorConvertersKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.TransformationRange;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FloatCompanionObject;

/* loaded from: classes.dex */
public final class TransitionBuilderImpl extends BaseTransitionBuilderImpl implements BaseTransitionBuilder {
    public final UserActionDistance distance;
    public final Lazy durationMillis$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.compose.animation.scene.TransitionBuilderImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            TweenSpec tweenSpec = this.f$0.spec;
            if (tweenSpec == null) {
                throw new IllegalStateException("timestampRange {} can only be used with a DurationBasedAnimationSpec");
            }
            FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
            return Integer.valueOf(tweenSpec.vectorize(VectorConvertersKt.FloatToVector).getDurationMillis());
        }
    });
    public TweenSpec spec;
    public final TransitionState.Transition transition;

    public TransitionBuilderImpl(TransitionState.Transition transition) {
        this.transition = transition;
    }

    public static void timestampRange$default(TransitionBuilderImpl transitionBuilderImpl, Integer num, Integer num2, Function1 function1, int i) {
        if ((i & 1) != 0) {
            num = null;
        }
        EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = EasingKt.LinearEasing;
        transitionBuilderImpl.getClass();
        if (num != null && (num.intValue() < 0 || num.intValue() > transitionBuilderImpl.getDurationMillis())) {
            throw new IllegalStateException(("invalid start value: startMillis=" + num + " durationMillis=" + transitionBuilderImpl.getDurationMillis()).toString());
        }
        if (num2.intValue() >= 0 && num2.intValue() <= transitionBuilderImpl.getDurationMillis()) {
            transitionBuilderImpl.range = new TransformationRange(num != null ? Float.valueOf(num.intValue() / transitionBuilderImpl.getDurationMillis()) : null, Float.valueOf(num2.intValue() / transitionBuilderImpl.getDurationMillis()), easingKt$$ExternalSyntheticLambda0);
            function1.mo781invoke(transitionBuilderImpl);
            transitionBuilderImpl.range = null;
        } else {
            throw new IllegalStateException(("invalid end value: endMillis=" + num + " durationMillis=" + transitionBuilderImpl.getDurationMillis()).toString());
        }
    }

    public final int getDurationMillis() {
        return ((Number) this.durationMillis$delegate.getValue()).intValue();
    }
}
