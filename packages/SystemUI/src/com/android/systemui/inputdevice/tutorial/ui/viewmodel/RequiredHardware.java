package com.android.systemui.inputdevice.tutorial.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RequiredHardware {
    public static final /* synthetic */ RequiredHardware[] $VALUES;
    public static final RequiredHardware KEYBOARD;
    public static final RequiredHardware TOUCHPAD;

    static {
        RequiredHardware requiredHardware = new RequiredHardware("TOUCHPAD", 0);
        TOUCHPAD = requiredHardware;
        RequiredHardware requiredHardware2 = new RequiredHardware("KEYBOARD", 1);
        KEYBOARD = requiredHardware2;
        RequiredHardware[] requiredHardwareArr = {requiredHardware, requiredHardware2};
        $VALUES = requiredHardwareArr;
        EnumEntriesKt.enumEntries(requiredHardwareArr);
    }

    private RequiredHardware(String str, int i) {
    }

    public static RequiredHardware valueOf(String str) {
        return (RequiredHardware) Enum.valueOf(RequiredHardware.class, str);
    }

    public static RequiredHardware[] values() {
        return (RequiredHardware[]) $VALUES.clone();
    }
}
