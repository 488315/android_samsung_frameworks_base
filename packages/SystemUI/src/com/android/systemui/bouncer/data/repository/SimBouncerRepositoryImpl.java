package com.android.systemui.bouncer.data.repository;

import android.R;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.telephony.euicc.EuiccManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.bouncer.data.model.SimPukInputModel;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.pipeline.mobile.util.SubscriptionManagerProxy;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SimBouncerRepositoryImpl implements SimBouncerRepository {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SimBouncerRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, final Resources resources, final KeyguardUpdateMonitor keyguardUpdateMonitor, SubscriptionManagerProxy subscriptionManagerProxy, BroadcastDispatcher broadcastDispatcher, final EuiccManager euiccManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.subscriptionManager = subscriptionManagerProxy;
        this.isPukScreenAvailable = resources.getBoolean(R.bool.config_flexibleSplitRatios);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SimBouncerRepositoryImpl$simBouncerModel$1 simBouncerRepositoryImpl$simBouncerModel$1 = new SimBouncerRepositoryImpl$simBouncerModel$1(keyguardUpdateMonitor, null);
        conflatedCallbackFlow.getClass();
        final Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(simBouncerRepositoryImpl$simBouncerModel$1);
        final Flow flow = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SimBouncerRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:20:0x00be  */
                /* JADX WARN: Removed duplicated region for block: B:23:0x00d5 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:24:0x00c7  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x007c  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x00a6 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:34:0x00a7  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0051  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        Method dump skipped, instructions count: 217
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, keyguardUpdateMonitor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.data.model.SimBouncerModel r5 = (com.android.systemui.bouncer.data.model.SimBouncerModel) r5
                        if (r5 == 0) goto L39
                        int r5 = r5.subscriptionId
                        goto L3a
                    L39:
                        r5 = -1
                    L3a:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), -1);
        this.subscriptionId = stateIn;
        Flow flow3 = new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SimBouncerRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:19:0x0064 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 2
                        r5 = 1
                        if (r2 == 0) goto L3b
                        if (r2 == r5) goto L33
                        if (r2 != r4) goto L2b
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L65
                    L2b:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L33:
                        java.lang.Object r7 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L5a
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.lang.Number r8 = (java.lang.Number) r8
                        int r8 = r8.intValue()
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl r9 = r7.this$0
                        kotlinx.coroutines.CoroutineDispatcher r2 = r9.backgroundDispatcher
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$activeSubscriptionInfo$1$1 r6 = new com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$activeSubscriptionInfo$1$1
                        r6.<init>(r9, r8, r3)
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        r0.L$0 = r7
                        r0.label = r5
                        java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
                        if (r9 != r1) goto L5a
                        return r1
                    L5a:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L65
                        return r1
                    L65:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(flow3, coroutineScope, startedEagerly, null);
        this.isLockedEsim = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ EuiccManager $euiccManager$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.telephony.SubscriptionInfo r5 = (android.telephony.SubscriptionInfo) r5
                        if (r5 == 0) goto L4e
                        android.telephony.euicc.EuiccManager r6 = r4.$euiccManager$inlined
                        if (r6 == 0) goto L48
                        boolean r6 = r6.isEnabled()
                        if (r6 == 0) goto L48
                        boolean r5 = r5.isEmbedded()
                        if (r5 == 0) goto L48
                        r5 = r3
                        goto L49
                    L48:
                        r5 = 0
                    L49:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, euiccManager), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5$2$1
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
                        com.android.systemui.bouncer.data.model.SimBouncerModel r5 = (com.android.systemui.bouncer.data.model.SimBouncerModel) r5
                        r6 = 0
                        if (r5 == 0) goto L3c
                        boolean r5 = r5.isSimPukLocked
                        if (r5 != r3) goto L3c
                        r6 = r3
                    L3c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, Boolean.FALSE);
        this.errorDialogMessage = FlowKt.stateIn(FlowKt.merge(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.keyguard.disable_esim"), null, new Function2() { // from class: com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl$disableEsimErrorMessage$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                if (((BroadcastReceiver) obj2).getResultCode() != 0) {
                    return resources.getString(com.android.systemui.R.string.error_disable_esim_msg);
                }
                return null;
            }
        }, 14), StateFlowKt.MutableStateFlow(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        new SimPukInputModel(null, null, 3, null);
    }
}
