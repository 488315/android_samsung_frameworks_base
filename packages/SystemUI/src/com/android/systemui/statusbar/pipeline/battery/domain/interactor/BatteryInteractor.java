package com.android.systemui.statusbar.pipeline.battery.domain.interactor;

import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$1;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$2;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$3;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$5;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class BatteryInteractor {
    public static final Companion Companion = new Companion(null);
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 batteryAttributionType;
    public final Flow batteryTimeRemainingEstimate;
    public final BatteryRepository$special$$inlined$map$3 isBatteryDefenderEnabled;
    public final ReadonlyStateFlow isBatteryPercentSettingEnabled;
    public final BatteryRepository$special$$inlined$map$1 isCharging;
    public final BatteryInteractor$special$$inlined$map$2 isCritical;
    public final BatteryInteractor$special$$inlined$map$1 isFull;
    public final BatteryRepository$special$$inlined$map$5 isStateUnknown;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 level;
    public final BatteryRepository$special$$inlined$map$2 powerSave;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2] */
    public BatteryInteractor(BatteryRepository batteryRepository) {
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(batteryRepository.level);
        this.level = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        this.isFull = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        int iIntValue = ((Number) obj).intValue();
                        BatteryInteractor.Companion.getClass();
                        Boolean boolValueOf = Boolean.valueOf(iIntValue >= 100);
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        BatteryRepository$special$$inlined$map$1 batteryRepository$special$$inlined$map$1 = batteryRepository.isPluggedIn;
        this.isCharging = batteryRepository$special$$inlined$map$1;
        this.isCritical = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).intValue() <= 20);
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isStateUnknown = batteryRepository.isStateUnknown;
        BatteryRepository$special$$inlined$map$3 batteryRepository$special$$inlined$map$3 = batteryRepository.isBatteryDefenderEnabled;
        this.isBatteryDefenderEnabled = batteryRepository$special$$inlined$map$3;
        BatteryRepository$special$$inlined$map$2 batteryRepository$special$$inlined$map$2 = batteryRepository.isPowerSaveEnabled;
        this.powerSave = batteryRepository$special$$inlined$map$2;
        this.isBatteryPercentSettingEnabled = batteryRepository.isShowBatteryPercentSettingEnabled;
        this.batteryAttributionType = FlowKt.combine(batteryRepository$special$$inlined$map$1, batteryRepository$special$$inlined$map$2, batteryRepository$special$$inlined$map$3, new BatteryInteractor$batteryAttributionType$1(null));
        this.batteryTimeRemainingEstimate = batteryRepository.batteryTimeRemainingEstimate;
    }
}
