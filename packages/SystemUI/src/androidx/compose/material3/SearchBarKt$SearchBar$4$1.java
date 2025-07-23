package androidx.compose.material3;

import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.material3.internal.BackHandlerKt;
import androidx.compose.material3.internal.PredictiveBack;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SearchBarKt$SearchBar$4$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animationProgress;
    final /* synthetic */ CoroutineScope $coroutineScope;
    final /* synthetic */ MutableState<BackEventCompat> $currentBackEvent;
    final /* synthetic */ MutableFloatState $finalBackProgress;
    final /* synthetic */ MutableState<BackEventCompat> $firstBackEvent;
    final /* synthetic */ MutatorMutex $mutatorMutex;
    final /* synthetic */ Function1 $onExpandedChange;
    /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.material3.SearchBarKt$SearchBar$4$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function1 {
        final /* synthetic */ Animatable<Float, AnimationVector1D> $animationProgress;
        final /* synthetic */ CoroutineScope $coroutineScope;
        final /* synthetic */ MutableState<BackEventCompat> $currentBackEvent;
        final /* synthetic */ MutableFloatState $finalBackProgress;
        final /* synthetic */ MutableState<BackEventCompat> $firstBackEvent;
        final /* synthetic */ Function1 $onExpandedChange;
        final /* synthetic */ Flow $progress;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.material3.SearchBarKt$SearchBar$4$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Animatable<Float, AnimationVector1D> $animationProgress;
            final /* synthetic */ MutableState<BackEventCompat> $currentBackEvent;
            final /* synthetic */ MutableFloatState $finalBackProgress;
            final /* synthetic */ MutableState<BackEventCompat> $firstBackEvent;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Animatable<Float, AnimationVector1D> animatable, MutableFloatState mutableFloatState, MutableState<BackEventCompat> mutableState, MutableState<BackEventCompat> mutableState2, Continuation continuation) {
                super(2, continuation);
                this.$animationProgress = animatable;
                this.$finalBackProgress = mutableFloatState;
                this.$firstBackEvent = mutableState;
                this.$currentBackEvent = mutableState2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$animationProgress, this.$finalBackProgress, this.$firstBackEvent, this.$currentBackEvent, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                AnonymousClass2 anonymousClass2;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Animatable<Float, AnimationVector1D> animatable = this.$animationProgress;
                    Float f = new Float(1.0f);
                    TweenSpec tweenSpec = SearchBarKt.AnimationPredictiveBackExitFloatSpec;
                    this.label = 1;
                    anonymousClass2 = this;
                    if (Animatable.animateTo$default(animatable, f, tweenSpec, null, null, anonymousClass2, 12) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    anonymousClass2 = this;
                }
                ((SnapshotMutableFloatStateImpl) anonymousClass2.$finalBackProgress).setFloatValue(Float.NaN);
                anonymousClass2.$firstBackEvent.setValue(null);
                anonymousClass2.$currentBackEvent.setValue(null);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MutableFloatState mutableFloatState, Flow flow, Animatable<Float, AnimationVector1D> animatable, Function1 function1, CoroutineScope coroutineScope, MutableState<BackEventCompat> mutableState, MutableState<BackEventCompat> mutableState2, Continuation continuation) {
            super(1, continuation);
            this.$finalBackProgress = mutableFloatState;
            this.$progress = flow;
            this.$animationProgress = animatable;
            this.$onExpandedChange = function1;
            this.$coroutineScope = coroutineScope;
            this.$firstBackEvent = mutableState;
            this.$currentBackEvent = mutableState2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new AnonymousClass1(this.$finalBackProgress, this.$progress, this.$animationProgress, this.$onExpandedChange, this.$coroutineScope, this.$firstBackEvent, this.$currentBackEvent, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            return ((AnonymousClass1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ((SnapshotMutableFloatStateImpl) this.$finalBackProgress).setFloatValue(Float.NaN);
                    Flow flow = this.$progress;
                    final MutableState<BackEventCompat> mutableState = this.$firstBackEvent;
                    final MutableState<BackEventCompat> mutableState2 = this.$currentBackEvent;
                    final Animatable<Float, AnimationVector1D> animatable = this.$animationProgress;
                    FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.material3.SearchBarKt.SearchBar.4.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BackEventCompat backEventCompat = (BackEventCompat) obj2;
                            MutableState mutableState3 = mutableState;
                            if (mutableState3.getValue() == null) {
                                mutableState3.setValue(backEventCompat);
                            }
                            mutableState2.setValue(backEventCompat);
                            PredictiveBack predictiveBack = PredictiveBack.INSTANCE;
                            float f = backEventCompat.progress;
                            predictiveBack.getClass();
                            Object snapTo = animatable.snapTo(new Float(1 - BackHandlerKt.PredictiveBackEasing.transform(f)), continuation);
                            return snapTo == CoroutineSingletons.COROUTINE_SUSPENDED ? snapTo : Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                ((SnapshotMutableFloatStateImpl) this.$finalBackProgress).setFloatValue(((Number) this.$animationProgress.internalState.getValue()).floatValue());
                this.$onExpandedChange.mo779invoke(Boolean.FALSE);
            } catch (CancellationException unused) {
                BuildersKt.launch$default(this.$coroutineScope, null, null, new AnonymousClass2(this.$animationProgress, this.$finalBackProgress, this.$firstBackEvent, this.$currentBackEvent, null), 3);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SearchBarKt$SearchBar$4$1(MutatorMutex mutatorMutex, MutableFloatState mutableFloatState, Animatable<Float, AnimationVector1D> animatable, Function1 function1, CoroutineScope coroutineScope, MutableState<BackEventCompat> mutableState, MutableState<BackEventCompat> mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$mutatorMutex = mutatorMutex;
        this.$finalBackProgress = mutableFloatState;
        this.$animationProgress = animatable;
        this.$onExpandedChange = function1;
        this.$coroutineScope = coroutineScope;
        this.$firstBackEvent = mutableState;
        this.$currentBackEvent = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SearchBarKt$SearchBar$4$1 searchBarKt$SearchBar$4$1 = new SearchBarKt$SearchBar$4$1(this.$mutatorMutex, this.$finalBackProgress, this.$animationProgress, this.$onExpandedChange, this.$coroutineScope, this.$firstBackEvent, this.$currentBackEvent, continuation);
        searchBarKt$SearchBar$4$1.L$0 = obj;
        return searchBarKt$SearchBar$4$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SearchBarKt$SearchBar$4$1) create((Flow) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = (Flow) this.L$0;
            MutatorMutex mutatorMutex = this.$mutatorMutex;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$finalBackProgress, flow, this.$animationProgress, this.$onExpandedChange, this.$coroutineScope, this.$firstBackEvent, this.$currentBackEvent, null);
            this.label = 1;
            if (mutatorMutex.mutate(MutatePriority.Default, anonymousClass1, this) == coroutineSingletons) {
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
