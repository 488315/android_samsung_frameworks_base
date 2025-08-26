package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$animateProgress$2$collectionJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SwipeAnimation $animation;
    final /* synthetic */ AnimationSpec<Float> $cancelSpec;
    final /* synthetic */ AnimationSpec<Float> $commitSpec;
    final /* synthetic */ Flow $progress;
    final /* synthetic */ MutableSceneTransitionLayoutStateImpl $state;
    int label;

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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:
    
        if (com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r4, r5, r7) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0058, code lost:
    
        if (com.android.compose.animation.scene.PredictiveBackHandlerKt.access$animateProgress$animateOffset(r8, r1, r3, r4, r7) != r0) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
        } catch (CancellationException unused) {
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.$state;
            SwipeAnimation swipeAnimation = this.$animation;
            ContentKey contentKey = swipeAnimation.fromContent;
            AnimationSpec<Float> animationSpec = this.$cancelSpec;
            this.label = 3;
        }
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$progress;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$animation, null);
            this.label = 1;
            if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2 && i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl2 = this.$state;
        SwipeAnimation swipeAnimation2 = this.$animation;
        ContentKey contentKey2 = swipeAnimation2.toContent;
        AnimationSpec<Float> animationSpec2 = this.$commitSpec;
        this.label = 2;
    }
}
