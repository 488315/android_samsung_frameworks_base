package com.android.systemui.bouncer.domain.interactor;

import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.domain.interactor.AuthenticationResult;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.bouncer.shared.logging.BouncerUiEvent;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
public final class BouncerInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _onImeHiddenByUser;
    public final SharedFlowImpl _onIncorrectBouncerInput;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 bouncerExpansion;
    public final DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor;
    public final FalsingInteractor falsingInteractor;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isAutoConfirmEnabled;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isOneHandedModeSupported;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isPinEnhancedPrivacyEnabled;
    public final BouncerInteractor$special$$inlined$map$1 isUserSwitcherVisible;
    public final StateFlowImpl lastRecordedLockscreenTouchPosition;
    public final SharedFlowImpl onImeHiddenByUser;
    public final SharedFlowImpl onIncorrectBouncerInput;
    public final BouncerInteractor$special$$inlined$map$2 onLockoutStarted;
    public final BouncerInteractor$special$$inlined$filter$1 onPrimaryBouncerLockoutStarted;
    public final PowerInteractor powerInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 preferredBouncerInputSide;
    public final BouncerRepository repository;
    public final ReadonlyStateFlow scale;
    public final SessionTracker sessionTracker;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$authenticate$1, reason: invalid class name */
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
            return BouncerInteractor.this.authenticate(null, false, this);
        }
    }

    static {
        new Companion(null);
    }

    public BouncerInteractor(CoroutineScope coroutineScope, BouncerRepository bouncerRepository, AuthenticationInteractor authenticationInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, FalsingInteractor falsingInteractor, PowerInteractor powerInteractor, UiEventLogger uiEventLogger, SessionTracker sessionTracker, SceneInteractor sceneInteractor, SceneBackInteractor sceneBackInteractor, ConfigurationInteractor configurationInteractor) {
        this.applicationScope = coroutineScope;
        this.repository = bouncerRepository;
        this.authenticationInteractor = authenticationInteractor;
        this.deviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.falsingInteractor = falsingInteractor;
        this.powerInteractor = powerInteractor;
        this.uiEventLogger = uiEventLogger;
        this.sessionTracker = sessionTracker;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onIncorrectBouncerInput = sharedFlowImplMutableSharedFlow$default;
        this.onIncorrectBouncerInput = sharedFlowImplMutableSharedFlow$default;
        this.isAutoConfirmEnabled = authenticationInteractor.isAutoConfirmEnabled;
        this.hintedPinLength = authenticationInteractor.hintedPinLength;
        this.isPatternVisible = authenticationInteractor.isPatternVisible;
        this.isPinEnhancedPrivacyEnabled = authenticationInteractor.isPinEnhancedPrivacyEnabled;
        Flow flow = authenticationInteractor.authenticationMethod;
        BouncerInteractor$special$$inlined$map$1 bouncerInteractor$special$$inlined$map$1 = new BouncerInteractor$special$$inlined$map$1(flow, this);
        this.isUserSwitcherVisible = bouncerInteractor$special$$inlined$map$1;
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        this.isOneHandedModeSupported = FlowKt.combine(bouncerInteractor$special$$inlined$map$1, flow, configurationInteractorImpl.onAnyConfigurationChange, new BouncerInteractor$isOneHandedModeSupported$1(this, null));
        this.preferredBouncerInputSide = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(configurationInteractorImpl.onAnyConfigurationChange, bouncerRepository.preferredBouncerInputSide, new BouncerInteractor$preferredBouncerInputSide$1(this, null));
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._onImeHiddenByUser = sharedFlowImplMutableSharedFlow$default2;
        this.onImeHiddenByUser = sharedFlowImplMutableSharedFlow$default2;
        this.onPrimaryBouncerLockoutStarted = new BouncerInteractor$special$$inlined$filter$1(authenticationInteractor.onPrimaryBouncerAuthenticationResult, this);
        final ReadonlySharedFlow readonlySharedFlow = authenticationInteractor.onAuthenticationResult;
        this.onLockoutStarted = new BouncerInteractor$special$$inlined$map$2(new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerInteractor this$0;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerInteractor bouncerInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerInteractor;
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
                        if (!((Boolean) obj).booleanValue()) {
                            AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) this.this$0.authenticationInteractor.repository;
                            long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
                            Long lValueOf = Long.valueOf(lockoutAttemptDeadline);
                            if (authenticationRepositoryImpl.clock.elapsedRealtime() >= lockoutAttemptDeadline) {
                                lValueOf = null;
                            }
                            if (lValueOf != null) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
                Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.lastRecordedLockscreenTouchPosition = bouncerRepository.lastRecordedLockscreenTouchPosition;
        this.scale = FlowKt.asStateFlow(bouncerRepository.scale);
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = sceneBackInteractor.backScene;
        new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        SceneKey sceneKey = (SceneKey) obj;
                        if (sceneKey == null) {
                            sceneKey = Scenes.Lockscreen;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sceneKey, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = sceneBackInteractor$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.bouncerExpansion = FlowTracing.traceAsCounter$default(FlowTracing.INSTANCE, FlowKt.distinctUntilChanged(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$1(new Float[0])), "bouncer_expansion", new BouncerInteractor$$ExternalSyntheticLambda0());
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f4, code lost:
    
        if (r12 == r1) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object authenticate(List list, boolean z, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        BouncerInteractor bouncerInteractor;
        boolean z2;
        AuthenticationResult authenticationResult;
        SharedFlowImpl sharedFlowImpl;
        Unit unit;
        AuthenticationResult authenticationResult2;
        BouncerInteractor bouncerInteractor2;
        AuthenticationResult authenticationResult3;
        Set set;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object authenticationMethod = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(authenticationMethod);
            if (list.isEmpty()) {
                return AuthenticationResult.SKIPPED;
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = list;
            anonymousClass1.Z$0 = z;
            anonymousClass1.label = 1;
            authenticationMethod = this.authenticationInteractor.getAuthenticationMethod(anonymousClass1);
            if (authenticationMethod != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            z = anonymousClass1.Z$0;
            list = (List) anonymousClass1.L$1;
            this = (BouncerInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(authenticationMethod);
        } else if (i2 == 2) {
            z2 = anonymousClass1.Z$0;
            bouncerInteractor = (BouncerInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(authenticationMethod);
            authenticationResult = (AuthenticationResult) authenticationMethod;
            if (authenticationResult != AuthenticationResult.FAILED || (authenticationResult == AuthenticationResult.SKIPPED && !z2)) {
                sharedFlowImpl = bouncerInteractor._onIncorrectBouncerInput;
                unit = Unit.INSTANCE;
                anonymousClass1.L$0 = bouncerInteractor;
                anonymousClass1.L$1 = authenticationResult;
                anonymousClass1.label = 3;
                if (sharedFlowImpl.emit(unit, anonymousClass1) != coroutineSingletons) {
                    authenticationResult2 = authenticationResult;
                    bouncerInteractor2 = bouncerInteractor;
                    authenticationResult3 = authenticationResult2;
                    set = ArraysKt___ArraysKt.toSet(new AuthenticationMethodModel[]{AuthenticationMethodModel.Pin.INSTANCE, AuthenticationMethodModel.Password.INSTANCE, AuthenticationMethodModel.Pattern.INSTANCE});
                    AuthenticationInteractor authenticationInteractor = bouncerInteractor2.authenticationInteractor;
                    anonymousClass1.L$0 = bouncerInteractor2;
                    anonymousClass1.L$1 = authenticationResult3;
                    anonymousClass1.L$2 = set;
                    anonymousClass1.label = 4;
                    authenticationMethod = authenticationInteractor.getAuthenticationMethod(anonymousClass1);
                }
                return coroutineSingletons;
            }
            bouncerInteractor2 = bouncerInteractor;
            authenticationResult3 = authenticationResult;
            set = ArraysKt___ArraysKt.toSet(new AuthenticationMethodModel[]{AuthenticationMethodModel.Pin.INSTANCE, AuthenticationMethodModel.Password.INSTANCE, AuthenticationMethodModel.Pattern.INSTANCE});
            AuthenticationInteractor authenticationInteractor2 = bouncerInteractor2.authenticationInteractor;
            anonymousClass1.L$0 = bouncerInteractor2;
            anonymousClass1.L$1 = authenticationResult3;
            anonymousClass1.L$2 = set;
            anonymousClass1.label = 4;
            authenticationMethod = authenticationInteractor2.getAuthenticationMethod(anonymousClass1);
        } else {
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                set = (Set) anonymousClass1.L$2;
                authenticationResult3 = (AuthenticationResult) anonymousClass1.L$1;
                bouncerInteractor2 = (BouncerInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(authenticationMethod);
                if (set.contains(authenticationMethod)) {
                    if (authenticationResult3 == AuthenticationResult.SUCCEEDED) {
                        bouncerInteractor2.uiEventLogger.log(BouncerUiEvent.BOUNCER_PASSWORD_SUCCESS);
                        return authenticationResult3;
                    }
                    if (authenticationResult3 == AuthenticationResult.FAILED) {
                        bouncerInteractor2.uiEventLogger.log(BouncerUiEvent.BOUNCER_PASSWORD_FAILURE, bouncerInteractor2.sessionTracker.getSessionId(1));
                    }
                }
                return authenticationResult3;
            }
            authenticationResult2 = (AuthenticationResult) anonymousClass1.L$1;
            bouncerInteractor = (BouncerInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(authenticationMethod);
            bouncerInteractor2 = bouncerInteractor;
            authenticationResult3 = authenticationResult2;
            set = ArraysKt___ArraysKt.toSet(new AuthenticationMethodModel[]{AuthenticationMethodModel.Pin.INSTANCE, AuthenticationMethodModel.Password.INSTANCE, AuthenticationMethodModel.Pattern.INSTANCE});
            AuthenticationInteractor authenticationInteractor22 = bouncerInteractor2.authenticationInteractor;
            anonymousClass1.L$0 = bouncerInteractor2;
            anonymousClass1.L$1 = authenticationResult3;
            anonymousClass1.L$2 = set;
            anonymousClass1.label = 4;
            authenticationMethod = authenticationInteractor22.getAuthenticationMethod(anonymousClass1);
        }
        if (Intrinsics.areEqual(authenticationMethod, AuthenticationMethodModel.Sim.INSTANCE)) {
            return AuthenticationResult.SKIPPED;
        }
        DeferredCoroutine deferredCoroutineAsyncTraced$default = CoroutineTracingKt.asyncTraced$default(this.applicationScope, null, null, new BouncerInteractor$authenticate$authResult$1(this, list, z, null), 7);
        anonymousClass1.L$0 = this;
        anonymousClass1.L$1 = null;
        anonymousClass1.Z$0 = z;
        anonymousClass1.label = 2;
        authenticationMethod = deferredCoroutineAsyncTraced$default.awaitInternal(anonymousClass1);
        if (authenticationMethod != coroutineSingletons) {
            bouncerInteractor = this;
            z2 = z;
            authenticationResult = (AuthenticationResult) authenticationMethod;
            if (authenticationResult != AuthenticationResult.FAILED) {
            }
            sharedFlowImpl = bouncerInteractor._onIncorrectBouncerInput;
            unit = Unit.INSTANCE;
            anonymousClass1.L$0 = bouncerInteractor;
            anonymousClass1.L$1 = authenticationResult;
            anonymousClass1.label = 3;
            if (sharedFlowImpl.emit(unit, anonymousClass1) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
