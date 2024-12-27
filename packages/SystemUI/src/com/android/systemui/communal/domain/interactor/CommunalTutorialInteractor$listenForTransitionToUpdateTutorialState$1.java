package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CommunalTutorialDisabledRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

final class CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalTutorialInteractor this$0;

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ int I$0;
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            int intValue = ((Number) obj2).intValue();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
            anonymousClass2.L$0 = (FlowCollector) obj;
            anonymousClass2.I$0 = intValue;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                int i3 = this.I$0;
                Integer num = new Integer(i3);
                this.I$0 = i3;
                this.label = 1;
                if (flowCollector.emit(num, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i = i3;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i = this.I$0;
                ResultKt.throwOnFailure(obj);
            }
            return Boolean.valueOf(i != 10);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1(CommunalTutorialInteractor communalTutorialInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalTutorialInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalTutorialInteractor communalTutorialInteractor = this.this$0;
            SafeFlow transformWhile = FlowKt.transformWhile(FlowKt.transformLatest(communalTutorialInteractor.communalSettingsInteractor.isCommunalEnabled, new CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1$invokeSuspend$$inlined$flatMapLatest$1(null, communalTutorialInteractor)), new AnonymousClass2(null));
            final CommunalTutorialInteractor communalTutorialInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ((Number) obj2).intValue();
                    ((CommunalTutorialDisabledRepositoryImpl) CommunalTutorialInteractor.this.communalTutorialRepository).getClass();
                    Unit unit = Unit.INSTANCE;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return unit;
                }
            };
            this.label = 1;
            if (transformWhile.collect(flowCollector, this) == coroutineSingletons) {
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
