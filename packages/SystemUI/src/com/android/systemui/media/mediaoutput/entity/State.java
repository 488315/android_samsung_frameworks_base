package com.android.systemui.media.mediaoutput.entity;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class State {
    public static final /* synthetic */ State[] $VALUES;
    public static final State CONNECTED;
    public static final State CONNECTING;
    public static final State DISCONNECTED;
    public static final State GROUPING;
    public static final State SELECTED;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        State state = new State(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0, -1);
        State state2 = new State("CONNECTED", 1, 0);
        CONNECTED = state2;
        State state3 = new State("CONNECTING", 2, 1);
        CONNECTING = state3;
        State state4 = new State("DISCONNECTED", 3, 2);
        DISCONNECTED = state4;
        State state5 = new State("CONNECTING_FAILED", 4, 3);
        State state6 = new State("SELECTED", 5, 4);
        SELECTED = state6;
        State state7 = new State("GROUPING", 6, 5);
        GROUPING = state7;
        State[] stateArr = {state, state2, state3, state4, state5, state6, state7};
        $VALUES = stateArr;
        EnumEntriesKt.enumEntries(stateArr);
        new Companion(null);
    }

    private State(String str, int i, int i2) {
    }

    public static State valueOf(String str) {
        return (State) Enum.valueOf(State.class, str);
    }

    public static State[] values() {
        return (State[]) $VALUES.clone();
    }
}
