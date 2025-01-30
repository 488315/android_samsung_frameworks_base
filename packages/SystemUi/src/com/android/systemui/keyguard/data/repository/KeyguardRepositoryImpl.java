package com.android.systemui.keyguard.data.repository;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.Position;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardRepositoryImpl implements KeyguardRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _animateBottomAreaDozingTransitions;
    public final StateFlowImpl _bottomAreaAlpha;
    public final StateFlowImpl _clockPosition;
    public final StateFlowImpl _isDozing;
    public final StateFlowImpl _isQuickSettingsVisible;
    public final StateFlowImpl _lastDozeTapToWakePosition;
    public final ReadonlyStateFlow animateBottomAreaDozingTransitions;
    public final AuthController authController;
    public final Flow biometricUnlockSource;
    public final Flow biometricUnlockState;
    public final ReadonlyStateFlow bottomAreaAlpha;
    public final ReadonlyStateFlow clockPosition;
    public final DozeParameters dozeParameters;
    public final DozeTransitionListener dozeTransitionListener;
    public final Flow dozeTransitionModel;
    public final DreamOverlayCallbackController dreamOverlayCallbackController;
    public final Flow faceSensorLocation;
    public final Flow fingerprintSensorLocation;
    public final Flow isAodAvailable;
    public final ReadonlyStateFlow isDozing;
    public final Flow isDreaming;
    public final Flow isDreamingWithOverlay;
    public final Flow isKeyguardGoingAway;
    public final Flow isKeyguardOccluded;
    public final Flow isKeyguardShowing;
    public final Flow isKeyguardUnlocked;
    public final ReadonlyStateFlow isQuickSettingsVisible;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ReadonlyStateFlow lastDozeTapToWakePosition;
    public final Flow linearDozeAmount;
    public final Flow statusBarState;
    public final Flow wakefulness;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                iArr[DozeMachine.State.SCRIM_AOD_ENDED.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[DozeMachine.State.DOZE_TRANSITION_ENDED.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[DozeMachine.State.DOZE_DISPLAY_STATE_ON.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardRepositoryImpl(StatusBarStateController statusBarStateController, WakefulnessLifecycle wakefulnessLifecycle, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeTransitionListener dozeTransitionListener, DozeParameters dozeParameters, AuthController authController, DreamOverlayCallbackController dreamOverlayCallbackController, CoroutineDispatcher coroutineDispatcher) {
        this.keyguardStateController = keyguardStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.dozeTransitionListener = dozeTransitionListener;
        this.dozeParameters = dozeParameters;
        this.authController = authController;
        this.dreamOverlayCallbackController = dreamOverlayCallbackController;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._animateBottomAreaDozingTransitions = MutableStateFlow;
        this.animateBottomAreaDozingTransitions = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._bottomAreaAlpha = MutableStateFlow2;
        this.bottomAreaAlpha = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(new Position(0, 0));
        this._clockPosition = MutableStateFlow3;
        this.clockPosition = FlowKt.asStateFlow(MutableStateFlow3);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardRepositoryImpl$isKeyguardShowing$1 keyguardRepositoryImpl$isKeyguardShowing$1 = new KeyguardRepositoryImpl$isKeyguardShowing$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isKeyguardShowing = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(keyguardRepositoryImpl$isKeyguardShowing$1));
        this.isAodAvailable = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isAodAvailable$1(this, null)));
        this.isKeyguardOccluded = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardOccluded$1(this, null)));
        this.isKeyguardUnlocked = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardUnlocked$1(this, null)));
        this.isKeyguardGoingAway = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardGoingAway$1(this, null));
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(Boolean.valueOf(statusBarStateController.isDozing()));
        this._isDozing = MutableStateFlow4;
        this.isDozing = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(null);
        this._lastDozeTapToWakePosition = MutableStateFlow5;
        this.lastDozeTapToWakePosition = FlowKt.asStateFlow(MutableStateFlow5);
        this.isDreamingWithOverlay = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDreamingWithOverlay$1(this, null)));
        this.isDreaming = FlowKt.distinctUntilChanged(FlowKt.flowOn(ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDreaming$1(this, null)), coroutineDispatcher));
        this.linearDozeAmount = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$linearDozeAmount$1(statusBarStateController, null));
        this.dozeTransitionModel = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$dozeTransitionModel$1(this, null));
        this.statusBarState = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$statusBarState$1(statusBarStateController, this, null));
        this.biometricUnlockState = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$biometricUnlockState$1(biometricUnlockController, this, null));
        this.wakefulness = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$wakefulness$1(wakefulnessLifecycle, null));
        this.fingerprintSensorLocation = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$fingerprintSensorLocation$1(this, null));
        this.faceSensorLocation = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$faceSensorLocation$1(this, null));
        this.biometricUnlockSource = ConflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$biometricUnlockSource$1(this, null));
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isQuickSettingsVisible = MutableStateFlow6;
        this.isQuickSettingsVisible = FlowKt.asStateFlow(MutableStateFlow6);
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
                return DozeStateModel.SCRIM_AOD_ENDED;
            case 16:
                return DozeStateModel.DOZE_TRANSITION_ENDED;
            case 17:
                return DozeStateModel.DOZE_DISPLAY_STATE_ON;
            default:
                throw new IllegalArgumentException("Invalid DozeMachine.State: state");
        }
    }

    public static final StatusBarState access$statusBarStateIntToObject(KeyguardRepositoryImpl keyguardRepositoryImpl, int i) {
        keyguardRepositoryImpl.getClass();
        if (i == 0) {
            return StatusBarState.SHADE;
        }
        if (i == 1) {
            return StatusBarState.KEYGUARD;
        }
        if (i == 2) {
            return StatusBarState.SHADE_LOCKED;
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid StatusBarState value: ", i));
    }
}
