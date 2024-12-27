package com.android.systemui.haptics.qs;

import android.os.VibrationEffect;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl$initLongPressEffectCallback$1;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSLongPressEffect {
    public QSTileViewImpl$initLongPressEffectCallback$1 callback;
    public final int[] durations;
    public int effectDuration;
    public Expandable expandable;
    public final KeyguardStateController keyguardStateController;
    public VibrationEffect longPressHint;
    public QSTile qsTile;
    public final VibrationEffect snapEffect;
    public State state = State.IDLE;
    public final VibratorHelper vibratorHelper;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State IDLE;
        public static final State RUNNING_BACKWARDS;
        public static final State RUNNING_FORWARD;
        public static final State TIMEOUT_WAIT;

        static {
            State state = new State("IDLE", 0);
            IDLE = state;
            State state2 = new State("TIMEOUT_WAIT", 1);
            TIMEOUT_WAIT = state2;
            State state3 = new State("RUNNING_FORWARD", 2);
            RUNNING_FORWARD = state3;
            State state4 = new State("RUNNING_BACKWARDS", 3);
            RUNNING_BACKWARDS = state4;
            State[] stateArr = {state, state2, state3, state4};
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.RUNNING_BACKWARDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.TIMEOUT_WAIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.RUNNING_FORWARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public QSLongPressEffect(VibratorHelper vibratorHelper, KeyguardStateController keyguardStateController) {
        int[] iArr;
        this.vibratorHelper = vibratorHelper;
        this.keyguardStateController = keyguardStateController;
        if (vibratorHelper != null) {
            iArr = vibratorHelper.mVibrator.getPrimitiveDurations(8, 3);
        } else {
            iArr = null;
        }
        this.durations = iArr;
        LongPressHapticBuilder.INSTANCE.getClass();
        this.snapEffect = VibrationEffect.startComposition().addPrimitive(1, 0.5f, 0).compose();
    }

    public final void setState(State state) {
        this.state = state;
    }
}
