package com.android.systemui.bouncer.ui.composable;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinInputEntry$animateAppearance$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ PinInputEntry this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputEntry$animateAppearance$2(PinInputEntry pinInputEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinInputEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PinInputEntry$animateAppearance$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputEntry$animateAppearance$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
    
        if (r11.snapTo(r1, r10) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r11)
            return r11
        L10:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L18:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L32
        L1c:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.bouncer.ui.composable.PinInputEntry r11 = r10.this$0
            androidx.compose.animation.core.Animatable r11 = r11.entryWidth
            r1 = 0
            float r1 = (float) r1
            androidx.compose.ui.unit.Dp r1 = androidx.compose.ui.unit.Dp.m835boximpl(r1)
            r10.label = r3
            java.lang.Object r11 = r11.snapTo(r1, r10)
            if (r11 != r0) goto L32
            goto L51
        L32:
            com.android.systemui.bouncer.ui.composable.PinInputEntry r11 = r10.this$0
            androidx.compose.animation.core.Animatable r3 = r11.entryWidth
            com.android.systemui.bouncer.ui.composable.ShapeAnimations r11 = r11.shapeAnimations
            float r11 = r11.shapeSize
            androidx.compose.ui.unit.Dp r4 = androidx.compose.ui.unit.Dp.m835boximpl(r11)
            com.android.systemui.bouncer.ui.composable.PinInputEntry r11 = r10.this$0
            com.android.systemui.bouncer.ui.composable.ShapeAnimations r11 = r11.shapeAnimations
            androidx.compose.animation.core.TweenSpec r5 = r11.inputShiftAnimationSpec
            r10.label = r2
            r7 = 0
            r9 = 12
            r6 = 0
            r8 = r10
            java.lang.Object r10 = androidx.compose.animation.core.Animatable.animateTo$default(r3, r4, r5, r6, r7, r8, r9)
            if (r10 != r0) goto L52
        L51:
            return r0
        L52:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.PinInputEntry$animateAppearance$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
