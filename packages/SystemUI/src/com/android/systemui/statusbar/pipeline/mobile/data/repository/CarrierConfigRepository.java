package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.util.SparseArray;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig;
import java.io.PrintWriter;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CarrierConfigRepository implements Dumpable {
    public final CarrierConfigManager carrierConfigManager;
    public final ReadonlySharedFlow carrierConfigStream;
    public boolean isListening;
    public final Lazy defaultConfig$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$defaultConfig$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return CarrierConfigManager.getDefaultConfig();
        }
    });
    public final Lazy defaultConfigForLogs$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$defaultConfigForLogs$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SystemUiCarrierConfig(-1, (PersistableBundle) CarrierConfigRepository.this.defaultConfig$delegate.getValue());
        }
    });
    public final SparseArray configs = new SparseArray();

    public CarrierConfigRepository(BroadcastDispatcher broadcastDispatcher, CarrierConfigManager carrierConfigManager, DumpManager dumpManager, MobileInputLogger mobileInputLogger, CoroutineScope coroutineScope) {
        this.carrierConfigManager = carrierConfigManager;
        dumpManager.registerNormalDumpable(this);
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$carrierConfigStream$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((Intent) obj).getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1));
            }
        }, 14), new CarrierConfigRepository$carrierConfigStream$2(mobileInputLogger, null));
        final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        boolean r6 = android.telephony.SubscriptionManager.isValidSubscriptionId(r6)
                        if (r6 == 0) goto L4a
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.carrierConfigStream = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CarrierConfigRepository this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CarrierConfigRepository carrierConfigRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = carrierConfigRepository;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository r6 = r4.this$0
                        android.telephony.CarrierConfigManager r6 = r6.carrierConfigManager
                        r2 = 0
                        if (r6 == 0) goto L44
                        android.os.PersistableBundle r6 = r6.getConfigForSubId(r5)
                        goto L45
                    L44:
                        r6 = r2
                    L45:
                        if (r6 == 0) goto L52
                        java.lang.Integer r2 = new java.lang.Integer
                        r2.<init>(r5)
                        kotlin.Pair r5 = new kotlin.Pair
                        r5.<init>(r2, r6)
                        r2 = r5
                    L52:
                        if (r2 == 0) goto L5f
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L5f
                        return r1
                    L5f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isListening: ", this.isListening, printWriter);
        if (this.configs.size() == 0) {
            printWriter.println("no carrier configs loaded");
            return;
        }
        printWriter.println("Carrier configs by subId");
        SparseArrayKt$keyIterator$1 sparseArrayKt$keyIterator$1 = new SparseArrayKt$keyIterator$1(this.configs);
        while (sparseArrayKt$keyIterator$1.hasNext()) {
            int intValue = ((Number) sparseArrayKt$keyIterator$1.next()).intValue();
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  subId=", intValue, printWriter);
            printWriter.println("    config=".concat(((SystemUiCarrierConfig) this.configs.get(intValue)).toStringConsideringDefaults()));
        }
        printWriter.println("Default config:");
        printWriter.println("  " + ((SystemUiCarrierConfig) this.defaultConfigForLogs$delegate.getValue()));
    }

    public final SystemUiCarrierConfig getOrCreateConfigForSubId(int i) {
        Object obj = this.configs.get(i);
        Object obj2 = obj;
        if (obj == null) {
            SystemUiCarrierConfig systemUiCarrierConfig = new SystemUiCarrierConfig(i, (PersistableBundle) this.defaultConfig$delegate.getValue());
            CarrierConfigManager carrierConfigManager = this.carrierConfigManager;
            PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
            if (configForSubId != null) {
                systemUiCarrierConfig.processNewCarrierConfig(configForSubId);
            }
            this.configs.put(i, systemUiCarrierConfig);
            obj2 = systemUiCarrierConfig;
        }
        return (SystemUiCarrierConfig) obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons startObservingCarrierConfigUpdates(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L46
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r4.isListening = r3
            com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$2 r5 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$2
            r5.<init>()
            r0.label = r3
            kotlinx.coroutines.flow.ReadonlySharedFlow r4 = r4.carrierConfigStream
            kotlinx.coroutines.flow.SharedFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.collect(r5, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository.startObservingCarrierConfigUpdates(kotlin.coroutines.Continuation):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static /* synthetic */ void getCarrierConfigStream$annotations() {
    }
}
