package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.keyguard.shared.model.DevicePosture;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class BiometricSettingsRepositoryImpl implements BiometricSettingsRepository, Dumpable {
    public final ReadonlyStateFlow areBiometricsEnabledForDeviceEntryFromUserSetting;
    public final ChannelFlowTransformLatest authenticationFlags;
    public final Map biometricsEnabledForUser = new LinkedHashMap();
    public final Flow devicePolicyChangedForAllUsers;
    public final BiometricSettingsRepositoryImpl$special$$inlined$map$1 isCurrentUserInLockdown;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFaceAuthCurrentlyAllowed;
    public final ReadonlyStateFlow isFaceAuthEnrolledAndEnabled;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isFaceAuthSupportedInCurrentPosture;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFaceAuthenticationEnabled;
    public final ChannelFlowTransformLatest isFaceBiometricsAllowed;
    public final ChannelFlowTransformLatest isFaceEnabledForCurrentUser;
    public final ChannelFlowTransformLatest isFaceEnrolled;
    public final ReadonlyStateFlow isFingerprintAuthCurrentlyAllowed;
    public final ChannelFlowTransformLatest isFingerprintBiometricAllowed;
    public final ChannelFlowTransformLatest isFingerprintEnabledByDevicePolicy;
    public final ChannelFlowTransformLatest isFingerprintEnabledForCurrentUser;
    public final ChannelFlowTransformLatest isFingerprintEnrolled;
    public final ReadonlyStateFlow isFingerprintEnrolledAndEnabled;
    public final ReadonlyStateFlow isNonStrongBiometricAllowed;
    public final ReadonlyStateFlow isStrongBiometricAllowed;
    public final UserRepository userRepository;

    /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isFaceAuthSupportedInCurrentPosture value changed to: ", "BiometricsRepositoryImpl", this.Z$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1] */
    public BiometricSettingsRepositoryImpl(Context context, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, AuthController authController, UserRepository userRepository, DevicePolicyManager devicePolicyManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BiometricManager biometricManager, DevicePostureRepository devicePostureRepository, FacePropertyRepository facePropertyRepository, FingerprintPropertyRepository fingerprintPropertyRepository, MobileConnectionsRepository mobileConnectionsRepository, DumpManager dumpManager) throws Resources.NotFoundException {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.userRepository = userRepository;
        new LinkedHashMap();
        new LinkedHashMap();
        StrongAuthTracker strongAuthTracker = new StrongAuthTracker(userRepository, context);
        final ChannelFlowTransformLatest channelFlowTransformLatest = strongAuthTracker.currentUserAuthFlags;
        this.isCurrentUserInLockdown = new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((AuthenticationFlags) obj).isInUserLockdown);
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
                Object objCollect = channelFlowTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.authenticationFlags = strongAuthTracker.currentUserAuthFlags;
        Log.d("BiometricsRepositoryImpl", "Registering StrongAuthTracker");
        lockPatternUtils.registerStrongAuthTracker(strongAuthTracker);
        dumpManager.registerDumpable(this);
        DevicePosture.Companion companion = DevicePosture.Companion;
        int integer = context.getResources().getInteger(R.integer.config_face_auth_supported_posture);
        companion.getClass();
        final DevicePosture posture = DevicePosture.Companion.toPosture(integer);
        if (posture == DevicePosture.UNKNOWN) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
        } else {
            final Flow currentDevicePosture = ((DevicePostureRepositoryImpl) devicePostureRepository).getCurrentDevicePosture();
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2

                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ DevicePosture $configFaceAuthSupportedPosture$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DevicePosture devicePosture) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$configFaceAuthSupportedPosture$inlined = devicePosture;
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
                            Boolean boolValueOf = Boolean.valueOf(((DevicePosture) obj) == this.$configFaceAuthSupportedPosture$inlined);
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
                    Object objCollect = currentDevicePosture.collect(new AnonymousClass2(flowCollector, posture), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        }
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new AnonymousClass2(null));
        this.isFaceAuthSupportedInCurrentPosture = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = userRepositoryImpl.selectedUserInfo;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((UserInfo) obj).id);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = userRepositoryImpl$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        Flow flowBroadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), UserHandle.ALL, 12);
        this.devicePolicyChangedForAllUsers = flowBroadcastFlow$default;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(flowDistinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1(null, authController));
        this.isFingerprintEnrolled = channelFlowTransformLatestTransformLatest;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(flowDistinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2(null, authController));
        this.isFaceEnrolled = channelFlowTransformLatestTransformLatest2;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest3 = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3(null, this));
        this.isFingerprintEnabledForCurrentUser = channelFlowTransformLatestTransformLatest3;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest4 = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4(null, this));
        this.isFaceEnabledForCurrentUser = channelFlowTransformLatestTransformLatest4;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(channelFlowTransformLatestTransformLatest4, FlowKt.distinctUntilChanged(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(devicePolicyManager, this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged, flowBroadcastFlow$default, new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1(devicePolicyManager, null))), coroutineDispatcher)), new BiometricSettingsRepositoryImpl$isFaceAuthenticationEnabled$1(null));
        this.isFaceAuthenticationEnabled = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.callbackFlow(new BiometricSettingsRepositoryImpl$areBiometricsEnabledForDeviceEntryFromUserSetting$1(biometricManager, null)), new BiometricSettingsRepositoryImpl$areBiometricsEnabledForDeviceEntryFromUserSetting$2(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.areBiometricsEnabledForDeviceEntryFromUserSetting = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, coroutineScope, startedEagerly, new Triple(0, bool, 0));
        this.isStrongBiometricAllowed = FlowKt.stateIn(strongAuthTracker.isStrongBiometricAllowed, coroutineScope, startedEagerly, Boolean.valueOf(strongAuthTracker.isBiometricAllowedForUser(true, userRepositoryImpl.getSelectedUserInfo().id)));
        this.isNonStrongBiometricAllowed = FlowKt.stateIn(strongAuthTracker.isNonStrongBiometricAllowed, coroutineScope, startedEagerly, Boolean.valueOf(strongAuthTracker.isBiometricAllowedForUser(false, userRepositoryImpl.getSelectedUserInfo().id)));
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest5 = FlowKt.transformLatest(((FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository).strength, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$5(null, this));
        this.isFingerprintBiometricAllowed = channelFlowTransformLatestTransformLatest5;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest6 = FlowKt.transformLatest(((FacePropertyRepositoryImpl) facePropertyRepository).sensorInfo, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$6(null, this));
        this.isFaceBiometricsAllowed = channelFlowTransformLatestTransformLatest6;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest7 = FlowKt.transformLatest(flowDistinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$7(null, this, coroutineDispatcher, devicePolicyManager));
        this.isFingerprintEnabledByDevicePolicy = channelFlowTransformLatestTransformLatest7;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(BiometricSettingsRepositoryKt.access$and(BiometricSettingsRepositoryKt.access$and(channelFlowTransformLatestTransformLatest, channelFlowTransformLatestTransformLatest3), channelFlowTransformLatestTransformLatest7), coroutineScope, startedEagerly, bool);
        this.isFingerprintEnrolledAndEnabled = readonlyStateFlowStateIn;
        this.isFingerprintAuthCurrentlyAllowed = FlowKt.stateIn(BiometricSettingsRepositoryKt.access$and(readonlyStateFlowStateIn, channelFlowTransformLatestTransformLatest5), coroutineScope, startedEagerly, bool);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1Access$and = BiometricSettingsRepositoryKt.access$and(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, channelFlowTransformLatestTransformLatest2);
        final Flow flowIsAnySimSecure = mobileConnectionsRepository.isAnySimSecure();
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(BiometricSettingsRepositoryKt.access$and(flowKt__ZipKt$combine$$inlined$unsafeFlow$1Access$and, new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryKt$isFalse$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryKt$isFalse$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryKt$isFalse$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                Object objCollect = flowIsAnySimSecure.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineScope, startedEagerly, bool);
        this.isFaceAuthEnrolledAndEnabled = readonlyStateFlowStateIn2;
        this.isFaceAuthCurrentlyAllowed = BiometricSettingsRepositoryKt.access$and(BiometricSettingsRepositoryKt.access$and(readonlyStateFlowStateIn2, channelFlowTransformLatestTransformLatest6), flowKt__TransformKt$onEach$$inlined$unsafeTransform$1);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("isFingerprintEnrolledAndEnabled=", this.isFingerprintEnrolledAndEnabled.$$delegate_0.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("isFingerprintAuthCurrentlyAllowed=", this.isFingerprintAuthCurrentlyAllowed.$$delegate_0.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("isNonStrongBiometricAllowed=", this.isNonStrongBiometricAllowed.$$delegate_0.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("isStrongBiometricAllowed=", this.isStrongBiometricAllowed.$$delegate_0.getValue(), printWriter);
    }
}
