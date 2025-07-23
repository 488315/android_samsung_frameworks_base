package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $it;
    final /* synthetic */ Animatable<Integer, AnimationVector1D> $offsetAnimatable;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1(Animatable<Integer, AnimationVector1D> animatable, int i, Continuation continuation) {
        super(2, continuation);
        this.$offsetAnimatable = animatable;
        this.$it = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1(this.$offsetAnimatable, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003f, code lost:
    
        if (r9.snapTo(r2, r8) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005b, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, null, null, null, r8, 14) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r9)
            goto L5e
        L10:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L18:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L42
        L1c:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.compose.animation.core.Animatable<java.lang.Integer, androidx.compose.animation.core.AnimationVector1D> r9 = r8.$offsetAnimatable
            androidx.compose.animation.core.AnimationState r9 = r9.internalState
            java.lang.Object r9 = r9.getValue()
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            r1 = -1
            if (r9 != r1) goto L45
            androidx.compose.animation.core.Animatable<java.lang.Integer, androidx.compose.animation.core.AnimationVector1D> r9 = r8.$offsetAnimatable
            int r1 = r8.$it
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r1)
            r8.label = r3
            java.lang.Object r8 = r9.snapTo(r2, r8)
            if (r8 != r0) goto L42
            goto L5d
        L42:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            goto L5e
        L45:
            androidx.compose.animation.core.Animatable<java.lang.Integer, androidx.compose.animation.core.AnimationVector1D> r1 = r8.$offsetAnimatable
            int r9 = r8.$it
            r3 = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r8.label = r3
            r5 = 0
            r7 = 14
            r3 = 0
            r4 = 0
            r6 = r8
            java.lang.Object r8 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto L5e
        L5d:
            return r0
        L5e:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.selector.ui.composable.VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
