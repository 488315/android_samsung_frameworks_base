package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.util.SparseArray;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2 */
            public final class C32072 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$filter$1$2", m277f = "CarrierConfigRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32072.this.emit(null, this);
                    }
                }

                public C32072(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (SubscriptionManager.isValidSubscriptionId(((Number) obj).intValue())) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32072(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.carrierConfigStream = FlowKt.shareIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2 */
            public final class C32082 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CarrierConfigRepository this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$special$$inlined$mapNotNull$1$2", m277f = "CarrierConfigRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setForceAutoShutDownState}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C32082.this.emit(null, this);
                    }
                }

                public C32082(FlowCollector flowCollector, CarrierConfigRepository carrierConfigRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = carrierConfigRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                int intValue = ((Number) obj).intValue();
                                PersistableBundle configForSubId = this.this$0.carrierConfigManager.getConfigForSubId(intValue);
                                Pair pair = configForSubId != null ? new Pair(new Integer(intValue), configForSubId) : null;
                                if (pair != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(pair, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C32082(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), 0);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("isListening: ", this.isListening, printWriter);
        SparseArray sparseArray = this.configs;
        if (sparseArray.size() == 0) {
            printWriter.println("no carrier configs loaded");
            return;
        }
        printWriter.println("Carrier configs by subId");
        SparseArrayKt$keyIterator$1 sparseArrayKt$keyIterator$1 = new SparseArrayKt$keyIterator$1(sparseArray);
        while (sparseArrayKt$keyIterator$1.hasNext()) {
            int intValue = ((Number) sparseArrayKt$keyIterator$1.next()).intValue();
            printWriter.println("  subId=" + intValue);
            printWriter.println("    config=".concat(((SystemUiCarrierConfig) sparseArray.get(intValue)).toStringConsideringDefaults()));
        }
        printWriter.println("Default config:");
        printWriter.println("  " + ((SystemUiCarrierConfig) this.defaultConfigForLogs$delegate.getValue()));
    }

    public final SystemUiCarrierConfig getOrCreateConfigForSubId(int i) {
        SparseArray sparseArray = this.configs;
        Object obj = sparseArray.get(i);
        Object obj2 = obj;
        if (obj == null) {
            SystemUiCarrierConfig systemUiCarrierConfig = new SystemUiCarrierConfig(i, (PersistableBundle) this.defaultConfig$delegate.getValue());
            PersistableBundle configForSubId = this.carrierConfigManager.getConfigForSubId(i);
            if (configForSubId != null) {
                systemUiCarrierConfig.processNewCarrierConfig(configForSubId);
            }
            sparseArray.put(i, systemUiCarrierConfig);
            obj2 = systemUiCarrierConfig;
        }
        return (SystemUiCarrierConfig) obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineSingletons startObservingCarrierConfigUpdates(Continuation continuation) {
        CarrierConfigRepository$startObservingCarrierConfigUpdates$1 carrierConfigRepository$startObservingCarrierConfigUpdates$1;
        int i;
        if (continuation instanceof CarrierConfigRepository$startObservingCarrierConfigUpdates$1) {
            carrierConfigRepository$startObservingCarrierConfigUpdates$1 = (CarrierConfigRepository$startObservingCarrierConfigUpdates$1) continuation;
            int i2 = carrierConfigRepository$startObservingCarrierConfigUpdates$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                carrierConfigRepository$startObservingCarrierConfigUpdates$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = carrierConfigRepository$startObservingCarrierConfigUpdates$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = carrierConfigRepository$startObservingCarrierConfigUpdates$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    this.isListening = true;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository$startObservingCarrierConfigUpdates$2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation2) {
                            Pair pair = (Pair) obj2;
                            int intValue = ((Number) pair.getFirst()).intValue();
                            CarrierConfigRepository.this.getOrCreateConfigForSubId(intValue).processNewCarrierConfig((PersistableBundle) pair.getSecond());
                            return Unit.INSTANCE;
                        }
                    };
                    carrierConfigRepository$startObservingCarrierConfigUpdates$1.label = 1;
                    if (this.carrierConfigStream.collect(flowCollector, carrierConfigRepository$startObservingCarrierConfigUpdates$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }
        carrierConfigRepository$startObservingCarrierConfigUpdates$1 = new CarrierConfigRepository$startObservingCarrierConfigUpdates$1(this, continuation);
        Object obj2 = carrierConfigRepository$startObservingCarrierConfigUpdates$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = carrierConfigRepository$startObservingCarrierConfigUpdates$1.label;
        if (i != 0) {
        }
        throw new KotlinNothingValueException();
    }

    public static /* synthetic */ void getCarrierConfigStream$annotations() {
    }
}
