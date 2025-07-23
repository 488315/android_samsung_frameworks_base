package androidx.compose.material3;

import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SearchBarKt$SearchBar$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animationProgress;
    final /* synthetic */ MutableState<BackEventCompat> $currentBackEvent;
    final /* synthetic */ boolean $expanded;
    final /* synthetic */ MutableFloatState $finalBackProgress;
    final /* synthetic */ MutableState<BackEventCompat> $firstBackEvent;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SearchBarKt$SearchBar$3$1(Animatable<Float, AnimationVector1D> animatable, boolean z, MutableFloatState mutableFloatState, MutableState<BackEventCompat> mutableState, MutableState<BackEventCompat> mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$animationProgress = animatable;
        this.$expanded = z;
        this.$finalBackProgress = mutableFloatState;
        this.$firstBackEvent = mutableState;
        this.$currentBackEvent = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SearchBarKt$SearchBar$3$1(this.$animationProgress, this.$expanded, this.$finalBackProgress, this.$firstBackEvent, this.$currentBackEvent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SearchBarKt$SearchBar$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x007f  */
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
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r12)
            goto L64
        Ld:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L15:
            kotlin.ResultKt.throwOnFailure(r12)
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r12 = r11.$animationProgress
            androidx.compose.animation.core.AnimationState r12 = r12.internalState
            java.lang.Object r12 = r12.getValue()
            java.lang.Number r12 = (java.lang.Number) r12
            float r12 = r12.floatValue()
            r1 = 0
            int r12 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r12 <= 0) goto L43
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r12 = r11.$animationProgress
            androidx.compose.animation.core.AnimationState r12 = r12.internalState
            java.lang.Object r12 = r12.getValue()
            java.lang.Number r12 = (java.lang.Number) r12
            float r12 = r12.floatValue()
            int r12 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r12 >= 0) goto L43
            androidx.compose.animation.core.TweenSpec r12 = androidx.compose.material3.SearchBarKt.AnimationPredictiveBackExitFloatSpec
        L41:
            r6 = r12
            goto L4d
        L43:
            boolean r12 = r11.$expanded
            if (r12 == 0) goto L4a
            androidx.compose.animation.core.TweenSpec r12 = androidx.compose.material3.SearchBarKt.AnimationEnterFloatSpec
            goto L41
        L4a:
            androidx.compose.animation.core.TweenSpec r12 = androidx.compose.material3.SearchBarKt.AnimationExitFloatSpec
            goto L41
        L4d:
            boolean r12 = r11.$expanded
            if (r12 == 0) goto L52
            r1 = r3
        L52:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r12 = r11.$animationProgress
            androidx.compose.animation.core.AnimationState r12 = r12.internalState
            java.lang.Object r12 = r12.getValue()
            java.lang.Number r12 = (java.lang.Number) r12
            float r12 = r12.floatValue()
            int r12 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r12 != 0) goto L66
        L64:
            r9 = r11
            goto L7b
        L66:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r11.$animationProgress
            java.lang.Float r5 = new java.lang.Float
            r5.<init>(r1)
            r11.label = r2
            r8 = 0
            r10 = 12
            r7 = 0
            r9 = r11
            java.lang.Object r11 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)
            if (r11 != r0) goto L7b
            return r0
        L7b:
            boolean r11 = r9.$expanded
            if (r11 != 0) goto L93
            androidx.compose.runtime.MutableFloatState r11 = r9.$finalBackProgress
            r12 = 2143289344(0x7fc00000, float:NaN)
            androidx.compose.runtime.SnapshotMutableFloatStateImpl r11 = (androidx.compose.runtime.SnapshotMutableFloatStateImpl) r11
            r11.setFloatValue(r12)
            androidx.compose.runtime.MutableState<androidx.activity.BackEventCompat> r11 = r9.$firstBackEvent
            r12 = 0
            r11.setValue(r12)
            androidx.compose.runtime.MutableState<androidx.activity.BackEventCompat> r11 = r9.$currentBackEvent
            r11.setValue(r12)
        L93:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SearchBarKt$SearchBar$3$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
