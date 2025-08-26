package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.MotionDurationScale;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class DefaultFlingBehavior implements FlingBehavior {
    public DecayAnimationSpec flingDecay;
    public final MotionDurationScale motionDurationScale;

    /* renamed from: androidx.compose.foundation.gestures.DefaultFlingBehavior$performFling$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ float $initialVelocity;
        final /* synthetic */ ScrollScope $this_performFling;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DefaultFlingBehavior this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(float f, DefaultFlingBehavior defaultFlingBehavior, ScrollScope scrollScope, Continuation continuation) {
            super(2, continuation);
            this.$initialVelocity = f;
            this.this$0 = defaultFlingBehavior;
            this.$this_performFling = scrollScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$initialVelocity, this.this$0, this.$this_performFling, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            float f;
            Ref$FloatRef ref$FloatRef;
            AnimationState animationState;
            DecayAnimationSpec decayAnimationSpec;
            Function1 function1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (Math.abs(this.$initialVelocity) > 1.0f) {
                    final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
                    ref$FloatRef2.element = this.$initialVelocity;
                    final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
                    AnimationState animationStateAnimationState$default = AnimationStateKt.AnimationState$default(0.0f, this.$initialVelocity, 28);
                    try {
                        final DefaultFlingBehavior defaultFlingBehavior = this.this$0;
                        decayAnimationSpec = defaultFlingBehavior.flingDecay;
                        final ScrollScope scrollScope = this.$this_performFling;
                        function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DefaultFlingBehavior.performFling.2.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                AnimationScope animationScope = (AnimationScope) obj2;
                                float fFloatValue = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue() - ref$FloatRef3.element;
                                float fScrollBy = scrollScope.scrollBy(fFloatValue);
                                ref$FloatRef3.element = ((Number) ((SnapshotMutableStateImpl) animationScope.value$delegate).getValue()).floatValue();
                                ref$FloatRef2.element = ((Number) animationScope.getVelocity()).floatValue();
                                if (Math.abs(fFloatValue - fScrollBy) > 0.5f) {
                                    animationScope.cancelAnimation();
                                }
                                defaultFlingBehavior.getClass();
                                return Unit.INSTANCE;
                            }
                        };
                        this.L$0 = ref$FloatRef2;
                        this.L$1 = animationStateAnimationState$default;
                        this.label = 1;
                    } catch (CancellationException unused) {
                        ref$FloatRef = ref$FloatRef2;
                        animationState = animationStateAnimationState$default;
                        ref$FloatRef.element = ((Number) animationState.getVelocity()).floatValue();
                        f = ref$FloatRef.element;
                        return new Float(f);
                    }
                    if (SuspendAnimationKt.animateDecay(animationStateAnimationState$default, decayAnimationSpec, false, function1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    ref$FloatRef = ref$FloatRef2;
                    f = ref$FloatRef.element;
                } else {
                    f = this.$initialVelocity;
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
                f = ref$FloatRef.element;
            }
            return new Float(f);
        }
    }

    public DefaultFlingBehavior(DecayAnimationSpec<Float> decayAnimationSpec, MotionDurationScale motionDurationScale) {
        this.flingDecay = decayAnimationSpec;
        this.motionDurationScale = motionDurationScale;
    }

    @Override // androidx.compose.foundation.gestures.FlingBehavior
    public final Object performFling(ScrollScope scrollScope, float f, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.motionDurationScale, new AnonymousClass2(f, this, scrollScope, null), continuationImpl);
    }

    public DefaultFlingBehavior(DecayAnimationSpec decayAnimationSpec, MotionDurationScale motionDurationScale, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(decayAnimationSpec, (i & 2) != 0 ? ScrollableKt.DefaultScrollMotionDurationScale : motionDurationScale);
    }
}
