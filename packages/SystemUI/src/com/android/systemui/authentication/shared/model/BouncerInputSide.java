package com.android.systemui.authentication.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class BouncerInputSide {
    public static final /* synthetic */ BouncerInputSide[] $VALUES;
    public static final BouncerInputSide LEFT;
    public static final BouncerInputSide RIGHT;
    private final int settingValue;

    static {
        BouncerInputSide bouncerInputSide = new BouncerInputSide("LEFT", 0, 0);
        LEFT = bouncerInputSide;
        BouncerInputSide bouncerInputSide2 = new BouncerInputSide("RIGHT", 1, 1);
        RIGHT = bouncerInputSide2;
        BouncerInputSide[] bouncerInputSideArr = {bouncerInputSide, bouncerInputSide2};
        $VALUES = bouncerInputSideArr;
        EnumEntriesKt.enumEntries(bouncerInputSideArr);
    }

    private BouncerInputSide(String str, int i, int i2) {
        this.settingValue = i2;
    }

    public static BouncerInputSide valueOf(String str) {
        return (BouncerInputSide) Enum.valueOf(BouncerInputSide.class, str);
    }

    public static BouncerInputSide[] values() {
        return (BouncerInputSide[]) $VALUES.clone();
    }

    public final int getSettingValue() {
        return this.settingValue;
    }
}
