package com.android.systemui.bouncer.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
public final class BouncerMessageAuditLogger implements CoreStartable {
    public final BouncerMessageRepository repository;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageAuditLogger$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BouncerMessageAuditLogger.this.new AnonymousClass1(continuation);
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
                StateFlowImpl stateFlowImpl = ((BouncerMessageRepositoryImpl) BouncerMessageAuditLogger.this.repository).bouncerMessage;
                C01171 c01171 = new FlowCollector() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageAuditLogger.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Log.d(BouncerMessageAuditLoggerKt.TAG, "bouncerMessage: " + ((BouncerMessageModel) obj2));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(c01171, this) == coroutineSingletons) {
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

    public BouncerMessageAuditLogger(CoroutineScope coroutineScope, BouncerMessageRepository bouncerMessageRepository) {
        this.scope = coroutineScope;
        this.repository = bouncerMessageRepository;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
