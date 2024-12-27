package com.android.systemui.user.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public final class UserSwitcherViewModel$special$$inlined$map$2 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherViewModel this$0;

    /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherViewModel this$0;

        /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2$2$1
                r0.<init>(r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r7)
                goto L66
            L27:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L2f:
                kotlin.ResultKt.throwOnFailure(r7)
                java.util.List r6 = (java.util.List) r6
                java.lang.Iterable r6 = (java.lang.Iterable) r6
                java.util.ArrayList r7 = new java.util.ArrayList
                r2 = 10
                int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r2)
                r7.<init>(r2)
                java.util.Iterator r6 = r6.iterator()
            L45:
                boolean r2 = r6.hasNext()
                if (r2 == 0) goto L5b
                java.lang.Object r2 = r6.next()
                com.android.systemui.user.shared.model.UserModel r2 = (com.android.systemui.user.shared.model.UserModel) r2
                com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel r4 = r5.this$0
                com.android.systemui.user.ui.viewmodel.UserViewModel r2 = com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel.access$toViewModel(r4, r2)
                r7.add(r2)
                goto L45
            L5b:
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                java.lang.Object r5 = r5.emit(r7, r0)
                if (r5 != r1) goto L66
                return r1
            L66:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public UserSwitcherViewModel$special$$inlined$map$2(Flow flow, UserSwitcherViewModel userSwitcherViewModel) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherViewModel;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
