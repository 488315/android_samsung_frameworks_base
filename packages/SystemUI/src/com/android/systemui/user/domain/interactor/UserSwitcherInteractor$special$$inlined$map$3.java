package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserSwitcherInteractor$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherInteractor this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            int I$0;
            Object L$0;
            Object L$1;
            Object L$2;
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

        public AnonymousClass2(FlowCollector flowCollector, UserSwitcherInteractor userSwitcherInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = userSwitcherInteractor;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x009b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x008d A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:25:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
            /*
                r8 = this;
                boolean r0 = r10 instanceof com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r10
                com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2$1
                r0.<init>(r10)
            L18:
                java.lang.Object r10 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 3
                r4 = 2
                r5 = 1
                r6 = 0
                if (r2 == 0) goto L54
                if (r2 == r5) goto L3f
                if (r2 == r4) goto L37
                if (r2 != r3) goto L2f
                kotlin.ResultKt.throwOnFailure(r10)
                goto L9c
            L2f:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L37:
                java.lang.Object r8 = r0.L$0
                kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                kotlin.ResultKt.throwOnFailure(r10)
                goto L91
            L3f:
                int r8 = r0.I$0
                java.lang.Object r9 = r0.L$2
                com.android.systemui.user.domain.interactor.UserSwitcherInteractor r9 = (com.android.systemui.user.domain.interactor.UserSwitcherInteractor) r9
                java.lang.Object r2 = r0.L$1
                android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2
                java.lang.Object r5 = r0.L$0
                kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                kotlin.ResultKt.throwOnFailure(r10)
                r7 = r5
                r5 = r10
                r10 = r7
                goto L77
            L54:
                kotlin.ResultKt.throwOnFailure(r10)
                r2 = r9
                android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2
                int r9 = r2.id
                kotlinx.coroutines.flow.FlowCollector r10 = r8.$this_unsafeFlow
                r0.L$0 = r10
                r0.L$1 = r2
                com.android.systemui.user.domain.interactor.UserSwitcherInteractor r8 = r8.this$0
                r0.L$2 = r8
                r0.I$0 = r9
                r0.label = r5
                int r5 = com.android.systemui.user.domain.interactor.UserSwitcherInteractor.$r8$clinit
                r5 = 0
                java.lang.Object r5 = r8.canSwitchUsers(r9, r0, r5)
                if (r5 != r1) goto L74
                return r1
            L74:
                r7 = r9
                r9 = r8
                r8 = r7
            L77:
                java.lang.Boolean r5 = (java.lang.Boolean) r5
                boolean r5 = r5.booleanValue()
                r0.L$0 = r10
                r0.L$1 = r6
                r0.L$2 = r6
                r0.label = r4
                int r4 = com.android.systemui.user.domain.interactor.UserSwitcherInteractor.$r8$clinit
                java.lang.Object r8 = r9.toUserModel(r2, r8, r5, r0)
                if (r8 != r1) goto L8e
                return r1
            L8e:
                r7 = r10
                r10 = r8
                r8 = r7
            L91:
                r0.L$0 = r6
                r0.label = r3
                java.lang.Object r8 = r8.emit(r10, r0)
                if (r8 != r1) goto L9c
                return r1
            L9c:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public UserSwitcherInteractor$special$$inlined$map$3(Flow flow, UserSwitcherInteractor userSwitcherInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
