package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0073, code lost:
    
        if (r12.snapTo(r13, r10) == r0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0075, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r5, r6, r7, null, null, r10, 12) == r0) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1e
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r13)
            goto L76
        L11:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L19:
            kotlin.ResultKt.throwOnFailure(r13)
            r10 = r12
            goto L66
        L1e:
            kotlin.ResultKt.throwOnFailure(r13)
            boolean r13 = r12.$checked
            androidx.compose.runtime.MutableState<java.lang.Boolean> r1 = r12.$prevChecked$delegate
            com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition r5 = com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition.INSTANCE
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r13 == r1) goto L76
            androidx.compose.runtime.MutableState<java.lang.Boolean> r13 = r12.$prevChecked$delegate
            boolean r1 = r12.$checked
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r13.setValue(r1)
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r5 = r12.$alphaAnimator
            boolean r13 = r12.$checked
            if (r13 == 0) goto L47
            r13 = 1065353216(0x3f800000, float:1.0)
            goto L48
        L47:
            r13 = r2
        L48:
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r13)
            boolean r13 = r12.$checked
            com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1$$ExternalSyntheticLambda0 r1 = new com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1$$ExternalSyntheticLambda0
            r1.<init>()
            androidx.compose.animation.core.KeyframesSpec r7 = androidx.compose.animation.core.AnimationSpecKt.keyframes(r1)
            r12.label = r4
            r9 = 0
            r11 = 12
            r8 = 0
            r10 = r12
            java.lang.Object r12 = androidx.compose.animation.core.Animatable.animateTo$default(r5, r6, r7, r8, r9, r10, r11)
            if (r12 != r0) goto L66
            goto L75
        L66:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r12 = r10.$alphaAnimator
            java.lang.Float r13 = new java.lang.Float
            r13.<init>(r2)
            r10.label = r3
            java.lang.Object r12 = r12.snapTo(r13, r10)
            if (r12 != r0) goto L76
        L75:
            return r0
        L76:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.foundation.SeslCheckboxDefaults$LeftCheck$AlphaTransition$animateAlpha$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
