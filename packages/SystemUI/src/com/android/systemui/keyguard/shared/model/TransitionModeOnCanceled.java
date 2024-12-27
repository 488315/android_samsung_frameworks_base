package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionModeOnCanceled {
    public static final /* synthetic */ TransitionModeOnCanceled[] $VALUES;
    public static final TransitionModeOnCanceled LAST_VALUE;
    public static final TransitionModeOnCanceled RESET;
    public static final TransitionModeOnCanceled REVERSE;

    static {
        TransitionModeOnCanceled transitionModeOnCanceled = new TransitionModeOnCanceled("LAST_VALUE", 0);
        LAST_VALUE = transitionModeOnCanceled;
        TransitionModeOnCanceled transitionModeOnCanceled2 = new TransitionModeOnCanceled("RESET", 1);
        RESET = transitionModeOnCanceled2;
        TransitionModeOnCanceled transitionModeOnCanceled3 = new TransitionModeOnCanceled("REVERSE", 2);
        REVERSE = transitionModeOnCanceled3;
        TransitionModeOnCanceled[] transitionModeOnCanceledArr = {transitionModeOnCanceled, transitionModeOnCanceled2, transitionModeOnCanceled3};
        $VALUES = transitionModeOnCanceledArr;
        EnumEntriesKt.enumEntries(transitionModeOnCanceledArr);
    }

    private TransitionModeOnCanceled(String str, int i) {
    }

    public static TransitionModeOnCanceled valueOf(String str) {
        return (TransitionModeOnCanceled) Enum.valueOf(TransitionModeOnCanceled.class, str);
    }

    public static TransitionModeOnCanceled[] values() {
        return (TransitionModeOnCanceled[]) $VALUES.clone();
    }
}
