package com.android.systemui.authentication.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.authentication.data.repository.AuthenticationRepository;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AuthenticationInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _onAuthenticationResult;
    public final SharedFlowImpl _onPrimaryBouncerAuthenticationResult;
    public final CoroutineScope applicationScope;
    public final Flow authenticationMethod;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow failedAuthenticationAttempts;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isAutoConfirmEnabled;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isPinEnhancedPrivacyEnabled;
    public final ReadonlySharedFlow onAuthenticationResult;
    public final ReadonlySharedFlow onPrimaryBouncerAuthenticationResult;
    public final AuthenticationRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final AuthenticationInteractor$special$$inlined$map$2 upcomingWipe;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AuthenticationInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, AuthenticationRepository authenticationRepository, SelectedUserInteractor selectedUserInteractor, TableLogBuffer tableLogBuffer) {
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = authenticationRepository;
        this.selectedUserInteractor = selectedUserInteractor;
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) authenticationRepository;
        this.authenticationMethod = DiffableKt.logDiffsForTable(authenticationRepositoryImpl.authenticationMethod, tableLogBuffer, "", AuthenticationMethodModel.None.INSTANCE);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(authenticationRepositoryImpl.isAutoConfirmFeatureEnabled, authenticationRepositoryImpl.hasLockoutOccurred, new AuthenticationInteractor$isAutoConfirmEnabled$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.isAutoConfirmEnabled = stateIn;
        this.hintedPinLength = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:20:0x0078, code lost:
                
                    if (r4 == 6) goto L26;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:22:0x0086, code lost:
                
                    if (r8.emit(r9, r0) != r1) goto L29;
                 */
                /* JADX WARN: Removed duplicated region for block: B:19:0x006e  */
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
                        goto L89
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
                        goto L88
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
                        if (r7 == 0) goto L7b
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r7 = r2.this$0
                        com.android.systemui.authentication.data.repository.AuthenticationRepository r7 = r7.repository
                        com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r7 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r7
                        r7.getClass()
                        r7 = 6
                        if (r4 != r7) goto L7b
                        goto L7c
                    L7b:
                        r9 = r5
                    L7c:
                        r0.L$0 = r5
                        r0.L$1 = r5
                        r0.label = r3
                        java.lang.Object r7 = r8.emit(r9, r0)
                        if (r7 != r1) goto L89
                    L88:
                        return r1
                    L89:
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
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onPrimaryBouncerAuthenticationResult = MutableSharedFlow$default;
        this.onPrimaryBouncerAuthenticationResult = FlowKt.asSharedFlow(MutableSharedFlow$default);
        SharedFlowImpl MutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onAuthenticationResult = MutableSharedFlow$default2;
        this.onAuthenticationResult = FlowKt.asSharedFlow(MutableSharedFlow$default2);
        this.isPinEnhancedPrivacyEnabled = authenticationRepositoryImpl.isPinEnhancedPrivacyEnabled;
        ReadonlyStateFlow readonlyStateFlow = authenticationRepositoryImpl.failedAuthenticationAttempts;
        this.failedAuthenticationAttempts = readonlyStateFlow;
        this.upcomingWipe = new AuthenticationInteractor$special$$inlined$map$2(readonlyStateFlow, this);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getWipeTarget(com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
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
            return r1
        L48:
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r0 = r4.selectedUserInteractor
            com.android.systemui.user.data.repository.UserRepository r0 = r0.repository
            com.android.systemui.user.data.repository.UserRepositoryImpl r0 = (com.android.systemui.user.data.repository.UserRepositoryImpl) r0
            int r0 = r0.mainUserId
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r4 = r4.selectedUserInteractor
            int r4 = r4.getSelectedUserId()
            if (r5 != r4) goto L66
            if (r5 != r0) goto L63
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$WholeDevice r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE
            return r4
        L63:
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$User r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.User.INSTANCE
            return r4
        L66:
            r4 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r5 != r4) goto L6d
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$WholeDevice r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE
            return r4
        L6d:
            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$ManagedProfile r4 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.access$getWipeTarget(com.android.systemui.authentication.domain.interactor.AuthenticationInteractor, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0240, code lost:
    
        if (r0.emit(r1, r3) == r4) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x01fe, code lost:
    
        if (kotlin.Unit.INSTANCE == r4) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x01dd, code lost:
    
        if (r1.emit(r2, r3) != r4) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01b0, code lost:
    
        if (r1 == r4) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00cd, code lost:
    
        if (r10 == r4) goto L93;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object authenticate(java.util.List r19, boolean r20, kotlin.coroutines.jvm.internal.ContinuationImpl r21) {
        /*
            Method dump skipped, instructions count: 616
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.authenticate(java.util.List, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object getAuthenticationMethod(ContinuationImpl continuationImpl) {
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.repository;
        return authenticationRepositoryImpl.getAuthenticationMethod(authenticationRepositoryImpl.getSelectedUserId(), continuationImpl);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0045, code lost:
    
        if (r6 == r1) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPowerButtonInstantlyLocks(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3a
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)
            return r6
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = r5.getAuthenticationMethod(r0)
            if (r6 != r1) goto L48
            goto L5d
        L48:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r6 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r6
            boolean r6 = r6.isSecure
            if (r6 == 0) goto L5f
            com.android.systemui.authentication.data.repository.AuthenticationRepository r5 = r5.repository
            r6 = 0
            r0.L$0 = r6
            r0.label = r3
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            java.lang.Object r5 = r5.getPowerButtonInstantlyLocks(r0)
            if (r5 != r1) goto L5e
        L5d:
            return r1
        L5e:
            return r5
        L5f:
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.getPowerButtonInstantlyLocks(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0071, code lost:
    
        if (r7 >= ((java.lang.Number) r8).intValue()) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0045, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0043, code lost:
    
        if (r7 < ((com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5).minPatternLength) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0053, code lost:
    
        if (r7 < ((com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5).minPasswordLength) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isInputTooShort(com.android.systemui.authentication.shared.model.AuthenticationMethodModel r6, int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L32
            if (r2 != r4) goto L2a
            int r7 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Pattern r8 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pattern.INSTANCE
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r8)
            com.android.systemui.authentication.data.repository.AuthenticationRepository r5 = r5.repository
            if (r8 == 0) goto L47
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            int r5 = r5.minPatternLength
            if (r7 >= r5) goto L74
        L45:
            r3 = r4
            goto L74
        L47:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Password r8 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Password.INSTANCE
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r8)
            if (r8 == 0) goto L56
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            int r5 = r5.minPasswordLength
            if (r7 >= r5) goto L74
            goto L45
        L56:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Pin r8 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pin.INSTANCE
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r8)
            if (r6 == 0) goto L74
            r0.I$0 = r7
            r0.label = r4
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            java.lang.Object r8 = r5.getPinLength(r0)
            if (r8 != r1) goto L6b
            return r1
        L6b:
            java.lang.Number r8 = (java.lang.Number) r8
            int r5 = r8.intValue()
            if (r7 >= r5) goto L74
            goto L45
        L74:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.isInputTooShort(com.android.systemui.authentication.shared.model.AuthenticationMethodModel, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void reportUnlockAttempt(boolean z) {
        Log.d("AuthenticationInteractor", "reportUnlockAttempt " + z);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AuthenticationInteractor$reportUnlockAttempt$1(this, z, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a9, code lost:
    
        if (r14 == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ab, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0092, code lost:
    
        if (r14 == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object shouldSkipAuthenticationAttempt(com.android.systemui.authentication.shared.model.AuthenticationMethodModel r11, boolean r12, int r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1
            if (r0 == 0) goto L13
            r0 = r14
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1 r0 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1 r0 = new com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1
            r0.<init>(r10, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L45
            if (r2 == r5) goto L34
            if (r2 != r4) goto L2c
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lac
        L2c:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L34:
            int r13 = r0.I$0
            boolean r12 = r0.Z$0
            java.lang.Object r10 = r0.L$1
            r11 = r10
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r11 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r11
            java.lang.Object r10 = r0.L$0
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r10 = (com.android.systemui.authentication.domain.interactor.AuthenticationInteractor) r10
            kotlin.ResultKt.throwOnFailure(r14)
            goto L95
        L45:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.systemui.authentication.data.repository.AuthenticationRepository r14 = r10.repository
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r14 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r14
            com.android.internal.widget.LockPatternUtils r2 = r14.lockPatternUtils
            int r6 = r14.getSelectedUserId()
            long r6 = r2.getLockoutAttemptDeadline(r6)
            java.lang.Long r2 = java.lang.Long.valueOf(r6)
            com.android.systemui.util.time.SystemClock r14 = r14.clock
            long r8 = r14.elapsedRealtime()
            int r14 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r14 >= 0) goto L65
            goto L66
        L65:
            r2 = r3
        L66:
            if (r2 == 0) goto L69
            goto Lb6
        L69:
            if (r12 == 0) goto L7c
            kotlinx.coroutines.flow.ReadonlyStateFlow r14 = r10.isAutoConfirmEnabled
            kotlinx.coroutines.flow.StateFlow r14 = r14.$$delegate_0
            java.lang.Object r14 = r14.getValue()
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r14 = r14.booleanValue()
            if (r14 != 0) goto L7c
            goto Lb6
        L7c:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Pin r14 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pin.INSTANCE
            boolean r14 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r14)
            if (r14 == 0) goto L9f
            r0.L$0 = r10
            r0.L$1 = r11
            r0.Z$0 = r12
            r0.I$0 = r13
            r0.label = r5
            java.lang.Object r14 = r10.isInputTooShort(r11, r13, r0)
            if (r14 != r1) goto L95
            goto Lab
        L95:
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r14 = r14.booleanValue()
            if (r14 == 0) goto L9f
            r5 = r12
            goto Lb6
        L9f:
            r0.L$0 = r3
            r0.L$1 = r3
            r0.label = r4
            java.lang.Object r14 = r10.isInputTooShort(r11, r13, r0)
            if (r14 != r1) goto Lac
        Lab:
            return r1
        Lac:
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r10 = r14.booleanValue()
            if (r10 == 0) goto Lb5
            goto Lb6
        Lb5:
            r5 = 0
        Lb6:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r5)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor.shouldSkipAuthenticationAttempt(com.android.systemui.authentication.shared.model.AuthenticationMethodModel, boolean, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
