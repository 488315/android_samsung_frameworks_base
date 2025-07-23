package com.android.systemui.qs.customize.viewcontroller;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ViewControllerType {
    public static final /* synthetic */ ViewControllerType[] $VALUES;
    public static final ViewControllerType LayoutEdit;
    public static final ViewControllerType None;
    public static final ViewControllerType Setting;
    public static final ViewControllerType TileEdit;

    static {
        ViewControllerType viewControllerType = new ViewControllerType("TileEdit", 0);
        TileEdit = viewControllerType;
        ViewControllerType viewControllerType2 = new ViewControllerType("LayoutEdit", 1);
        LayoutEdit = viewControllerType2;
        ViewControllerType viewControllerType3 = new ViewControllerType("Setting", 2);
        Setting = viewControllerType3;
        ViewControllerType viewControllerType4 = new ViewControllerType("None", 3);
        None = viewControllerType4;
        ViewControllerType[] viewControllerTypeArr = {viewControllerType, viewControllerType2, viewControllerType3, viewControllerType4};
        $VALUES = viewControllerTypeArr;
        EnumEntriesKt.enumEntries(viewControllerTypeArr);
    }

    private ViewControllerType(String str, int i) {
    }

    public static ViewControllerType valueOf(String str) {
        return (ViewControllerType) Enum.valueOf(ViewControllerType.class, str);
    }

    public static ViewControllerType[] values() {
        return (ViewControllerType[]) $VALUES.clone();
    }
}
