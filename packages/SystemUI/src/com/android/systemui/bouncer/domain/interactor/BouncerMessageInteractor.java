package com.android.systemui.bouncer.domain.interactor;

import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.kotlin.Septuple;
import kotlin.Pair;
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
            boolean booleanValue = ((Boolean) obj).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.Z$0 = booleanValue;
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
            ((BouncerMessageRepositoryImpl) BouncerMessageInteractor.this.repository).setMessage((BouncerMessageModel) this.L$0);
            return Unit.INSTANCE;
        }
    }

    public BouncerMessageInteractor(BouncerMessageRepository bouncerMessageRepository, UserRepository userRepository, CountDownTimerUtil countDownTimerUtil, KeyguardUpdateMonitor keyguardUpdateMonitor, final TrustRepository trustRepository, final BiometricSettingsRepository biometricSettingsRepository, SystemPropertiesHelper systemPropertiesHelper, PrimaryBouncerInteractor primaryBouncerInteractor, CoroutineScope coroutineScope, FacePropertyRepository facePropertyRepository, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryFaceAuthRepository deviceEntryFaceAuthRepository, KeyguardSecurityModel keyguardSecurityModel) {
        this.repository = bouncerMessageRepository;
        this.userRepository = userRepository;
        this.countDownTimerUtil = countDownTimerUtil;
        this.systemPropertiesHelper = systemPropertiesHelper;
        this.facePropertyRepository = facePropertyRepository;
        this.securityModel = keyguardSecurityModel;
        ChannelFlowTransformLatest channelFlowTransformLatest = deviceEntryFingerprintAuthInteractor.isFingerprintCurrentlyAllowedOnBouncer;
        SharingStarted.Companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(channelFlowTransformLatest, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
        this.isFingerprintAuthCurrentlyAllowedOnBouncer = stateIn;
        ?? r8 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$kumCallback$1

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
            public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                BouncerMessageModel message;
                BouncerMessageInteractor bouncerMessageInteractor = BouncerMessageInteractor.this;
                BouncerMessageRepository bouncerMessageRepository2 = bouncerMessageInteractor.repository;
                int i = biometricSourceType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[biometricSourceType.ordinal()];
                if (i != 1) {
                    ReadonlyStateFlow readonlyStateFlow = bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer;
                    if (i != 2) {
                        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
                        AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                        boolean booleanValue = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue();
                        bouncerMessageStrings.getClass();
                        message = BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.defaultMessage(authModel, booleanValue));
                    } else {
                        BouncerMessageStrings bouncerMessageStrings2 = BouncerMessageStrings.INSTANCE;
                        AuthenticationMethodModel authModel2 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                        boolean booleanValue2 = ((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue();
                        bouncerMessageStrings2.getClass();
                        Integer valueOf = Integer.valueOf(R.string.bouncer_face_not_recognized);
                        Pair pair = BouncerMessageStrings.EmptyMessage;
                        if (booleanValue2) {
                            if (Intrinsics.areEqual(authModel2, AuthenticationMethodModel.Pattern.INSTANCE)) {
                                pair = new Pair(Integer.valueOf(BouncerMessageStrings.patternDefaultMessage(true)), valueOf);
                            } else if (Intrinsics.areEqual(authModel2, AuthenticationMethodModel.Password.INSTANCE)) {
                                pair = new Pair(Integer.valueOf(BouncerMessageStrings.passwordDefaultMessage(true)), valueOf);
                            } else if (Intrinsics.areEqual(authModel2, AuthenticationMethodModel.Pin.INSTANCE)) {
                                pair = new Pair(Integer.valueOf(BouncerMessageStrings.pinDefaultMessage(true)), valueOf);
                            }
                        } else if (authModel2.equals(AuthenticationMethodModel.Pattern.INSTANCE)) {
                            pair = new Pair(valueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pattern));
                        } else if (authModel2.equals(AuthenticationMethodModel.Password.INSTANCE)) {
                            pair = new Pair(valueOf, Integer.valueOf(R.string.kg_bio_try_again_or_password));
                        } else if (authModel2.equals(AuthenticationMethodModel.Pin.INSTANCE)) {
                            pair = new Pair(valueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pin));
                        }
                        message = BouncerMessageInteractorKt.toMessage(pair);
                    }
                } else {
                    BouncerMessageStrings bouncerMessageStrings3 = BouncerMessageStrings.INSTANCE;
                    AuthenticationMethodModel authModel3 = BouncerMessageInteractorKt.toAuthModel(bouncerMessageInteractor.getCurrentSecurityMode());
                    bouncerMessageStrings3.getClass();
                    message = BouncerMessageInteractorKt.toMessage(authModel3.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_fp_not_recognized), Integer.valueOf(R.string.kg_bio_try_again_or_pattern)) : authModel3.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_fp_not_recognized), Integer.valueOf(R.string.kg_bio_try_again_or_password)) : authModel3.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_fp_not_recognized), Integer.valueOf(R.string.kg_bio_try_again_or_pin)) : BouncerMessageStrings.EmptyMessage);
                }
                ((BouncerMessageRepositoryImpl) bouncerMessageRepository2).setMessage(message);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                BouncerMessageInteractor bouncerMessageInteractor = BouncerMessageInteractor.this;
                ((BouncerMessageRepositoryImpl) bouncerMessageInteractor.repository).setMessage(bouncerMessageInteractor.getDefaultMessage());
            }
        };
        this.kumCallback = r8;
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled, biometricSettingsRepositoryImpl.isFingerprintEnrolledAndEnabled, new BouncerMessageInteractorKt$or$1(null));
        ReadonlyStateFlow readonlyStateFlow = primaryBouncerInteractor.lastShownSecurityMode;
        ReadonlyStateFlow isCurrentUserTrustManaged = ((TrustRepositoryImpl) trustRepository).isCurrentUserTrustManaged();
        StateFlowImpl stateFlowImpl = ((DeviceEntryFaceAuthRepositoryImpl) deviceEntryFaceAuthRepository).isLockedOut;
        final Flow[] flowArr = {readonlyStateFlow, biometricSettingsRepositoryImpl.authenticationFlags, isCurrentUserTrustManaged, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, deviceEntryFingerprintAuthInteractor.isLockedOut, stateFlowImpl, stateIn};
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
                        boolean booleanValue = ((Boolean) objArr[6]).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj7).booleanValue();
                        boolean booleanValue3 = ((Boolean) obj6).booleanValue();
                        KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) obj2;
                        Septuple septuple = new Septuple(securityMode, (AuthenticationFlags) obj3, Boolean.valueOf(((Boolean) obj4).booleanValue()), Boolean.valueOf(((Boolean) obj5).booleanValue()), Boolean.valueOf(booleanValue3), Boolean.valueOf(booleanValue2), Boolean.valueOf(booleanValue));
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$combine$1.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        Method dump skipped, instructions count: 1360
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, trustRepository, this, biometricSettingsRepository), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.bouncerMessage = ((BouncerMessageRepositoryImpl) bouncerMessageRepository).bouncerMessage;
        keyguardUpdateMonitor.registerCallback(r8);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(primaryBouncerInteractor.isShowing, flow2, new AnonymousClass1(null))), new AnonymousClass2(null)), coroutineScope);
    }

    public final KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.securityModel.getSecurityMode(((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id);
    }

    public final BouncerMessageModel getDefaultMessage() {
        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
        AuthenticationMethodModel authModel = BouncerMessageInteractorKt.toAuthModel(getCurrentSecurityMode());
        boolean booleanValue = ((Boolean) this.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue();
        bouncerMessageStrings.getClass();
        return BouncerMessageInteractorKt.toMessage(BouncerMessageStrings.defaultMessage(authModel, booleanValue));
    }
}
