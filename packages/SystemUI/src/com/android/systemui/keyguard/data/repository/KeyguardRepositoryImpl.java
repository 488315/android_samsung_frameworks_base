package com.android.systemui.keyguard.data.repository;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.keyguard.shared.model.DismissAction;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardRepositoryImpl implements KeyguardRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _animateBottomAreaDozingTransitions;
    public final StateFlowImpl _biometricUnlockState;
    public final StateFlowImpl _canIgnoreAuthAndReturnToGone;
    public final StateFlowImpl _dismissAction;
    public final StateFlowImpl _dozeTimeTick;
    public final StateFlowImpl _isAodAvailable;
    public final StateFlowImpl _isDozing;
    public final StateFlowImpl _isKeyguardEnabled;
    public final StateFlowImpl _isQuickSettingsVisible;
    public final StateFlowImpl _keyguardAlpha;
    public final SharedFlowImpl _keyguardDone;
    public final StateFlowImpl _lastDozeTapToWakePosition;
    public final Flow _preSceneLinearDozeAmount;
    public final StateFlowImpl ambientIndicationVisible;
    public final ReadonlyStateFlow animateBottomAreaDozingTransitions;
    public final AuthController authController;
    public final ReadonlyStateFlow biometricUnlockState;
    public final ReadonlyStateFlow canIgnoreAuthAndReturnToGone;
    public final ReadonlyStateFlow dismissAction;
    public final ReadonlyStateFlow dozeTimeTick;
    public final DozeTransitionListener dozeTransitionListener;
    public final Flow dozeTransitionModel;
    public final DreamOverlayCallbackController dreamOverlayCallbackController;
    public final ReadonlyStateFlow faceSensorLocation;
    public final Flow fingerprintSensorLocation;
    public final ReadonlyStateFlow isAodAvailable;
    public final ReadonlyStateFlow isDozing;
    public final StateFlowImpl isDreaming;
    public final Flow isDreamingWithOverlay;
    public final Flow isEncryptedOrLockdown;
    public final StateFlowImpl isKeyguardDismissible;
    public final ReadonlyStateFlow isKeyguardEnabled;
    public final StateFlowImpl isKeyguardGoingAway;
    public final StateFlowImpl isKeyguardOccluded;
    public final StateFlowImpl isKeyguardShowing;
    public final ReadonlyStateFlow isQuickSettingsVisible;
    public final ReadonlySharedFlow keyguardDone;
    public final SharedFlowImpl keyguardDoneAnimationsFinished;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ReadonlyStateFlow lastDozeTapToWakePosition;
    public final StateFlowImpl lastRootViewTapPosition;
    public final StateFlowImpl onCameraLaunchDetected;
    public final StateFlowImpl panelAlpha;
    public final StateFlowImpl showDismissibleKeyguard;
    public final ReadonlyStateFlow statusBarState;
    public final SystemClock systemClock;
    public final StateFlowImpl topClippingBounds;
    public final UserTracker userTracker;
    public final StateFlowImpl zoomOut;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardRepositoryImpl.this.new AnonymousClass1(continuation);
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
                StateFlowImpl stateFlowImpl = KeyguardRepositoryImpl.this.isKeyguardShowing;
                C01181 c01181 = new FlowCollector() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(c01181, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            try {
                iArr[DozeMachine.State.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DozeMachine.State.FINISH.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[DozeMachine.State.DOZE_MOD.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[DozeMachine.State.DOZE_TRANSITION_ENDED.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[DozeMachine.State.DOZE_DISPLAY_STATE_ON.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$callback$1, java.lang.Object] */
    public KeyguardRepositoryImpl(StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeTransitionListener dozeTransitionListener, AuthController authController, DreamOverlayCallbackController dreamOverlayCallbackController, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SystemClock systemClock, FacePropertyRepository facePropertyRepository, UserTracker userTracker, LockPatternUtils lockPatternUtils) {
        this.keyguardStateController = keyguardStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.dozeTransitionListener = dozeTransitionListener;
        this.authController = authController;
        this.dreamOverlayCallbackController = dreamOverlayCallbackController;
        this.systemClock = systemClock;
        this.userTracker = userTracker;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(DismissAction.None.INSTANCE);
        this._dismissAction = MutableStateFlow;
        this.dismissAction = FlowKt.asStateFlow(MutableStateFlow);
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._keyguardDone = MutableSharedFlow$default;
        this.keyguardDone = FlowKt.asSharedFlow(MutableSharedFlow$default);
        this.keyguardDoneAnimationsFinished = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._animateBottomAreaDozingTransitions = MutableStateFlow2;
        this.animateBottomAreaDozingTransitions = FlowKt.asStateFlow(MutableStateFlow2);
        Float valueOf = Float.valueOf(1.0f);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(valueOf);
        this._keyguardAlpha = MutableStateFlow3;
        FlowKt.asStateFlow(MutableStateFlow3);
        this.onCameraLaunchDetected = StateFlowKt.MutableStateFlow(new CameraLaunchSourceModel(null, 0L, 3, null));
        this.panelAlpha = StateFlowKt.MutableStateFlow(valueOf);
        this.zoomOut = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this.topClippingBounds = StateFlowKt.MutableStateFlow(null);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
        this.isKeyguardShowing = StateFlowKt.MutableStateFlow(Boolean.valueOf(keyguardStateControllerImpl.mShowing));
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._isAodAvailable = MutableStateFlow4;
        this.isAodAvailable = FlowKt.asStateFlow(MutableStateFlow4);
        this.isKeyguardOccluded = StateFlowKt.MutableStateFlow(Boolean.valueOf(keyguardStateControllerImpl.mOccluded));
        this.isKeyguardDismissible = StateFlowKt.MutableStateFlow(Boolean.valueOf(keyguardStateController.isUnlocked()));
        KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) keyguardStateController;
        this.isKeyguardGoingAway = StateFlowKt.MutableStateFlow(Boolean.valueOf(keyguardStateControllerImpl2.mKeyguardGoingAway));
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!lockPatternUtils.isLockScreenDisabled(((UserTrackerImpl) userTracker).getUserId())));
        this._isKeyguardEnabled = MutableStateFlow5;
        this.isKeyguardEnabled = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._canIgnoreAuthAndReturnToGone = MutableStateFlow6;
        this.canIgnoreAuthAndReturnToGone = FlowKt.asStateFlow(MutableStateFlow6);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(Boolean.valueOf(statusBarStateController.isDozing()));
        this._isDozing = MutableStateFlow7;
        this.isDozing = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(0L);
        this._dozeTimeTick = MutableStateFlow8;
        this.dozeTimeTick = FlowKt.asStateFlow(MutableStateFlow8);
        this.showDismissibleKeyguard = StateFlowKt.MutableStateFlow(0L);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._lastDozeTapToWakePosition = MutableStateFlow9;
        this.lastDozeTapToWakePosition = FlowKt.asStateFlow(MutableStateFlow9);
        this.lastRootViewTapPosition = StateFlowKt.MutableStateFlow(null);
        this.ambientIndicationVisible = StateFlowKt.MutableStateFlow(bool);
        this.isDreamingWithOverlay = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDreamingWithOverlay$1(this, null)));
        this.isDreaming = StateFlowKt.MutableStateFlow(bool);
        this._preSceneLinearDozeAmount = FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1(statusBarStateController, null));
        this.dozeTransitionModel = FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$dozeTransitionModel$1(this, null));
        final Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$isEncryptedOrLockdown$1(this, null));
        this.isEncryptedOrLockdown = FlowKt.flowOn(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRepositoryImpl$isEncryptedOrLockdown$3(this, null), new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardRepositoryImpl keyguardRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl r2 = r4.this$0
                        com.android.systemui.settings.UserTracker r2 = r2.userTracker
                        com.android.systemui.settings.UserTrackerImpl r2 = (com.android.systemui.settings.UserTrackerImpl) r2
                        int r2 = r2.getUserId()
                        if (r6 != r2) goto L50
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new KeyguardRepositoryImpl$isEncryptedOrLockdown$4(this, null)), coroutineDispatcher);
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$statusBarState$1(statusBarStateController, this, null));
        SharingStarted.Companion.getClass();
        this.statusBarState = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, statusBarStateIntToObject(statusBarStateController.getState()));
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(new BiometricUnlockModel(BiometricUnlockMode.NONE, null));
        this._biometricUnlockState = MutableStateFlow10;
        this.biometricUnlockState = FlowKt.asStateFlow(MutableStateFlow10);
        this.fingerprintSensorLocation = FlowConflatedKt.conflatedCallbackFlow(new KeyguardRepositoryImpl$fingerprintSensorLocation$1(this, null));
        this.faceSensorLocation = ((FacePropertyRepositoryImpl) facePropertyRepository).sensorLocation;
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._isQuickSettingsVisible = MutableStateFlow11;
        this.isQuickSettingsVisible = FlowKt.asStateFlow(MutableStateFlow11);
        final ?? r1 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$callback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                KeyguardRepositoryImpl keyguardRepositoryImpl = KeyguardRepositoryImpl.this;
                StateFlowImpl stateFlowImpl = keyguardRepositoryImpl.isKeyguardShowing;
                KeyguardStateController keyguardStateController2 = keyguardRepositoryImpl.keyguardStateController;
                stateFlowImpl.updateState(null, Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController2).mShowing));
                keyguardRepositoryImpl.isKeyguardOccluded.updateState(null, Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController2).mOccluded));
                keyguardRepositoryImpl.isKeyguardDismissible.updateState(null, Boolean.valueOf(keyguardStateController2.isUnlocked()));
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardRepositoryImpl keyguardRepositoryImpl = KeyguardRepositoryImpl.this;
                keyguardRepositoryImpl.isKeyguardDismissible.updateState(null, Boolean.valueOf(keyguardRepositoryImpl.keyguardStateController.isUnlocked()));
            }
        };
        keyguardStateControllerImpl2.addCallback(r1);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7).invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((KeyguardStateControllerImpl) KeyguardRepositoryImpl.this.keyguardStateController).removeCallback(r1);
                return Unit.INSTANCE;
            }
        });
    }

    public static final DozeStateModel access$dozeMachineStateToModel(KeyguardRepositoryImpl keyguardRepositoryImpl, DozeMachine.State state) {
        keyguardRepositoryImpl.getClass();
        switch (WhenMappings.$EnumSwitchMapping$0[state.ordinal()]) {
            case 1:
                return DozeStateModel.UNINITIALIZED;
            case 2:
                return DozeStateModel.INITIALIZED;
            case 3:
                return DozeStateModel.DOZE;
            case 4:
                return DozeStateModel.DOZE_SUSPEND_TRIGGERS;
            case 5:
                return DozeStateModel.DOZE_AOD;
            case 6:
                return DozeStateModel.DOZE_REQUEST_PULSE;
            case 7:
                return DozeStateModel.DOZE_PULSING;
            case 8:
                return DozeStateModel.DOZE_PULSING_BRIGHT;
            case 9:
                return DozeStateModel.DOZE_PULSE_DONE;
            case 10:
                return DozeStateModel.FINISH;
            case 11:
                return DozeStateModel.DOZE_AOD_PAUSED;
            case 12:
                return DozeStateModel.DOZE_AOD_PAUSING;
            case 13:
                return DozeStateModel.DOZE_AOD_DOCKED;
            case 14:
                return DozeStateModel.DOZE_MOD;
            case 15:
                return DozeStateModel.DOZE_TRANSITION_ENDED;
            case 16:
                return DozeStateModel.DOZE_DISPLAY_STATE_ON;
            default:
                throw new IllegalArgumentException("Invalid DozeMachine.State: state");
        }
    }

    public static StatusBarState statusBarStateIntToObject(int i) {
        if (i == 0) {
            return StatusBarState.SHADE;
        }
        if (i == 1) {
            return StatusBarState.KEYGUARD;
        }
        if (i == 2) {
            return StatusBarState.SHADE_LOCKED;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid StatusBarState value: "));
    }
}
