package com.android.systemui.bouncer.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerContainerViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerContainerViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerContainerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerContainerViewModel bouncerContainerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerContainerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
                final BouncerContainerViewModel bouncerContainerViewModel = this.this$0;
                ReadonlySharedFlow readonlySharedFlow = bouncerContainerViewModel.authenticationInteractor.onAuthenticationResult;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel.onActivated.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Boolean) obj2).booleanValue()) {
                            BouncerContainerViewModel bouncerContainerViewModel2 = BouncerContainerViewModel.this;
                            bouncerContainerViewModel2.legacyInteractor.notifyKeyguardAuthenticatedPrimaryAuth(bouncerContainerViewModel2.selectedUserInteractor.getSelectedUserId());
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerContainerViewModel$onActivated$2(BouncerContainerViewModel bouncerContainerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerContainerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerContainerViewModel$onActivated$2 bouncerContainerViewModel$onActivated$2 = new BouncerContainerViewModel$onActivated$2(this.this$0, continuation);
        bouncerContainerViewModel$onActivated$2.L$0 = obj;
        return bouncerContainerViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerContainerViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.this$0, null), 7);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
