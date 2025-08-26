package com.android.systemui.haptics.qs;

import android.os.VibrationEffect;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QSLongPressEffect {
    public static final /* synthetic */ int $r8$clinit = 0;
    public QSTileViewImpl$initLongPressEffectCallback$1 callback;
    public final int[] durations;
    public int effectDuration;
    public QSLongPressEffect$createExpandableFromView$1 expandable;
    public final FalsingManager falsingManager;
    public final KeyguardStateController keyguardStateController;
    public final LogBuffer logBuffer;
    public VibrationEffect longPressHint;
    public QSTile qsTile;
    public final VibrationEffect snapEffect;
    public State state = State.IDLE;
    public final VibratorHelper vibratorHelper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State CLICKED;
        public static final State IDLE;
        public static final State LONG_CLICKED;
        public static final State RUNNING_BACKWARDS_FROM_CANCEL;
        public static final State RUNNING_BACKWARDS_FROM_UP;
        public static final State RUNNING_FORWARD;
        public static final State TIMEOUT_WAIT;

        static {
            State state = new State("IDLE", 0);
            IDLE = state;
            State state2 = new State("TIMEOUT_WAIT", 1);
            TIMEOUT_WAIT = state2;
            State state3 = new State("RUNNING_FORWARD", 2);
            RUNNING_FORWARD = state3;
            State state4 = new State("RUNNING_BACKWARDS_FROM_UP", 3);
            RUNNING_BACKWARDS_FROM_UP = state4;
            State state5 = new State("RUNNING_BACKWARDS_FROM_CANCEL", 4);
            RUNNING_BACKWARDS_FROM_CANCEL = state5;
            State state6 = new State("CLICKED", 5);
            CLICKED = state6;
            State state7 = new State("LONG_CLICKED", 6);
            LONG_CLICKED = state7;
            State[] stateArr = {state, state2, state3, state4, state5, state6, state7};
            $VALUES = stateArr;
            EnumEntriesKt.enumEntries(stateArr);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.LONG_CLICKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.RUNNING_BACKWARDS_FROM_UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[State.RUNNING_BACKWARDS_FROM_CANCEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[State.TIMEOUT_WAIT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[State.RUNNING_FORWARD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public QSLongPressEffect(VibratorHelper vibratorHelper, KeyguardStateController keyguardStateController, FalsingManager falsingManager, LogBuffer logBuffer) {
        int[] primitiveDurations;
        this.vibratorHelper = vibratorHelper;
        this.keyguardStateController = keyguardStateController;
        this.falsingManager = falsingManager;
        this.logBuffer = logBuffer;
        if (vibratorHelper != null) {
            primitiveDurations = vibratorHelper.mVibrator.getPrimitiveDurations(8, 3);
        } else {
            primitiveDurations = null;
        }
        this.durations = primitiveDurations;
        LongPressHapticBuilder.INSTANCE.getClass();
        this.snapEffect = VibrationEffect.startComposition().addPrimitive(1, 0.5f, 0).compose();
    }

    public final DelegateTransitionAnimatorController createTransitionControllerDelegate(final ActivityTransitionAnimator.Controller controller) {
        return new DelegateTransitionAnimatorController(controller) { // from class: com.android.systemui.haptics.qs.QSLongPressEffect$createTransitionControllerDelegate$delegated$1
            @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
            public final void onTransitionAnimationCancelled() {
                QSLongPressEffect qSLongPressEffect = this;
                if (qSLongPressEffect.state == QSLongPressEffect.State.LONG_CLICKED) {
                    qSLongPressEffect.setState(QSLongPressEffect.State.RUNNING_BACKWARDS_FROM_CANCEL);
                    QSTileViewImpl$initLongPressEffectCallback$1 qSTileViewImpl$initLongPressEffectCallback$1 = qSLongPressEffect.callback;
                    if (qSTileViewImpl$initLongPressEffectCallback$1 != null) {
                        qSTileViewImpl$initLongPressEffectCallback$1.onReverseAnimator(false);
                    }
                }
                this.delegate.onTransitionAnimationCancelled();
            }
        };
    }

    public final State getStateForClick() {
        QSTile.State state;
        QSTile.State state2;
        QSTile qSTile = this.qsTile;
        boolean z = (qSTile == null || (state2 = qSTile.getState()) == null || state2.state != 0) ? false : true;
        QSTile qSTile2 = this.qsTile;
        return (z || !((qSTile2 == null || (state = qSTile2.getState()) == null || !state.handlesLongClick) ? false : true) || ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) ? State.IDLE : State.CLICKED;
    }

    public final void logEvent(String str, State state, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLongPressEffect$$ExternalSyntheticLambda0 qSLongPressEffect$$ExternalSyntheticLambda0 = new QSLongPressEffect$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = this.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSLongPressEffect", logLevel, qSLongPressEffect$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = state.name();
        logBuffer.commit(logMessageObtain);
    }

    public final void setState(State state) {
        this.state = state;
    }
}
