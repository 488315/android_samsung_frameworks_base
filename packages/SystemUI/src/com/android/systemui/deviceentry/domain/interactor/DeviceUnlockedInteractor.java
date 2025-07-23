package com.android.systemui.deviceentry.domain.interactor;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.TrustInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceUnlockedInteractor extends ExclusiveActivatable {
    public static final String TAG;
    public final AuthenticationInteractor authenticationInteractor;
    public final DeviceEntryBiometricSettingsInteractor biometricSettingsInteractor;
    public final ChannelFlowTransformLatest deviceEntryRestrictionReason;
    public final ChannelLimitedFlowMerge deviceUnlockSource;
    public final ReadonlyStateFlow deviceUnlockStatus;
    public final StateFlow faceEnrolledAndEnabled;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 faceOrFingerprintOrTrustEnabled;
    public final StateFlow fingerprintEnrolledAndEnabled;
    public final DeviceUnlockedInteractor$special$$inlined$map$5 isInLockdown;
    public final KeyguardInteractor keyguardInteractor;
    public final BufferedChannel lockNowRequests;
    public final PowerInteractor powerInteractor;
    public final DeviceEntryRepository repository;
    public final SystemPropertiesHelper systemPropertiesHelper;
    public final TableLogBuffer tableLogBuffer;
    public final ReadonlyStateFlow trustAgentEnabled;
    public final TrustInteractor trustInteractor;
    public final UserAwareSecureSettingsRepository userAwareSecureSettingsRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CancelDelayedLock implements LockEvent {
        public final String debugReason;

        public CancelDelayedLock(String str) {
            this.debugReason = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof CancelDelayedLock) && Intrinsics.areEqual(this.debugReason, ((CancelDelayedLock) obj).debugReason);
        }

        @Override // com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.LockEvent
        public final String getDebugReason() {
            return this.debugReason;
        }

        public final int hashCode() {
            return this.debugReason.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("CancelDelayedLock(debugReason="), this.debugReason, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getREBOOT_MAINLINE_UPDATE$annotations() {
        }

        public static /* synthetic */ void getSYS_BOOT_REASON_PROP$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface LockEvent {
        String getDebugReason();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LockImmediately implements LockEvent {
        public final String debugReason;

        public LockImmediately(String str) {
            this.debugReason = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof LockImmediately) && Intrinsics.areEqual(this.debugReason, ((LockImmediately) obj).debugReason);
        }

        @Override // com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.LockEvent
        public final String getDebugReason() {
            return this.debugReason;
        }

        public final int hashCode() {
            return this.debugReason.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("LockImmediately(debugReason="), this.debugReason, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LockWithDelay implements LockEvent {
        public final String debugReason;

        public LockWithDelay(String str) {
            this.debugReason = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof LockWithDelay) && Intrinsics.areEqual(this.debugReason, ((LockWithDelay) obj).debugReason);
        }

        @Override // com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.LockEvent
        public final String getDebugReason() {
            return this.debugReason;
        }

        public final int hashCode() {
            return this.debugReason.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("LockWithDelay(debugReason="), this.debugReason, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceEntryRestrictionReason.values().length];
            try {
                iArr[DeviceEntryRestrictionReason.UserLockdown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.PolicyLockdown.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceReboot.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.BouncerLockedOut.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.AdaptiveAuthRequest.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongBiometricsSecurityTimeout.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.TrustAgentDisabled.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.StrongBiometricsLockedOut.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.SecurityTimeout.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceMainlineUpdate.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.UnattendedUpdate.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongFaceLockedOut.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        TAG = "DeviceUnlockedInteractor";
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5] */
    public DeviceUnlockedInteractor(AuthenticationInteractor authenticationInteractor, DeviceEntryRepository deviceEntryRepository, TrustInteractor trustInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, PowerInteractor powerInteractor, DeviceEntryBiometricSettingsInteractor deviceEntryBiometricSettingsInteractor, SystemPropertiesHelper systemPropertiesHelper, UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, KeyguardInteractor keyguardInteractor, TableLogBuffer tableLogBuffer) {
        this.authenticationInteractor = authenticationInteractor;
        this.repository = deviceEntryRepository;
        this.trustInteractor = trustInteractor;
        this.powerInteractor = powerInteractor;
        this.biometricSettingsInteractor = deviceEntryBiometricSettingsInteractor;
        this.systemPropertiesHelper = systemPropertiesHelper;
        this.userAwareSecureSettingsRepository = userAwareSecureSettingsRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.tableLogBuffer = tableLogBuffer;
        final DeviceEntryFingerprintAuthInteractor$special$$inlined$filterIsInstance$4 deviceEntryFingerprintAuthInteractor$special$$inlined$filterIsInstance$4 = deviceEntryFingerprintAuthInteractor.fingerprintSuccess;
        Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1$2$1
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
                        com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus r5 = (com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus) r5
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockSource$Fingerprint r5 = com.android.systemui.deviceentry.shared.model.DeviceUnlockSource.Fingerprint.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final StateFlow isAuthenticated = deviceEntryFaceAuthInteractor.isAuthenticated();
        final Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow3 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceUnlockedInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceUnlockedInteractor deviceUnlockedInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceUnlockedInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r5 = r4.this$0
                        com.android.systemui.deviceentry.data.repository.DeviceEntryRepository r5 = r5.repository
                        com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl r5 = (com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl) r5
                        kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.isBypassEnabled
                        kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
                        java.lang.Object r5 = r5.getValue()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L50
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockSource$FaceWithBypass r5 = com.android.systemui.deviceentry.shared.model.DeviceUnlockSource.FaceWithBypass.INSTANCE
                        goto L52
                    L50:
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockSource$FaceWithoutBypass r5 = com.android.systemui.deviceentry.shared.model.DeviceUnlockSource.FaceWithoutBypass.INSTANCE
                    L52:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5d
                        return r1
                    L5d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ReadonlyStateFlow readonlyStateFlow = trustInteractor.isTrusted;
        final Flow flow4 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow5 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockSource$TrustAgent r5 = com.android.systemui.deviceentry.shared.model.DeviceUnlockSource.TrustAgent.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ReadonlySharedFlow readonlySharedFlow = authenticationInteractor.onAuthenticationResult;
        final Flow flow6 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.deviceUnlockSource = FlowKt.merge(flow, flow3, flow5, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockSource$BouncerInput r5 = com.android.systemui.deviceentry.shared.model.DeviceUnlockSource.BouncerInput.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        StateFlow stateFlow = deviceEntryBiometricSettingsInteractor.isFaceAuthEnrolledAndEnabled;
        this.faceEnrolledAndEnabled = stateFlow;
        StateFlow stateFlow2 = deviceEntryBiometricSettingsInteractor.isFingerprintAuthEnrolledAndEnabled;
        this.fingerprintEnrolledAndEnabled = stateFlow2;
        ReadonlyStateFlow readonlyStateFlow2 = trustInteractor.isEnrolledAndEnabled;
        this.trustAgentEnabled = readonlyStateFlow2;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(stateFlow, stateFlow2, readonlyStateFlow2, DeviceUnlockedInteractor$faceOrFingerprintOrTrustEnabled$3.INSTANCE);
        this.faceOrFingerprintOrTrustEnabled = combine;
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(combine, new DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1(null, this, deviceEntryFaceAuthInteractor, deviceEntryFingerprintAuthInteractor));
        this.deviceEntryRestrictionReason = transformLatest;
        this.isInLockdown = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceUnlockedInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceUnlockedInteractor deviceUnlockedInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceUnlockedInteractor;
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L62
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason r5 = (com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason) r5
                        java.lang.String r6 = com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.TAG
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r6 = r4.this$0
                        r6.getClass()
                        if (r5 != 0) goto L3f
                        r5 = -1
                        goto L47
                    L3f:
                        int[] r6 = com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                    L47:
                        switch(r5) {
                            case -1: goto L52;
                            case 0: goto L4a;
                            case 1: goto L50;
                            case 2: goto L50;
                            case 3: goto L52;
                            case 4: goto L52;
                            case 5: goto L52;
                            case 6: goto L52;
                            case 7: goto L52;
                            case 8: goto L52;
                            case 9: goto L52;
                            case 10: goto L52;
                            case 11: goto L52;
                            case 12: goto L52;
                            default: goto L4a;
                        }
                    L4a:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    L50:
                        r5 = r3
                        goto L53
                    L52:
                        r5 = 0
                    L53:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L62
                        return r1
                    L62:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.deviceUnlockStatus = FlowKt.asStateFlow(((DeviceEntryRepositoryImpl) deviceEntryRepository).deviceUnlockStatus);
        this.lockNowRequests = ChannelKt.Channel$default(0, null, null, 7);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleLockAndUnlockEvents(com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6.getClass()
            boolean r0 = r7 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$1
            r0.<init>(r6, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            java.lang.String r3 = "stopped watching for lock and unlock events"
            r4 = 1
            java.lang.String r5 = com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.TAG
            if (r2 == 0) goto L39
            if (r2 != r4) goto L31
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L2f
            goto L51
        L2f:
            r6 = move-exception
            goto L59
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.String r7 = "started watching for lock and unlock events"
            android.util.Log.d(r5, r7)     // Catch: java.lang.Throwable -> L2f
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$2 r7 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockAndUnlockEvents$2     // Catch: java.lang.Throwable -> L2f
            r2 = 0
            r7.<init>(r6, r2)     // Catch: java.lang.Throwable -> L2f
            r0.label = r4     // Catch: java.lang.Throwable -> L2f
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)     // Catch: java.lang.Throwable -> L2f
            if (r7 != r1) goto L51
            return r1
        L51:
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7     // Catch: java.lang.Throwable -> L2f
            android.util.Log.d(r5, r3)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L59:
            android.util.Log.d(r5, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.access$handleLockAndUnlockEvents(com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:0|1|(2:3|(8:5|6|(1:(1:(6:10|11|12|13|14|15)(2:20|21))(1:22))(4:31|(1:33)(2:34|(3:36|(1:38)|27)(2:39|(2:41|42)))|14|15)|23|24|25|(4:28|13|14|15)|27))|43|6|(0)(0)|23|24|25|(0)|27|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00eb, code lost:
    
        r5 = r1;
        r0 = r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$onLockEvent(com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r17, com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.LockEvent r18, kotlin.coroutines.Continuation r19) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.access$onLockEvent(com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor, com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockEvent, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0062, code lost:
    
        if (r15 == r1) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object lockDelay(kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.lockDelay(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L51
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2 r6 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.label = r4
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)
            if (r5 != r1) goto L48
            goto L50
        L48:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r5 != r1) goto L51
        L50:
            return r1
        L51:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Activator implements CoreStartable {
        public final DeviceUnlockedInteractor interactor;

        public Activator(CoroutineScope coroutineScope, DeviceUnlockedInteractor deviceUnlockedInteractor) {
            this.interactor = deviceUnlockedInteractor;
        }

        @Override // com.android.systemui.CoreStartable
        public final void start() {
        }
    }
}
