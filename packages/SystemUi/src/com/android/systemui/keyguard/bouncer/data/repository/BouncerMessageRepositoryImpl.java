package com.android.systemui.keyguard.bouncer.data.repository;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.bouncer.data.factory.BouncerMessageFactory;
import com.android.systemui.keyguard.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl;
import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageRepositoryImpl implements BouncerMessageRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _customMessage;
    public final StateFlowImpl _faceAcquisitionMessage;
    public final StateFlowImpl _fingerprintAcquisitionMessage;
    public final StateFlowImpl _primaryAuthMessage;
    public final BouncerMessageRepositoryImpl$special$$inlined$map$1 authFlagsBasedPromptReason;
    public final Flow authFlagsMessage;
    public final Flow biometricAuthMessage;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 biometricLockedOutMessage;
    public final BouncerMessageFactory bouncerMessageFactory;
    public final StateFlowImpl customMessage;
    public final StateFlowImpl faceAcquisitionMessage;
    public final StateFlowImpl fingerprintAcquisitionMessage;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isAnyBiometricsEnabledAndEnrolled;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFaceEnrolledAndEnabled;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFingerprintEnrolledAndEnabled;
    public final StateFlowImpl primaryAuthMessage;
    public final UserRepository userRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public BouncerMessageRepositoryImpl(TrustRepository trustRepository, BiometricSettingsRepository biometricSettingsRepository, KeyguardUpdateMonitor keyguardUpdateMonitor, BouncerMessageFactory bouncerMessageFactory, UserRepository userRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository) {
        this.bouncerMessageFactory = bouncerMessageFactory;
        this.userRepository = userRepository;
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) biometricSettingsRepository;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(biometricSettingsRepositoryImpl.isFaceAuthenticationEnabled(), biometricSettingsRepositoryImpl.isFaceEnrolled, new BouncerMessageRepositoryKt$and$1(null));
        this.isFaceEnrolledAndEnabled = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(biometricSettingsRepositoryImpl.isFingerprintEnabledByDevicePolicy, biometricSettingsRepositoryImpl.isFingerprintEnrolled, new BouncerMessageRepositoryKt$and$1(null));
        this.isFingerprintEnrolledAndEnabled = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$13 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowKt__ZipKt$combine$$inlined$unsafeFlow$12, new BouncerMessageRepositoryKt$or$1(null));
        this.isAnyBiometricsEnabledAndEnrolled = flowKt__ZipKt$combine$$inlined$unsafeFlow$13;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(biometricSettingsRepositoryImpl.authenticationFlags, ((TrustRepositoryImpl) trustRepository).isCurrentUserTrustManaged(), flowKt__ZipKt$combine$$inlined$unsafeFlow$13, BouncerMessageRepositoryImpl$authFlagsBasedPromptReason$2.INSTANCE);
        final ?? r6 = new Flow() { // from class: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$1$2 */
            public final class C15442 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$1$2", m277f = "BouncerMessageRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C15442.this.emit(null, this);
                    }
                }

                public C15442(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
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
                                Triple triple = (Triple) obj;
                                AuthenticationFlags authenticationFlags = (AuthenticationFlags) triple.component1();
                                boolean booleanValue = ((Boolean) triple.component2()).booleanValue();
                                int i3 = 0;
                                boolean z = booleanValue || ((Boolean) triple.component3()).booleanValue();
                                if (z && authenticationFlags.isPrimaryAuthRequiredAfterReboot) {
                                    i3 = 1;
                                } else if (z && authenticationFlags.isPrimaryAuthRequiredAfterTimeout) {
                                    i3 = 2;
                                } else if (authenticationFlags.isPrimaryAuthRequiredAfterDpmLockdown) {
                                    i3 = 3;
                                } else if ((booleanValue && authenticationFlags.someAuthRequiredAfterUserRequest) || (booleanValue && authenticationFlags.someAuthRequiredAfterTrustAgentExpired)) {
                                    i3 = 8;
                                } else if (z && authenticationFlags.isInUserLockdown) {
                                    i3 = 4;
                                } else if (z && authenticationFlags.primaryAuthRequiredForUnattendedUpdate) {
                                    i3 = 6;
                                } else if (z && authenticationFlags.strongerAuthRequiredAfterNonStrongBiometricsTimeout) {
                                    i3 = 7;
                                }
                                Integer num = new Integer(i3);
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
                Object collect = Flow.this.collect(new C15442(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.authFlagsBasedPromptReason = r6;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        BouncerMessageRepositoryImpl$biometricAuthReason$1 bouncerMessageRepositoryImpl$biometricAuthReason$1 = new BouncerMessageRepositoryImpl$biometricAuthReason$1(keyguardUpdateMonitor, null);
        conflatedCallbackFlow.getClass();
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(bouncerMessageRepositoryImpl$biometricAuthReason$1));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._primaryAuthMessage = MutableStateFlow;
        this.primaryAuthMessage = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._faceAcquisitionMessage = MutableStateFlow2;
        this.faceAcquisitionMessage = MutableStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._fingerprintAcquisitionMessage = MutableStateFlow3;
        this.fingerprintAcquisitionMessage = MutableStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._customMessage = MutableStateFlow4;
        this.customMessage = MutableStateFlow4;
        this.biometricAuthMessage = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BouncerMessageRepositoryImpl$biometricAuthMessage$2(null), new Flow() { // from class: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$2$2 */
            public final class C15452 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerMessageRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$2$2", m277f = "BouncerMessageRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C15452.this.emit(null, this);
                    }
                }

                public C15452(FlowCollector flowCollector, BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerMessageRepositoryImpl;
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
                    BouncerMessageModel createFromPromptReason;
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
                                int intValue = ((Number) obj).intValue();
                                if (intValue == 0) {
                                    createFromPromptReason = null;
                                } else {
                                    BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = this.this$0;
                                    createFromPromptReason = bouncerMessageRepositoryImpl.bouncerMessageFactory.createFromPromptReason(intValue, ((UserRepositoryImpl) bouncerMessageRepositoryImpl.userRepository).getSelectedUserInfo().id);
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(createFromPromptReason, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C15452(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
        this.authFlagsMessage = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BouncerMessageRepositoryImpl$authFlagsMessage$2(null), new Flow() { // from class: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$3$2 */
            public final class C15462 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerMessageRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$3$2", m277f = "BouncerMessageRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        return C15462.this.emit(null, this);
                    }
                }

                public C15462(FlowCollector flowCollector, BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerMessageRepositoryImpl;
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
                    BouncerMessageModel createFromPromptReason;
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
                                int intValue = ((Number) obj).intValue();
                                if (intValue == 0) {
                                    createFromPromptReason = null;
                                } else {
                                    BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = this.this$0;
                                    createFromPromptReason = bouncerMessageRepositoryImpl.bouncerMessageFactory.createFromPromptReason(intValue, ((UserRepositoryImpl) bouncerMessageRepositoryImpl.userRepository).getSelectedUserInfo().id);
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(createFromPromptReason, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C15462(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
        this.biometricLockedOutMessage = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((DeviceEntryFingerprintAuthRepositoryImpl) deviceEntryFingerprintAuthRepository).isLockedOut, ConflatedCallbackFlow.conflatedCallbackFlow(new BouncerMessageRepositoryImpl$faceLockedOut$1(keyguardUpdateMonitor, null)), new BouncerMessageRepositoryImpl$biometricLockedOutMessage$1(this, null));
    }
}
