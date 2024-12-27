package com.android.systemui.communal.data.repository;

import android.content.pm.UserInfo;
import com.android.systemui.util.kotlin.SharedPreferencesExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class CommunalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalPrefsRepositoryImpl receiver$inlined;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl) {
        super(3, continuation);
        this.receiver$inlined = communalPrefsRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1 communalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1 = new CommunalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.receiver$inlined);
        communalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalPrefsRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            UserInfo userInfo = (UserInfo) this.L$1;
            final CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl = this.receiver$inlined;
            int i2 = CommunalPrefsRepositoryImpl.$r8$clinit;
            communalPrefsRepositoryImpl.getClass();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalPrefsRepositoryImpl$observeCtaDismissState$1(null), SharedPreferencesExt.INSTANCE.observe(communalPrefsRepositoryImpl.getSharedPrefsForUser(userInfo)));
            Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1

                /* renamed from: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ CommunalPrefsRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = communalPrefsRepositoryImpl;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:19:0x0066 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1$2$1
                            r0.<init>(r8)
                        L18:
                            java.lang.Object r8 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 0
                            r4 = 2
                            r5 = 1
                            if (r2 == 0) goto L3b
                            if (r2 == r5) goto L33
                            if (r2 != r4) goto L2b
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L67
                        L2b:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L33:
                            java.lang.Object r6 = r0.L$0
                            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L5c
                        L3b:
                            kotlin.ResultKt.throwOnFailure(r8)
                            kotlin.Unit r7 = (kotlin.Unit) r7
                            kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                            r0.L$0 = r7
                            r0.label = r5
                            int r8 = com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl.$r8$clinit
                            com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl r6 = r6.this$0
                            r6.getClass()
                            com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$getCtaDismissedState$2 r8 = new com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$getCtaDismissedState$2
                            r8.<init>(r6, r3)
                            kotlinx.coroutines.CoroutineDispatcher r6 = r6.bgDispatcher
                            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                            if (r8 != r1) goto L5b
                            return r1
                        L5b:
                            r6 = r7
                        L5c:
                            r0.L$0 = r3
                            r0.label = r4
                            java.lang.Object r6 = r6.emit(r8, r0)
                            if (r6 != r1) goto L67
                            return r1
                        L67:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl$observeCtaDismissState$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, communalPrefsRepositoryImpl), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, communalPrefsRepositoryImpl.bgDispatcher);
            this.label = 1;
            if (FlowKt.emitAll(this, flowOn, flowCollector) == coroutineSingletons) {
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
