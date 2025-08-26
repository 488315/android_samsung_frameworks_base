package com.android.systemui.touchpad.tutorial.ui.gesture;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class GestureDirection {
    public static final /* synthetic */ GestureDirection[] $VALUES;
    public static final GestureDirection LEFT;
    public static final GestureDirection RIGHT;

    static {
        GestureDirection gestureDirection = new GestureDirection("LEFT", 0);
        LEFT = gestureDirection;
        GestureDirection gestureDirection2 = new GestureDirection("RIGHT", 1);
        RIGHT = gestureDirection2;
        GestureDirection[] gestureDirectionArr = {gestureDirection, gestureDirection2};
        $VALUES = gestureDirectionArr;
        EnumEntriesKt.enumEntries(gestureDirectionArr);
    }

    private GestureDirection(String str, int i) {
    }

    public static GestureDirection valueOf(String str) {
        return (GestureDirection) Enum.valueOf(GestureDirection.class, str);
    }

    public static GestureDirection[] values() {
        return (GestureDirection[]) $VALUES.clone();
    }
}
