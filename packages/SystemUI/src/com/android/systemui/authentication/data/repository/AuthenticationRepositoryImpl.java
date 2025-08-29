package com.android.systemui.authentication.data.repository;

import android.app.admin.DevicePolicyManager;
import android.content.pm.UserInfo;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.authentication.shared.model.AuthenticationResultModel;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.util.time.SystemClock;
import java.util.function.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class AuthenticationRepositoryImpl implements AuthenticationRepository {
    public final StateFlowImpl _failedAuthenticationAttempts;
    public final StateFlowImpl _hasLockoutOccurred;
    public final CoroutineScope applicationScope;
    public final Flow authenticationMethod;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SystemClock clock;
    public final DevicePolicyManager devicePolicyManager;
    public final ReadonlyStateFlow failedAuthenticationAttempts;
    public final Function getSecurityMode;
    public final ReadonlyStateFlow hasLockoutOccurred;
    public final ReadonlyStateFlow isAutoConfirmFeatureEnabled;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isPinEnhancedPrivacyEnabled;
    public final LockPatternUtils lockPatternUtils;
    public final int minPasswordLength;
    public final int minPatternLength;
    public final UserRepository userRepository;

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00551 implements FlowCollector {
            public final /* synthetic */ AuthenticationRepositoryImpl this$0;

            public C00551(AuthenticationRepositoryImpl authenticationRepositoryImpl) {
                this.this$0 = authenticationRepositoryImpl;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(UserInfo userInfo, Continuation continuation) throws Throwable {
                AuthenticationRepositoryImpl$1$1$emit$1 authenticationRepositoryImpl$1$1$emit$1;
                MutableStateFlow mutableStateFlow;
                if (continuation instanceof AuthenticationRepositoryImpl$1$1$emit$1) {
                    authenticationRepositoryImpl$1$1$emit$1 = (AuthenticationRepositoryImpl$1$1$emit$1) continuation;
                    int i = authenticationRepositoryImpl$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        authenticationRepositoryImpl$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        authenticationRepositoryImpl$1$1$emit$1 = new AuthenticationRepositoryImpl$1$1$emit$1(this, continuation);
                    }
                }
                Object obj = authenticationRepositoryImpl$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = authenticationRepositoryImpl$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
                    StateFlowImpl stateFlowImpl = authenticationRepositoryImpl._failedAuthenticationAttempts;
                    authenticationRepositoryImpl$1$1$emit$1.L$0 = stateFlowImpl;
                    authenticationRepositoryImpl$1$1$emit$1.label = 1;
                    Object objWithContext = BuildersKt.withContext(authenticationRepositoryImpl.backgroundDispatcher, new AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2(authenticationRepositoryImpl, null), authenticationRepositoryImpl$1$1$emit$1);
                    if (objWithContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = objWithContext;
                    mutableStateFlow = stateFlowImpl;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mutableStateFlow = (MutableStateFlow) authenticationRepositoryImpl$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                mutableStateFlow.setValue(obj);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new AnonymousClass1(continuation);
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
                AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) authenticationRepositoryImpl.userRepository).selectedUserInfo;
                C00551 c00551 = new C00551(authenticationRepositoryImpl);
                this.label = 1;
                if (userRepositoryImpl$special$$inlined$map$2.collect(c00551, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$checkCredential$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LockscreenCredential $credential;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LockscreenCredential lockscreenCredential, Continuation continuation) {
            super(2, continuation);
            this.$credential = lockscreenCredential;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new AnonymousClass2(this.$credential, continuation);
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
            try {
                AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
                return new AuthenticationResultModel(authenticationRepositoryImpl.lockPatternUtils.checkCredential(this.$credential, authenticationRepositoryImpl.getSelectedUserId(), new LockPatternUtils.CheckCredentialProgressCallback() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$checkCredential$2$matched$1
                    public final void onEarlyMatched() {
                    }
                }), 0);
            } catch (LockPatternUtils.RequestThrottledException e) {
                return new AuthenticationResultModel(false, e.getTimeoutMs());
            }
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getAuthenticationMethod$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $userId;
        int label;

        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getAuthenticationMethod$3$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 9;
                } catch (NoSuchFieldError unused9) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 10;
                } catch (NoSuchFieldError unused10) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 11;
                } catch (NoSuchFieldError unused11) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 12;
                } catch (NoSuchFieldError unused12) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 13;
                } catch (NoSuchFieldError unused13) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 14;
                } catch (NoSuchFieldError unused14) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 15;
                } catch (NoSuchFieldError unused15) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.Swipe.ordinal()] = 16;
                } catch (NoSuchFieldError unused16) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.ForgotPassword.ordinal()] = 17;
                } catch (NoSuchFieldError unused17) {
                }
                try {
                    iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 18;
                } catch (NoSuchFieldError unused18) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int i, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new AnonymousClass3(this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            switch (WhenMappings.$EnumSwitchMapping$0[((KeyguardSecurityModel.SecurityMode) AuthenticationRepositoryImpl.this.getSecurityMode.apply(new Integer(this.$userId))).ordinal()]) {
                case 1:
                    return AuthenticationMethodModel.Pin.INSTANCE;
                case 2:
                case 3:
                case 4:
                    return AuthenticationMethodModel.Sim.INSTANCE;
                case 5:
                    return AuthenticationMethodModel.Password.INSTANCE;
                case 6:
                    return AuthenticationMethodModel.Pattern.INSTANCE;
                case 7:
                    return AuthenticationMethodModel.None.INSTANCE;
                case 8:
                    return AuthenticationMethodModel.SmartcardPIN.INSTANCE;
                case 9:
                    return AuthenticationMethodModel.AdminLock.INSTANCE;
                case 10:
                    return AuthenticationMethodModel.Permanent.INSTANCE;
                case 11:
                    return AuthenticationMethodModel.FMM.INSTANCE;
                case 12:
                    return AuthenticationMethodModel.RMM.INSTANCE;
                case 13:
                    return AuthenticationMethodModel.KNOXGUARD.INSTANCE;
                case 14:
                case 15:
                    return AuthenticationMethodModel.SKTCarrierLock.INSTANCE;
                case 16:
                    return AuthenticationMethodModel.Swipe.INSTANCE;
                case 17:
                    return AuthenticationMethodModel.ForgotPassword.INSTANCE;
                case 18:
                    throw new IllegalStateException("Invalid security mode!");
                default:
                    throw new IllegalStateException("Invalid security mode!");
            }
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getMaxFailedUnlockAttemptsForWipe$2, reason: invalid class name and case insensitive filesystem */
    final class C07942 extends SuspendLambda implements Function2 {
        int label;

        public C07942(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07942(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07942) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            return new Integer(authenticationRepositoryImpl.lockPatternUtils.getMaximumFailedPasswordsForWipe(authenticationRepositoryImpl.getSelectedUserId()));
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getMaximumTimeToLock$2, reason: invalid class name and case insensitive filesystem */
    final class C07952 extends SuspendLambda implements Function2 {
        int label;

        public C07952(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07952(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07952) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            return new Long(authenticationRepositoryImpl.devicePolicyManager.getMaximumTimeToLock(null, authenticationRepositoryImpl.getSelectedUserId()));
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getPinLength$2, reason: invalid class name and case insensitive filesystem */
    final class C07962 extends SuspendLambda implements Function2 {
        int label;

        public C07962(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07962(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07962) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            return new Integer(authenticationRepositoryImpl.lockPatternUtils.getPinLength(authenticationRepositoryImpl.getSelectedUserId()));
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getPowerButtonInstantlyLocks$2, reason: invalid class name and case insensitive filesystem */
    final class C07972 extends SuspendLambda implements Function2 {
        int label;

        public C07972(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07972(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07972) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            return Boolean.valueOf(authenticationRepositoryImpl.lockPatternUtils.getPowerButtonInstantlyLocks(authenticationRepositoryImpl.getSelectedUserId()));
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getProfileWithMinFailedUnlockAttemptsForWipe$2, reason: invalid class name and case insensitive filesystem */
    final class C07982 extends SuspendLambda implements Function2 {
        int label;

        public C07982(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07982(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07982) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            return new Integer(authenticationRepositoryImpl.devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(authenticationRepositoryImpl.getSelectedUserId()));
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttempt$2, reason: invalid class name and case insensitive filesystem */
    final class C08002 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isSuccessful;
        Object L$0;
        int label;
        final /* synthetic */ AuthenticationRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08002(boolean z, AuthenticationRepositoryImpl authenticationRepositoryImpl, Continuation continuation) {
            super(2, continuation);
            this.$isSuccessful = z;
            this.this$0 = authenticationRepositoryImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08002(this.$isSuccessful, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08002) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            MutableStateFlow mutableStateFlow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$isSuccessful) {
                    AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
                    authenticationRepositoryImpl.lockPatternUtils.userPresent(authenticationRepositoryImpl.getSelectedUserId());
                    AuthenticationRepositoryImpl authenticationRepositoryImpl2 = this.this$0;
                    authenticationRepositoryImpl2.lockPatternUtils.reportSuccessfulPasswordAttempt(authenticationRepositoryImpl2.getSelectedUserId());
                    this.this$0._hasLockoutOccurred.updateState(null, Boolean.FALSE);
                } else {
                    AuthenticationRepositoryImpl authenticationRepositoryImpl3 = this.this$0;
                    authenticationRepositoryImpl3.lockPatternUtils.reportFailedPasswordAttempt(authenticationRepositoryImpl3.getSelectedUserId());
                }
                AuthenticationRepositoryImpl authenticationRepositoryImpl4 = this.this$0;
                StateFlowImpl stateFlowImpl = authenticationRepositoryImpl4._failedAuthenticationAttempts;
                this.L$0 = stateFlowImpl;
                this.label = 1;
                obj = BuildersKt.withContext(authenticationRepositoryImpl4.backgroundDispatcher, new AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2(authenticationRepositoryImpl4, null), this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutableStateFlow = stateFlowImpl;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutableStateFlow = (MutableStateFlow) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            mutableStateFlow.setValue(obj);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1, reason: invalid class name and case insensitive filesystem */
    final class C08011 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08011(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AuthenticationRepositoryImpl.this.reportAuthenticationAttemptFromPrimaryBouncer(false, this);
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1, reason: invalid class name and case insensitive filesystem */
    final class C08021 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08021(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AuthenticationRepositoryImpl.this.reportLockoutStarted(0, this);
        }
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$2, reason: invalid class name and case insensitive filesystem */
    final class C08032 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $durationMs;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08032(int i, Continuation continuation) {
            super(2, continuation);
            this.$durationMs = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C08032(this.$durationMs, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08032) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = AuthenticationRepositoryImpl.this;
            authenticationRepositoryImpl.lockPatternUtils.reportPasswordLockout(this.$durationMs, authenticationRepositoryImpl.getSelectedUserId());
            return Unit.INSTANCE;
        }
    }

    public AuthenticationRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SystemClock systemClock, Function<Integer, KeyguardSecurityModel.SecurityMode> function, UserRepository userRepository, LockPatternUtils lockPatternUtils, DevicePolicyManager devicePolicyManager, BroadcastDispatcher broadcastDispatcher, MobileConnectionsRepository mobileConnectionsRepository) {
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.clock = systemClock;
        this.getSecurityMode = function;
        this.userRepository = userRepository;
        this.lockPatternUtils = lockPatternUtils;
        this.devicePolicyManager = devicePolicyManager;
        Boolean bool = Boolean.TRUE;
        this.isPatternVisible = refreshingFlow(bool, new AuthenticationRepositoryImpl$isPatternVisible$1(lockPatternUtils));
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        this.isAutoConfirmFeatureEnabled = refreshingFlow(Boolean.valueOf(lockPatternUtils.isAutoPinConfirmEnabled(userRepositoryImpl.getSelectedUserInfo().id)), new AuthenticationRepositoryImpl$isAutoConfirmFeatureEnabled$1(lockPatternUtils));
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userRepositoryImpl.selectedUserInfo, mobileConnectionsRepository.isAnySimSecure(), new AuthenticationRepositoryImpl$authenticationMethod$1(null)), new AuthenticationRepositoryImpl$special$$inlined$flatMapLatest$1(null, broadcastDispatcher));
        this.authenticationMethod = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ AuthenticationRepositoryImpl $receiver$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AuthenticationRepositoryImpl authenticationRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$receiver$inlined = authenticationRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        int iIntValue = ((Number) obj).intValue();
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        Object authenticationMethod = this.$receiver$inlined.getAuthenticationMethod(iIntValue, anonymousClass1);
                        if (authenticationMethod != coroutineSingletons) {
                            obj2 = authenticationMethod;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.minPatternLength = 4;
        this.minPasswordLength = 4;
        this.isPinEnhancedPrivacyEnabled = refreshingFlow(bool, new AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1(this, null));
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._failedAuthenticationAttempts = stateFlowImplMutableStateFlow;
        this.failedAuthenticationAttempts = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._hasLockoutOccurred = stateFlowImplMutableStateFlow2;
        this.hasLockoutOccurred = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    public final Object checkCredential(LockscreenCredential lockscreenCredential, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(lockscreenCredential, null), continuation);
    }

    public final Object getAuthenticationMethod(int i, ContinuationImpl continuationImpl) {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return BuildersKt.withContext(MainDispatcherLoader.dispatcher, new AnonymousClass3(i, null), continuationImpl);
    }

    public final Object getMaxFailedUnlockAttemptsForWipe(AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1 anonymousClass1) throws Throwable {
        return BuildersKt.withContext(this.backgroundDispatcher, new C07942(null), anonymousClass1);
    }

    public final Object getMaximumTimeToLock(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new C07952(null), continuation);
    }

    public final Object getPinLength(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new C07962(null), continuationImpl);
    }

    public final Object getPowerButtonInstantlyLocks(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new C07972(null), continuation);
    }

    public final Object getProfileWithMinFailedUnlockAttemptsForWipe(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new C07982(null), continuation);
    }

    public final int getSelectedUserId() {
        return ((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id;
    }

    public final ReadonlyStateFlow refreshingFlow(Object obj, Function2 function2) {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(obj);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C07991(stateFlowImplMutableStateFlow, function2, null), 7);
        return FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    public final Object reportAuthenticationAttempt(boolean z, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C08002(z, this, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object reportAuthenticationAttemptFromPrimaryBouncer(boolean z, ContinuationImpl continuationImpl) throws Throwable {
        C08011 c08011;
        MutableStateFlow mutableStateFlow;
        if (continuationImpl instanceof C08011) {
            c08011 = (C08011) continuationImpl;
            int i = c08011.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08011.label = i - Integer.MIN_VALUE;
            } else {
                c08011 = new C08011(continuationImpl);
            }
        }
        Object objWithContext = c08011.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08011.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            if (z) {
                this._hasLockoutOccurred.updateState(null, Boolean.FALSE);
            }
            StateFlowImpl stateFlowImpl = this._failedAuthenticationAttempts;
            c08011.L$0 = stateFlowImpl;
            c08011.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2(this, null), c08011);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            mutableStateFlow = stateFlowImpl;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            mutableStateFlow = (MutableStateFlow) c08011.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        mutableStateFlow.setValue(objWithContext);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object reportLockoutStarted(int i, ContinuationImpl continuationImpl) {
        C08021 c08021;
        if (continuationImpl instanceof C08021) {
            c08021 = (C08021) continuationImpl;
            int i2 = c08021.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c08021.label = i2 - Integer.MIN_VALUE;
            } else {
                c08021 = new C08021(continuationImpl);
            }
        }
        Object obj = c08021.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c08021.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            this.lockPatternUtils.setLockoutAttemptDeadline(getSelectedUserId(), i);
            C08032 c08032 = new C08032(i, null);
            c08021.L$0 = this;
            c08021.label = 1;
            if (BuildersKt.withContext(this.backgroundDispatcher, c08032, c08021) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (AuthenticationRepositoryImpl) c08021.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this._hasLockoutOccurred.updateState(null, Boolean.TRUE);
        return Unit.INSTANCE;
    }

    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1, reason: invalid class name and case insensitive filesystem */
    final class C07991 extends SuspendLambda implements Function2 {
        final /* synthetic */ MutableStateFlow $flow;
        final /* synthetic */ Function2 $getFreshValue;
        int label;

        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function3 {
            /* synthetic */ int I$0;
            int label;

            public AnonymousClass2(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int iIntValue = ((Number) obj).intValue();
                AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
                anonymousClass2.I$0 = iIntValue;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return new Integer(this.I$0);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07991(MutableStateFlow mutableStateFlow, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$flow = mutableStateFlow;
            this.$getFreshValue = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationRepositoryImpl.this.new C07991(this.$flow, this.$getFreshValue, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07991) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) AuthenticationRepositoryImpl.this.userRepository).selectedUserInfo;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                }), com.android.systemui.util.kotlin.FlowKt.onSubscriberAdded(this.$flow), new AnonymousClass2(null));
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$flow, AuthenticationRepositoryImpl.this, this.$getFreshValue);
                this.label = 1;
                if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(anonymousClass3, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3, reason: invalid class name */
        public final class AnonymousClass3 implements FlowCollector {
            public final /* synthetic */ MutableStateFlow $flow;
            public final /* synthetic */ Function2 $getFreshValue;
            public final /* synthetic */ AuthenticationRepositoryImpl this$0;

            /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$refreshingFlow$1$3$1, reason: invalid class name and collision with other inner class name */
            final class C00561 extends SuspendLambda implements Function2 {
                final /* synthetic */ Function2 $getFreshValue;
                final /* synthetic */ int $selectedUserId;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00561(Function2 function2, int i, Continuation continuation) {
                    super(2, continuation);
                    this.$getFreshValue = function2;
                    this.$selectedUserId = i;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00561(this.$getFreshValue, this.$selectedUserId, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00561) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i != 0) {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return obj;
                    }
                    ResultKt.throwOnFailure(obj);
                    Function2 function2 = this.$getFreshValue;
                    Integer num = new Integer(this.$selectedUserId);
                    this.label = 1;
                    Object objInvoke = function2.invoke(num, this);
                    return objInvoke == coroutineSingletons ? coroutineSingletons : objInvoke;
                }
            }

            public AnonymousClass3(MutableStateFlow mutableStateFlow, AuthenticationRepositoryImpl authenticationRepositoryImpl, Function2 function2) {
                this.$flow = mutableStateFlow;
                this.this$0 = authenticationRepositoryImpl;
                this.$getFreshValue = function2;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(int i, Continuation continuation) throws Throwable {
                AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1 authenticationRepositoryImpl$refreshingFlow$1$3$emit$1;
                MutableStateFlow mutableStateFlow;
                if (continuation instanceof AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1) {
                    authenticationRepositoryImpl$refreshingFlow$1$3$emit$1 = (AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1) continuation;
                    int i2 = authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.label;
                    if ((i2 & Integer.MIN_VALUE) != 0) {
                        authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.label = i2 - Integer.MIN_VALUE;
                    } else {
                        authenticationRepositoryImpl$refreshingFlow$1$3$emit$1 = new AuthenticationRepositoryImpl$refreshingFlow$1$3$emit$1(this, continuation);
                    }
                }
                Object objWithContext = authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i3 = authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.label;
                if (i3 == 0) {
                    ResultKt.throwOnFailure(objWithContext);
                    CoroutineDispatcher coroutineDispatcher = this.this$0.backgroundDispatcher;
                    C00561 c00561 = new C00561(this.$getFreshValue, i, null);
                    mutableStateFlow = this.$flow;
                    authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.L$0 = mutableStateFlow;
                    authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.label = 1;
                    objWithContext = BuildersKt.withContext(coroutineDispatcher, c00561, authenticationRepositoryImpl$refreshingFlow$1$3$emit$1);
                    if (objWithContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i3 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mutableStateFlow = (MutableStateFlow) authenticationRepositoryImpl$refreshingFlow$1$3$emit$1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                }
                mutableStateFlow.setValue(objWithContext);
                return Unit.INSTANCE;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Number) obj).intValue(), continuation);
            }
        }
    }
}
