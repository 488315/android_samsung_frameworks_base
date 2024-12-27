package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AnimatedValueKt {
    public static final <A, B> AnimatedValue<B> flatMap(AnimatedValue<? extends A> animatedValue, Function1 function1) {
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (AnimatedValue) function1.invoke(((AnimatedValue.NotAnimating) animatedValue).getValue());
        }
        if (!(animatedValue instanceof AnimatedValue.Animating)) {
            throw new NoWhenBranchMatchedException();
        }
        AnimatedValue.Animating animating = (AnimatedValue.Animating) animatedValue;
        AnimatedValue animatedValue2 = (AnimatedValue) function1.invoke(animating.getValue());
        if (animatedValue2 instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(((AnimatedValue.Animating) animatedValue2).getValue(), new AnimatedValueKt$flatMap$1(animatedValue, animatedValue2));
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.Animating(((AnimatedValue.NotAnimating) animatedValue2).getValue(), animating.getOnStopAnimating());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> AnimatedValue<T> flatten(AnimatedValue<? extends AnimatedValue<? extends T>> animatedValue) {
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (AnimatedValue) ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        if (!(animatedValue instanceof AnimatedValue.Animating)) {
            throw new NoWhenBranchMatchedException();
        }
        AnimatedValue.Animating animating = (AnimatedValue.Animating) animatedValue;
        AnimatedValue animatedValue2 = (AnimatedValue) animating.getValue();
        if (animatedValue2 instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(((AnimatedValue.Animating) animatedValue2).getValue(), new AnimatedValueKt$flatMap$1(animatedValue, animatedValue2));
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.Animating(((AnimatedValue.NotAnimating) animatedValue2).getValue(), animating.getOnStopAnimating());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> T getValue(AnimatedValue<? extends T> animatedValue) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            return (T) ((AnimatedValue.Animating) animatedValue).getValue();
        }
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (T) ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> boolean isAnimating(AnimatedValue<? extends T> animatedValue) {
        return animatedValue instanceof AnimatedValue.Animating;
    }

    public static final <A, B> AnimatedValue<B> map(AnimatedValue<? extends A> animatedValue, Function1 function1) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(function1.invoke(((AnimatedValue.Animating) animatedValue).getValue()), new AnimatedValueKt$map$1(animatedValue));
        }
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.NotAnimating(function1.invoke(((AnimatedValue.NotAnimating) animatedValue).getValue()));
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final void stopAnimating(AnimatedValue<?> animatedValue) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            ((AnimatedValue.Animating) animatedValue).getOnStopAnimating().invoke();
        }
    }

    public static final <T> Flow toAnimatedValueFlow(Flow flow) {
        return FlowKt.transformLatest(flow, new AnimatedValueKt$toAnimatedValueFlow$1(null));
    }

    public static final <A, B, Z> AnimatedValue<Z> zip(AnimatedValue<? extends A> animatedValue, AnimatedValue<? extends B> animatedValue2, Function2 function2) {
        Object value;
        Object value2;
        boolean z = animatedValue instanceof AnimatedValue.Animating;
        if (z) {
            value = ((AnimatedValue.Animating) animatedValue).getValue();
        } else {
            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        boolean z2 = animatedValue2 instanceof AnimatedValue.Animating;
        if (z2) {
            value2 = ((AnimatedValue.Animating) animatedValue2).getValue();
        } else {
            if (!(animatedValue2 instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value2 = ((AnimatedValue.NotAnimating) animatedValue2).getValue();
        }
        Object invoke = function2.invoke(value, value2);
        if (z) {
            if (z2) {
                return new AnimatedValue.Animating(invoke, new AnimatedValueKt$zip$1(animatedValue, animatedValue2));
            }
            if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
                return new AnimatedValue.Animating(invoke, ((AnimatedValue.Animating) animatedValue).getOnStopAnimating());
            }
            throw new NoWhenBranchMatchedException();
        }
        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
            throw new NoWhenBranchMatchedException();
        }
        if (z2) {
            return new AnimatedValue.Animating(invoke, ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating());
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.NotAnimating(invoke);
        }
        throw new NoWhenBranchMatchedException();
    }
}
