package com.android.systemui.lifecycle;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class WindowLifecycleState {
    public static final /* synthetic */ WindowLifecycleState[] $VALUES;
    public static final WindowLifecycleState ATTACHED;
    public static final WindowLifecycleState FOCUSED;
    public static final WindowLifecycleState VISIBLE;

    static {
        WindowLifecycleState windowLifecycleState = new WindowLifecycleState("ATTACHED", 0);
        ATTACHED = windowLifecycleState;
        WindowLifecycleState windowLifecycleState2 = new WindowLifecycleState("VISIBLE", 1);
        VISIBLE = windowLifecycleState2;
        WindowLifecycleState windowLifecycleState3 = new WindowLifecycleState("FOCUSED", 2);
        FOCUSED = windowLifecycleState3;
        WindowLifecycleState[] windowLifecycleStateArr = {windowLifecycleState, windowLifecycleState2, windowLifecycleState3};
        $VALUES = windowLifecycleStateArr;
        EnumEntriesKt.enumEntries(windowLifecycleStateArr);
    }

    private WindowLifecycleState(String str, int i) {
    }

    public static WindowLifecycleState valueOf(String str) {
        return (WindowLifecycleState) Enum.valueOf(WindowLifecycleState.class, str);
    }

    public static WindowLifecycleState[] values() {
        return (WindowLifecycleState[]) $VALUES.clone();
    }
}
