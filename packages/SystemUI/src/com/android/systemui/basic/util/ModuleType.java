package com.android.systemui.basic.util;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class ModuleType {
    public static final /* synthetic */ ModuleType[] $VALUES;
    public static final ModuleType CONTROLS;
    public static final ModuleType GLOBALACTIONS;
    public static final ModuleType KEYGUARD;
    public static final ModuleType NAVBAR;
    public static final ModuleType POPUPUI;
    public static final ModuleType VOLUME;
    private final String mModuleTag;

    static {
        ModuleType moduleType = new ModuleType("NAVBAR", 0, "Navbar.");
        NAVBAR = moduleType;
        ModuleType moduleType2 = new ModuleType("VOLUME", 1, "SecVolume.");
        VOLUME = moduleType2;
        ModuleType moduleType3 = new ModuleType("GLOBALACTIONS", 2, "[SamsungGlobalActions]");
        GLOBALACTIONS = moduleType3;
        ModuleType moduleType4 = new ModuleType("POPUPUI", 3, "PopupUI.");
        POPUPUI = moduleType4;
        ModuleType moduleType5 = new ModuleType("CONTROLS", 4, "Controls.");
        CONTROLS = moduleType5;
        ModuleType moduleType6 = new ModuleType("INDICATOR", 5, "Indicator.");
        ModuleType moduleType7 = new ModuleType("KEYGUARD", 6, "Keyguard.");
        KEYGUARD = moduleType7;
        ModuleType[] moduleTypeArr = {moduleType, moduleType2, moduleType3, moduleType4, moduleType5, moduleType6, moduleType7};
        $VALUES = moduleTypeArr;
        EnumEntriesKt.enumEntries(moduleTypeArr);
    }

    private ModuleType(String str, int i, String str2) {
        this.mModuleTag = str2;
    }

    public static ModuleType valueOf(String str) {
        return (ModuleType) Enum.valueOf(ModuleType.class, str);
    }

    public static ModuleType[] values() {
        return (ModuleType[]) $VALUES.clone();
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.mModuleTag;
    }
}
