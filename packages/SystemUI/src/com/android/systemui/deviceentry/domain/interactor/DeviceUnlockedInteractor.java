package com.android.systemui.deviceentry.domain.interactor;

import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockSource;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.TrustInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import java.util.concurrent.CancellationException;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
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

    public interface LockEvent {
        String getDebugReason();
    }

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

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$lockDelay$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        long J$0;
        long J$1;
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
            DeviceUnlockedInteractor deviceUnlockedInteractor = DeviceUnlockedInteractor.this;
            String str = DeviceUnlockedInteractor.TAG;
            return deviceUnlockedInteractor.lockDelay(this);
        }
    }

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C08621 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08621(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DeviceUnlockedInteractor.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DeviceUnlockedInteractor this$0;

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C01801 extends SuspendLambda implements Function2 {
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ DeviceUnlockedInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01801(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = deviceUnlockedInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C01801 c01801 = new C01801(this.this$0, continuation);
                    c01801.L$0 = obj;
                    return c01801;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01801) create((AuthenticationMethodModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) this.L$0;
                        if (!authenticationMethodModel.isSecure) {
                            Log.d(DeviceUnlockedInteractor.TAG, "remaining unlocked because auth method not secure");
                            ((DeviceEntryRepositoryImpl) this.this$0.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(true, null));
                        } else if (authenticationMethodModel.equals(AuthenticationMethodModel.Sim.INSTANCE)) {
                            Log.d(DeviceUnlockedInteractor.TAG, "remaining locked because SIM locked");
                            ((DeviceEntryRepositoryImpl) this.this$0.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(false, null));
                        } else {
                            DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                            this.label = 1;
                            if (DeviceUnlockedInteractor.access$handleLockAndUnlockEvents(deviceUnlockedInteractor, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = deviceUnlockedInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                    Flow flow = deviceUnlockedInteractor.authenticationInteractor.authenticationMethod;
                    C01801 c01801 = new C01801(deviceUnlockedInteractor, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c01801, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01812 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DeviceUnlockedInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01812(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = deviceUnlockedInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01812(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01812) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final ReadonlyStateFlow readonlyStateFlow = this.this$0.deviceUnlockStatus;
                    Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    Boolean boolValueOf = Boolean.valueOf(((DeviceUnlockStatus) obj).isUnlocked);
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
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                    Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(flow, deviceUnlockedInteractor.tableLogBuffer, "", "isUnlocked", ((DeviceUnlockStatus) deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked);
                    this.label = 1;
                    if (FlowKt.collect(flowLogDiffsForTable, this) == coroutineSingletons) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = DeviceUnlockedInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(DeviceUnlockedInteractor.this, null), 3);
            return BuildersKt.launch$default(coroutineScope, null, null, new C01812(DeviceUnlockedInteractor.this, null), 3);
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
                        DeviceUnlockSource.Fingerprint fingerprint = DeviceUnlockSource.Fingerprint.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(fingerprint, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = deviceEntryFingerprintAuthInteractor$special$$inlined$filterIsInstance$4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final StateFlow stateFlowIsAuthenticated = deviceEntryFaceAuthInteractor.isAuthenticated();
        final Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$1

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
                        if (((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowIsAuthenticated.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow3 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$2

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
                        ((Boolean) obj).getClass();
                        Object obj3 = ((Boolean) ((DeviceEntryRepositoryImpl) this.this$0.repository).isBypassEnabled.$$delegate_0.getValue()).booleanValue() ? DeviceUnlockSource.FaceWithBypass.INSTANCE : DeviceUnlockSource.FaceWithoutBypass.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final ReadonlyStateFlow readonlyStateFlow = trustInteractor.isTrusted;
        final Flow flow4 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$2

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
                        if (((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow5 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$3

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
                        ((Boolean) obj).getClass();
                        DeviceUnlockSource.TrustAgent trustAgent = DeviceUnlockSource.TrustAgent.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(trustAgent, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final ReadonlySharedFlow readonlySharedFlow = authenticationInteractor.onAuthenticationResult;
        final Flow flow6 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$filter$3

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
                        if (((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.deviceUnlockSource = FlowKt.merge(flow, flow3, flow5, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$4

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
                        ((Boolean) obj).getClass();
                        DeviceUnlockSource.BouncerInput bouncerInput = DeviceUnlockSource.BouncerInput.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(bouncerInput, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow6.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        StateFlow stateFlow = deviceEntryBiometricSettingsInteractor.isFaceAuthEnrolledAndEnabled;
        this.faceEnrolledAndEnabled = stateFlow;
        StateFlow stateFlow2 = deviceEntryBiometricSettingsInteractor.isFingerprintAuthEnrolledAndEnabled;
        this.fingerprintEnrolledAndEnabled = stateFlow2;
        ReadonlyStateFlow readonlyStateFlow2 = trustInteractor.isEnrolledAndEnabled;
        this.trustAgentEnabled = readonlyStateFlow2;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(stateFlow, stateFlow2, readonlyStateFlow2, DeviceUnlockedInteractor$faceOrFingerprintOrTrustEnabled$3.INSTANCE);
        this.faceOrFingerprintOrTrustEnabled = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine;
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, new DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1(null, this, deviceEntryFaceAuthInteractor, deviceEntryFingerprintAuthInteractor));
        this.deviceEntryRestrictionReason = channelFlowTransformLatestTransformLatest;
        this.isInLockdown = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$special$$inlined$map$5

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
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
                        DeviceEntryRestrictionReason deviceEntryRestrictionReason = (DeviceEntryRestrictionReason) obj;
                        String str = DeviceUnlockedInteractor.TAG;
                        this.this$0.getClass();
                        switch (deviceEntryRestrictionReason == null ? -1 : DeviceUnlockedInteractor.WhenMappings.$EnumSwitchMapping$0[deviceEntryRestrictionReason.ordinal()]) {
                            case -1:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                                z = false;
                                break;
                            case 0:
                            default:
                                throw new NoWhenBranchMatchedException();
                            case 1:
                            case 2:
                                z = true;
                                break;
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
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
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.deviceUnlockStatus = FlowKt.asStateFlow(((DeviceEntryRepositoryImpl) deviceEntryRepository).deviceUnlockStatus);
        this.lockNowRequests = ChannelKt.Channel$default(0, null, null, 7);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleLockAndUnlockEvents(DeviceUnlockedInteractor deviceUnlockedInteractor, ContinuationImpl continuationImpl) {
        DeviceUnlockedInteractor$handleLockAndUnlockEvents$1 deviceUnlockedInteractor$handleLockAndUnlockEvents$1;
        deviceUnlockedInteractor.getClass();
        if (continuationImpl instanceof DeviceUnlockedInteractor$handleLockAndUnlockEvents$1) {
            deviceUnlockedInteractor$handleLockAndUnlockEvents$1 = (DeviceUnlockedInteractor$handleLockAndUnlockEvents$1) continuationImpl;
            int i = deviceUnlockedInteractor$handleLockAndUnlockEvents$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                deviceUnlockedInteractor$handleLockAndUnlockEvents$1.label = i - Integer.MIN_VALUE;
            } else {
                deviceUnlockedInteractor$handleLockAndUnlockEvents$1 = new DeviceUnlockedInteractor$handleLockAndUnlockEvents$1(deviceUnlockedInteractor, continuationImpl);
            }
        }
        Object objCoroutineScope = deviceUnlockedInteractor$handleLockAndUnlockEvents$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = deviceUnlockedInteractor$handleLockAndUnlockEvents$1.label;
        String str = TAG;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objCoroutineScope);
                Log.d(str, "started watching for lock and unlock events");
                DeviceUnlockedInteractor$handleLockAndUnlockEvents$2 deviceUnlockedInteractor$handleLockAndUnlockEvents$2 = new DeviceUnlockedInteractor$handleLockAndUnlockEvents$2(deviceUnlockedInteractor, null);
                deviceUnlockedInteractor$handleLockAndUnlockEvents$1.label = 1;
                objCoroutineScope = CoroutineScopeKt.coroutineScope(deviceUnlockedInteractor$handleLockAndUnlockEvents$2, deviceUnlockedInteractor$handleLockAndUnlockEvents$1);
                if (objCoroutineScope == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objCoroutineScope);
            }
            Log.d(str, "stopped watching for lock and unlock events");
            return Unit.INSTANCE;
        } catch (Throwable th) {
            Log.d(str, "stopped watching for lock and unlock events");
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|2|(2:4|(1:6)(1:7))(0)|8|(1:(1:(6:12|45|13|32|38|39)(2:15|16))(1:17))(4:18|(1:20)(2:21|(3:23|(1:26)|30)(2:36|(2:40|41)))|38|39)|27|43|28|(4:31|32|38|39)|30|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00eb, code lost:
    
        r5 = r1;
        r0 = r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$onLockEvent(DeviceUnlockedInteractor deviceUnlockedInteractor, LockEvent lockEvent, Continuation continuation) {
        DeviceUnlockedInteractor$onLockEvent$1 deviceUnlockedInteractor$onLockEvent$1;
        String str;
        String str2;
        long j;
        DeviceUnlockedInteractor deviceUnlockedInteractor2;
        DeviceUnlockedInteractor deviceUnlockedInteractor3 = deviceUnlockedInteractor;
        deviceUnlockedInteractor3.getClass();
        if (continuation instanceof DeviceUnlockedInteractor$onLockEvent$1) {
            deviceUnlockedInteractor$onLockEvent$1 = (DeviceUnlockedInteractor$onLockEvent$1) continuation;
            int i = deviceUnlockedInteractor$onLockEvent$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                deviceUnlockedInteractor$onLockEvent$1.label = i - Integer.MIN_VALUE;
            } else {
                deviceUnlockedInteractor$onLockEvent$1 = new DeviceUnlockedInteractor$onLockEvent$1(deviceUnlockedInteractor3, continuation);
            }
        }
        Object obj = deviceUnlockedInteractor$onLockEvent$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = deviceUnlockedInteractor$onLockEvent$1.label;
        String str3 = TAG;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            String debugReason = lockEvent.getDebugReason();
            if (lockEvent instanceof LockImmediately) {
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("locking without delay due to \"", debugReason, "\"", str3);
                ((DeviceEntryRepositoryImpl) deviceUnlockedInteractor3.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(false, null));
            } else {
                if (lockEvent instanceof LockWithDelay) {
                    deviceUnlockedInteractor$onLockEvent$1.L$0 = deviceUnlockedInteractor3;
                    deviceUnlockedInteractor$onLockEvent$1.L$1 = debugReason;
                    deviceUnlockedInteractor$onLockEvent$1.label = 1;
                    Object objLockDelay = deviceUnlockedInteractor3.lockDelay(deviceUnlockedInteractor$onLockEvent$1);
                    if (objLockDelay != coroutineSingletons) {
                        obj = objLockDelay;
                        str = debugReason;
                    }
                    return coroutineSingletons;
                }
                if (!(lockEvent instanceof CancelDelayedLock)) {
                    throw new NoWhenBranchMatchedException();
                }
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = deviceUnlockedInteractor$onLockEvent$1.J$0;
            str2 = (String) deviceUnlockedInteractor$onLockEvent$1.L$1;
            deviceUnlockedInteractor2 = (DeviceUnlockedInteractor) deviceUnlockedInteractor$onLockEvent$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                Log.d(str3, "locking after having waited for " + j + "ms due to \"" + str2 + "\"");
                ((DeviceEntryRepositoryImpl) deviceUnlockedInteractor2.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(false, null));
                Unit unit = Unit.INSTANCE;
            } catch (CancellationException unused) {
                Boxing.boxInt(Log.d(str3, "delayed locking canceled, original delay was " + j + "ms and reason was \"" + str2 + "\""));
                return Unit.INSTANCE;
            }
            return Unit.INSTANCE;
        }
        String str4 = (String) deviceUnlockedInteractor$onLockEvent$1.L$1;
        DeviceUnlockedInteractor deviceUnlockedInteractor4 = (DeviceUnlockedInteractor) deviceUnlockedInteractor$onLockEvent$1.L$0;
        ResultKt.throwOnFailure(obj);
        str = str4;
        deviceUnlockedInteractor3 = deviceUnlockedInteractor4;
        long jLongValue = ((Number) obj).longValue();
        StringBuilder sb = new StringBuilder("locking in ");
        sb.append(jLongValue);
        sb.append("ms due to \"");
        sb.append(str);
        ExifInterface$$ExternalSyntheticOutline0.m(sb, "\"", str3);
        deviceUnlockedInteractor$onLockEvent$1.L$0 = deviceUnlockedInteractor3;
        deviceUnlockedInteractor$onLockEvent$1.L$1 = str;
        deviceUnlockedInteractor$onLockEvent$1.J$0 = jLongValue;
        deviceUnlockedInteractor$onLockEvent$1.label = 2;
        if (DelayKt.delay(jLongValue, deviceUnlockedInteractor$onLockEvent$1) != coroutineSingletons) {
            deviceUnlockedInteractor2 = deviceUnlockedInteractor3;
            str2 = str;
            j = jLongValue;
            Log.d(str3, "locking after having waited for " + j + "ms due to \"" + str2 + "\"");
            ((DeviceEntryRepositoryImpl) deviceUnlockedInteractor2.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(false, null));
            Unit unit2 = Unit.INSTANCE;
            return Unit.INSTANCE;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object lockDelay(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        long j;
        long jLongValue;
        long j2;
        long j3;
        int iIntValue;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object maximumTimeToLock = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        String str = TAG;
        if (i2 == 0) {
            ResultKt.throwOnFailure(maximumTimeToLock);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            maximumTimeToLock = this.userAwareSecureSettingsRepository.getInt("lock_screen_lock_after_timeout", 5000, anonymousClass1);
            if (maximumTimeToLock != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j3 = anonymousClass1.J$1;
                long j4 = anonymousClass1.J$0;
                ResultKt.throwOnFailure(maximumTimeToLock);
                j2 = j4;
                iIntValue = ((Number) maximumTimeToLock).intValue();
                if (iIntValue < 0) {
                    iIntValue = 0;
                }
                long j5 = iIntValue;
                Log.d(str, "Screen off timeout setting set to " + j5 + "ms");
                Long l = new Long(RangesKt___RangesKt.coerceIn(j3 - j5, 0L, j2));
                Log.d(str, "Device policy max enforced, delay is " + l.longValue() + "ms");
                return l;
            }
            j = anonymousClass1.J$0;
            this = (DeviceUnlockedInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(maximumTimeToLock);
            jLongValue = ((Number) maximumTimeToLock).longValue();
            Log.d(str, "Device policy max set to " + jLongValue + "ms");
            if (jLongValue > 0) {
                Log.d(str, "No device policy max, delay is " + j + "ms");
                return new Long(j);
            }
            UserAwareSecureSettingsRepository userAwareSecureSettingsRepository = this.userAwareSecureSettingsRepository;
            anonymousClass1.L$0 = null;
            anonymousClass1.J$0 = j;
            anonymousClass1.J$1 = jLongValue;
            anonymousClass1.label = 3;
            maximumTimeToLock = userAwareSecureSettingsRepository.getInt("screen_off_timeout", PluginLockInstancePolicy.DISABLED_BY_SUB_USER, anonymousClass1);
            if (maximumTimeToLock != coroutineSingletons) {
                j2 = j;
                j3 = jLongValue;
                iIntValue = ((Number) maximumTimeToLock).intValue();
                if (iIntValue < 0) {
                }
                long j52 = iIntValue;
                Log.d(str, "Screen off timeout setting set to " + j52 + "ms");
                Long l2 = new Long(RangesKt___RangesKt.coerceIn(j3 - j52, 0L, j2));
                Log.d(str, "Device policy max enforced, delay is " + l2.longValue() + "ms");
                return l2;
            }
            return coroutineSingletons;
        }
        this = (DeviceUnlockedInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(maximumTimeToLock);
        long jIntValue = ((Number) maximumTimeToLock).intValue();
        Log.d(str, "Lock after screen timeout setting set to " + jIntValue + "ms");
        AuthenticationInteractor authenticationInteractor = this.authenticationInteractor;
        anonymousClass1.L$0 = this;
        anonymousClass1.J$0 = jIntValue;
        anonymousClass1.label = 2;
        maximumTimeToLock = ((AuthenticationRepositoryImpl) authenticationInteractor.repository).getMaximumTimeToLock(anonymousClass1);
        if (maximumTimeToLock != coroutineSingletons) {
            j = jIntValue;
            jLongValue = ((Number) maximumTimeToLock).longValue();
            Log.d(str, "Device policy max set to " + jLongValue + "ms");
            if (jLongValue > 0) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C08621 c08621;
        if (continuation instanceof C08621) {
            c08621 = (C08621) continuation;
            int i = c08621.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08621.label = i - Integer.MIN_VALUE;
            } else {
                c08621 = new C08621(continuation);
            }
        }
        Object obj = c08621.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08621.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            c08621.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, c08621) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        c08621.label = 2;
    }

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
