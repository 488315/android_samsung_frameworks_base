package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.ui.unit.IntOffset;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutItemAnimation$animatePlacementDelta$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FiniteAnimationSpec<IntOffset> $spec;
    final /* synthetic */ long $totalDelta;
    Object L$0;
    int label;
    final /* synthetic */ LazyLayoutItemAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyLayoutItemAnimation$animatePlacementDelta$1(LazyLayoutItemAnimation lazyLayoutItemAnimation, FiniteAnimationSpec<IntOffset> finiteAnimationSpec, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lazyLayoutItemAnimation;
        this.$spec = finiteAnimationSpec;
        this.$totalDelta = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LazyLayoutItemAnimation$animatePlacementDelta$1(this.this$0, this.$spec, this.$totalDelta, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LazyLayoutItemAnimation$animatePlacementDelta$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0098, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r11, r4, r5, null, r7, r8, 4) != r0) goto L31;
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
            if (r1 == 0) goto L22
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.util.concurrent.CancellationException -> La7
            r8 = r10
            goto L9b
        L12:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L1a:
            java.lang.Object r1 = r10.L$0
            androidx.compose.animation.core.FiniteAnimationSpec r1 = (androidx.compose.animation.core.FiniteAnimationSpec) r1
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.util.concurrent.CancellationException -> La7
            goto L5e
        L22:
            kotlin.ResultKt.throwOnFailure(r11)
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.Animatable r11 = r11.placementDeltaAnimation     // Catch: java.util.concurrent.CancellationException -> La7
            boolean r11 = r11.isRunning()     // Catch: java.util.concurrent.CancellationException -> La7
            if (r11 == 0) goto L3c
            androidx.compose.animation.core.FiniteAnimationSpec<androidx.compose.ui.unit.IntOffset> r11 = r10.$spec     // Catch: java.util.concurrent.CancellationException -> La7
            boolean r1 = r11 instanceof androidx.compose.animation.core.SpringSpec     // Catch: java.util.concurrent.CancellationException -> La7
            if (r1 == 0) goto L38
            androidx.compose.animation.core.SpringSpec r11 = (androidx.compose.animation.core.SpringSpec) r11     // Catch: java.util.concurrent.CancellationException -> La7
            goto L3a
        L38:
            androidx.compose.animation.core.SpringSpec r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimationKt.InterruptionSpec     // Catch: java.util.concurrent.CancellationException -> La7
        L3a:
            r1 = r11
            goto L3f
        L3c:
            androidx.compose.animation.core.FiniteAnimationSpec<androidx.compose.ui.unit.IntOffset> r11 = r10.$spec     // Catch: java.util.concurrent.CancellationException -> La7
            goto L3a
        L3f:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.Animatable r11 = r11.placementDeltaAnimation     // Catch: java.util.concurrent.CancellationException -> La7
            boolean r11 = r11.isRunning()     // Catch: java.util.concurrent.CancellationException -> La7
            if (r11 != 0) goto L65
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.Animatable r11 = r11.placementDeltaAnimation     // Catch: java.util.concurrent.CancellationException -> La7
            long r4 = r10.$totalDelta     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.ui.unit.IntOffset r4 = androidx.compose.ui.unit.IntOffset.m847boximpl(r4)     // Catch: java.util.concurrent.CancellationException -> La7
            r10.L$0 = r1     // Catch: java.util.concurrent.CancellationException -> La7
            r10.label = r3     // Catch: java.util.concurrent.CancellationException -> La7
            java.lang.Object r11 = r11.snapTo(r4, r10)     // Catch: java.util.concurrent.CancellationException -> La7
            if (r11 != r0) goto L5e
            goto L9a
        L5e:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            kotlin.jvm.functions.Function0 r11 = r11.onLayerPropertyChanged     // Catch: java.util.concurrent.CancellationException -> La7
            r11.invoke()     // Catch: java.util.concurrent.CancellationException -> La7
        L65:
            r5 = r1
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.Animatable r11 = r11.placementDeltaAnimation     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.AnimationState r11 = r11.internalState     // Catch: java.util.concurrent.CancellationException -> La7
            java.lang.Object r11 = r11.getValue()     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.ui.unit.IntOffset r11 = (androidx.compose.ui.unit.IntOffset) r11     // Catch: java.util.concurrent.CancellationException -> La7
            long r3 = r11.packedValue     // Catch: java.util.concurrent.CancellationException -> La7
            long r6 = r10.$totalDelta     // Catch: java.util.concurrent.CancellationException -> La7
            long r3 = androidx.compose.ui.unit.IntOffset.m850minusqkQi6aY(r3, r6)     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r11 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.animation.core.Animatable r11 = r11.placementDeltaAnimation     // Catch: java.util.concurrent.CancellationException -> La7
            r6 = r3
            androidx.compose.ui.unit.IntOffset r4 = androidx.compose.ui.unit.IntOffset.m847boximpl(r6)     // Catch: java.util.concurrent.CancellationException -> La7
            r8 = r6
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animatePlacementDelta$1$1 r7 = new androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animatePlacementDelta$1$1     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r1 = r10.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            r7.<init>()     // Catch: java.util.concurrent.CancellationException -> La7
            r1 = 0
            r10.L$0 = r1     // Catch: java.util.concurrent.CancellationException -> La7
            r10.label = r2     // Catch: java.util.concurrent.CancellationException -> La7
            r6 = 0
            r9 = 4
            r8 = r10
            r3 = r11
            java.lang.Object r10 = androidx.compose.animation.core.Animatable.animateTo$default(r3, r4, r5, r6, r7, r8, r9)     // Catch: java.util.concurrent.CancellationException -> La7
            if (r10 != r0) goto L9b
        L9a:
            return r0
        L9b:
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r10 = r8.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$Companion r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.Companion     // Catch: java.util.concurrent.CancellationException -> La7
            r11 = 0
            r10.setPlacementAnimationInProgress(r11)     // Catch: java.util.concurrent.CancellationException -> La7
            androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation r10 = r8.this$0     // Catch: java.util.concurrent.CancellationException -> La7
            r10.isRunningMovingAwayAnimation = r11     // Catch: java.util.concurrent.CancellationException -> La7
        La7:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation$animatePlacementDelta$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
