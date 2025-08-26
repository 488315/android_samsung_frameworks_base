package com.android.systemui.bouncer.ui.viewmodel;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
final class BouncerMessageViewModel$startLockoutCountdown$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

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

        /* JADX WARN: Code restructure failed: missing block: B:43:0x01a8, code lost:
        
            if (kotlinx.coroutines.DelayKt.m3469delayVtjQ1oo(r4, r23) == r2) goto L44;
         */
        /* JADX WARN: Removed duplicated region for block: B:15:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0070  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x008c  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0171  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x01a8 -> B:7:0x001d). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
            AuthenticationMethodModel authenticationMethodModel;
            double d;
            double d2;
            MutableStateFlow mutableStateFlow;
            MutableStateFlow mutableStateFlow2;
            MessageViewModel messageViewModel;
            AuthenticationMethodModel authenticationMethodModel2;
            char c;
            AuthenticationRepositoryImpl authenticationRepositoryImpl;
            long lockoutAttemptDeadline;
            double dMax;
            String quantityString;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            double d3 = 0.0d;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                authenticationMethodModel = (AuthenticationMethodModel) this.L$0;
                BouncerMessageStrings.INSTANCE.getClass();
                Pair pairPrimaryAuthLockedOut = BouncerMessageStrings.primaryAuthLockedOut(authenticationMethodModel);
                authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.this$0.authenticationInteractor.repository;
                lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
                Long lValueOf = Long.valueOf(lockoutAttemptDeadline);
                if (authenticationRepositoryImpl.clock.elapsedRealtime() >= lockoutAttemptDeadline) {
                }
                dMax = Math.max(0L, (lValueOf == null ? lValueOf.longValue() : 0L) - this.this$0.clock.elapsedRealtime());
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                mutableStateFlow2 = bouncerMessageViewModel.lockoutMessage;
                if (dMax > d3) {
                }
            } else if (i == 1) {
                d2 = this.D$0;
                MutableStateFlow mutableStateFlow3 = (MutableStateFlow) this.L$1;
                authenticationMethodModel = (AuthenticationMethodModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                d = 0.0d;
                mutableStateFlow = mutableStateFlow3;
                mutableStateFlow2 = mutableStateFlow;
                messageViewModel = null;
                authenticationMethodModel2 = authenticationMethodModel;
                mutableStateFlow2.setValue(messageViewModel);
                Duration.Companion companion = Duration.Companion;
                long duration = DurationKt.toDuration(1, DurationUnit.SECONDS);
                this.L$0 = authenticationMethodModel2;
                this.L$1 = null;
                this.D$0 = d2;
                c = 2;
                this.label = 2;
            } else {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                d2 = this.D$0;
                authenticationMethodModel2 = (AuthenticationMethodModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                d = 0.0d;
                c = 2;
                authenticationMethodModel = authenticationMethodModel2;
                if (d2 <= d) {
                    this.this$0.getClass();
                    return Unit.INSTANCE;
                }
                d3 = d;
                BouncerMessageStrings.INSTANCE.getClass();
                Pair pairPrimaryAuthLockedOut2 = BouncerMessageStrings.primaryAuthLockedOut(authenticationMethodModel);
                authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.this$0.authenticationInteractor.repository;
                lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
                Long lValueOf2 = Long.valueOf(lockoutAttemptDeadline);
                if (authenticationRepositoryImpl.clock.elapsedRealtime() >= lockoutAttemptDeadline) {
                    lValueOf2 = null;
                }
                dMax = Math.max(0L, (lValueOf2 == null ? lValueOf2.longValue() : 0L) - this.this$0.clock.elapsedRealtime());
                BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
                mutableStateFlow2 = bouncerMessageViewModel2.lockoutMessage;
                if (dMax > d3) {
                    d = d3;
                    SharedFlowImpl sharedFlowImpl = bouncerMessageViewModel2.resetToDefault;
                    Boolean bool = Boolean.TRUE;
                    this.L$0 = authenticationMethodModel;
                    this.L$1 = mutableStateFlow2;
                    this.D$0 = dMax;
                    this.label = 1;
                    if (sharedFlowImpl.emit(bool, this) != coroutineSingletons) {
                        d2 = dMax;
                        mutableStateFlow = mutableStateFlow2;
                        mutableStateFlow2 = mutableStateFlow;
                        messageViewModel = null;
                        authenticationMethodModel2 = authenticationMethodModel;
                        mutableStateFlow2.setValue(messageViewModel);
                        Duration.Companion companion2 = Duration.Companion;
                        long duration2 = DurationKt.toDuration(1, DurationUnit.SECONDS);
                        this.L$0 = authenticationMethodModel2;
                        this.L$1 = null;
                        this.D$0 = d2;
                        c = 2;
                        this.label = 2;
                    }
                    return coroutineSingletons;
                }
                int iFloor = (int) Math.floor(dMax / 3600000);
                d = d3;
                double d4 = 60;
                int iFloor2 = (int) Math.floor((dMax / 60000) % d4);
                int iRint = (int) Math.rint((dMax / 1000) % d4);
                int i2 = iFloor2 + 1;
                if (iFloor <= 0) {
                    if (i2 > 1) {
                        quantityString = bouncerMessageViewModel2.applicationContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_min, i2, Integer.valueOf(i2));
                        quantityString.getClass();
                    } else {
                        quantityString = bouncerMessageViewModel2.applicationContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_sec, iRint, Integer.valueOf(iRint));
                        quantityString.getClass();
                    }
                } else if (i2 == 60) {
                    int i3 = iFloor + 1;
                    quantityString = bouncerMessageViewModel2.applicationContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour, i3, Integer.valueOf(i3));
                    quantityString.getClass();
                } else if (iFloor == 1) {
                    quantityString = bouncerMessageViewModel2.applicationContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_1_hour_and_min, i2, Integer.valueOf(i2));
                    quantityString.getClass();
                } else if (iFloor <= 1 || i2 != 1) {
                    quantityString = bouncerMessageViewModel2.applicationContext.getString(R.string.kg_too_many_failed_attempts_countdown_hour_and_min, Integer.valueOf(iFloor), Integer.valueOf(i2));
                    quantityString.getClass();
                } else {
                    quantityString = bouncerMessageViewModel2.applicationContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_attempts_countdown_hour_and_1_min, iFloor, Integer.valueOf(iFloor));
                    quantityString.getClass();
                }
                messageViewModel = new MessageViewModel(quantityString, this.this$0.setSubMessage(pairPrimaryAuthLockedOut2.getSecond()), false);
                d2 = dMax;
                authenticationMethodModel2 = authenticationMethodModel;
                mutableStateFlow2.setValue(messageViewModel);
                Duration.Companion companion22 = Duration.Companion;
                long duration22 = DurationKt.toDuration(1, DurationUnit.SECONDS);
                this.L$0 = authenticationMethodModel2;
                this.L$1 = null;
                this.D$0 = d2;
                c = 2;
                this.label = 2;
            }
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
