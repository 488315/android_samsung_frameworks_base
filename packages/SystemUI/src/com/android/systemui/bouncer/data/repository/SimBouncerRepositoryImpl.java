package com.android.systemui.bouncer.data.repository;

import android.R;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.bouncer.data.model.SimBouncerModel;
import com.android.systemui.bouncer.data.model.SimPukInputModel;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxyImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class SimBouncerRepositoryImpl implements SimBouncerRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SimPukInputModel _simPukInputModel;
    public final ReadonlyStateFlow activeSubscriptionInfo;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow errorDialogMessage;
    public final ReadonlyStateFlow isLockedEsim;
    public final boolean isPukScreenAvailable;
    public final ReadonlyStateFlow isSimPukLocked;
    public final SimBouncerRepositoryImpl$special$$inlined$map$1 simBouncerModel;
    public final StateFlowImpl simVerificationErrorMessage;
    public final ReadonlyStateFlow subscriptionId;
    public final SubscriptionManagerProxy subscriptionManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public SimBouncerRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, final Resources resources, final KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionManagerProxy subscriptionManagerProxy, BroadcastDispatcher broadcastDispatcher, final EuiccManager euiccManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.subscriptionManager = subscriptionManagerProxy;
        this.isPukScreenAvailable = resources.getBoolean(R.bool.config_hotswapCapable);
        final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new SimBouncerRepositoryImpl$simBouncerModel$1(keyguardUpdateMonitor, null));
        final ?? r0 = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SimBouncerRepositoryImpl this$0;

                /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SimBouncerRepositoryImpl simBouncerRepositoryImpl, KeyguardUpdateMonitor keyguardUpdateMonitor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = simBouncerRepositoryImpl;
                    this.$keyguardUpdateMonitor$inlined = keyguardUpdateMonitor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:35:0x00d3, code lost:
                
                    if (r2.emit(r8, r0) == r1) goto L36;
                 */
                /* JADX WARN: Removed duplicated region for block: B:32:0x00be  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00c7  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    AnonymousClass2 anonymousClass2;
                    FlowCollector flowCollector2;
                    SimBouncerModel simBouncerModel;
                    int iIntValue;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        CoroutineDispatcher coroutineDispatcher = this.this$0.backgroundDispatcher;
                        SimBouncerRepositoryImpl$simBouncerModel$2$pukLockedSubId$1 simBouncerRepositoryImpl$simBouncerModel$2$pukLockedSubId$1 = new SimBouncerRepositoryImpl$simBouncerModel$2$pukLockedSubId$1(this.$keyguardUpdateMonitor$inlined, null);
                        anonymousClass1.L$0 = this;
                        flowCollector = this.$this_unsafeFlow;
                        anonymousClass1.L$1 = flowCollector;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(coroutineDispatcher, simBouncerRepositoryImpl$simBouncerModel$2$pukLockedSubId$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 == 1) {
                        FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$1;
                        AnonymousClass2 anonymousClass22 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(objWithContext);
                        flowCollector = flowCollector3;
                        this = anonymousClass22;
                    } else {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(objWithContext);
                            return Unit.INSTANCE;
                        }
                        flowCollector2 = (FlowCollector) anonymousClass1.L$1;
                        anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(objWithContext);
                        iIntValue = ((Number) objWithContext).intValue();
                        ((SubscriptionManagerProxyImpl) anonymousClass2.this$0.subscriptionManager).getClass();
                        if (SubscriptionManager.isValidSubscriptionId(iIntValue)) {
                            flowCollector = flowCollector2;
                            simBouncerModel = null;
                        } else {
                            flowCollector = flowCollector2;
                            simBouncerModel = new SimBouncerModel(false, iIntValue);
                        }
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 3;
                    }
                    int iIntValue2 = ((Number) objWithContext).intValue();
                    SimBouncerRepositoryImpl simBouncerRepositoryImpl = this.this$0;
                    if (simBouncerRepositoryImpl.isPukScreenAvailable) {
                        ((SubscriptionManagerProxyImpl) simBouncerRepositoryImpl.subscriptionManager).getClass();
                        if (SubscriptionManager.isValidSubscriptionId(iIntValue2)) {
                            simBouncerModel = new SimBouncerModel(true, iIntValue2);
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.label = 3;
                        }
                    }
                    CoroutineDispatcher coroutineDispatcher2 = this.this$0.backgroundDispatcher;
                    SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1 simBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1 = new SimBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1(this.$keyguardUpdateMonitor$inlined, null);
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = flowCollector;
                    anonymousClass1.label = 2;
                    objWithContext = BuildersKt.withContext(coroutineDispatcher2, simBouncerRepositoryImpl$simBouncerModel$2$pinLockedSubId$1, anonymousClass1);
                    if (objWithContext != coroutineSingletons) {
                        anonymousClass2 = this;
                        flowCollector2 = flowCollector;
                        iIntValue = ((Number) objWithContext).intValue();
                        ((SubscriptionManagerProxyImpl) anonymousClass2.this$0.subscriptionManager).getClass();
                        if (SubscriptionManager.isValidSubscriptionId(iIntValue)) {
                        }
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 3;
                    }
                    return coroutineSingletons;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector, this, keyguardUpdateMonitor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.simBouncerModel = r0;
        Flow flow = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        SimBouncerModel simBouncerModel = (SimBouncerModel) obj;
                        Integer num = new Integer(simBouncerModel != null ? simBouncerModel.subscriptionId : -1);
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
                Object objCollect = r0.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), -1);
        this.subscriptionId = readonlyStateFlowStateIn;
        Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SimBouncerRepositoryImpl this$0;

                /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SimBouncerRepositoryImpl simBouncerRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = simBouncerRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x0062, code lost:
                
                    if (r7.emit(r9, r0) == r1) goto L21;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        int iIntValue = ((Number) obj).intValue();
                        SimBouncerRepositoryImpl simBouncerRepositoryImpl = this.this$0;
                        CoroutineDispatcher coroutineDispatcher = simBouncerRepositoryImpl.backgroundDispatcher;
                        SimBouncerRepositoryImpl$activeSubscriptionInfo$1$1 simBouncerRepositoryImpl$activeSubscriptionInfo$1$1 = new SimBouncerRepositoryImpl$activeSubscriptionInfo$1$1(simBouncerRepositoryImpl, iIntValue, null);
                        flowCollector = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(coroutineDispatcher, simBouncerRepositoryImpl$activeSubscriptionInfo$1$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(flow2, coroutineScope, startedEagerly, null);
        this.activeSubscriptionInfo = readonlyStateFlowStateIn2;
        this.isLockedEsim = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4

            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ EuiccManager $euiccManager$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, EuiccManager euiccManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$euiccManager$inlined = euiccManager;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Boolean boolValueOf;
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
                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                        if (subscriptionInfo != null) {
                            EuiccManager euiccManager = this.$euiccManager$inlined;
                            boolValueOf = Boolean.valueOf(euiccManager != null && euiccManager.isEnabled() && subscriptionInfo.isEmbedded());
                        } else {
                            boolValueOf = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector, euiccManager), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        this.isSimPukLocked = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5

            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        SimBouncerModel simBouncerModel = (SimBouncerModel) obj;
                        boolean z = false;
                        if (simBouncerModel != null && simBouncerModel.isSimPukLocked) {
                            z = true;
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = r0.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, Boolean.FALSE);
        Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.keyguard.disable_esim"), null, new Function2() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Resources resources2 = resources;
                int i = SimBouncerRepositoryImpl.$r8$clinit;
                if (((BroadcastReceiver) obj2).getResultCode() != 0) {
                    return resources2.getString(com.android.systemui.R.string.error_disable_esim_msg);
                }
                return null;
            }
        }, 14);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.simVerificationErrorMessage = stateFlowImplMutableStateFlow;
        this.errorDialogMessage = FlowKt.stateIn(FlowKt.merge(flowBroadcastFlow$default, stateFlowImplMutableStateFlow), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this._simPukInputModel = new SimPukInputModel(null, null, 3, null);
    }
}
