package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SelectionKt$animateAngle$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animatable;
    final /* synthetic */ MutableState<Boolean> $animate$delegate;
    final /* synthetic */ TileState $tileState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectionKt$animateAngle$1$1(TileState tileState, Animatable<Float, AnimationVector1D> animatable, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$tileState = tileState;
        this.$animatable = animatable;
        this.$animate$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SelectionKt$animateAngle$1$1(this.$tileState, this.$animatable, this.$animate$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SelectionKt$animateAngle$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0078, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, null, null, null, r9, 14) == r0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0084, code lost:
    
        if (r11.snapTo(r5, r9) == r0) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r12)
            r9 = r11
            goto L87
        L12:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1a:
            kotlin.ResultKt.throwOnFailure(r12)
            r9 = r11
            goto L89
        L20:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.qs.panels.ui.compose.selection.TileState r12 = r11.$tileState
            int[] r1 = com.android.systemui.qs.panels.ui.compose.selection.SelectionKt.WhenMappings.$EnumSwitchMapping$0
            int r12 = r12.ordinal()
            r12 = r1[r12]
            if (r12 == r3) goto L50
            if (r12 == r2) goto L50
            r1 = 3
            if (r12 == r1) goto L48
            r1 = 4
            if (r12 == r1) goto L41
            r1 = 5
            if (r12 != r1) goto L3b
            goto L50
        L3b:
            kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
            r11.<init>()
            throw r11
        L41:
            r12 = 0
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
        L46:
            r5 = r12
            goto L52
        L48:
            r12 = -1085485875(0xffffffffbf4ccccd, float:-0.8)
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            goto L46
        L50:
            r12 = 0
            goto L46
        L52:
            if (r5 != 0) goto L5c
            androidx.compose.runtime.MutableState<java.lang.Boolean> r11 = r11.$animate$delegate
            java.lang.Boolean r12 = java.lang.Boolean.FALSE
            r11.setValue(r12)
            goto L90
        L5c:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r12 = r11.$animate$delegate
            java.lang.Object r12 = r12.getValue()
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L7b
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r11.$animatable
            r11.label = r3
            r8 = 0
            r10 = 14
            r6 = 0
            r7 = 0
            r9 = r11
            java.lang.Object r11 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)
            if (r11 != r0) goto L89
            goto L86
        L7b:
            r9 = r11
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r11 = r9.$animatable
            r9.label = r2
            java.lang.Object r11 = r11.snapTo(r5, r9)
            if (r11 != r0) goto L87
        L86:
            return r0
        L87:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
        L89:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r11 = r9.$animate$delegate
            java.lang.Boolean r12 = java.lang.Boolean.TRUE
            r11.setValue(r12)
        L90:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.selection.SelectionKt$animateAngle$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
