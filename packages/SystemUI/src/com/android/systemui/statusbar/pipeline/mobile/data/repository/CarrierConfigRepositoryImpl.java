package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes3.dex */
public final class CarrierConfigRepositoryImpl implements CarrierConfigRepository, Dumpable {
    public final CarrierConfigManager carrierConfigManager;
    public final CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1 carrierConfigStream;
    public boolean isListening;
    public final Lazy defaultConfig$delegate = LazyKt__LazyJVMKt.lazy(new CarrierConfigRepositoryImpl$$ExternalSyntheticLambda0());
    public final Lazy defaultConfigForLogs$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SystemUiCarrierConfig(-1, (PersistableBundle) this.f$0.defaultConfig$delegate.getValue());
        }
    });
    public final SparseArray configs = new SparseArray();

    public CarrierConfigRepositoryImpl(BroadcastDispatcher broadcastDispatcher, CarrierConfigManager carrierConfigManager, DumpManager dumpManager, MobileInputLogger mobileInputLogger) {
        this.carrierConfigManager = carrierConfigManager;
        dumpManager.registerNormalDumpable(this);
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, new CarrierConfigRepositoryImpl$$ExternalSyntheticLambda2(), 14), new CarrierConfigRepositoryImpl$carrierConfigStream$2(mobileInputLogger, null));
        this.carrierConfigStream = new CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$filter$1

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
                        if (SubscriptionManager.isValidSubscriptionId(((Number) obj).intValue())) {
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
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
            int iIntValue = ((Number) sparseArrayKt$keyIterator$1.next()).intValue();
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  subId=", iIntValue, printWriter);
            SystemUiCarrierConfig systemUiCarrierConfig = (SystemUiCarrierConfig) this.configs.get(iIntValue);
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
        int iIntValue = num != null ? num.intValue() : -1;
        Object obj = this.configs.get(iIntValue);
        Object obj2 = obj;
        if (obj == null) {
            SystemUiCarrierConfig systemUiCarrierConfig = new SystemUiCarrierConfig(iIntValue, (PersistableBundle) this.defaultConfig$delegate.getValue());
            CarrierConfigManager carrierConfigManager = this.carrierConfigManager;
            PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(iIntValue) : null;
            if (configForSubId != null) {
                systemUiCarrierConfig.processNewCarrierConfig(configForSubId);
            }
            this.configs.put(iIntValue, systemUiCarrierConfig);
            obj2 = systemUiCarrierConfig;
        }
        return (SystemUiCarrierConfig) obj2;
    }

    public static /* synthetic */ void getCarrierConfigStream$annotations() {
    }
}
