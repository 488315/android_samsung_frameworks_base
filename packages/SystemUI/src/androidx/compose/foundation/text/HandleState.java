package androidx.compose.foundation.text;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HandleState {
    public static final /* synthetic */ HandleState[] $VALUES;
    public static final HandleState Cursor;
    public static final HandleState None;
    public static final HandleState Selection;

    static {
        HandleState handleState = new HandleState("None", 0);
        None = handleState;
        HandleState handleState2 = new HandleState("Selection", 1);
        Selection = handleState2;
        HandleState handleState3 = new HandleState("Cursor", 2);
        Cursor = handleState3;
        HandleState[] handleStateArr = {handleState, handleState2, handleState3};
        $VALUES = handleStateArr;
        EnumEntriesKt.enumEntries(handleStateArr);
    }

    private HandleState(String str, int i) {
    }

    public static HandleState valueOf(String str) {
        return (HandleState) Enum.valueOf(HandleState.class, str);
    }

    public static HandleState[] values() {
        return (HandleState[]) $VALUES.clone();
    }
}
