package com.android.systemui.keyboard.stickykeys.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class ModifierKey {
    public static final /* synthetic */ ModifierKey[] $VALUES;
    public static final ModifierKey ALT;
    public static final ModifierKey ALT_GR;
    public static final ModifierKey CTRL;
    public static final ModifierKey META;
    public static final ModifierKey SHIFT;
    private final String displayedText;

    static {
        ModifierKey modifierKey = new ModifierKey("ALT", 0, "ALT");
        ALT = modifierKey;
        ModifierKey modifierKey2 = new ModifierKey("ALT_GR", 1, "ALT");
        ALT_GR = modifierKey2;
        ModifierKey modifierKey3 = new ModifierKey("CTRL", 2, "CTRL");
        CTRL = modifierKey3;
        ModifierKey modifierKey4 = new ModifierKey("META", 3, "ACTION");
        META = modifierKey4;
        ModifierKey modifierKey5 = new ModifierKey("SHIFT", 4, "SHIFT");
        SHIFT = modifierKey5;
        ModifierKey[] modifierKeyArr = {modifierKey, modifierKey2, modifierKey3, modifierKey4, modifierKey5};
        $VALUES = modifierKeyArr;
        EnumEntriesKt.enumEntries(modifierKeyArr);
    }

    private ModifierKey(String str, int i, String str2) {
        this.displayedText = str2;
    }

    public static ModifierKey valueOf(String str) {
        return (ModifierKey) Enum.valueOf(ModifierKey.class, str);
    }

    public static ModifierKey[] values() {
        return (ModifierKey[]) $VALUES.clone();
    }

    public final String getDisplayedText() {
        return this.displayedText;
    }
}
