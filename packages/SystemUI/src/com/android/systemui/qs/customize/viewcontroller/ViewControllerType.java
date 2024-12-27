package com.android.systemui.qs.customize.viewcontroller;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ViewControllerType {
    public static final /* synthetic */ ViewControllerType[] $VALUES;
    public static final ViewControllerType LayoutEdit;
    public static final ViewControllerType None;
    public static final ViewControllerType PanelType;
    public static final ViewControllerType Setting;
    public static final ViewControllerType TileEdit;

    static {
        ViewControllerType viewControllerType = new ViewControllerType("TileEdit", 0);
        TileEdit = viewControllerType;
        ViewControllerType viewControllerType2 = new ViewControllerType("LayoutEdit", 1);
        LayoutEdit = viewControllerType2;
        ViewControllerType viewControllerType3 = new ViewControllerType("PanelType", 2);
        PanelType = viewControllerType3;
        ViewControllerType viewControllerType4 = new ViewControllerType("Setting", 3);
        Setting = viewControllerType4;
        ViewControllerType viewControllerType5 = new ViewControllerType("None", 4);
        None = viewControllerType5;
        ViewControllerType[] viewControllerTypeArr = {viewControllerType, viewControllerType2, viewControllerType3, viewControllerType4, viewControllerType5};
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
