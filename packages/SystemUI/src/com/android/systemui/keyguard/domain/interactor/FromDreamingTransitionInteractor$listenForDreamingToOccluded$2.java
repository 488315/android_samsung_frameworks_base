package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

final class FromDreamingTransitionInteractor$listenForDreamingToOccluded$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$2$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            Boolean bool2 = (Boolean) obj2;
            bool2.booleanValue();
            return new Pair(bool, bool2);
        }
    }

    public FromDreamingTransitionInteractor$listenForDreamingToOccluded$2(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDreamingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDreamingTransitionInteractor$listenForDreamingToOccluded$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDreamingTransitionInteractor$listenForDreamingToOccluded$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromDreamingTransitionInteractor fromDreamingTransitionInteractor = this.this$0;
            KeyguardInteractor keyguardInteractor = fromDreamingTransitionInteractor.keyguardInteractor;
            Flow flow = keyguardInteractor.isKeyguardOccluded;
            Duration.Companion companion = Duration.Companion;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, FlowKt.debounce(keyguardInteractor.isDreaming, DelayKt.m2547toDelayMillisLRDsOJo(DurationKt.toDuration(100, DurationUnit.MILLISECONDS))), AnonymousClass2.INSTANCE), fromDreamingTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$2.3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Pair pair = (Pair) obj2;
                    return Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
                }
            });
            final FromDreamingTransitionInteractor fromDreamingTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$2.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromDreamingTransitionInteractor.this, KeyguardState.OCCLUDED, null, null, "Occluded but no longer dreaming", continuation, 6);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
