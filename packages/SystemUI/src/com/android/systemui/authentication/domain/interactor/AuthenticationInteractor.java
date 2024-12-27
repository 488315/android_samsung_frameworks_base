package com.android.systemui.authentication.domain.interactor;

import com.android.systemui.authentication.data.repository.AuthenticationRepository;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

public final class AuthenticationInteractor {
    public final SharedFlowImpl _onAuthenticationResult;
    public final CoroutineScope applicationScope;
    public final Flow authenticationMethod;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isAutoConfirmEnabled;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isPinEnhancedPrivacyEnabled;
    public final ReadonlySharedFlow onAuthenticationResult;
    public final AuthenticationRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final AuthenticationInteractor$special$$inlined$map$2 upcomingWipe;

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

    public AuthenticationInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, AuthenticationRepository authenticationRepository, SelectedUserInteractor selectedUserInteractor) {
        this.repository = authenticationRepository;
        this.selectedUserInteractor = selectedUserInteractor;
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) authenticationRepository;
        this.authenticationMethod = authenticationRepositoryImpl.authenticationMethod;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(authenticationRepositoryImpl.isAutoConfirmFeatureEnabled, authenticationRepositoryImpl.hasLockoutOccurred, new AuthenticationInteractor$isAutoConfirmEnabled$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.isAutoConfirmEnabled = stateIn;
        this.hintedPinLength = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AuthenticationInteractor this$0;

                /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    boolean Z$0;
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

                public AnonymousClass2(FlowCollector flowCollector, AuthenticationInteractor authenticationInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = authenticationInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:23:0x0086 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:25:0x0040  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L40
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L87
                    L2a:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L32:
                        boolean r7 = r0.Z$0
                        java.lang.Object r8 = r0.L$1
                        kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                        java.lang.Object r2 = r0.L$0
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1$2 r2 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1.AnonymousClass2) r2
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L64
                    L40:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.lang.Boolean r8 = (java.lang.Boolean) r8
                        boolean r8 = r8.booleanValue()
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r9 = r7.this$0
                        com.android.systemui.authentication.data.repository.AuthenticationRepository r9 = r9.repository
                        r0.L$0 = r7
                        kotlinx.coroutines.flow.FlowCollector r2 = r7.$this_unsafeFlow
                        r0.L$1 = r2
                        r0.Z$0 = r8
                        r0.label = r4
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r9 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r9
                        java.lang.Object r9 = r9.getPinLength(r0)
                        if (r9 != r1) goto L60
                        return r1
                    L60:
                        r6 = r2
                        r2 = r7
                        r7 = r8
                        r8 = r6
                    L64:
                        r4 = r9
                        java.lang.Number r4 = (java.lang.Number) r4
                        int r4 = r4.intValue()
                        r5 = 0
                        if (r7 == 0) goto L79
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r7 = r2.this$0
                        com.android.systemui.authentication.data.repository.AuthenticationRepository r7 = r7.repository
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r7 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r7
                        int r7 = r7.hintedPinLength
                        if (r4 != r7) goto L79
                        goto L7a
                    L79:
                        r9 = r5
                    L7a:
                        r0.L$0 = r5
                        r0.L$1 = r5
                        r0.label = r3
                        java.lang.Object r7 = r8.emit(r9, r0)
                        if (r7 != r1) goto L87
                        return r1
                    L87:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isPatternVisible = authenticationRepositoryImpl.isPatternVisible;
        this.onAuthenticationResult = FlowKt.asSharedFlow(SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7));
        this.isPinEnhancedPrivacyEnabled = authenticationRepositoryImpl.isPinEnhancedPrivacyEnabled;
        final ReadonlyStateFlow readonlyStateFlow = authenticationRepositoryImpl.failedAuthenticationAttempts;
        new Flow() { // from class: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AuthenticationInteractor this$0;

                /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    int I$1;
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

                public AnonymousClass2(FlowCollector flowCollector, AuthenticationInteractor authenticationInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = authenticationInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:21:0x00b2 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:30:0x009b A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:31:0x009c  */
                /* JADX WARN: Removed duplicated region for block: B:32:0x0055  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 3
                        r4 = 2
                        r5 = 1
                        r6 = 0
                        if (r2 == 0) goto L55
                        if (r2 == r5) goto L43
                        if (r2 == r4) goto L37
                        if (r2 != r3) goto L2f
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto Lb3
                    L2f:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L37:
                        int r8 = r0.I$1
                        int r9 = r0.I$0
                        java.lang.Object r2 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L9f
                    L43:
                        int r8 = r0.I$0
                        java.lang.Object r9 = r0.L$1
                        kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
                        java.lang.Object r2 = r0.L$0
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2$2 r2 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2) r2
                        kotlin.ResultKt.throwOnFailure(r10)
                        r7 = r9
                        r9 = r8
                        r8 = r2
                        r2 = r7
                        goto L75
                    L55:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.lang.Number r9 = (java.lang.Number) r9
                        int r9 = r9.intValue()
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r10 = r8.this$0
                        com.android.systemui.authentication.data.repository.AuthenticationRepository r10 = r10.repository
                        r0.L$0 = r8
                        kotlinx.coroutines.flow.FlowCollector r2 = r8.$this_unsafeFlow
                        r0.L$1 = r2
                        r0.I$0 = r9
                        r0.label = r5
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r10 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r10
                        java.lang.Object r10 = r10.getMaxFailedUnlockAttemptsForWipe(r0)
                        if (r10 != r1) goto L75
                        return r1
                    L75:
                        java.lang.Number r10 = (java.lang.Number) r10
                        int r10 = r10.intValue()
                        if (r10 != 0) goto L7f
                    L7d:
                        r4 = r6
                        goto La6
                    L7f:
                        r5 = 0
                        int r10 = r10 - r9
                        int r10 = java.lang.Math.max(r5, r10)
                        r5 = 5
                        if (r10 < r5) goto L89
                        goto L7d
                    L89:
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r8 = r8.this$0
                        r0.L$0 = r2
                        r0.L$1 = r6
                        r0.I$0 = r9
                        r0.I$1 = r10
                        r0.label = r4
                        java.lang.Object r8 = com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.access$getWipeTarget(r8, r0)
                        if (r8 != r1) goto L9c
                        return r1
                    L9c:
                        r7 = r10
                        r10 = r8
                        r8 = r7
                    L9f:
                        com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget r10 = (com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget) r10
                        com.android.systemui.authentication.shared.model.AuthenticationWipeModel r4 = new com.android.systemui.authentication.shared.model.AuthenticationWipeModel
                        r4.<init>(r10, r9, r8)
                    La6:
                        r0.L$0 = r6
                        r0.L$1 = r6
                        r0.label = r3
                        java.lang.Object r8 = r2.emit(r4, r0)
                        if (r8 != r1) goto Lb3
                        return r1
                    Lb3:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getWipeTarget(com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r4, kotlin.coroutines.Continuation r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getWipeTarget$1
            if (r0 == 0) goto L16
            r0 = r5
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getWipeTarget$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getWipeTarget$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getWipeTarget$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getWipeTarget$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r4 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L48
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.authentication.data.repository.AuthenticationRepository r5 = r4.repository
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            java.lang.Object r5 = r5.getProfileWithMinFailedUnlockAttemptsForWipe(r0)
            if (r5 != r1) goto L48
            goto L77
        L48:
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            android.app.admin.flags.FeatureFlagsImpl r0 = android.app.admin.flags.Flags.FEATURE_FLAGS
            r0.getClass()
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r0 = r4.selectedUserInteractor
            com.android.systemui.user.data.repository.UserRepository r0 = r0.repository
            com.android.systemui.user.data.repository.UserRepositoryImpl r0 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r0
            int r0 = r0.mainUserId
            r1 = 0
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r4 = r4.selectedUserInteractor
            int r4 = r4.getSelectedUserId(r1)
            if (r5 != r4) goto L6d
            if (r5 != r0) goto L6a
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$WholeDevice r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE
        L68:
            r1 = r4
            goto L77
        L6a:
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$User r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.User.INSTANCE
            goto L68
        L6d:
            r4 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r5 != r4) goto L74
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$WholeDevice r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE
            goto L68
        L74:
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$ManagedProfile r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE
            goto L68
        L77:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.access$getWipeTarget(com.android.systemui.authentication.domain.interactor.AuthenticationInteractor, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object getAuthenticationMethod(Continuation continuation) {
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.repository;
        return authenticationRepositoryImpl.getAuthenticationMethod(authenticationRepositoryImpl.getSelectedUserId(), continuation);
    }
}
