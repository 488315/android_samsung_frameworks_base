package com.android.systemui.display.domain.interactor;

import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ConnectedDisplayInteractor {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State CONNECTED;
        public static final State CONNECTED_SECURE;
        public static final State DISCONNECTED;

        static {
            State state = new State("DISCONNECTED", 0);
            DISCONNECTED = state;
            State state2 = new State("CONNECTED", 1);
            CONNECTED = state2;
            State state3 = new State("CONNECTED_SECURE", 2);
            CONNECTED_SECURE = state3;
            State[] stateArr = {state, state2, state3};
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
}
