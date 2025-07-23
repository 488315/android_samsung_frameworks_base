package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda6;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ MobileConnectionRepositoryKairosImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0(BroadcastDispatcher broadcastDispatcher, MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl) {
        this.f$1 = broadcastDispatcher;
        this.f$0 = mobileConnectionRepositoryKairosImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                TelephonyCallbackState telephonyCallbackState = new TelephonyCallbackState(null, null, null, null, null, null, null, null, null, null, null, null, null, null, 16383, null);
                MobileConnectionRepositoryKairosImpl$callbackEvents$1$1 mobileConnectionRepositoryKairosImpl$callbackEvents$1$1 = MobileConnectionRepositoryKairosImpl$callbackEvents$1$1.INSTANCE;
                MobileConnectionRepositoryKairosImpl$callbackEvents$1$2 mobileConnectionRepositoryKairosImpl$callbackEvents$1$2 = new MobileConnectionRepositoryKairosImpl$callbackEvents$1$2(this.f$0, (MobileInputLogger) this.f$1, null);
                BuildScopeKt$$ExternalSyntheticLambda6 buildScopeKt$$ExternalSyntheticLambda6 = new BuildScopeKt$$ExternalSyntheticLambda6(telephonyCallbackState);
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                return BuildScopeImpl.buildEvents$default(buildScopeImpl, new BuildScopeImpl$$ExternalSyntheticLambda3(buildScopeImpl, buildScopeKt$$ExternalSyntheticLambda6, mobileConnectionRepositoryKairosImpl$callbackEvents$1$1), mobileConnectionRepositoryKairosImpl$callbackEvents$1$2);
            default:
                final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) this.f$1, new IntentFilter("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED"), null, new CarrierHomeLogoViewController$$ExternalSyntheticLambda0(), 14);
                final MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

                        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = mobileConnectionRepositoryKairosImpl;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L4d
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                r7 = r6
                                android.content.Intent r7 = (android.content.Intent) r7
                                java.lang.String r2 = "android.telephony.extra.SUBSCRIPTION_ID"
                                r4 = -1
                                int r7 = r7.getIntExtra(r2, r4)
                                com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl r2 = r5.this$0
                                int r2 = r2.subId
                                if (r7 != r2) goto L4d
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r6, r0)
                                if (r5 != r1) goto L4d
                                return r1
                            L4d:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, mobileConnectionRepositoryKairosImpl), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L4b
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                android.content.Intent r5 = (android.content.Intent) r5
                                java.lang.String r6 = "android.telephony.extra.CARRIER_ID"
                                r2 = -1
                                int r5 = r5.getIntExtra(r6, r2)
                                java.lang.Integer r6 = new java.lang.Integer
                                r6.<init>(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r6, r0)
                                if (r4 != r1) goto L4b
                                return r1
                            L4b:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                Integer valueOf = Integer.valueOf(mobileConnectionRepositoryKairosImpl.telephonyManager.getSimCarrierId());
                BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl2.getClass();
                return buildScopeImpl2.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flow2), valueOf);
        }
    }

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0(MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileInputLogger mobileInputLogger) {
        this.f$0 = mobileConnectionRepositoryKairosImpl;
        this.f$1 = mobileInputLogger;
    }
}
