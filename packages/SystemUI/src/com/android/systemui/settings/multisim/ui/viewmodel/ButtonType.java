package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class ButtonType {
    public static final /* synthetic */ ButtonType[] $VALUES;
    public static final ButtonType DATA;
    public static final ButtonType SIMINFO1;
    public static final ButtonType SIMINFO2;
    public static final ButtonType SMS;
    public static final ButtonType VOICE;
    private final int index;

    static {
        ButtonType buttonType = new ButtonType("VOICE", 0, 0);
        VOICE = buttonType;
        ButtonType buttonType2 = new ButtonType("SMS", 1, 1);
        SMS = buttonType2;
        ButtonType buttonType3 = new ButtonType("DATA", 2, 2);
        DATA = buttonType3;
        ButtonType buttonType4 = new ButtonType("SIMINFO1", 3, 3);
        SIMINFO1 = buttonType4;
        ButtonType buttonType5 = new ButtonType("SIMINFO2", 4, 4);
        SIMINFO2 = buttonType5;
        ButtonType[] buttonTypeArr = {buttonType, buttonType2, buttonType3, buttonType4, buttonType5};
        $VALUES = buttonTypeArr;
        EnumEntriesKt.enumEntries(buttonTypeArr);
    }

    private ButtonType(String str, int i, int i2) {
        this.index = i2;
    }

    public static ButtonType valueOf(String str) {
        return (ButtonType) Enum.valueOf(ButtonType.class, str);
    }

    public static ButtonType[] values() {
        return (ButtonType[]) $VALUES.clone();
    }

    public final int getIndex() {
        return this.index;
    }
}
