package androidx.compose.material3;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ButtonElevation$animateElevation$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Dp, AnimationVector1D> $animatable;
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ Interaction $interaction;
    final /* synthetic */ float $target;
    int label;
    final /* synthetic */ ButtonElevation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ButtonElevation$animateElevation$2$1(Animatable<Dp, AnimationVector1D> animatable, float f, boolean z, ButtonElevation buttonElevation, Interaction interaction, Continuation continuation) {
        super(2, continuation);
        this.$animatable = animatable;
        this.$target = f;
        this.$enabled = z;
        this.this$0 = buttonElevation;
        this.$interaction = interaction;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ButtonElevation$animateElevation$2$1(this.$animatable, this.$target, this.$enabled, this.this$0, this.$interaction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ButtonElevation$animateElevation$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0045, code lost:
    
        if (r7.snapTo(r1, r6) == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x009c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x009a, code lost:
    
        if (androidx.compose.material3.internal.ElevationKt.m319animateElevationrAjV9yQ(r7, r1, r3, r4, r6) == r0) goto L28;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1a
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L15:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L9d
        L1a:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.animation.core.Animatable<androidx.compose.ui.unit.Dp, androidx.compose.animation.core.AnimationVector1D> r7 = r6.$animatable
            androidx.compose.runtime.MutableState r7 = r7.targetValue$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r7 = (androidx.compose.runtime.SnapshotMutableStateImpl) r7
            java.lang.Object r7 = r7.getValue()
            androidx.compose.ui.unit.Dp r7 = (androidx.compose.ui.unit.Dp) r7
            float r7 = r7.value
            float r1 = r6.$target
            boolean r7 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r7, r1)
            if (r7 != 0) goto L9d
            boolean r7 = r6.$enabled
            if (r7 != 0) goto L48
            androidx.compose.animation.core.Animatable<androidx.compose.ui.unit.Dp, androidx.compose.animation.core.AnimationVector1D> r7 = r6.$animatable
            float r1 = r6.$target
            androidx.compose.ui.unit.Dp r1 = androidx.compose.ui.unit.Dp.m835boximpl(r1)
            r6.label = r3
            java.lang.Object r6 = r7.snapTo(r1, r6)
            if (r6 != r0) goto L9d
            goto L9c
        L48:
            androidx.compose.animation.core.Animatable<androidx.compose.ui.unit.Dp, androidx.compose.animation.core.AnimationVector1D> r7 = r6.$animatable
            androidx.compose.runtime.MutableState r7 = r7.targetValue$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r7 = (androidx.compose.runtime.SnapshotMutableStateImpl) r7
            java.lang.Object r7 = r7.getValue()
            androidx.compose.ui.unit.Dp r7 = (androidx.compose.ui.unit.Dp) r7
            float r7 = r7.value
            androidx.compose.material3.ButtonElevation r1 = r6.this$0
            float r1 = r1.pressedElevation
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r7, r1)
            r3 = 0
            if (r1 == 0) goto L6f
            androidx.compose.foundation.interaction.PressInteraction$Press r7 = new androidx.compose.foundation.interaction.PressInteraction$Press
            androidx.compose.ui.geometry.Offset$Companion r1 = androidx.compose.ui.geometry.Offset.Companion
            r1.getClass()
            r4 = 0
            r7.<init>(r4, r3)
            r3 = r7
            goto L8e
        L6f:
            androidx.compose.material3.ButtonElevation r1 = r6.this$0
            float r1 = r1.hoveredElevation
            boolean r1 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r7, r1)
            if (r1 == 0) goto L7f
            androidx.compose.foundation.interaction.HoverInteraction$Enter r3 = new androidx.compose.foundation.interaction.HoverInteraction$Enter
            r3.<init>()
            goto L8e
        L7f:
            androidx.compose.material3.ButtonElevation r1 = r6.this$0
            float r1 = r1.focusedElevation
            boolean r7 = androidx.compose.ui.unit.Dp.m836equalsimpl0(r7, r1)
            if (r7 == 0) goto L8e
            androidx.compose.foundation.interaction.FocusInteraction$Focus r3 = new androidx.compose.foundation.interaction.FocusInteraction$Focus
            r3.<init>()
        L8e:
            androidx.compose.animation.core.Animatable<androidx.compose.ui.unit.Dp, androidx.compose.animation.core.AnimationVector1D> r7 = r6.$animatable
            float r1 = r6.$target
            androidx.compose.foundation.interaction.Interaction r4 = r6.$interaction
            r6.label = r2
            java.lang.Object r6 = androidx.compose.material3.internal.ElevationKt.m319animateElevationrAjV9yQ(r7, r1, r3, r4, r6)
            if (r6 != r0) goto L9d
        L9c:
            return r0
        L9d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ButtonElevation$animateElevation$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
