package com.android.systemui.authentication.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AuthenticationInteractor$special$$inlined$map$2 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ AuthenticationInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:20:0x00b0, code lost:
        
            if (r2.emit(r4, r0) != r1) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0072, code lost:
        
            if (r10 == r1) goto L34;
         */
        /* JADX WARN: Removed duplicated region for block: B:30:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0055  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
            /*
                r8 = this;
                boolean r0 = r10 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r10
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1
                r0.<init>(r10)
            L18:
                java.lang.Object r10 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 3
                r4 = 2
                r5 = 1
                r6 = 0
                if (r2 == 0) goto L55
                if (r2 == r5) goto L43
                if (r2 == r4) goto L37
                if (r2 != r3) goto L2f
                kotlin.ResultKt.throwOnFailure(r10)
                goto Lb3
            L2f:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L37:
                int r8 = r0.I$1
                int r9 = r0.I$0
                java.lang.Object r2 = r0.L$0
                kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                kotlin.ResultKt.throwOnFailure(r10)
                goto L9f
            L43:
                int r8 = r0.I$0
                java.lang.Object r9 = r0.L$1
                kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
                java.lang.Object r2 = r0.L$0
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2 r2 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2) r2
                kotlin.ResultKt.throwOnFailure(r10)
                r7 = r9
                r9 = r8
                r8 = r2
                r2 = r7
                goto L75
            L55:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Number r9 = (java.lang.Number) r9
                int r9 = r9.intValue()
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r10 = r8.this$0
                com.android.systemui.authentication.data.repository.AuthenticationRepository r10 = r10.repository
                r0.L$0 = r8
                kotlinx.coroutines.flow.FlowCollector r2 = r8.$this_unsafeFlow
                r0.L$1 = r2
                r0.I$0 = r9
                r0.label = r5
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r10 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r10
                java.lang.Object r10 = r10.getMaxFailedUnlockAttemptsForWipe(r0)
                if (r10 != r1) goto L75
                goto Lb2
            L75:
                java.lang.Number r10 = (java.lang.Number) r10
                int r10 = r10.intValue()
                if (r10 != 0) goto L7f
            L7d:
                r4 = r6
                goto La6
            L7f:
                r5 = 0
                int r10 = r10 - r9
                int r10 = java.lang.Math.max(r5, r10)
                r5 = 5
                if (r10 < r5) goto L89
                goto L7d
            L89:
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r8 = r8.this$0
                r0.L$0 = r2
                r0.L$1 = r6
                r0.I$0 = r9
                r0.I$1 = r10
                r0.label = r4
                java.lang.Object r8 = com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.access$getWipeTarget(r8, r0)
                if (r8 != r1) goto L9c
                goto Lb2
            L9c:
                r7 = r10
                r10 = r8
                r8 = r7
            L9f:
                com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget r10 = (com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget) r10
                com.android.systemui.authentication.shared.model.AuthenticationWipeModel r4 = new com.android.systemui.authentication.shared.model.AuthenticationWipeModel
                r4.<init>(r10, r9, r8)
            La6:
                r0.L$0 = r6
                r0.L$1 = r6
                r0.label = r3
                java.lang.Object r8 = r2.emit(r4, r0)
                if (r8 != r1) goto Lb3
            Lb2:
                return r1
            Lb3:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public AuthenticationInteractor$special$$inlined$map$2(Flow flow, AuthenticationInteractor authenticationInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = authenticationInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
