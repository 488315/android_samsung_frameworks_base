package com.android.systemui.keyguard.data.repository;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

public final class DeviceEntryFingerprintAuthRepositoryImpl implements DeviceEntryFingerprintAuthRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthController authController;
    public final ReadonlyStateFlow isEngaged;
    public final ReadonlyStateFlow isLockedOut;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final CoroutineDispatcher mainDispatcher;
    public final ReadonlySharedFlow shouldUpdateIndicatorVisibility;

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

    public DeviceEntryFingerprintAuthRepositoryImpl(AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.authController = authController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainDispatcher = coroutineDispatcher;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1 deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1 = new DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(deviceEntryFingerprintAuthRepositoryImpl$isLockedOut$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isLockedOut = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, WhileSubscribed$default, bool);
        final Flow authenticationStatus = getAuthenticationStatus();
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1$2$1
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
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r5 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r5
                        java.lang.Boolean r5 = r5.isEngaged
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isEngaged = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.shouldUpdateIndicatorVisibility = FlowKt.shareIn(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFingerprintAuthRepositoryImpl$shouldUpdateIndicatorVisibility$1(this, null)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
    }

    public final Flow getAuthenticationStatus() {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1 deviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1 = new DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.buffer$default(FlowConflatedKt.conflatedCallbackFlow(deviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1), 4);
    }

    public final Flow getAvailableFpSensorType() {
        if (this.authController.mAllFingerprintAuthenticatorsRegistered) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(getFpSensorType());
        }
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1 deviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1 = new DeviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(deviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1);
    }

    public final BiometricType getFpSensorType() {
        AuthController authController = this.authController;
        if (authController.isUdfpsSupported()) {
            return BiometricType.UNDER_DISPLAY_FINGERPRINT;
        }
        List list = authController.mSidefpsProps;
        if (list != null && !list.isEmpty()) {
            return BiometricType.SIDE_FINGERPRINT;
        }
        List list2 = authController.mFpProps;
        if (list2 != null) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                if (((FingerprintSensorPropertiesInternal) it.next()).sensorType == 1) {
                    return BiometricType.REAR_FINGERPRINT;
                }
            }
        }
        return null;
    }

    public final Flow isRunning() {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1 deviceEntryFingerprintAuthRepositoryImpl$isRunning$1 = new DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(deviceEntryFingerprintAuthRepositoryImpl$isRunning$1), this.mainDispatcher);
    }
}
