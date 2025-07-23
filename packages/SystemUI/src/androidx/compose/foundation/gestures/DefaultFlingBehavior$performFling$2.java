package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultFlingBehavior$performFling$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $initialVelocity;
    final /* synthetic */ ScrollScope $this_performFling;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DefaultFlingBehavior this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultFlingBehavior$performFling$2(float f, DefaultFlingBehavior defaultFlingBehavior, ScrollScope scrollScope, Continuation continuation) {
        super(2, continuation);
        this.$initialVelocity = f;
        this.this$0 = defaultFlingBehavior;
        this.$this_performFling = scrollScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultFlingBehavior$performFling$2(this.$initialVelocity, this.this$0, this.$this_performFling, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultFlingBehavior$performFling$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        float f;
        Ref$FloatRef ref$FloatRef;
        AnimationState animationState;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (Math.abs(this.$initialVelocity) <= 1.0f) {
                f = this.$initialVelocity;
                return new Float(f);
            }
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            ref$FloatRef2.element = this.$initialVelocity;
            final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
            AnimationState AnimationState$default = AnimationStateKt.AnimationState$default(0.0f, this.$initialVelocity, 28);
            try {
                final DefaultFlingBehavior defaultFlingBehavior = this.this$0;
                DecayAnimationSpec decayAnimationSpec = defaultFlingBehavior.flingDecay;
                final ScrollScope scrollScope = this.$this_performFling;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DefaultFlingBehavior$performFling$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        AnimationScope animationScope = (AnimationScope) obj2;
                        float floatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() - Ref$FloatRef.this.element;
                        float scrollBy = scrollScope.scrollBy(floatValue);
                        Ref$FloatRef.this.element = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                        ref$FloatRef2.element = ((Number) animationScope.getVelocity()).floatValue();
                        if (Math.abs(floatValue - scrollBy) > 0.5f) {
                            animationScope.cancelAnimation();
                        }
                        defaultFlingBehavior.getClass();
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = ref$FloatRef2;
                this.L$1 = AnimationState$default;
                this.label = 1;
                if (SuspendAnimationKt.animateDecay(AnimationState$default, decayAnimationSpec, false, function1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                ref$FloatRef = ref$FloatRef2;
            } catch (CancellationException unused) {
                ref$FloatRef = ref$FloatRef2;
                animationState = AnimationState$default;
                ref$FloatRef.element = ((Number) animationState.getVelocity()).floatValue();
                f = ref$FloatRef.element;
                return new Float(f);
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            animationState = (AnimationState) this.L$1;
            ref$FloatRef = (Ref$FloatRef) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (CancellationException unused2) {
                ref$FloatRef.element = ((Number) animationState.getVelocity()).floatValue();
                f = ref$FloatRef.element;
                return new Float(f);
            }
        }
        f = ref$FloatRef.element;
        return new Float(f);
    }
}
