package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryBiometricsAllowedInteractor {
    public final StateFlow isFaceLockedOut;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isFingerprintAuthCurrentlyAllowed;
    public final ChannelFlowTransformLatest isFingerprintCurrentlyAllowedOnBouncer;
    public final StateFlow isFingerprintLockedOut;
    public final DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1 isStrongFaceAuth;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isStrongFaceAuthLockedOut;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public DeviceEntryBiometricsAllowedInteractor(DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryBiometricSettingsInteractor deviceEntryBiometricSettingsInteractor, FacePropertyRepository facePropertyRepository) {
        StateFlow isLockedOut = deviceEntryFaceAuthInteractor.isLockedOut();
        this.isFaceLockedOut = isLockedOut;
        final ReadonlyStateFlow readonlyStateFlow = ((FacePropertyRepositoryImpl) facePropertyRepository).sensorInfo;
        ?? r0 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.data.repository.FaceSensorInfo r5 = (com.android.systemui.biometrics.data.repository.FaceSensorInfo) r5
                        if (r5 == 0) goto L39
                        com.android.systemui.biometrics.shared.model.SensorStrength r5 = r5.strength
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        com.android.systemui.biometrics.shared.model.SensorStrength r6 = com.android.systemui.biometrics.shared.model.SensorStrength.STRONG
                        if (r5 != r6) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isStrongFaceAuth = r0;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(r0, isLockedOut, new DeviceEntryBiometricsAllowedInteractor$isStrongFaceAuthLockedOut$1(null));
        this.isStrongFaceAuthLockedOut = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        StateFlow stateFlow = deviceEntryFingerprintAuthInteractor.isLockedOut;
        this.isFingerprintLockedOut = stateFlow;
        this.isFingerprintAuthCurrentlyAllowed = FlowKt.combine(stateFlow, deviceEntryBiometricSettingsInteractor.fingerprintAuthCurrentlyAllowed, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new DeviceEntryBiometricsAllowedInteractor$isFingerprintAuthCurrentlyAllowed$1(null));
        this.isFingerprintCurrentlyAllowedOnBouncer = FlowKt.transformLatest(deviceEntryFingerprintAuthInteractor.isSensorUnderDisplay, new DeviceEntryBiometricsAllowedInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
