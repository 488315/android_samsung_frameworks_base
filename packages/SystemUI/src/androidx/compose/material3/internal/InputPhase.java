package androidx.compose.material3.internal;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
final class InputPhase {
    public static final /* synthetic */ InputPhase[] $VALUES;
    public static final InputPhase Focused;
    public static final InputPhase UnfocusedEmpty;
    public static final InputPhase UnfocusedNotEmpty;

    static {
        InputPhase inputPhase = new InputPhase("Focused", 0);
        Focused = inputPhase;
        InputPhase inputPhase2 = new InputPhase("UnfocusedEmpty", 1);
        UnfocusedEmpty = inputPhase2;
        InputPhase inputPhase3 = new InputPhase("UnfocusedNotEmpty", 2);
        UnfocusedNotEmpty = inputPhase3;
        InputPhase[] inputPhaseArr = {inputPhase, inputPhase2, inputPhase3};
        $VALUES = inputPhaseArr;
        EnumEntriesKt.enumEntries(inputPhaseArr);
    }

    private InputPhase(String str, int i) {
    }

    public static InputPhase valueOf(String str) {
        return (InputPhase) Enum.valueOf(InputPhase.class, str);
    }

    public static InputPhase[] values() {
        return (InputPhase[]) $VALUES.clone();
    }
}
