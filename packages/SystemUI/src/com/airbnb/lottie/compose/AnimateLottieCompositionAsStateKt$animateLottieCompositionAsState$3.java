package com.airbnb.lottie.compose;

import androidx.compose.runtime.MutableState;
import com.airbnb.lottie.LottieComposition;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $actualSpeed;
    final /* synthetic */ LottieAnimatable $animatable;
    final /* synthetic */ LottieCancellationBehavior $cancellationBehavior;
    final /* synthetic */ LottieClipSpec $clipSpec;
    final /* synthetic */ LottieComposition $composition;
    final /* synthetic */ boolean $isPlaying;
    final /* synthetic */ int $iterations;
    final /* synthetic */ boolean $restartOnPlay;
    final /* synthetic */ MutableState<Boolean> $wasPlaying$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3(boolean z, boolean z2, LottieAnimatable lottieAnimatable, LottieComposition lottieComposition, int i, float f, LottieClipSpec lottieClipSpec, LottieCancellationBehavior lottieCancellationBehavior, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$isPlaying = z;
        this.$restartOnPlay = z2;
        this.$animatable = lottieAnimatable;
        this.$composition = lottieComposition;
        this.$iterations = i;
        this.$actualSpeed = f;
        this.$clipSpec = lottieClipSpec;
        this.$cancellationBehavior = lottieCancellationBehavior;
        this.$wasPlaying$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3(this.$isPlaying, this.$restartOnPlay, this.$animatable, this.$composition, this.$iterations, this.$actualSpeed, this.$clipSpec, this.$cancellationBehavior, this.$wasPlaying$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00f6, code lost:
    
        if (r14 == r0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00f8, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a7, code lost:
    
        if (r15 != r0) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.compose.AnimateLottieCompositionAsStateKt$animateLottieCompositionAsState$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
