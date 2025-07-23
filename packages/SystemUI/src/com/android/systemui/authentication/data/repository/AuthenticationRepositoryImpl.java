package com.android.systemui.authentication.data.repository;

import android.app.admin.DevicePolicyManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00261 implements FlowCollector {
            public final /* synthetic */ AuthenticationRepositoryImpl this$0;

            public C00261(AuthenticationRepositoryImpl authenticationRepositoryImpl) {
                this.this$0 = authenticationRepositoryImpl;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(android.content.pm.UserInfo r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r5 = r6 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1$emit$1
                    if (r5 == 0) goto L13
                    r5 = r6
                    com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1$emit$1 r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1$emit$1) r5
                    int r0 = r5.label
                    r1 = -2147483648(0xffffffff80000000, float:-0.0)
                    r2 = r0 & r1
                    if (r2 == 0) goto L13
                    int r0 = r0 - r1
                    r5.label = r0
                    goto L18
                L13:
                    com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1$emit$1 r5 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$1$1$emit$1
                    r5.<init>(r4, r6)
                L18:
                    java.lang.Object r6 = r5.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r5.label
                    r2 = 1
                    if (r1 == 0) goto L33
                    if (r1 != r2) goto L2b
                    java.lang.Object r4 = r5.L$0
                    kotlinx.coroutines.flow.MutableStateFlow r4 = (kotlinx.coroutines.flow.MutableStateFlow) r4
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L50
                L2b:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r5)
                    throw r4
                L33:
                    kotlin.ResultKt.throwOnFailure(r6)
                    com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r4 = r4.this$0
                    kotlinx.coroutines.flow.StateFlowImpl r6 = r4._failedAuthenticationAttempts
                    r5.L$0 = r6
                    r5.label = r2
                    com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2 r1 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2
                    r2 = 0
                    r1.<init>(r4, r2)
                    kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
                    java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r1, r5)
                    if (r4 != r0) goto L4d
                    return r0
                L4d:
                    r3 = r6
                    r6 = r4
                    r4 = r3
                L50:
                    r4.setValue(r6)
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl.AnonymousClass1.C00261.emit(android.content.pm.UserInfo, kotlin.coroutines.Continuation):java.lang.Object");
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
                C00261 c00261 = new C00261(authenticationRepositoryImpl);
                this.label = 1;
                if (userRepositoryImpl$special$$inlined$map$2.collect(c00261, this) == coroutineSingletons) {
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
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userRepositoryImpl.selectedUserInfo, mobileConnectionsRepository.isAnySimSecure(), new AuthenticationRepositoryImpl$authenticationMethod$1(null)), new AuthenticationRepositoryImpl$special$$inlined$flatMapLatest$1(null, broadcastDispatcher));
        this.authenticationMethod = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L61
                    L2a:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L32:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L55
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Number r7 = (java.lang.Number) r7
                        int r7 = r7.intValue()
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        r0.L$0 = r8
                        r0.label = r4
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r6 = r6.$receiver$inlined
                        java.lang.Object r6 = r6.getAuthenticationMethod(r7, r0)
                        if (r6 != r1) goto L52
                        goto L60
                    L52:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L55:
                        r7 = 0
                        r0.L$0 = r7
                        r0.label = r3
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L61
                    L60:
                        return r1
                    L61:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.minPatternLength = 4;
        this.minPasswordLength = 4;
        this.isPinEnhancedPrivacyEnabled = refreshingFlow(bool, new AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1(this, null));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._failedAuthenticationAttempts = MutableStateFlow;
        this.failedAuthenticationAttempts = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._hasLockoutOccurred = MutableStateFlow2;
        this.hasLockoutOccurred = FlowKt.asStateFlow(MutableStateFlow2);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    public final Object checkCredential(LockscreenCredential lockscreenCredential, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$checkCredential$2(this, lockscreenCredential, null), continuation);
    }

    public final Object getAuthenticationMethod(int i, ContinuationImpl continuationImpl) {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return BuildersKt.withContext(MainDispatcherLoader.dispatcher, new AuthenticationRepositoryImpl$getAuthenticationMethod$3(this, i, null), continuationImpl);
    }

    public final Object getMaxFailedUnlockAttemptsForWipe(AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1 anonymousClass1) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getMaxFailedUnlockAttemptsForWipe$2(this, null), anonymousClass1);
    }

    public final Object getMaximumTimeToLock(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getMaximumTimeToLock$2(this, null), continuation);
    }

    public final Object getPinLength(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getPinLength$2(this, null), continuationImpl);
    }

    public final Object getPowerButtonInstantlyLocks(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getPowerButtonInstantlyLocks$2(this, null), continuation);
    }

    public final Object getProfileWithMinFailedUnlockAttemptsForWipe(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$getProfileWithMinFailedUnlockAttemptsForWipe$2(this, null), continuation);
    }

    public final int getSelectedUserId() {
        return ((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id;
    }

    public final ReadonlyStateFlow refreshingFlow(Object obj, Function2 function2) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(obj);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AuthenticationRepositoryImpl$refreshingFlow$1(this, MutableStateFlow, function2, null), 7);
        return FlowKt.asStateFlow(MutableStateFlow);
    }

    public final Object reportAuthenticationAttempt(boolean z, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new AuthenticationRepositoryImpl$reportAuthenticationAttempt$2(z, this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object reportAuthenticationAttemptFromPrimaryBouncer(boolean r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1 r0 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1 r0 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportAuthenticationAttemptFromPrimaryBouncer$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.MutableStateFlow r4 = (kotlinx.coroutines.flow.MutableStateFlow) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L55
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r6 = 0
            if (r5 == 0) goto L40
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.StateFlowImpl r2 = r4._hasLockoutOccurred
            r2.updateState(r6, r5)
        L40:
            kotlinx.coroutines.flow.StateFlowImpl r5 = r4._failedAuthenticationAttempts
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2 r2 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2
            r2.<init>(r4, r6)
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r4, r2, r0)
            if (r6 != r1) goto L54
            return r1
        L54:
            r4 = r5
        L55:
            r4.setValue(r6)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl.reportAuthenticationAttemptFromPrimaryBouncer(boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object reportLockoutStarted(int r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1 r0 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1 r0 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            java.lang.Object r5 = r0.L$0
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L52
        L2c:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L34:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.internal.widget.LockPatternUtils r7 = r5.lockPatternUtils
            int r2 = r5.getSelectedUserId()
            r7.setLockoutAttemptDeadline(r2, r6)
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$2 r7 = new com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$reportLockoutStarted$2
            r7.<init>(r5, r6, r3)
            r0.L$0 = r5
            r0.label = r4
            kotlinx.coroutines.CoroutineDispatcher r6 = r5.backgroundDispatcher
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r0)
            if (r6 != r1) goto L52
            return r1
        L52:
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._hasLockoutOccurred
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r5.updateState(r3, r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl.reportLockoutStarted(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
