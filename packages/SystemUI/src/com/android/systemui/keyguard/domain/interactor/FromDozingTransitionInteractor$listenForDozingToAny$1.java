package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

final class FromDozingTransitionInteractor$listenForDozingToAny$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDozingTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromDozingTransitionInteractor this$0;

        public AnonymousClass2(FromDozingTransitionInteractor fromDozingTransitionInteractor) {
            this.this$0 = fromDozingTransitionInteractor;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(com.android.systemui.util.kotlin.Quint r10, kotlin.coroutines.Continuation r11) {
            /*
                Method dump skipped, instructions count: 202
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2.emit(com.android.systemui.util.kotlin.Quint, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public FromDozingTransitionInteractor$listenForDozingToAny$1(FromDozingTransitionInteractor fromDozingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDozingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDozingTransitionInteractor$listenForDozingToAny$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDozingTransitionInteractor$listenForDozingToAny$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(FlowKt.debounce(fromDozingTransitionInteractor.powerInteractor.isAwake, 50L), fromDozingTransitionInteractor, new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    return bool;
                }
            });
            FromDozingTransitionInteractor fromDozingTransitionInteractor2 = this.this$0;
            KeyguardInteractor keyguardInteractor = fromDozingTransitionInteractor2.keyguardInteractor;
            Flow sample = companion.sample(transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1, keyguardInteractor.isKeyguardOccluded, fromDozingTransitionInteractor2.communalInteractor.isIdleOnCommunal, fromDozingTransitionInteractor2.canTransitionToGoneOnWake, keyguardInteractor.primaryBouncerShowing);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (sample.collect(anonymousClass2, this) == coroutineSingletons) {
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
