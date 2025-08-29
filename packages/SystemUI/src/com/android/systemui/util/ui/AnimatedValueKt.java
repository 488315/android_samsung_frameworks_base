package com.android.systemui.util.ui;

import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class AnimatedValueKt {

    /* renamed from: com.android.systemui.util.ui.AnimatedValueKt$flatMap$1, reason: invalid class name */
    public final class AnonymousClass1 implements Function0 {
        final /* synthetic */ AnimatedValue<Object> $inner;
        final /* synthetic */ AnimatedValue<Object> $this_flatMap;

        public AnonymousClass1(AnimatedValue<Object> animatedValue, AnimatedValue<Object> animatedValue2) {
            this.$this_flatMap = animatedValue;
            this.$inner = animatedValue2;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            m3207invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final void m3207invoke() {
            ((AnimatedValue.Animating) this.$this_flatMap).getOnStopAnimating().invoke();
            ((AnimatedValue.Animating) this.$inner).getOnStopAnimating().invoke();
        }
    }

    /* renamed from: com.android.systemui.util.ui.AnimatedValueKt$map$1, reason: invalid class name and case insensitive filesystem */
    public final /* synthetic */ class C11811 extends FunctionReferenceImpl implements Function0 {
        public C11811(Object obj) {
            super(0, obj, AnimatedValueKt.class, "stopAnimating", "stopAnimating(Lcom/android/systemui/util/ui/AnimatedValue;)V", 1);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            m3208invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final void m3208invoke() {
            AnimatedValue animatedValue = (AnimatedValue) this.receiver;
            if (animatedValue instanceof AnimatedValue.Animating) {
                ((AnimatedValue.Animating) animatedValue).getOnStopAnimating().invoke();
            }
        }
    }

    /* renamed from: com.android.systemui.util.ui.AnimatedValueKt$toAnimatedValueFlow$1, reason: invalid class name and case insensitive filesystem */
    final class C11821 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        Object L$2;
        int label;

        public C11821(Continuation continuation) {
            super(3, continuation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(CompletableDeferred completableDeferred) {
            Unit unit = Unit.INSTANCE;
            ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
            return unit;
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x008d, code lost:
        
            if (r10.emit(r1, r9) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            FlowCollector flowCollector;
            Object objComponent1;
            Object obj2;
            Deferred deferred;
            FlowCollector flowCollector2;
            Object obj3;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                AnimatableEvent animatableEvent = (AnimatableEvent) this.L$1;
                objComponent1 = animatableEvent.component1();
                if (animatableEvent.component2()) {
                    final CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
                    AnimatedValue.Animating animating = new AnimatedValue.Animating(objComponent1, new Function0() { // from class: com.android.systemui.util.ui.AnimatedValueKt$toAnimatedValueFlow$1$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return AnimatedValueKt.C11821.invokeSuspend$lambda$0(completableDeferredImplCompletableDeferred$default);
                        }
                    });
                    this.L$0 = flowCollector;
                    this.L$1 = objComponent1;
                    this.L$2 = completableDeferredImplCompletableDeferred$default;
                    this.label = 1;
                    if (flowCollector.emit(animating, this) != coroutineSingletons) {
                        obj2 = objComponent1;
                        deferred = completableDeferredImplCompletableDeferred$default;
                    }
                } else {
                    AnimatedValue.NotAnimating notAnimating = new AnimatedValue.NotAnimating(objComponent1);
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 3;
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                Deferred deferred2 = (CompletableDeferred) this.L$2;
                obj2 = this.L$1;
                FlowCollector flowCollector3 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                flowCollector = flowCollector3;
                deferred = deferred2;
            } else {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                obj3 = this.L$1;
                flowCollector2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                objComponent1 = obj3;
                flowCollector = flowCollector2;
                AnimatedValue.NotAnimating notAnimating2 = new AnimatedValue.NotAnimating(objComponent1);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 3;
            }
            this.L$0 = flowCollector;
            this.L$1 = obj2;
            this.L$2 = null;
            this.label = 2;
            if (((CompletableDeferredImpl) deferred).awaitInternal(this) != coroutineSingletons) {
                flowCollector2 = flowCollector;
                obj3 = obj2;
                objComponent1 = obj3;
                flowCollector = flowCollector2;
                AnimatedValue.NotAnimating notAnimating22 = new AnimatedValue.NotAnimating(objComponent1);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 3;
            }
            return coroutineSingletons;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector flowCollector, AnimatableEvent<Object> animatableEvent, Continuation continuation) {
            C11821 c11821 = new C11821(continuation);
            c11821.L$0 = flowCollector;
            c11821.L$1 = animatableEvent;
            return c11821.invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.ui.AnimatedValueKt$zip$1, reason: invalid class name and case insensitive filesystem */
    public final class C11831 implements Function0 {
        final /* synthetic */ AnimatedValue<Object> $valueA;
        final /* synthetic */ AnimatedValue<Object> $valueB;

        public C11831(AnimatedValue<Object> animatedValue, AnimatedValue<Object> animatedValue2) {
            this.$valueA = animatedValue;
            this.$valueB = animatedValue2;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            m3210invoke();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final void m3210invoke() {
            ((AnimatedValue.Animating) this.$valueA).getOnStopAnimating().invoke();
            ((AnimatedValue.Animating) this.$valueB).getOnStopAnimating().invoke();
        }
    }

    public static final <A, B> AnimatedValue<B> flatMap(AnimatedValue<? extends A> animatedValue, Function1 function1) {
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (AnimatedValue) function1.mo781invoke(((AnimatedValue.NotAnimating) animatedValue).getValue());
        }
        if (!(animatedValue instanceof AnimatedValue.Animating)) {
            throw new NoWhenBranchMatchedException();
        }
        AnimatedValue.Animating animating = (AnimatedValue.Animating) animatedValue;
        AnimatedValue animatedValue2 = (AnimatedValue) function1.mo781invoke(animating.getValue());
        if (animatedValue2 instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(((AnimatedValue.Animating) animatedValue2).getValue(), new AnonymousClass1(animatedValue, animatedValue2));
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.Animating(((AnimatedValue.NotAnimating) animatedValue2).getValue(), animating.getOnStopAnimating());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> AnimatedValue<T> flatten(AnimatedValue<? extends AnimatedValue<? extends T>> animatedValue) {
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (AnimatedValue) ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        if (!(animatedValue instanceof AnimatedValue.Animating)) {
            throw new NoWhenBranchMatchedException();
        }
        AnimatedValue.Animating animating = (AnimatedValue.Animating) animatedValue;
        AnimatedValue animatedValue2 = (AnimatedValue) animating.getValue();
        if (animatedValue2 instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(((AnimatedValue.Animating) animatedValue2).getValue(), new AnonymousClass1(animatedValue, animatedValue2));
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.Animating(((AnimatedValue.NotAnimating) animatedValue2).getValue(), animating.getOnStopAnimating());
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> T getValue(AnimatedValue<? extends T> animatedValue) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            return (T) ((AnimatedValue.Animating) animatedValue).getValue();
        }
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return (T) ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final <T> boolean isAnimating(AnimatedValue<? extends T> animatedValue) {
        return animatedValue instanceof AnimatedValue.Animating;
    }

    public static final <A, B> AnimatedValue<B> map(AnimatedValue<? extends A> animatedValue, Function1 function1) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            return new AnimatedValue.Animating(function1.mo781invoke(((AnimatedValue.Animating) animatedValue).getValue()), new C11811(animatedValue));
        }
        if (animatedValue instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.NotAnimating(function1.mo781invoke(((AnimatedValue.NotAnimating) animatedValue).getValue()));
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final void stopAnimating(AnimatedValue<?> animatedValue) {
        if (animatedValue instanceof AnimatedValue.Animating) {
            ((AnimatedValue.Animating) animatedValue).getOnStopAnimating().invoke();
        }
    }

    public static final <T> Flow toAnimatedValueFlow(Flow flow) {
        return FlowKt.transformLatest(flow, new C11821(null));
    }

    public static final <A, B, Z> AnimatedValue<Z> zip(AnimatedValue<? extends A> animatedValue, AnimatedValue<? extends B> animatedValue2, Function2 function2) {
        Object value;
        Object value2;
        boolean z = animatedValue instanceof AnimatedValue.Animating;
        if (z) {
            value = ((AnimatedValue.Animating) animatedValue).getValue();
        } else {
            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        boolean z2 = animatedValue2 instanceof AnimatedValue.Animating;
        if (z2) {
            value2 = ((AnimatedValue.Animating) animatedValue2).getValue();
        } else {
            if (!(animatedValue2 instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value2 = ((AnimatedValue.NotAnimating) animatedValue2).getValue();
        }
        Object objInvoke = function2.invoke(value, value2);
        if (z) {
            if (z2) {
                return new AnimatedValue.Animating(objInvoke, new C11831(animatedValue, animatedValue2));
            }
            if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
                return new AnimatedValue.Animating(objInvoke, ((AnimatedValue.Animating) animatedValue).getOnStopAnimating());
            }
            throw new NoWhenBranchMatchedException();
        }
        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
            throw new NoWhenBranchMatchedException();
        }
        if (z2) {
            return new AnimatedValue.Animating(objInvoke, ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating());
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.NotAnimating(objInvoke);
        }
        throw new NoWhenBranchMatchedException();
    }
}
