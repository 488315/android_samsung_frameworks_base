package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$animateProgress$2$collectionJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SwipeAnimation $animation;
    final /* synthetic */ AnimationSpec<Float> $cancelSpec;
    final /* synthetic */ AnimationSpec<Float> $commitSpec;
    final /* synthetic */ Flow $progress;
    final /* synthetic */ MutableSceneTransitionLayoutStateImpl $state;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.compose.animation.scene.PredictiveBackHandlerKt$animateProgress$2$collectionJob$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SwipeAnimation $animation;
        /* synthetic */ float F$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SwipeAnimation swipeAnimation, Continuation continuation) {
            super(2, continuation);
            this.$animation = swipeAnimation;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$animation, continuation);
            anonymousClass1.F$0 = ((Number) obj).floatValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            float f = this.F$0;
            SwipeAnimation swipeAnimation = this.$animation;
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            swipeAnimation.setDragOffset(f);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PredictiveBackHandlerKt$animateProgress$2$collectionJob$1(Flow flow, SwipeAnimation swipeAnimation, AnimationSpec<Float> animationSpec, AnimationSpec<Float> animationSpec2, MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Continuation continuation) {
        super(2, continuation);
        this.$progress = flow;
        this.$animation = swipeAnimation;
        this.$commitSpec = animationSpec;
        this.$cancelSpec = animationSpec2;
        this.$state = mutableSceneTransitionLayoutStateImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PredictiveBackHandlerKt$animateProgress$2$collectionJob$1(this.$progress, this.$animation, this.$commitSpec, this.$cancelSpec, this.$state, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PredictiveBackHandlerKt$animateProgress$2$collectionJob$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
    
        if (com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r4, r5, r7) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0058, code lost:
    
        if (com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r3, r4, r7) != r0) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L23
            if (r1 == r4) goto L1f
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5b
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.util.concurrent.CancellationException -> L4a
            goto L5b
        L1f:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.util.concurrent.CancellationException -> L4a
            goto L39
        L23:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.Flow r8 = r7.$progress     // Catch: java.util.concurrent.CancellationException -> L4a
            com.android.compose.animation.scene.PredictiveBackHandlerKt$animateProgress$2$collectionJob$1$1 r1 = new com.android.compose.animation.scene.PredictiveBackHandlerKt$animateProgress$2$collectionJob$1$1     // Catch: java.util.concurrent.CancellationException -> L4a
            com.android.compose.animation.scene.SwipeAnimation r5 = r7.$animation     // Catch: java.util.concurrent.CancellationException -> L4a
            r6 = 0
            r1.<init>(r5, r6)     // Catch: java.util.concurrent.CancellationException -> L4a
            r7.label = r4     // Catch: java.util.concurrent.CancellationException -> L4a
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.collectLatest(r8, r1, r7)     // Catch: java.util.concurrent.CancellationException -> L4a
            if (r8 != r0) goto L39
            goto L5a
        L39:
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl r8 = r7.$state     // Catch: java.util.concurrent.CancellationException -> L4a
            com.android.compose.animation.scene.SwipeAnimation r1 = r7.$animation     // Catch: java.util.concurrent.CancellationException -> L4a
            com.android.compose.animation.scene.ContentKey r4 = r1.toContent     // Catch: java.util.concurrent.CancellationException -> L4a
            androidx.compose.animation.core.AnimationSpec<java.lang.Float> r5 = r7.$commitSpec     // Catch: java.util.concurrent.CancellationException -> L4a
            r7.label = r3     // Catch: java.util.concurrent.CancellationException -> L4a
            java.lang.Object r7 = com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r4, r5, r7)     // Catch: java.util.concurrent.CancellationException -> L4a
            if (r7 != r0) goto L5b
            goto L5a
        L4a:
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl r8 = r7.$state
            com.android.compose.animation.scene.SwipeAnimation r1 = r7.$animation
            com.android.compose.animation.scene.ContentKey r3 = r1.fromContent
            androidx.compose.animation.core.AnimationSpec<java.lang.Float> r4 = r7.$cancelSpec
            r7.label = r2
            java.lang.Object r7 = com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r3, r4, r7)
            if (r7 != r0) goto L5b
        L5a:
            return r0
        L5b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.PredictiveBackHandlerKt$animateProgress$2$collectionJob$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
