package com.android.systemui.bouncer.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerInteractor$special$$inlined$filter$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ BouncerInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ BouncerInteractor this$0;

        /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
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

        public AnonymousClass2(FlowCollector flowCollector, BouncerInteractor bouncerInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = bouncerInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
            /*
                r8 = this;
                boolean r0 = r10 instanceof com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r10
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1$2$1
                r0.<init>(r10)
            L18:
                java.lang.Object r10 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r10)
                goto L6a
            L27:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L2f:
                kotlin.ResultKt.throwOnFailure(r10)
                r10 = r9
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                if (r10 != 0) goto L6a
                com.android.systemui.bouncer.domain.interactor.BouncerInteractor r10 = r8.this$0
                com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r10 = r10.authenticationInteractor
                com.android.systemui.authentication.data.repository.AuthenticationRepository r10 = r10.repository
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r10 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r10
                com.android.internal.widget.LockPatternUtils r2 = r10.lockPatternUtils
                int r4 = r10.getSelectedUserId()
                long r4 = r2.getLockoutAttemptDeadline(r4)
                java.lang.Long r2 = java.lang.Long.valueOf(r4)
                com.android.systemui.util.time.SystemClock r10 = r10.clock
                long r6 = r10.elapsedRealtime()
                int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r10 >= 0) goto L5c
                goto L5d
            L5c:
                r2 = 0
            L5d:
                if (r2 == 0) goto L6a
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                java.lang.Object r8 = r8.emit(r9, r0)
                if (r8 != r1) goto L6a
                return r1
            L6a:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public BouncerInteractor$special$$inlined$filter$1(Flow flow, BouncerInteractor bouncerInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = bouncerInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
