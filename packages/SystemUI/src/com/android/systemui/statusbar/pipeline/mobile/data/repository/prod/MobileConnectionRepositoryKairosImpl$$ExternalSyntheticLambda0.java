package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt$$ExternalSyntheticLambda6;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

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
    public final Object mo781invoke(Object obj) {
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
                final Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default((BroadcastDispatcher) this.f$1, new IntentFilter("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED"), null, new CarrierHomeLogoViewController$$ExternalSyntheticLambda0(), 14);
                final MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl = this.f$0;
                final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$filter$1

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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((Intent) obj).getIntExtra("android.telephony.extra.SUBSCRIPTION_ID", -1) == this.this$0.subId) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flowBroadcastFlow$default.collect(new AnonymousClass2(flowCollector, mobileConnectionRepositoryKairosImpl), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl$carrierId$lambda$44$$inlined$map$1

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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                Integer num = new Integer(((Intent) obj).getIntExtra("android.telephony.extra.CARRIER_ID", -1));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                Integer numValueOf = Integer.valueOf(mobileConnectionRepositoryKairosImpl.telephonyManager.getSimCarrierId());
                BuildScopeImpl buildScopeImpl2 = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl2.getClass();
                return buildScopeImpl2.stateScope.holdState(BuildScope.DefaultImpls.toEvents(buildScopeImpl2, flow2), numValueOf);
        }
    }

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda0(MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, MobileInputLogger mobileInputLogger) {
        this.f$0 = mobileConnectionRepositoryKairosImpl;
        this.f$1 = mobileInputLogger;
    }
}
