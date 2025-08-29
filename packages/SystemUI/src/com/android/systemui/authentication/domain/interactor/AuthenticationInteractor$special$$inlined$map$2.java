package com.android.systemui.authentication.domain.interactor;

import com.android.systemui.authentication.data.repository.AuthenticationRepository;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.shared.model.AuthenticationWipeModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
public final class AuthenticationInteractor$special$$inlined$map$2 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ AuthenticationInteractor this$0;

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ AuthenticationInteractor this$0;

        /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            int I$0;
            int I$1;
            Object L$0;
            Object L$1;
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

        public AnonymousClass2(FlowCollector flowCollector, AuthenticationInteractor authenticationInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = authenticationInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00b0, code lost:
        
            if (r2.emit(r4, r0) == r1) goto L34;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) throws Throwable {
            AnonymousClass1 anonymousClass1;
            int iIntValue;
            FlowCollector flowCollector;
            int iMax;
            int i;
            AuthenticationWipeModel authenticationWipeModel;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i2 - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object maxFailedUnlockAttemptsForWipe = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = anonymousClass1.label;
            if (i3 == 0) {
                ResultKt.throwOnFailure(maxFailedUnlockAttemptsForWipe);
                iIntValue = ((Number) obj).intValue();
                AuthenticationRepository authenticationRepository = this.this$0.repository;
                anonymousClass1.L$0 = this;
                flowCollector = this.$this_unsafeFlow;
                anonymousClass1.L$1 = flowCollector;
                anonymousClass1.I$0 = iIntValue;
                anonymousClass1.label = 1;
                maxFailedUnlockAttemptsForWipe = ((AuthenticationRepositoryImpl) authenticationRepository).getMaxFailedUnlockAttemptsForWipe(anonymousClass1);
                if (maxFailedUnlockAttemptsForWipe != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i3 == 1) {
                int i4 = anonymousClass1.I$0;
                FlowCollector flowCollector2 = (FlowCollector) anonymousClass1.L$1;
                AnonymousClass2 anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                ResultKt.throwOnFailure(maxFailedUnlockAttemptsForWipe);
                iIntValue = i4;
                this = anonymousClass2;
                flowCollector = flowCollector2;
            } else {
                if (i3 != 2) {
                    if (i3 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(maxFailedUnlockAttemptsForWipe);
                    return Unit.INSTANCE;
                }
                i = anonymousClass1.I$1;
                iIntValue = anonymousClass1.I$0;
                flowCollector = (FlowCollector) anonymousClass1.L$0;
                ResultKt.throwOnFailure(maxFailedUnlockAttemptsForWipe);
                authenticationWipeModel = new AuthenticationWipeModel((AuthenticationWipeModel.WipeTarget) maxFailedUnlockAttemptsForWipe, iIntValue, i);
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.label = 3;
            }
            int iIntValue2 = ((Number) maxFailedUnlockAttemptsForWipe).intValue();
            if (iIntValue2 != 0 && (iMax = Math.max(0, iIntValue2 - iIntValue)) < 5) {
                AuthenticationInteractor authenticationInteractor = this.this$0;
                anonymousClass1.L$0 = flowCollector;
                anonymousClass1.L$1 = null;
                anonymousClass1.I$0 = iIntValue;
                anonymousClass1.I$1 = iMax;
                anonymousClass1.label = 2;
                Object objAccess$getWipeTarget = AuthenticationInteractor.access$getWipeTarget(authenticationInteractor, anonymousClass1);
                if (objAccess$getWipeTarget != coroutineSingletons) {
                    maxFailedUnlockAttemptsForWipe = objAccess$getWipeTarget;
                    i = iMax;
                    authenticationWipeModel = new AuthenticationWipeModel((AuthenticationWipeModel.WipeTarget) maxFailedUnlockAttemptsForWipe, iIntValue, i);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 3;
                }
                return coroutineSingletons;
            }
            authenticationWipeModel = null;
            anonymousClass1.L$0 = null;
            anonymousClass1.L$1 = null;
            anonymousClass1.label = 3;
        }
    }

    public AuthenticationInteractor$special$$inlined$map$2(Flow flow, AuthenticationInteractor authenticationInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = authenticationInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
