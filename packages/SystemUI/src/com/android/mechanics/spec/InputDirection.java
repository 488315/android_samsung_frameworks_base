package com.android.mechanics.spec;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class InputDirection {
    public static final /* synthetic */ InputDirection[] $VALUES;
    public static final InputDirection Max;
    public static final InputDirection Min;
    private final int sign;

    static {
        InputDirection inputDirection = new InputDirection("Min", 0, -1);
        Min = inputDirection;
        InputDirection inputDirection2 = new InputDirection("Max", 1, 1);
        Max = inputDirection2;
        InputDirection[] inputDirectionArr = {inputDirection, inputDirection2};
        $VALUES = inputDirectionArr;
        EnumEntriesKt.enumEntries(inputDirectionArr);
    }

    private InputDirection(String str, int i, int i2) {
        this.sign = i2;
    }

    public static InputDirection valueOf(String str) {
        return (InputDirection) Enum.valueOf(InputDirection.class, str);
    }

    public static InputDirection[] values() {
        return (InputDirection[]) $VALUES.clone();
    }
}
