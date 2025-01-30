package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.hardware.biometrics.BiometricManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.keyguard.shared.model.DevicePosture;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl implements BiometricSettingsRepository, Dumpable {
    public final ChannelFlowTransformLatest authenticationFlags;
    public final Map biometricsEnabledForUser = new LinkedHashMap();
    public final Flow devicePolicyChangedForAllUsers;
    public final BiometricSettingsRepositoryImpl$special$$inlined$map$1 isCurrentUserInLockdown;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isFaceAuthSupportedInCurrentPosture;
    public final ReadonlyStateFlow isFaceEnabledByBiometricsManager;
    public final ChannelFlowTransformLatest isFaceEnabledByBiometricsManagerForCurrentUser;
    public final Flow isFaceEnabledByDevicePolicy;
    public final ChannelFlowTransformLatest isFaceEnrolled;
    public final ReadonlyStateFlow isFingerprintEnabledByDevicePolicy;
    public final ReadonlyStateFlow isFingerprintEnrolled;
    public final ReadonlyStateFlow isNonStrongBiometricAllowed;
    public final ReadonlyStateFlow isStrongBiometricAllowed;
    public final UserRepository userRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$2", m277f = "BiometricSettingsRepository.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$2 */
    public static final class C15902 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public C15902(Continuation<? super C15902> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C15902 c15902 = new C15902(continuation);
            c15902.Z$0 = ((Boolean) obj).booleanValue();
            return c15902;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C15902) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isFaceAuthSupportedInCurrentPosture value changed to: ", this.Z$0, "BiometricsRepositoryImpl");
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1] */
    public BiometricSettingsRepositoryImpl(Context context, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, AuthController authController, UserRepository userRepository, DevicePolicyManager devicePolicyManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BiometricManager biometricManager, DevicePostureRepository devicePostureRepository, DumpManager dumpManager) {
        Flow flow;
        this.userRepository = userRepository;
        StrongAuthTracker strongAuthTracker = new StrongAuthTracker(userRepository, context);
        final ChannelFlowTransformLatest channelFlowTransformLatest = strongAuthTracker.currentUserAuthFlags;
        this.isCurrentUserInLockdown = new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1$2 */
            public final class C15872 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1$2", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C15872.this.emit(null, this);
                    }
                }

                public C15872(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((AuthenticationFlags) obj).isInUserLockdown);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C15872(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
            flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
        } else {
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            DevicePostureRepositoryImpl$currentDevicePosture$1 devicePostureRepositoryImpl$currentDevicePosture$1 = new DevicePostureRepositoryImpl$currentDevicePosture$1((DevicePostureRepositoryImpl) devicePostureRepository, null);
            conflatedCallbackFlow.getClass();
            final Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(devicePostureRepositoryImpl$currentDevicePosture$1);
            flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2$2 */
                public final class C15882 implements FlowCollector {
                    public final /* synthetic */ DevicePosture $configFaceAuthSupportedPosture$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$2$2", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C15882.this.emit(null, this);
                        }
                    }

                    public C15882(FlowCollector flowCollector, DevicePosture devicePosture) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$configFaceAuthSupportedPosture$inlined = devicePosture;
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
                                    Boolean valueOf = Boolean.valueOf(((DevicePosture) obj) == this.$configFaceAuthSupportedPosture$inlined);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
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
                    Object collect = Flow.this.collect(new C15882(flowCollector, posture), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        }
        this.isFaceAuthSupportedInCurrentPosture = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new C15902(null));
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = userRepositoryImpl.selectedUserInfo;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3$2 */
            public final class C15892 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$3$2", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C15892.this.emit(null, this);
                    }
                }

                public C15892(FlowCollector flowCollector) {
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
                                Integer num = new Integer(((UserInfo) obj).id);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C15892(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), UserHandle.ALL, 0, null, 12);
        this.devicePolicyChangedForAllUsers = broadcastFlow$default;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(distinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$1(null, authController));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Integer valueOf = Integer.valueOf(userRepositoryImpl.getSelectedUserInfo().id);
        Boolean bool = Boolean.FALSE;
        this.isFingerprintEnrolled = FlowKt.stateIn(transformLatest, coroutineScope, startedEagerly, Boolean.valueOf(((Boolean) ((HashMap) authController.mFpEnrolledForUser).getOrDefault(valueOf, bool)).booleanValue()));
        this.isFaceEnrolled = FlowKt.transformLatest(distinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$2(null, authController));
        this.isFaceEnabledByBiometricsManagerForCurrentUser = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3(null, this));
        this.isFaceEnabledByDevicePolicy = FlowKt.distinctUntilChanged(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$2(devicePolicyManager, this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, broadcastFlow$default, new BiometricSettingsRepositoryImpl$isFaceEnabledByDevicePolicy$1(devicePolicyManager, null))), coroutineDispatcher));
        ConflatedCallbackFlow conflatedCallbackFlow3 = ConflatedCallbackFlow.INSTANCE;
        C1591x823f4eed c1591x823f4eed = new C1591x823f4eed(biometricManager, null);
        conflatedCallbackFlow3.getClass();
        this.isFaceEnabledByBiometricsManager = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(ConflatedCallbackFlow.conflatedCallbackFlow(c1591x823f4eed), new C1593x823f4eee(this, null)), coroutineScope, startedEagerly, new Pair(0, bool));
        this.isStrongBiometricAllowed = FlowKt.stateIn(strongAuthTracker.isStrongBiometricAllowed, coroutineScope, startedEagerly, Boolean.valueOf(strongAuthTracker.isBiometricAllowedForUser(true, userRepositoryImpl.getSelectedUserInfo().id)));
        this.isNonStrongBiometricAllowed = FlowKt.stateIn(strongAuthTracker.isNonStrongBiometricAllowed, coroutineScope, startedEagerly, Boolean.valueOf(strongAuthTracker.isBiometricAllowedForUser(false, userRepositoryImpl.getSelectedUserInfo().id)));
        this.isFingerprintEnabledByDevicePolicy = FlowKt.stateIn(FlowKt.transformLatest(distinctUntilChanged, new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$4(null, this, coroutineDispatcher, devicePolicyManager)), coroutineScope, startedEagerly, Boolean.valueOf((devicePolicyManager.getKeyguardDisabledFeatures(null, userRepositoryImpl.getSelectedUserInfo().id) & 32) == 0));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("isFingerprintEnrolled=" + this.isFingerprintEnrolled.getValue());
        printWriter.println("isStrongBiometricAllowed=" + this.isStrongBiometricAllowed.getValue());
        printWriter.println("isFingerprintEnabledByDevicePolicy=" + this.isFingerprintEnabledByDevicePolicy.getValue());
    }

    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFaceAuthenticationEnabled() {
        BiometricSettingsRepositoryImpl$isFaceAuthenticationEnabled$1 biometricSettingsRepositoryImpl$isFaceAuthenticationEnabled$1 = new BiometricSettingsRepositoryImpl$isFaceAuthenticationEnabled$1(null);
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.isFaceEnabledByBiometricsManagerForCurrentUser, this.isFaceEnabledByDevicePolicy, biometricSettingsRepositoryImpl$isFaceAuthenticationEnabled$1);
    }
}
