package com.android.systemui.authentication.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.authentication.data.repository.AuthenticationRepository;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.authentication.shared.model.AuthenticationPatternCoordinate;
import com.android.systemui.authentication.shared.model.AuthenticationResultModel;
import com.android.systemui.authentication.shared.model.AuthenticationWipeModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$authenticate$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
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
            return AuthenticationInteractor.this.authenticate(null, false, this);
        }
    }

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$getPowerButtonInstantlyLocks$1, reason: invalid class name and case insensitive filesystem */
    final class C08041 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08041(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AuthenticationInteractor.this.getPowerButtonInstantlyLocks(this);
        }
    }

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$isInputTooShort$1, reason: invalid class name and case insensitive filesystem */
    final class C08051 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        public C08051(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            AuthenticationInteractor authenticationInteractor = AuthenticationInteractor.this;
            int i = AuthenticationInteractor.$r8$clinit;
            return authenticationInteractor.isInputTooShort(null, 0, this);
        }
    }

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$reportUnlockAttempt$1, reason: invalid class name and case insensitive filesystem */
    final class C08061 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isMatched;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08061(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isMatched = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AuthenticationInteractor.this.new C08061(this.$isMatched, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08061) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
        
            if (r5.emit(r1, r4) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AuthenticationRepository authenticationRepository = AuthenticationInteractor.this.repository;
                boolean z = this.$isMatched;
                this.label = 1;
                if (((AuthenticationRepositoryImpl) authenticationRepository).reportAuthenticationAttemptFromPrimaryBouncer(z, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl sharedFlowImpl = AuthenticationInteractor.this._onPrimaryBouncerAuthenticationResult;
            Boolean boolValueOf = Boolean.valueOf(this.$isMatched);
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$shouldSkipAuthenticationAttempt$1, reason: invalid class name and case insensitive filesystem */
    final class C08071 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C08071(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            AuthenticationInteractor authenticationInteractor = AuthenticationInteractor.this;
            int i = AuthenticationInteractor.$r8$clinit;
            return authenticationInteractor.shouldSkipAuthenticationAttempt(null, false, 0, this);
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
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.isAutoConfirmEnabled = readonlyStateFlowStateIn;
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

                /* JADX WARN: Code restructure failed: missing block: B:27:0x0086, code lost:
                
                    if (r8.emit(r9, r0) != r1) goto L29;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    AnonymousClass2 anonymousClass2;
                    boolean z;
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
                    Object pinLength = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(pinLength);
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        AuthenticationRepository authenticationRepository = this.this$0.repository;
                        anonymousClass1.L$0 = this;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.Z$0 = zBooleanValue;
                        anonymousClass1.label = 1;
                        pinLength = ((AuthenticationRepositoryImpl) authenticationRepository).getPinLength(anonymousClass1);
                        if (pinLength != coroutineSingletons) {
                            anonymousClass2 = this;
                            z = zBooleanValue;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(pinLength);
                        return Unit.INSTANCE;
                    }
                    z = anonymousClass1.Z$0;
                    flowCollector = (FlowCollector) anonymousClass1.L$1;
                    anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(pinLength);
                    int iIntValue = ((Number) pinLength).intValue();
                    if (z) {
                        ((AuthenticationRepositoryImpl) anonymousClass2.this$0.repository).getClass();
                        if (iIntValue != 6) {
                        }
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 2;
                    }
                    pinLength = null;
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isPatternVisible = authenticationRepositoryImpl.isPatternVisible;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onPrimaryBouncerAuthenticationResult = sharedFlowImplMutableSharedFlow$default;
        this.onPrimaryBouncerAuthenticationResult = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onAuthenticationResult = sharedFlowImplMutableSharedFlow$default2;
        this.onAuthenticationResult = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default2);
        this.isPinEnhancedPrivacyEnabled = authenticationRepositoryImpl.isPinEnhancedPrivacyEnabled;
        ReadonlyStateFlow readonlyStateFlow = authenticationRepositoryImpl.failedAuthenticationAttempts;
        this.failedAuthenticationAttempts = readonlyStateFlow;
        this.upcomingWipe = new AuthenticationInteractor$special$$inlined$map$2(readonlyStateFlow, this);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getWipeTarget(AuthenticationInteractor authenticationInteractor, ContinuationImpl continuationImpl) {
        AuthenticationInteractor$getWipeTarget$1 authenticationInteractor$getWipeTarget$1;
        authenticationInteractor.getClass();
        if (continuationImpl instanceof AuthenticationInteractor$getWipeTarget$1) {
            authenticationInteractor$getWipeTarget$1 = (AuthenticationInteractor$getWipeTarget$1) continuationImpl;
            int i = authenticationInteractor$getWipeTarget$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                authenticationInteractor$getWipeTarget$1.label = i - Integer.MIN_VALUE;
            } else {
                authenticationInteractor$getWipeTarget$1 = new AuthenticationInteractor$getWipeTarget$1(authenticationInteractor, continuationImpl);
            }
        }
        Object profileWithMinFailedUnlockAttemptsForWipe = authenticationInteractor$getWipeTarget$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = authenticationInteractor$getWipeTarget$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(profileWithMinFailedUnlockAttemptsForWipe);
            authenticationInteractor$getWipeTarget$1.L$0 = authenticationInteractor;
            authenticationInteractor$getWipeTarget$1.label = 1;
            profileWithMinFailedUnlockAttemptsForWipe = ((AuthenticationRepositoryImpl) authenticationInteractor.repository).getProfileWithMinFailedUnlockAttemptsForWipe(authenticationInteractor$getWipeTarget$1);
            if (profileWithMinFailedUnlockAttemptsForWipe == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            authenticationInteractor = (AuthenticationInteractor) authenticationInteractor$getWipeTarget$1.L$0;
            ResultKt.throwOnFailure(profileWithMinFailedUnlockAttemptsForWipe);
        }
        int iIntValue = ((Number) profileWithMinFailedUnlockAttemptsForWipe).intValue();
        return iIntValue == authenticationInteractor.selectedUserInteractor.getSelectedUserId() ? iIntValue == ((UserRepositoryImpl) authenticationInteractor.selectedUserInteractor.repository).mainUserId ? AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE : AuthenticationWipeModel.WipeTarget.User.INSTANCE : iIntValue == -10000 ? AuthenticationWipeModel.WipeTarget.WholeDevice.INSTANCE : AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x01fe, code lost:
    
        if (kotlin.Unit.INSTANCE != r4) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0240, code lost:
    
        if (r0.emit(r1, r3) == r4) goto L93;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e0 A[PHI: r0
      0x01e0: PHI (r0v39 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor) = 
      (r0v36 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor)
      (r0v43 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor)
     binds: [B:73:0x01dd, B:16:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0232 A[PHI: r2
      0x0232: PHI (r2v7 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor) = 
      (r2v4 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor)
      (r2v8 com.android.systemui.authentication.domain.interactor.AuthenticationInteractor)
     binds: [B:85:0x021b, B:90:0x0231] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object authenticate(List list, boolean z, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        List list2;
        boolean z2;
        Object authenticationMethod;
        Object objIsInputTooShort;
        AuthenticationInteractor authenticationInteractor;
        AuthenticationMethodModel authenticationMethodModel;
        List list3;
        AuthenticationInteractor authenticationInteractor2;
        LockscreenCredential lockscreenCredentialCreatePattern;
        AuthenticationResultModel authenticationResultModel;
        AuthenticationResultModel authenticationResultModel2;
        AuthenticationInteractor authenticationInteractor3;
        AuthenticationInteractor authenticationInteractor4;
        SharedFlowImpl sharedFlowImpl;
        Boolean bool;
        int i;
        AuthenticationInteractor authenticationInteractor5;
        AuthenticationInteractor authenticationInteractor6 = this;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = authenticationInteractor6.new AnonymousClass1(continuationImpl);
            }
        }
        Object objShouldSkipAuthenticationAttempt = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (anonymousClass1.label) {
            case 0:
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                if (list.isEmpty()) {
                    throw new IllegalArgumentException("Input was empty!");
                }
                anonymousClass1.L$0 = authenticationInteractor6;
                list2 = list;
                anonymousClass1.L$1 = list2;
                z2 = z;
                anonymousClass1.Z$0 = z2;
                anonymousClass1.label = 1;
                authenticationMethod = authenticationInteractor6.getAuthenticationMethod(anonymousClass1);
                if (authenticationMethod != coroutineSingletons) {
                    AuthenticationMethodModel authenticationMethodModel2 = (AuthenticationMethodModel) authenticationMethod;
                    int size = list2.size();
                    anonymousClass1.L$0 = authenticationInteractor6;
                    anonymousClass1.L$1 = list2;
                    anonymousClass1.L$2 = authenticationMethodModel2;
                    anonymousClass1.Z$0 = z2;
                    anonymousClass1.label = 2;
                    objIsInputTooShort = authenticationInteractor6.isInputTooShort(authenticationMethodModel2, size, anonymousClass1);
                    if (objIsInputTooShort != coroutineSingletons) {
                        authenticationInteractor = authenticationInteractor6;
                        authenticationMethodModel = authenticationMethodModel2;
                        list3 = list2;
                        objShouldSkipAuthenticationAttempt = objIsInputTooShort;
                        if (!((Boolean) objShouldSkipAuthenticationAttempt).booleanValue()) {
                            return AuthenticationResult.SKIPPED;
                        }
                        int size2 = list3.size();
                        anonymousClass1.L$0 = authenticationInteractor;
                        anonymousClass1.L$1 = list3;
                        anonymousClass1.L$2 = authenticationMethodModel;
                        anonymousClass1.label = 3;
                        objShouldSkipAuthenticationAttempt = authenticationInteractor.shouldSkipAuthenticationAttempt(authenticationMethodModel, z2, size2, anonymousClass1);
                        if (objShouldSkipAuthenticationAttempt != coroutineSingletons) {
                            authenticationInteractor2 = authenticationInteractor;
                            if (!((Boolean) objShouldSkipAuthenticationAttempt).booleanValue()) {
                                return AuthenticationResult.SKIPPED;
                            }
                            authenticationInteractor2.getClass();
                            if (authenticationMethodModel instanceof AuthenticationMethodModel.Pin) {
                                lockscreenCredentialCreatePattern = LockscreenCredential.createPin(CollectionsKt___CollectionsKt.joinToString$default(list3, "", null, null, null, 62));
                            } else if (authenticationMethodModel instanceof AuthenticationMethodModel.Password) {
                                lockscreenCredentialCreatePattern = LockscreenCredential.createPassword(CollectionsKt___CollectionsKt.joinToString$default(list3, "", null, null, null, 62));
                            } else if (authenticationMethodModel instanceof AuthenticationMethodModel.Pattern) {
                                List list4 = list3;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                                Iterator it = list4.iterator();
                                while (it.hasNext()) {
                                    arrayList.add((AuthenticationPatternCoordinate) it.next());
                                }
                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                                int size3 = arrayList.size();
                                int i3 = 0;
                                while (i3 < size3) {
                                    Object obj = arrayList.get(i3);
                                    i3++;
                                    AuthenticationPatternCoordinate authenticationPatternCoordinate = (AuthenticationPatternCoordinate) obj;
                                    arrayList2.add(LockPatternView.Cell.of(authenticationPatternCoordinate.y, authenticationPatternCoordinate.x));
                                }
                                lockscreenCredentialCreatePattern = LockscreenCredential.createPattern(arrayList2);
                            } else {
                                lockscreenCredentialCreatePattern = null;
                            }
                            if (lockscreenCredentialCreatePattern == null) {
                                return AuthenticationResult.SKIPPED;
                            }
                            anonymousClass1.L$0 = authenticationInteractor2;
                            anonymousClass1.L$1 = lockscreenCredentialCreatePattern;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.label = 4;
                            objShouldSkipAuthenticationAttempt = ((AuthenticationRepositoryImpl) authenticationInteractor2.repository).checkCredential(lockscreenCredentialCreatePattern, anonymousClass1);
                            if (objShouldSkipAuthenticationAttempt != coroutineSingletons) {
                                authenticationResultModel = (AuthenticationResultModel) objShouldSkipAuthenticationAttempt;
                                lockscreenCredentialCreatePattern.zeroize();
                                if (!authenticationResultModel.isSuccessful) {
                                    AuthenticationRepository authenticationRepository = authenticationInteractor2.repository;
                                    anonymousClass1.L$0 = authenticationInteractor2;
                                    anonymousClass1.L$1 = null;
                                    anonymousClass1.label = 5;
                                    if (((AuthenticationRepositoryImpl) authenticationRepository).reportAuthenticationAttempt(true, anonymousClass1) != coroutineSingletons) {
                                        authenticationInteractor4 = authenticationInteractor2;
                                        sharedFlowImpl = authenticationInteractor4._onAuthenticationResult;
                                        bool = Boolean.TRUE;
                                        anonymousClass1.L$0 = authenticationInteractor4;
                                        anonymousClass1.label = 6;
                                        if (sharedFlowImpl.emit(bool, anonymousClass1) != coroutineSingletons) {
                                            Duration.Companion companion = Duration.Companion;
                                            long duration = DurationKt.toDuration(5, DurationUnit.SECONDS);
                                            anonymousClass1.L$0 = null;
                                            anonymousClass1.label = 7;
                                            authenticationInteractor4.getClass();
                                            CoroutineTracingKt.launchTraced$default(authenticationInteractor4.applicationScope, authenticationInteractor4.backgroundDispatcher, null, new AuthenticationInteractor$initiateGarbageCollection$2(duration, null), 5);
                                            break;
                                        }
                                    }
                                } else {
                                    AuthenticationRepository authenticationRepository2 = authenticationInteractor2.repository;
                                    anonymousClass1.L$0 = authenticationInteractor2;
                                    anonymousClass1.L$1 = authenticationResultModel;
                                    anonymousClass1.label = 8;
                                    if (((AuthenticationRepositoryImpl) authenticationRepository2).reportAuthenticationAttempt(false, anonymousClass1) != coroutineSingletons) {
                                        authenticationResultModel2 = authenticationResultModel;
                                        authenticationInteractor3 = authenticationInteractor2;
                                        i = authenticationResultModel2.lockoutDurationMs;
                                        if (i <= 0) {
                                            AuthenticationRepository authenticationRepository3 = authenticationInteractor3.repository;
                                            anonymousClass1.L$0 = authenticationInteractor3;
                                            anonymousClass1.L$1 = null;
                                            anonymousClass1.label = 9;
                                            if (((AuthenticationRepositoryImpl) authenticationRepository3).reportLockoutStarted(i, anonymousClass1) != coroutineSingletons) {
                                                authenticationInteractor5 = authenticationInteractor3;
                                                authenticationInteractor3 = authenticationInteractor5;
                                                SharedFlowImpl sharedFlowImpl2 = authenticationInteractor3._onAuthenticationResult;
                                                Boolean bool2 = Boolean.FALSE;
                                                anonymousClass1.L$0 = null;
                                                anonymousClass1.L$1 = null;
                                                anonymousClass1.label = 10;
                                                break;
                                            }
                                        } else {
                                            SharedFlowImpl sharedFlowImpl22 = authenticationInteractor3._onAuthenticationResult;
                                            Boolean bool22 = Boolean.FALSE;
                                            anonymousClass1.L$0 = null;
                                            anonymousClass1.L$1 = null;
                                            anonymousClass1.label = 10;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                boolean z3 = anonymousClass1.Z$0;
                List list5 = (List) anonymousClass1.L$1;
                AuthenticationInteractor authenticationInteractor7 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                z2 = z3;
                authenticationInteractor6 = authenticationInteractor7;
                authenticationMethod = objShouldSkipAuthenticationAttempt;
                list2 = list5;
                AuthenticationMethodModel authenticationMethodModel22 = (AuthenticationMethodModel) authenticationMethod;
                int size4 = list2.size();
                anonymousClass1.L$0 = authenticationInteractor6;
                anonymousClass1.L$1 = list2;
                anonymousClass1.L$2 = authenticationMethodModel22;
                anonymousClass1.Z$0 = z2;
                anonymousClass1.label = 2;
                objIsInputTooShort = authenticationInteractor6.isInputTooShort(authenticationMethodModel22, size4, anonymousClass1);
                if (objIsInputTooShort != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                boolean z4 = anonymousClass1.Z$0;
                AuthenticationMethodModel authenticationMethodModel3 = (AuthenticationMethodModel) anonymousClass1.L$2;
                list3 = (List) anonymousClass1.L$1;
                authenticationInteractor = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                z2 = z4;
                authenticationMethodModel = authenticationMethodModel3;
                if (!((Boolean) objShouldSkipAuthenticationAttempt).booleanValue()) {
                }
                break;
            case 3:
                authenticationMethodModel = (AuthenticationMethodModel) anonymousClass1.L$2;
                List list6 = (List) anonymousClass1.L$1;
                AuthenticationInteractor authenticationInteractor8 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                list3 = list6;
                authenticationInteractor2 = authenticationInteractor8;
                if (!((Boolean) objShouldSkipAuthenticationAttempt).booleanValue()) {
                }
                break;
            case 4:
                lockscreenCredentialCreatePattern = (LockscreenCredential) anonymousClass1.L$1;
                authenticationInteractor2 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                authenticationResultModel = (AuthenticationResultModel) objShouldSkipAuthenticationAttempt;
                lockscreenCredentialCreatePattern.zeroize();
                if (!authenticationResultModel.isSuccessful) {
                }
                return coroutineSingletons;
            case 5:
                authenticationInteractor4 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                sharedFlowImpl = authenticationInteractor4._onAuthenticationResult;
                bool = Boolean.TRUE;
                anonymousClass1.L$0 = authenticationInteractor4;
                anonymousClass1.label = 6;
                if (sharedFlowImpl.emit(bool, anonymousClass1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                authenticationInteractor4 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                Duration.Companion companion2 = Duration.Companion;
                long duration2 = DurationKt.toDuration(5, DurationUnit.SECONDS);
                anonymousClass1.L$0 = null;
                anonymousClass1.label = 7;
                authenticationInteractor4.getClass();
                CoroutineTracingKt.launchTraced$default(authenticationInteractor4.applicationScope, authenticationInteractor4.backgroundDispatcher, null, new AuthenticationInteractor$initiateGarbageCollection$2(duration2, null), 5);
                break;
            case 7:
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                return AuthenticationResult.SUCCEEDED;
            case 8:
                authenticationResultModel2 = (AuthenticationResultModel) anonymousClass1.L$1;
                authenticationInteractor3 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                i = authenticationResultModel2.lockoutDurationMs;
                if (i <= 0) {
                }
                return coroutineSingletons;
            case 9:
                authenticationInteractor5 = (AuthenticationInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                authenticationInteractor3 = authenticationInteractor5;
                SharedFlowImpl sharedFlowImpl222 = authenticationInteractor3._onAuthenticationResult;
                Boolean bool222 = Boolean.FALSE;
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.label = 10;
                break;
            case 10:
                ResultKt.throwOnFailure(objShouldSkipAuthenticationAttempt);
                return AuthenticationResult.FAILED;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final Object getAuthenticationMethod(ContinuationImpl continuationImpl) {
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.repository;
        return authenticationRepositoryImpl.getAuthenticationMethod(authenticationRepositoryImpl.getSelectedUserId(), continuationImpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPowerButtonInstantlyLocks(ContinuationImpl continuationImpl) {
        C08041 c08041;
        if (continuationImpl instanceof C08041) {
            c08041 = (C08041) continuationImpl;
            int i = c08041.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08041.label = i - Integer.MIN_VALUE;
            } else {
                c08041 = new C08041(continuationImpl);
            }
        }
        Object authenticationMethod = c08041.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08041.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(authenticationMethod);
            c08041.L$0 = this;
            c08041.label = 1;
            authenticationMethod = getAuthenticationMethod(c08041);
            if (authenticationMethod != coroutineSingletons) {
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(authenticationMethod);
            return authenticationMethod;
        }
        this = (AuthenticationInteractor) c08041.L$0;
        ResultKt.throwOnFailure(authenticationMethod);
        if (!((AuthenticationMethodModel) authenticationMethod).isSecure) {
            return Boolean.TRUE;
        }
        AuthenticationRepository authenticationRepository = this.repository;
        c08041.L$0 = null;
        c08041.label = 2;
        Object powerButtonInstantlyLocks = ((AuthenticationRepositoryImpl) authenticationRepository).getPowerButtonInstantlyLocks(c08041);
        return powerButtonInstantlyLocks == coroutineSingletons ? coroutineSingletons : powerButtonInstantlyLocks;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isInputTooShort(AuthenticationMethodModel authenticationMethodModel, int i, ContinuationImpl continuationImpl) {
        C08051 c08051;
        if (continuationImpl instanceof C08051) {
            c08051 = (C08051) continuationImpl;
            int i2 = c08051.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c08051.label = i2 - Integer.MIN_VALUE;
            } else {
                c08051 = new C08051(continuationImpl);
            }
        }
        Object pinLength = c08051.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c08051.label;
        boolean z = false;
        if (i3 == 0) {
            ResultKt.throwOnFailure(pinLength);
            boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
            AuthenticationRepository authenticationRepository = this.repository;
            if (zAreEqual) {
                if (i < ((AuthenticationRepositoryImpl) authenticationRepository).minPatternLength) {
                    z = true;
                }
            } else if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
                if (i < ((AuthenticationRepositoryImpl) authenticationRepository).minPasswordLength) {
                }
            } else if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
                c08051.I$0 = i;
                c08051.label = 1;
                pinLength = ((AuthenticationRepositoryImpl) authenticationRepository).getPinLength(c08051);
                if (pinLength == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Boolean.valueOf(z);
        }
        if (i3 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i = c08051.I$0;
        ResultKt.throwOnFailure(pinLength);
        if (i < ((Number) pinLength).intValue()) {
        }
        return Boolean.valueOf(z);
    }

    public final void reportUnlockAttempt(boolean z) {
        Log.d("AuthenticationInteractor", "reportUnlockAttempt " + z);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C08061(z, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0092, code lost:
    
        if (r14 == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a9, code lost:
    
        if (r14 != r1) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object shouldSkipAuthenticationAttempt(AuthenticationMethodModel authenticationMethodModel, boolean z, int i, ContinuationImpl continuationImpl) {
        C08071 c08071;
        if (continuationImpl instanceof C08071) {
            c08071 = (C08071) continuationImpl;
            int i2 = c08071.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c08071.label = i2 - Integer.MIN_VALUE;
            } else {
                c08071 = new C08071(continuationImpl);
            }
        }
        Object objIsInputTooShort = c08071.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c08071.label;
        boolean z2 = true;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objIsInputTooShort);
            AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.repository;
            long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
            Long lValueOf = Long.valueOf(lockoutAttemptDeadline);
            if (authenticationRepositoryImpl.clock.elapsedRealtime() >= lockoutAttemptDeadline) {
                lValueOf = null;
            }
            if (lValueOf == null && (!z || ((Boolean) this.isAutoConfirmEnabled.$$delegate_0.getValue()).booleanValue())) {
                if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
                    c08071.L$0 = this;
                    c08071.L$1 = authenticationMethodModel;
                    c08071.Z$0 = z;
                    c08071.I$0 = i;
                    c08071.label = 1;
                    objIsInputTooShort = isInputTooShort(authenticationMethodModel, i, c08071);
                } else {
                    c08071.L$0 = null;
                    c08071.L$1 = null;
                    c08071.label = 2;
                    objIsInputTooShort = this.isInputTooShort(authenticationMethodModel, i, c08071);
                }
                return coroutineSingletons;
            }
            return Boolean.valueOf(z2);
        }
        if (i3 != 1) {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objIsInputTooShort);
            if (!((Boolean) objIsInputTooShort).booleanValue()) {
                z2 = false;
            }
            return Boolean.valueOf(z2);
        }
        i = c08071.I$0;
        z = c08071.Z$0;
        authenticationMethodModel = (AuthenticationMethodModel) c08071.L$1;
        this = (AuthenticationInteractor) c08071.L$0;
        ResultKt.throwOnFailure(objIsInputTooShort);
        if (((Boolean) objIsInputTooShort).booleanValue()) {
            z2 = z;
            return Boolean.valueOf(z2);
        }
        c08071.L$0 = null;
        c08071.L$1 = null;
        c08071.label = 2;
        objIsInputTooShort = this.isInputTooShort(authenticationMethodModel, i, c08071);
    }
}
