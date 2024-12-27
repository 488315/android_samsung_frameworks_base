package com.android.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

final class ClockEventController$listenForDozing$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ClockEventController this$0;

    /* renamed from: com.android.keyguard.ClockEventController$listenForDozing$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ float F$0;
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ ClockEventController this$0;

        public AnonymousClass1(ClockEventController clockEventController, Continuation continuation) {
            super(3, continuation);
            this.this$0 = clockEventController;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            float floatValue = ((Number) obj).floatValue();
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
            anonymousClass1.F$0 = floatValue;
            anonymousClass1.Z$0 = booleanValue;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.F$0 > this.this$0.dozeAmount || this.Z$0);
        }
    }

    public ClockEventController$listenForDozing$1(ClockEventController clockEventController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = clockEventController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ClockEventController$listenForDozing$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ClockEventController$listenForDozing$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ClockEventController clockEventController = this.this$0;
            KeyguardInteractor keyguardInteractor = clockEventController.keyguardInteractor;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.dozeAmount, keyguardInteractor.isDozing, new AnonymousClass1(clockEventController, null));
            final ClockEventController clockEventController2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.ClockEventController$listenForDozing$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ClockEventController.this.isDozing = ((Boolean) obj2).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
