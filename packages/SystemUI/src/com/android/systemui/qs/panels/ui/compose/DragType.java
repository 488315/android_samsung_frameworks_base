package com.android.systemui.qs.panels.ui.compose;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class DragType {
    public static final /* synthetic */ DragType[] $VALUES;
    public static final DragType Add;
    public static final DragType Move;

    static {
        DragType dragType = new DragType("Add", 0);
        Add = dragType;
        DragType dragType2 = new DragType("Move", 1);
        Move = dragType2;
        DragType[] dragTypeArr = {dragType, dragType2};
        $VALUES = dragTypeArr;
        EnumEntriesKt.enumEntries(dragTypeArr);
    }

    private DragType(String str, int i) {
    }

    public static DragType valueOf(String str) {
        return (DragType) Enum.valueOf(DragType.class, str);
    }

    public static DragType[] values() {
        return (DragType[]) $VALUES.clone();
    }
}
