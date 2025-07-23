package androidx.compose.ui.state;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ToggleableState {
    public static final /* synthetic */ ToggleableState[] $VALUES;
    public static final ToggleableState Indeterminate;
    public static final ToggleableState Off;
    public static final ToggleableState On;

    static {
        ToggleableState toggleableState = new ToggleableState("On", 0);
        On = toggleableState;
        ToggleableState toggleableState2 = new ToggleableState("Off", 1);
        Off = toggleableState2;
        ToggleableState toggleableState3 = new ToggleableState("Indeterminate", 2);
        Indeterminate = toggleableState3;
        ToggleableState[] toggleableStateArr = {toggleableState, toggleableState2, toggleableState3};
        $VALUES = toggleableStateArr;
        EnumEntriesKt.enumEntries(toggleableStateArr);
    }

    private ToggleableState(String str, int i) {
    }

    public static ToggleableState valueOf(String str) {
        return (ToggleableState) Enum.valueOf(ToggleableState.class, str);
    }

    public static ToggleableState[] values() {
        return (ToggleableState[]) $VALUES.clone();
    }
}
