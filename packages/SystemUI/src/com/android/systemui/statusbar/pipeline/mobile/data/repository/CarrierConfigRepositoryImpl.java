package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.util.SparseArray;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig$$ExternalSyntheticLambda0;
import java.io.PrintWriter;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarrierConfigRepositoryImpl implements CarrierConfigRepository, Dumpable {
    public final CarrierConfigManager carrierConfigManager;
    public final CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1 carrierConfigStream;
    public boolean isListening;
    public final Lazy defaultConfig$delegate = LazyKt__LazyJVMKt.lazy(new CarrierConfigRepositoryImpl$$ExternalSyntheticLambda0());
    public final Lazy defaultConfigForLogs$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SystemUiCarrierConfig(-1, (PersistableBundle) CarrierConfigRepositoryImpl.this.defaultConfig$delegate.getValue());
        }
    });
    public final SparseArray configs = new SparseArray();

    public CarrierConfigRepositoryImpl(BroadcastDispatcher broadcastDispatcher, CarrierConfigManager carrierConfigManager, DumpManager dumpManager, MobileInputLogger mobileInputLogger) {
        this.carrierConfigManager = carrierConfigManager;
        dumpManager.registerNormalDumpable(this);
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, new CarrierConfigRepositoryImpl$$ExternalSyntheticLambda2(), 14), new CarrierConfigRepositoryImpl$carrierConfigStream$2(mobileInputLogger, null));
        this.carrierConfigStream = new CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isListening: ", this.isListening);
        if (this.configs.size() == 0) {
            printWriter.println("no carrier configs loaded");
            return;
        }
        printWriter.println("Carrier configs by subId");
        SparseArrayKt$keyIterator$1 sparseArrayKt$keyIterator$1 = new SparseArrayKt$keyIterator$1(this.configs);
        while (sparseArrayKt$keyIterator$1.hasNext()) {
            int intValue = ((Number) sparseArrayKt$keyIterator$1.next()).intValue();
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  subId=", intValue, printWriter);
            SystemUiCarrierConfig systemUiCarrierConfig = (SystemUiCarrierConfig) this.configs.get(intValue);
            if (systemUiCarrierConfig == null) {
                printWriter.println("    config=null (config was removed during dump)");
            } else {
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "    config=", systemUiCarrierConfig.isUsingDefault ? "using defaults" : CollectionsKt___CollectionsKt.joinToString$default(systemUiCarrierConfig.trackedConfigs, null, null, null, new SystemUiCarrierConfig$$ExternalSyntheticLambda0(1), 31));
            }
        }
        printWriter.println("Default config:");
        printWriter.println("  " + ((SystemUiCarrierConfig) this.defaultConfigForLogs$delegate.getValue()));
    }

    public final SystemUiCarrierConfig getOrCreateConfigForSubId(Integer num) {
        int intValue = num != null ? num.intValue() : -1;
        Object obj = this.configs.get(intValue);
        Object obj2 = obj;
        if (obj == null) {
            SystemUiCarrierConfig systemUiCarrierConfig = new SystemUiCarrierConfig(intValue, (PersistableBundle) this.defaultConfig$delegate.getValue());
            CarrierConfigManager carrierConfigManager = this.carrierConfigManager;
            PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(intValue) : null;
            if (configForSubId != null) {
                systemUiCarrierConfig.processNewCarrierConfig(configForSubId);
            }
            this.configs.put(intValue, systemUiCarrierConfig);
            obj2 = systemUiCarrierConfig;
        }
        return (SystemUiCarrierConfig) obj2;
    }

    public static /* synthetic */ void getCarrierConfigStream$annotations() {
    }
}
