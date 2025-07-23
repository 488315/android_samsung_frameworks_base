package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$animateProgress$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SwipeAnimation $animation;
    final /* synthetic */ CoroutineScope $animationScope;
    final /* synthetic */ AnimationSpec<Float> $cancelSpec;
    final /* synthetic */ AnimationSpec<Float> $commitSpec;
    final /* synthetic */ Flow $progress;
    final /* synthetic */ MutableSceneTransitionLayoutStateImpl $state;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.compose.animation.scene.PredictiveBackHandlerKt$animateProgress$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SwipeAnimation $animation;
        final /* synthetic */ Job $collectionJob;
        final /* synthetic */ MutableSceneTransitionLayoutStateImpl $state;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SwipeAnimation swipeAnimation, Job job, Continuation continuation) {
            super(2, continuation);
            this.$state = mutableSceneTransitionLayoutStateImpl;
            this.$animation = swipeAnimation;
            this.$collectionJob = job;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$state, this.$animation, this.$collectionJob, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.$state;
                SwipeAnimation swipeAnimation = this.$animation;
                Job job = this.$collectionJob;
                this.label = 1;
                if (PredictiveBackHandlerKt.access$startTransition(mutableSceneTransitionLayoutStateImpl, swipeAnimation, job, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PredictiveBackHandlerKt$animateProgress$2(CoroutineScope coroutineScope, MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SwipeAnimation swipeAnimation, Flow flow, AnimationSpec<Float> animationSpec, AnimationSpec<Float> animationSpec2, Continuation continuation) {
        super(2, continuation);
        this.$animationScope = coroutineScope;
        this.$state = mutableSceneTransitionLayoutStateImpl;
        this.$animation = swipeAnimation;
        this.$progress = flow;
        this.$commitSpec = animationSpec;
        this.$cancelSpec = animationSpec2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PredictiveBackHandlerKt$animateProgress$2 predictiveBackHandlerKt$animateProgress$2 = new PredictiveBackHandlerKt$animateProgress$2(this.$animationScope, this.$state, this.$animation, this.$progress, this.$commitSpec, this.$cancelSpec, continuation);
        predictiveBackHandlerKt$animateProgress$2.L$0 = obj;
        return predictiveBackHandlerKt$animateProgress$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PredictiveBackHandlerKt$animateProgress$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StandaloneCoroutine launch$default = BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new PredictiveBackHandlerKt$animateProgress$2$collectionJob$1(this.$progress, this.$animation, this.$commitSpec, this.$cancelSpec, this.$state, null), 3);
            CoroutineScope coroutineScope = this.$animationScope;
            if (coroutineScope != null) {
                return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$state, this.$animation, launch$default, null), 3);
            }
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.$state;
            SwipeAnimation swipeAnimation = this.$animation;
            this.label = 1;
            if (PredictiveBackHandlerKt.access$startTransition(mutableSceneTransitionLayoutStateImpl, swipeAnimation, launch$default, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
