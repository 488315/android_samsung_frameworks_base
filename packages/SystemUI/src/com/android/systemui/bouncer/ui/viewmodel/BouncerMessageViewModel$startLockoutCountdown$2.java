package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewModel$startLockoutCountdown$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$startLockoutCountdown$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        double D$0;
        /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ BouncerMessageViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerMessageViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((AuthenticationMethodModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x01a8, code lost:
        
            if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r23) == r2) goto L44;
         */
        /* JADX WARN: Removed duplicated region for block: B:15:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0070  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x008c  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0171  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0075  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x01a8 -> B:6:0x001d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r24) {
            /*
                Method dump skipped, instructions count: 444
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$startLockoutCountdown$2.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$startLockoutCountdown$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerMessageViewModel$startLockoutCountdown$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$startLockoutCountdown$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
            Flow flow = bouncerMessageViewModel.authenticationInteractor.authenticationMethod;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(bouncerMessageViewModel, null);
            this.label = 1;
            if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
