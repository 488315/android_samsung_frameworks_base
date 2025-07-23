package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSettingsInteractor$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSettingsInteractor$special$$inlined$flatMapLatest$3(Continuation continuation, CommunalSettingsInteractor communalSettingsInteractor) {
        super(3, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSettingsInteractor$special$$inlined$flatMapLatest$3 communalSettingsInteractor$special$$inlined$flatMapLatest$3 = new CommunalSettingsInteractor$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        communalSettingsInteractor$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        communalSettingsInteractor$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return communalSettingsInteractor$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final UserInfo userInfo = (UserInfo) this.L$1;
            if (userInfo != null) {
                final CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1 allowedByDevicePolicy = ((CommunalSettingsRepositoryImpl) this.this$0.repository).getAllowedByDevicePolicy(userInfo);
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ UserInfo $it$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, UserInfo userInfo) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$it$inlined = userInfo;
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
                                boolean r0 = r6 instanceof com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L49
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.lang.Boolean r5 = (java.lang.Boolean) r5
                                boolean r5 = r5.booleanValue()
                                if (r5 != 0) goto L3d
                                android.content.pm.UserInfo r5 = r4.$it$inlined
                                goto L3e
                            L3d:
                                r5 = 0
                            L3e:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L49
                                return r1
                            L49:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserDisallowedByDevicePolicy$lambda$4$lambda$3$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, userInfo), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
