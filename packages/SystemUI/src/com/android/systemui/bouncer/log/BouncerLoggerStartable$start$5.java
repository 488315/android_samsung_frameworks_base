package com.android.systemui.bouncer.log;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class BouncerLoggerStartable$start$5 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BouncerLoggerStartable this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.bouncer.log.BouncerLoggerStartable$start$5$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ BouncerLoggerStartable this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerLoggerStartable bouncerLoggerStartable, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerLoggerStartable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.bouncerLogger.interestedStateChanged("fingerprintCurrentlyAllowedOnBouncer", this.Z$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerLoggerStartable$start$5(BouncerLoggerStartable bouncerLoggerStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerLoggerStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerLoggerStartable$start$5(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerLoggerStartable$start$5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BouncerLoggerStartable bouncerLoggerStartable = this.this$0;
            ChannelFlowTransformLatest channelFlowTransformLatest = bouncerLoggerStartable.fingerprintAuthInteractor.isFingerprintCurrentlyAllowedOnBouncer;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(bouncerLoggerStartable, null);
            this.label = 1;
            if (FlowKt.collectLatest(channelFlowTransformLatest, anonymousClass1, this) == coroutineSingletons) {
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
