package com.android.systemui.deviceentry.domain.interactor;

import com.android.keyguard.logging.BiometricUnlockLogger;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.keyevent.domain.interactor.KeyEventInteractor;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class DeviceEntryHapticsInteractor {
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 lastPowerButtonWakeup;
    public final BiometricUnlockLogger logger;
    public final DeviceEntryHapticsInteractor$special$$inlined$map$3 playErrorHapticForBiometricFailure;
    public final long recentPowerButtonPressThresholdMs;
    public final SystemClock systemClock;

    public DeviceEntryHapticsInteractor(DeviceEntrySourceInteractor deviceEntrySourceInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryBiometricAuthInteractor deviceEntryBiometricAuthInteractor, FingerprintPropertyRepository fingerprintPropertyRepository, BiometricSettingsRepository biometricSettingsRepository, KeyEventInteractor keyEventInteractor, PowerInteractor powerInteractor, SystemClock systemClock, BiometricUnlockLogger biometricUnlockLogger) {
        this.systemClock = systemClock;
        this.logger = biometricUnlockLogger;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combineTransform(((FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository).sensorType, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFingerprintEnrolledAndEnabled, new DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1(null)));
        Flow flow = keyEventInteractor.isPowerButtonDown;
        final ReadonlyStateFlow readonlyStateFlow = powerInteractor.detailedWakefulness;
        final Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.power.shared.model.WakefulnessModel r7 = (com.android.systemui.power.shared.model.WakefulnessModel) r7
                        com.android.systemui.power.shared.model.WakeSleepReason r2 = com.android.systemui.power.shared.model.WakeSleepReason.POWER_BUTTON
                        boolean r4 = r7.isAwake()
                        if (r4 == 0) goto L4c
                        com.android.systemui.power.shared.model.WakeSleepReason r7 = r7.lastWakeReason
                        if (r7 != r2) goto L4c
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(deviceEntrySourceInteractor.deviceEntryFromBiometricSource, FlowKt.combine(distinctUntilChanged, flow, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryHapticsInteractor$lastPowerButtonWakeup$3(this, null), new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntryHapticsInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntryHapticsInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.power.shared.model.WakefulnessModel r5 = (com.android.systemui.power.shared.model.WakefulnessModel) r5
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor r5 = r4.this$0
                        com.android.systemui.util.time.SystemClock r5 = r5.systemClock
                        long r5 = r5.uptimeMillis()
                        java.lang.Long r2 = new java.lang.Long
                        r2.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), DeviceEntryHapticsInteractor$playSuccessHaptic$2.INSTANCE));
        final Flow flow3 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntryHapticsInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntryHapticsInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L8d
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        r12 = r11
                        kotlin.Triple r12 = (kotlin.Triple) r12
                        java.lang.Object r2 = r12.component1()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        java.lang.Object r4 = r12.component2()
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        java.lang.Object r12 = r12.component3()
                        java.lang.Number r12 = (java.lang.Number) r12
                        long r5 = r12.longValue()
                        r12 = 0
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor r7 = r10.this$0
                        if (r4 != 0) goto L67
                        com.android.systemui.util.time.SystemClock r4 = r7.systemClock
                        long r8 = r4.uptimeMillis()
                        long r8 = r8 - r5
                        long r4 = r7.recentPowerButtonPressThresholdMs
                        int r4 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                        if (r4 <= 0) goto L67
                        r4 = r3
                        goto L68
                    L67:
                        r4 = r12
                    L68:
                        if (r2 == 0) goto L6c
                        if (r4 == 0) goto L6d
                    L6c:
                        r12 = r3
                    L6d:
                        if (r12 != 0) goto L80
                        com.android.keyguard.logging.BiometricUnlockLogger r2 = r7.logger
                        r2.getClass()
                        com.android.systemui.log.core.LogLevel r4 = com.android.systemui.log.core.LogLevel.DEBUG
                        java.lang.String r5 = "BiometricUnlockLogger"
                        r6 = 0
                        com.android.systemui.log.LogBuffer r2 = r2.logBuffer
                        java.lang.String r7 = "Skip success haptic. Recent power button press or button is down."
                        r2.log(r5, r4, r7, r6)
                    L80:
                        if (r12 == 0) goto L8d
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto L8d
                        return r1
                    L8d:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Triple r5 = (kotlin.Triple) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ChannelLimitedFlowMerge merge = FlowKt.merge(deviceEntryFingerprintAuthInteractor.fingerprintFailure, deviceEntryBiometricAuthInteractor.faceOnlyFaceFailure);
        final Flow sample2 = com.android.systemui.util.kotlin.FlowKt.sample(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                    /*
                        r3 = this;
                        boolean r4 = r5 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r4 == 0) goto L13
                        r4 = r5
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3$2$1 r4 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r4
                        int r0 = r4.label
                        r1 = -2147483648(0xffffffff80000000, float:-0.0)
                        r2 = r0 & r1
                        if (r2 == 0) goto L13
                        int r0 = r0 - r1
                        r4.label = r0
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3$2$1 r4 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3$2$1
                        r4.<init>(r5)
                    L18:
                        java.lang.Object r5 = r4.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r4.label
                        r2 = 1
                        if (r1 == 0) goto L2f
                        if (r1 != r2) goto L27
                        kotlin.ResultKt.throwOnFailure(r5)
                        goto L3f
                    L27:
                        java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                        java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                        r3.<init>(r4)
                        throw r3
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r5)
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r4.label = r2
                        kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                        java.lang.Object r3 = r3.emit(r5, r4)
                        if (r3 != r0) goto L3f
                        return r0
                    L3f:
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, flow, DeviceEntryHapticsInteractor$playErrorHaptic$2.INSTANCE));
        final Flow flow4 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntryHapticsInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntryHapticsInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L73
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        r10 = r9
                        kotlin.Pair r10 = (kotlin.Pair) r10
                        java.lang.Object r2 = r10.component1()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        java.lang.Object r10 = r10.component2()
                        java.lang.Boolean r10 = (java.lang.Boolean) r10
                        boolean r10 = r10.booleanValue()
                        if (r2 == 0) goto L50
                        if (r10 != 0) goto L4e
                        goto L50
                    L4e:
                        r10 = 0
                        goto L51
                    L50:
                        r10 = r3
                    L51:
                        if (r10 != 0) goto L66
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor r2 = r8.this$0
                        com.android.keyguard.logging.BiometricUnlockLogger r2 = r2.logger
                        r2.getClass()
                        com.android.systemui.log.core.LogLevel r4 = com.android.systemui.log.core.LogLevel.DEBUG
                        java.lang.String r5 = "BiometricUnlockLogger"
                        r6 = 0
                        com.android.systemui.log.LogBuffer r2 = r2.logBuffer
                        java.lang.String r7 = "Skip error haptic. Power button is down."
                        r2.log(r5, r4, r7, r6)
                    L66:
                        if (r10 == 0) goto L73
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r9, r0)
                        if (r8 != r1) goto L73
                        return r1
                    L73:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.recentPowerButtonPressThresholdMs = 400L;
    }
}
