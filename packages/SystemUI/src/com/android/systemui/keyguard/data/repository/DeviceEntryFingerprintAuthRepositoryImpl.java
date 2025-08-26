package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class DeviceEntryFingerprintAuthRepositoryImpl implements DeviceEntryFingerprintAuthRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthController authController;
    public final ReadonlyStateFlow isEngaged;
    public final Lazy isLockedOut$delegate;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final CoroutineDispatcher mainDispatcher;
    public final ReadonlySharedFlow shouldUpdateIndicatorVisibility;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = DeviceEntryFingerprintAuthRepositoryImpl.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1$callback$1
                    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                    public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                        if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                            ChannelExt channelExt = ChannelExt.INSTANCE;
                            Boolean boolValueOf = Boolean.valueOf(z);
                            channelExt.getClass();
                            ChannelExt.trySendWithFailureLogging(producerScope, boolValueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "Fingerprint running state changed");
                        }
                    }
                };
                DeviceEntryFingerprintAuthRepositoryImpl.this.keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
                ChannelExt channelExt = ChannelExt.INSTANCE;
                Boolean boolValueOf = Boolean.valueOf(DeviceEntryFingerprintAuthRepositoryImpl.this.keyguardUpdateMonitor.isFingerprintDetectionRunning());
                channelExt.getClass();
                ChannelExt.trySendWithFailureLogging(producerScope, boolValueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "Initial fingerprint running state");
                DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(DeviceEntryFingerprintAuthRepositoryImpl.this, keyguardUpdateMonitorCallback, 5);
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, deviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public DeviceEntryFingerprintAuthRepositoryImpl(AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.authController = authController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainDispatcher = coroutineDispatcher;
        this.isLockedOut$delegate = LazyKt__LazyJVMKt.lazy(new DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(coroutineScope, this, 0));
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
                        Boolean bool = ((FingerprintAuthenticationStatus) obj).isEngaged;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(bool, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = authenticationStatus.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl$special$$inlined$map$2

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
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) obj).booleanValue());
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
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.isEngaged = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.shouldUpdateIndicatorVisibility = FlowKt.shareIn(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFingerprintAuthRepositoryImpl$shouldUpdateIndicatorVisibility$1(this, null)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
    }

    public final Flow getAuthenticationStatus() {
        return FlowKt.buffer$default(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1(this, null)), this.mainDispatcher), 4, 2);
    }

    public final Flow getAvailableFpSensorType() {
        return this.authController.mAllFingerprintAuthenticatorsRegistered ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(getFpSensorType()) : FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1(this, null));
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
        if (list2 == null) {
            return null;
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            if (((FingerprintSensorPropertiesInternal) it.next()).sensorType == 1) {
                return BiometricType.REAR_FINGERPRINT;
            }
        }
        return null;
    }

    public final Flow isRunning() {
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null)), this.mainDispatcher);
    }
}
