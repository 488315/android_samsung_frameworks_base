package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.ScrollScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        if (r14 == r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r13.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L22
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r14)
            return r14
        L11:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L19:
            java.lang.Object r1 = r13.L$0
            kotlin.jvm.internal.Ref$FloatRef r1 = (kotlin.jvm.internal.Ref$FloatRef) r1
            kotlin.ResultKt.throwOnFailure(r14)
            r10 = r13
            goto L7c
        L22:
            kotlin.ResultKt.throwOnFailure(r14)
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r14 = r13.this$0
            androidx.compose.animation.core.DecayAnimationSpec r14 = r14.decayAnimationSpec
            float r1 = r13.$initialVelocity
            float r14 = androidx.compose.animation.core.DecayAnimationSpecKt.calculateTargetValue(r14, r2, r1)
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r1 = r13.this$0
            androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider r1 = r1.snapLayoutInfoProvider
            float r5 = r13.$initialVelocity
            float r14 = r1.calculateApproachOffset(r5, r14)
            boolean r1 = java.lang.Float.isNaN(r14)
            if (r1 == 0) goto L44
            java.lang.String r1 = "calculateApproachOffset returned NaN. Please use a valid value."
            androidx.compose.foundation.internal.InlineClassHelperKt.throwIllegalStateException(r1)
        L44:
            kotlin.jvm.internal.Ref$FloatRef r1 = new kotlin.jvm.internal.Ref$FloatRef
            r1.<init>()
            float r14 = java.lang.Math.abs(r14)
            float r5 = r13.$initialVelocity
            float r5 = java.lang.Math.signum(r5)
            float r5 = r5 * r14
            r1.element = r5
            kotlin.jvm.functions.Function1 r14 = r13.$onRemainingScrollOffsetUpdate
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r5)
            r14.mo779invoke(r6)
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r7 = r13.this$0
            androidx.compose.foundation.gestures.ScrollScope r8 = r13.$this_fling
            float r9 = r1.element
            float r10 = r13.$initialVelocity
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1$animationState$1 r11 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1$animationState$1
            kotlin.jvm.functions.Function1 r14 = r13.$onRemainingScrollOffsetUpdate
            r11.<init>()
            r13.L$0 = r1
            r13.label = r4
            r12 = r13
            java.lang.Object r14 = androidx.compose.foundation.gestures.snapping.SnapFlingBehavior.access$tryApproach(r7, r8, r9, r10, r11, r12)
            r10 = r12
            if (r14 != r0) goto L7c
            goto Lbc
        L7c:
            androidx.compose.animation.core.AnimationState r14 = (androidx.compose.animation.core.AnimationState) r14
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r13 = r10.this$0
            androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider r13 = r13.snapLayoutInfoProvider
            java.lang.Object r4 = r14.getVelocity()
            java.lang.Number r4 = (java.lang.Number) r4
            float r4 = r4.floatValue()
            float r5 = r13.calculateSnapOffset(r4)
            boolean r13 = java.lang.Float.isNaN(r5)
            if (r13 == 0) goto L9b
            java.lang.String r13 = "calculateSnapOffset returned NaN. Please use a valid value."
            androidx.compose.foundation.internal.InlineClassHelperKt.throwIllegalStateException(r13)
        L9b:
            r1.element = r5
            androidx.compose.foundation.gestures.ScrollScope r4 = r10.$this_fling
            r13 = 30
            androidx.compose.animation.core.AnimationState r7 = androidx.compose.animation.core.AnimationStateKt.copy$default(r14, r2, r2, r13)
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r13 = r10.this$0
            androidx.compose.animation.core.AnimationSpec r8 = r13.snapAnimationSpec
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1$4 r9 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1$4
            kotlin.jvm.functions.Function1 r13 = r10.$onRemainingScrollOffsetUpdate
            r9.<init>()
            r13 = 0
            r10.L$0 = r13
            r10.label = r3
            r6 = r5
            java.lang.Object r13 = androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt.access$animateWithTarget(r4, r5, r6, r7, r8, r9, r10)
            if (r13 != r0) goto Lbd
        Lbc:
            return r0
        Lbd:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
