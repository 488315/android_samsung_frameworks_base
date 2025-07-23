package com.android.systemui.statusbar.pipeline.battery.domain.interactor;

import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$1;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$2;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$3;
import com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryRepository$special$$inlined$map$5;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$Companion r6 = com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor.Companion
                        r6.getClass()
                        r6 = 100
                        if (r5 < r6) goto L43
                        r5 = r3
                        goto L44
                    L43:
                        r5 = 0
                    L44:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        BatteryRepository$special$$inlined$map$1 batteryRepository$special$$inlined$map$1 = batteryRepository.isPluggedIn;
        this.isCharging = batteryRepository$special$$inlined$map$1;
        this.isCritical = new Flow() { // from class: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        r6 = 20
                        if (r5 > r6) goto L3e
                        r5 = r3
                        goto L3f
                    L3e:
                        r5 = 0
                    L3f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
