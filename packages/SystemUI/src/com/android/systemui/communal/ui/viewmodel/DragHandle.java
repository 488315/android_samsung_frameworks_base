package com.android.systemui.communal.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class DragHandle {
    public static final /* synthetic */ DragHandle[] $VALUES;
    public static final DragHandle BOTTOM;
    public static final DragHandle TOP;

    static {
        DragHandle dragHandle = new DragHandle("TOP", 0);
        TOP = dragHandle;
        DragHandle dragHandle2 = new DragHandle("BOTTOM", 1);
        BOTTOM = dragHandle2;
        DragHandle[] dragHandleArr = {dragHandle, dragHandle2};
        $VALUES = dragHandleArr;
        EnumEntriesKt.enumEntries(dragHandleArr);
    }

    private DragHandle(String str, int i) {
    }

    public static DragHandle valueOf(String str) {
        return (DragHandle) Enum.valueOf(DragHandle.class, str);
    }

    public static DragHandle[] values() {
        return (DragHandle[]) $VALUES.clone();
    }
}
