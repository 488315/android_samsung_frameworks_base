package com.android.systemui.basic.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum ModuleType {
    NAVBAR("Navbar."),
    VOLUME("SecVolume."),
    GLOBALACTIONS("[SamsungGlobalActions]"),
    POPUPUI("PopupUI."),
    CONTROLS("Controls."),
    /* JADX INFO: Fake field, exist only in values array */
    INDICATOR("Indicator."),
    KEYGUARD("Keyguard.");

    private final String mModuleTag;

    ModuleType(String str) {
        this.mModuleTag = str;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.mModuleTag;
    }
}
