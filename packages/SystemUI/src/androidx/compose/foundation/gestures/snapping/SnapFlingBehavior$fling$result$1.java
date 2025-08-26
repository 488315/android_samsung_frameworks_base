package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class SnapFlingBehavior$fling$result$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $initialVelocity;
    final /* synthetic */ Function1 $onRemainingScrollOffsetUpdate;
    final /* synthetic */ ScrollScope $this_fling;
    Object L$0;
    int label;
    final /* synthetic */ SnapFlingBehavior this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnapFlingBehavior$fling$result$1(SnapFlingBehavior snapFlingBehavior, float f, Function1 function1, ScrollScope scrollScope, Continuation continuation) {
        super(2, continuation);
        this.this$0 = snapFlingBehavior;
        this.$initialVelocity = f;
        this.$onRemainingScrollOffsetUpdate = function1;
        this.$this_fling = scrollScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SnapFlingBehavior$fling$result$1(this.this$0, this.$initialVelocity, this.$onRemainingScrollOffsetUpdate, this.$this_fling, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SnapFlingBehavior$fling$result$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final Ref$FloatRef ref$FloatRef;
        SnapFlingBehavior$fling$result$1 snapFlingBehavior$fling$result$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            float fCalculateApproachOffset = this.this$0.snapLayoutInfoProvider.calculateApproachOffset(this.$initialVelocity, DecayAnimationSpecKt.calculateTargetValue(this.this$0.decayAnimationSpec, 0.0f, this.$initialVelocity));
            if (Float.isNaN(fCalculateApproachOffset)) {
                InlineClassHelperKt.throwIllegalStateException("calculateApproachOffset returned NaN. Please use a valid value.");
            }
            ref$FloatRef = new Ref$FloatRef();
            float fSignum = Math.signum(this.$initialVelocity) * Math.abs(fCalculateApproachOffset);
            ref$FloatRef.element = fSignum;
            this.$onRemainingScrollOffsetUpdate.mo781invoke(new Float(fSignum));
            SnapFlingBehavior snapFlingBehavior = this.this$0;
            ScrollScope scrollScope = this.$this_fling;
            float f = ref$FloatRef.element;
            float f2 = this.$initialVelocity;
            final Function1 function1 = this.$onRemainingScrollOffsetUpdate;
            Function1 function12 = new Function1() { // from class: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1$animationState$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    float fFloatValue = ((Number) obj2).floatValue();
                    Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
                    float f3 = ref$FloatRef2.element - fFloatValue;
                    ref$FloatRef2.element = f3;
                    function1.mo781invoke(Float.valueOf(f3));
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = ref$FloatRef;
            this.label = 1;
            obj = SnapFlingBehavior.access$tryApproach(snapFlingBehavior, scrollScope, f, f2, function12, this);
            snapFlingBehavior$fling$result$1 = this;
            if (obj != coroutineSingletons) {
            }
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ref$FloatRef = (Ref$FloatRef) this.L$0;
        ResultKt.throwOnFailure(obj);
        snapFlingBehavior$fling$result$1 = this;
        AnimationState animationState = (AnimationState) obj;
        float fCalculateSnapOffset = snapFlingBehavior$fling$result$1.this$0.snapLayoutInfoProvider.calculateSnapOffset(((Number) animationState.getVelocity()).floatValue());
        if (Float.isNaN(fCalculateSnapOffset)) {
            InlineClassHelperKt.throwIllegalStateException("calculateSnapOffset returned NaN. Please use a valid value.");
        }
        ref$FloatRef.element = fCalculateSnapOffset;
        ScrollScope scrollScope2 = snapFlingBehavior$fling$result$1.$this_fling;
        AnimationState animationStateCopy$default = AnimationStateKt.copy$default(animationState, 0.0f, 0.0f, 30);
        AnimationSpec animationSpec = snapFlingBehavior$fling$result$1.this$0.snapAnimationSpec;
        final Function1 function13 = snapFlingBehavior$fling$result$1.$onRemainingScrollOffsetUpdate;
        Function1 function14 = new Function1() { // from class: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                float fFloatValue = ((Number) obj2).floatValue();
                Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
                float f3 = ref$FloatRef2.element - fFloatValue;
                ref$FloatRef2.element = f3;
                function13.mo781invoke(Float.valueOf(f3));
                return Unit.INSTANCE;
            }
        };
        snapFlingBehavior$fling$result$1.L$0 = null;
        snapFlingBehavior$fling$result$1.label = 2;
        Object objAccess$animateWithTarget = SnapFlingBehaviorKt.access$animateWithTarget(scrollScope2, fCalculateSnapOffset, fCalculateSnapOffset, animationStateCopy$default, animationSpec, function14, snapFlingBehavior$fling$result$1);
        return objAccess$animateWithTarget == coroutineSingletons ? coroutineSingletons : objAccess$animateWithTarget;
    }
}
