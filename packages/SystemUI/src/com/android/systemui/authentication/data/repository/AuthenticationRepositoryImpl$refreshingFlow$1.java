package com.android.systemui.authentication.data.repository;

import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class AuthenticationRepositoryImpl$refreshingFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableStateFlow $flow;
    final /* synthetic */ Function2 $getFreshValue;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ int I$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            int intValue = ((Number) obj).intValue();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
            anonymousClass2.I$0 = intValue;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(this.I$0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$refreshingFlow$1(AuthenticationRepositoryImpl authenticationRepositoryImpl, MutableStateFlow mutableStateFlow, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
        this.$flow = mutableStateFlow;
        this.$getFreshValue = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$refreshingFlow$1(this.this$0, this.$flow, this.$getFreshValue, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$refreshingFlow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) this.this$0.userRepository).selectedUserInfo;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L46
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            android.content.pm.UserInfo r5 = (android.content.pm.UserInfo) r5
                            int r5 = r5.id
                            java.lang.Integer r6 = new java.lang.Integer
                            r6.<init>(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L46
                            return r1
                        L46:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }), com.android.systemui.util.kotlin.FlowKt.onSubscriberAdded(this.$flow), new AnonymousClass2(null));
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$flow, this.this$0, this.$getFreshValue);
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(anonymousClass3, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3, reason: invalid class name */
    public final class AnonymousClass3 implements FlowCollector {
        public final /* synthetic */ MutableStateFlow $flow;
        public final /* synthetic */ Function2 $getFreshValue;
        public final /* synthetic */ AuthenticationRepositoryImpl this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $getFreshValue;
            final /* synthetic */ int $selectedUserId;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function2 function2, int i, Continuation continuation) {
                super(2, continuation);
                this.$getFreshValue = function2;
                this.$selectedUserId = i;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$getFreshValue, this.$selectedUserId, continuation);
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
                    Function2 function2 = this.$getFreshValue;
                    Integer num = new Integer(this.$selectedUserId);
                    this.label = 1;
                    obj = function2.invoke(num, this);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return obj;
            }
        }

        public AnonymousClass3(MutableStateFlow mutableStateFlow, AuthenticationRepositoryImpl authenticationRepositoryImpl, Function2 function2) {
            this.$flow = mutableStateFlow;
            this.this$0 = authenticationRepositoryImpl;
            this.$getFreshValue = function2;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(int r7, kotlin.coroutines.Continuation r8) {
            /*
                r6 = this;
                boolean r0 = r8 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1
                if (r0 == 0) goto L13
                r0 = r8
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1 r0 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1 r0 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1
                r0.<init>(r6, r8)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L33
                if (r2 != r3) goto L2b
                java.lang.Object r6 = r0.L$0
                kotlinx.coroutines.flow.MutableStateFlow r6 = (kotlinx.coroutines.flow.MutableStateFlow) r6
                kotlin.ResultKt.throwOnFailure(r8)
                goto L4f
            L2b:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L33:
                kotlin.ResultKt.throwOnFailure(r8)
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r8 = r6.this$0
                kotlinx.coroutines.CoroutineDispatcher r8 = r8.backgroundDispatcher
                com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$1 r2 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$1
                kotlin.jvm.functions.Function2 r4 = r6.$getFreshValue
                r5 = 0
                r2.<init>(r4, r7, r5)
                kotlinx.coroutines.flow.MutableStateFlow r6 = r6.$flow
                r0.L$0 = r6
                r0.label = r3
                java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
                if (r8 != r1) goto L4f
                return r1
            L4f:
                kotlinx.coroutines.flow.StateFlowImpl r6 = (kotlinx.coroutines.flow.StateFlowImpl) r6
                r6.setValue(r8)
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1.AnonymousClass3.emit(int, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Number) obj).intValue(), continuation);
        }
    }
}
