package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class KeyguardDone {
    public static final /* synthetic */ KeyguardDone[] $VALUES;
    public static final KeyguardDone IMMEDIATE;
    public static final KeyguardDone LATER = null;

    static {
        KeyguardDone keyguardDone = new KeyguardDone("IMMEDIATE", 0);
        IMMEDIATE = keyguardDone;
        KeyguardDone[] keyguardDoneArr = {keyguardDone, new KeyguardDone("LATER", 1)};
        $VALUES = keyguardDoneArr;
        EnumEntriesKt.enumEntries(keyguardDoneArr);
    }

    private KeyguardDone(String str, int i) {
    }

    public static KeyguardDone valueOf(String str) {
        return (KeyguardDone) Enum.valueOf(KeyguardDone.class, str);
    }

    public static KeyguardDone[] values() {
        return (KeyguardDone[]) $VALUES.clone();
    }
}
