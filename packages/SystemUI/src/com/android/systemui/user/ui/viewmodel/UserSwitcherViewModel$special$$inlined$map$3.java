package com.android.systemui.user.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public final class UserSwitcherViewModel$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherViewModel this$0;

    /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherViewModel this$0;

        /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector, UserSwitcherViewModel userSwitcherViewModel) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = userSwitcherViewModel;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
            /*
                r6 = this;
                boolean r0 = r8 instanceof com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r8
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2$1
                r0.<init>(r8)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r8)
                goto L5b
            L27:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L2f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.util.List r7 = (java.util.List) r7
                int r7 = r7.size()
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel r8 = r6.this$0
                r8.getClass()
                r8 = 5
                if (r7 >= r8) goto L42
                r7 = 4
                goto L4b
            L42:
                double r7 = (double) r7
                r4 = 4611686018427387904(0x4000000000000000, double:2.0)
                double r7 = r7 / r4
                double r7 = java.lang.Math.ceil(r7)
                int r7 = (int) r7
            L4b:
                java.lang.Integer r8 = new java.lang.Integer
                r8.<init>(r7)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                java.lang.Object r6 = r6.emit(r8, r0)
                if (r6 != r1) goto L5b
                return r1
            L5b:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public UserSwitcherViewModel$special$$inlined$map$3(Flow flow, UserSwitcherViewModel userSwitcherViewModel) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherViewModel;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
