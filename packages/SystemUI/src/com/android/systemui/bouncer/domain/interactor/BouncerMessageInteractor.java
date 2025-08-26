package com.android.systemui.bouncer.domain.interactor;

import android.hardware.biometrics.BiometricSourceType;
import android.os.SystemProperties;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.FaceSensorInfo;
import com.android.systemui.biometrics.shared.model.SensorStrength;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.kotlin.Septuple;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes.dex */
public final class BouncerMessageInteractor {
    public final StateFlowImpl bouncerMessage;
    public final CountDownTimerUtil countDownTimerUtil;
    public final FacePropertyRepository facePropertyRepository;
    public final BouncerMessageInteractor$special$$inlined$map$1 initialBouncerMessage;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isAnyBiometricsEnabledAndEnrolled;
    public final ReadonlyStateFlow isFingerprintAuthCurrentlyAllowedOnBouncer;
    public final BouncerMessageInteractor$kumCallback$1 kumCallback;
    public final BouncerMessageRepository repository;
    public final KeyguardSecurityModel securityModel;
    public final SystemPropertiesHelper systemPropertiesHelper;
    public final UserRepository userRepository;

    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.L$0 = (BouncerMessageModel) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            BouncerMessageModel bouncerMessageModel = (BouncerMessageModel) this.L$0;
            if (z) {
                return bouncerMessageModel;
            }
            return null;
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = BouncerMessageInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((BouncerMessageModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BouncerMessageRepository.setMessage$default(BouncerMessageInteractor.this.repository, (BouncerMessageModel) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$kumCallback$1] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public BouncerMessageInteractor(BouncerMessageRepository bouncerMessageRepository, UserRepository userRepository, CountDownTimerUtil countDownTimerUtil, KeyguardUpdateMonitor keyguardUpdateMonitor, TrustRepository trustRepository, final BiometricSettingsRepository biometricSettingsRepository, SystemPropertiesHelper systemPropertiesHelper, PrimaryBouncerInteractor primaryBouncerInteractor, CoroutineScope coroutineScope, FacePropertyRepository facePropertyRepository, KeyguardSecurityModel keyguardSecurityModel, final DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor) {
        this.repository = bouncerMessageRepository;
        this.userRepository = userRepository;
        this.countDownTimerUtil = countDownTimerUtil;
        this.systemPropertiesHelper = systemPropertiesHelper;
        this.facePropertyRepository = facePropertyRepository;
        this.securityModel = keyguardSecurityModel;
        ChannelFlowTransformLatest channelFlowTransformLatest = deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer;
        SharingStarted.Companion.getClass();
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(channelFlowTransformLatest, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
        this.isFingerprintAuthCurrentlyAllowedOnBouncer = readonlyStateFlowStateIn;
        ?? r6 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$kumCallback$1

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[BiometricSourceType.values().length];
                    try {
                        iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[BiometricSourceType.FACE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
                BouncerMessageInteractor bouncerMessageInteractor = this;
                BouncerMessageRepository bouncerMessageRepository2 = bouncerMessageInteractor.repository;
                if (((BouncerMessageRepositoryImpl) bouncerMessageRepository2).messageSource == BiometricSourceType.FACE && i == 20) {
                    BouncerMessageRepository.setMessage$default(bouncerMessageRepository2, bouncerMessageInteractor.getDefaultMessage());
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                BouncerMessageModel message;
                BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
                DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor2 = deviceEntryBiometricsAllowedInteractor;
                if (biometricSourceType == biometricSourceType2 && ((Boolean) deviceEntryBiometricsAllowedInteractor2.isFaceLockedOut.getValue()).booleanValue()) {
                    return;
                }
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && ((Boolean) deviceEntryBiometricsAllowedInteractor2.isFingerprintLockedOut.getValue()).booleanValue()) {
                    return;
                }
                BouncerMessageInteractor bouncerMessageInteractor = this;
                BouncerMessageRepository bouncerMessageRepository2 = bouncerMessageInteractor.repository;
                int i = biometricSourceType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[biometricSourceType.ordinal()];
                if (i != 1) {
                    ReadonlyStateFlow readonlyStateFlow = bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer;
                    if (i != 2) {
                        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
                        AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                        boolean zBooleanValue = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue();
                        bouncerMessageStrings.getClass();
                        message = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.defaultMessage(authModel, zBooleanValue));
                    } else {
                        BouncerMessageStrings bouncerMessageStrings2 = BouncerMessageStrings.INSTANCE;
                        AuthenticationMethodModel authModel2 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                        boolean zBooleanValue2 = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue();
                        bouncerMessageStrings2.getClass();
                        message = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.incorrectFaceInput(authModel2, zBooleanValue2));
                    }
                } else {
                    BouncerMessageStrings bouncerMessageStrings3 = BouncerMessageStrings.INSTANCE;
                    AuthenticationMethodModel authModel3 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                    bouncerMessageStrings3.getClass();
                    message = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.incorrectFingerprintInput(authModel3));
                }
                BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageRepository2;
                bouncerMessageRepositoryImpl._bouncerMessage.setValue(message);
                bouncerMessageRepositoryImpl.messageSource = biometricSourceType;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                BouncerMessageInteractor bouncerMessageInteractor = this;
                BouncerMessageRepository bouncerMessageRepository2 = bouncerMessageInteractor.repository;
                BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageRepository2;
                bouncerMessageRepositoryImpl._bouncerMessage.setValue(bouncerMessageInteractor.getDefaultMessage());
                bouncerMessageRepositoryImpl.messageSource = biometricSourceType;
            }
        };
        this.kumCallback = r6;
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled, biometricSettingsRepositoryImpl.isFingerprintEnrolledAndEnabled, new BouncerMessageInteractorKt$or$1(null));
        this.isAnyBiometricsEnabledAndEnrolled = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        final TrustRepositoryImpl trustRepositoryImpl = (TrustRepositoryImpl) trustRepository;
        final Flow[] flowArr = {primaryBouncerInteractor.lastShownSecurityMode, biometricSettingsRepositoryImpl.authenticationFlags, trustRepositoryImpl.isCurrentUserTrustManaged(), flowKt__ZipKt$combine$$inlined$unsafeFlow$1, deviceEntryBiometricsAllowedInteractor.isFingerprintLockedOut, deviceEntryBiometricsAllowedInteractor.isFaceLockedOut, readonlyStateFlowStateIn};
        final Flow flow = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$combine$1

            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public AnonymousClass3(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        Object obj7 = objArr[5];
                        boolean zBooleanValue = ((Boolean) objArr[6]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj7).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) obj6).booleanValue();
                        KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) obj2;
                        Septuple septuple = new Septuple(securityMode, (AuthenticationFlags) obj3, Boolean.valueOf(((Boolean) obj4).booleanValue()), Boolean.valueOf(((Boolean) obj5).booleanValue()), Boolean.valueOf(zBooleanValue3), Boolean.valueOf(zBooleanValue2), Boolean.valueOf(zBooleanValue));
                        this.label = 1;
                        if (flowCollector.emit(septuple, this) == coroutineSingletons) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        ?? r7 = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ BiometricSettingsRepository $biometricSettingsRepository$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TrustRepository $trustRepository$inlined;
                public final /* synthetic */ BouncerMessageInteractor this$0;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, TrustRepository trustRepository, BouncerMessageInteractor bouncerMessageInteractor, BiometricSettingsRepository biometricSettingsRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$trustRepository$inlined = trustRepository;
                    this.this$0 = bouncerMessageInteractor;
                    this.$biometricSettingsRepository$inlined = biometricSettingsRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    BouncerMessageModel defaultMessage;
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
                        Septuple septuple = (Septuple) obj;
                        AuthenticationFlags authenticationFlags = (AuthenticationFlags) septuple.component2();
                        boolean zBooleanValue = ((Boolean) septuple.component4()).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) septuple.component5()).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) septuple.component6()).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) ((TrustRepositoryImpl) this.$trustRepository$inlined).isCurrentUserTrustUsuallyManaged.$$delegate_0.getValue()).booleanValue();
                        boolean z = zBooleanValue4 || zBooleanValue;
                        BouncerMessageInteractor bouncerMessageInteractor = this.this$0;
                        if (z && authenticationFlags.isPrimaryAuthRequiredAfterReboot) {
                            bouncerMessageInteractor.systemPropertiesHelper.getClass();
                            if (Intrinsics.areEqual(SystemProperties.get("sys.boot.reason.last"), "reboot,mainline_update")) {
                                BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                bouncerMessageStrings.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredForMainlineUpdate(authModel));
                            } else {
                                BouncerMessageStrings bouncerMessageStrings2 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel2 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                bouncerMessageStrings2.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredAfterReboot(authModel2));
                            }
                        } else if (z && authenticationFlags.isPrimaryAuthRequiredAfterTimeout) {
                            BouncerMessageStrings bouncerMessageStrings3 = BouncerMessageStrings.INSTANCE;
                            AuthenticationMethodModel authModel3 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                            bouncerMessageStrings3.getClass();
                            defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredAfterPrimaryAuthTimeout(authModel3));
                        } else if (authenticationFlags.isPrimaryAuthRequiredAfterDpmLockdown) {
                            BouncerMessageStrings bouncerMessageStrings4 = BouncerMessageStrings.INSTANCE;
                            AuthenticationMethodModel authModel4 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                            bouncerMessageStrings4.getClass();
                            defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredAfterAdminLockdown(authModel4));
                        } else if (z && authenticationFlags.isPrimaryAuthRequiredForUnattendedUpdate) {
                            BouncerMessageStrings bouncerMessageStrings5 = BouncerMessageStrings.INSTANCE;
                            AuthenticationMethodModel authModel5 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                            bouncerMessageStrings5.getClass();
                            defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredForUnattendedUpdate(authModel5));
                        } else {
                            BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) this.$biometricSettingsRepository$inlined;
                            if (((Boolean) biometricSettingsRepositoryImpl.isFingerprintEnrolledAndEnabled.$$delegate_0.getValue()).booleanValue() && zBooleanValue2) {
                                BouncerMessageStrings bouncerMessageStrings6 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel6 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                bouncerMessageStrings6.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.class3AuthLockedOut(authModel6));
                            } else if (((Boolean) biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled.$$delegate_0.getValue()).booleanValue() && zBooleanValue3) {
                                FaceSensorInfo faceSensorInfo = (FaceSensorInfo) ((FacePropertyRepositoryImpl) bouncerMessageInteractor.facePropertyRepository).sensorInfo.$$delegate_0.getValue();
                                if ((faceSensorInfo != null ? faceSensorInfo.strength : null) == SensorStrength.STRONG) {
                                    BouncerMessageStrings bouncerMessageStrings7 = BouncerMessageStrings.INSTANCE;
                                    AuthenticationMethodModel authModel7 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                    bouncerMessageStrings7.getClass();
                                    defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.class3AuthLockedOut(authModel7));
                                } else {
                                    BouncerMessageStrings bouncerMessageStrings8 = BouncerMessageStrings.INSTANCE;
                                    AuthenticationMethodModel authModel8 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                    boolean zBooleanValue5 = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                                    bouncerMessageStrings8.getClass();
                                    defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.faceLockedOut(authModel8, zBooleanValue5));
                                }
                            } else if (authenticationFlags.isSomeAuthRequiredAfterAdaptiveAuthRequest) {
                                BouncerMessageStrings bouncerMessageStrings9 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel9 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                boolean zBooleanValue6 = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                                bouncerMessageStrings9.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredAfterAdaptiveAuthRequest(authModel9, zBooleanValue6));
                            } else if (z && authenticationFlags.strongerAuthRequiredAfterNonStrongBiometricsTimeout) {
                                BouncerMessageStrings bouncerMessageStrings10 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel10 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                boolean zBooleanValue7 = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                                bouncerMessageStrings10.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.nonStrongAuthTimeout(authModel10, zBooleanValue7));
                            } else if (zBooleanValue4 && authenticationFlags.someAuthRequiredAfterUserRequest) {
                                BouncerMessageStrings bouncerMessageStrings11 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel11 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                boolean zBooleanValue8 = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                                bouncerMessageStrings11.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.trustAgentDisabled(authModel11, zBooleanValue8));
                            } else if (zBooleanValue4 && authenticationFlags.someAuthRequiredAfterTrustAgentExpired) {
                                BouncerMessageStrings bouncerMessageStrings12 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel12 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                boolean zBooleanValue9 = ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
                                bouncerMessageStrings12.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.trustAgentDisabled(authModel12, zBooleanValue9));
                            } else if (z && authenticationFlags.isInUserLockdown) {
                                BouncerMessageStrings bouncerMessageStrings13 = BouncerMessageStrings.INSTANCE;
                                AuthenticationMethodModel authModel13 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                                bouncerMessageStrings13.getClass();
                                defaultMessage = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.authRequiredAfterUserLockdown(authModel13));
                            } else {
                                defaultMessage = bouncerMessageInteractor.getDefaultMessage();
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(defaultMessage, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, trustRepositoryImpl, this, biometricSettingsRepository), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.initialBouncerMessage = r7;
        this.bouncerMessage = ((BouncerMessageRepositoryImpl) bouncerMessageRepository).bouncerMessage;
        keyguardUpdateMonitor.registerCallback(r6);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(primaryBouncerInteractor.isShowing, r7, new AnonymousClass1(null))), new AnonymousClass2(null)), coroutineScope);
    }

    public final KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.securityModel.getSecurityMode(((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id);
    }

    public final BouncerMessageModel getDefaultMessage() {
        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
        AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(getCurrentSecurityMode());
        boolean zBooleanValue = ((Boolean) this.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
        bouncerMessageStrings.getClass();
        return BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.defaultMessage(authModel, zBooleanValue));
    }

    public final void setFaceAcquisitionMessage(String str) {
        BouncerMessageModel bouncerMessageModelAccess$defaultMessage = BouncerMessageInteractorKt.access$defaultMessage(getCurrentSecurityMode(), str, ((Boolean) this.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue());
        BiometricSourceType biometricSourceType = BiometricSourceType.FACE;
        BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) this.repository;
        bouncerMessageRepositoryImpl._bouncerMessage.setValue(bouncerMessageModelAccess$defaultMessage);
        bouncerMessageRepositoryImpl.messageSource = biometricSourceType;
    }
}
