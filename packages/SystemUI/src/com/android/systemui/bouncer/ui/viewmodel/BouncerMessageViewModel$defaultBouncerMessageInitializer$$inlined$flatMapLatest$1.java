package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1(Continuation continuation, BouncerMessageViewModel bouncerMessageViewModel) {
        super(3, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 = new BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.L$1 = obj2;
        return bouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) this.L$1;
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Sim.INSTANCE)) {
                final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                final SharedFlowImpl sharedFlowImpl = bouncerMessageViewModel.resetToDefault;
                flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ BouncerMessageViewModel this$0;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, BouncerMessageViewModel bouncerMessageViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = bouncerMessageViewModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:24:0x010d A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                            /*
                                Method dump skipped, instructions count: 273
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, bouncerMessageViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else if (authenticationMethodModel.isSecure) {
                BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
                flow = FlowKt.combine(bouncerMessageViewModel2.deviceUnlockedInteractor.deviceEntryRestrictionReason, bouncerMessageViewModel2.lockoutMessage, bouncerMessageViewModel2.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer, bouncerMessageViewModel2.resetToDefault, bouncerMessageViewModel2.authenticationInteractor.failedAuthenticationAttempts, new BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(bouncerMessageViewModel2, authenticationMethodModel, null));
            } else {
                flow = EmptyFlow.INSTANCE;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
