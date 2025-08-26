package com.android.systemui.qs.customize.viewcontroller;

import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class CustomizerInteractionType {
    public static final /* synthetic */ CustomizerInteractionType[] $VALUES;
    public static final CustomizerInteractionType ACTIVE_TO_AVAILABLE;
    public static final CustomizerInteractionType AREA_ACTIVE;
    public static final CustomizerInteractionType AREA_AVAILABLE;
    public static final CustomizerInteractionType AVAILABLE_TO_ACTIVE;
    public static final CustomizerInteractionType CUSTOMIZER_TILE_DRAG_AND_DROP_NON_DC_MOTOR;
    public static final CustomizerInteractionType MSG_HANDLE_ANIMATE_AREA;
    public static final CustomizerInteractionType MSG_HANDLE_ANIMATE_DROP;
    public static final CustomizerInteractionType MSG_HANDLE_ANIMATE_PAGE;
    public static final CustomizerInteractionType MSG_HANDLE_ANIMATE_START;
    private final int value;

    static {
        CustomizerInteractionType customizerInteractionType = new CustomizerInteractionType("CUSTOMIZER_TILE_DRAG_AND_DROP_NON_DC_MOTOR", 0, 108);
        CUSTOMIZER_TILE_DRAG_AND_DROP_NON_DC_MOTOR = customizerInteractionType;
        CustomizerInteractionType customizerInteractionType2 = new CustomizerInteractionType("AREA_AVAILABLE", 1, PluginEdgeLightingPlus.VERSION);
        AREA_AVAILABLE = customizerInteractionType2;
        CustomizerInteractionType customizerInteractionType3 = new CustomizerInteractionType("AREA_ACTIVE", 2, 5000);
        AREA_ACTIVE = customizerInteractionType3;
        CustomizerInteractionType customizerInteractionType4 = new CustomizerInteractionType("ACTIVE_TO_AVAILABLE", 3, 1000);
        ACTIVE_TO_AVAILABLE = customizerInteractionType4;
        CustomizerInteractionType customizerInteractionType5 = new CustomizerInteractionType("AVAILABLE_TO_ACTIVE", 4, 2000);
        AVAILABLE_TO_ACTIVE = customizerInteractionType5;
        CustomizerInteractionType customizerInteractionType6 = new CustomizerInteractionType("MSG_HANDLE_ANIMATE_START", 5, 100);
        MSG_HANDLE_ANIMATE_START = customizerInteractionType6;
        CustomizerInteractionType customizerInteractionType7 = new CustomizerInteractionType("MSG_HANDLE_ANIMATE_DROP", 6, 101);
        MSG_HANDLE_ANIMATE_DROP = customizerInteractionType7;
        CustomizerInteractionType customizerInteractionType8 = new CustomizerInteractionType("MSG_HANDLE_ANIMATE_PAGE", 7, 102);
        MSG_HANDLE_ANIMATE_PAGE = customizerInteractionType8;
        CustomizerInteractionType customizerInteractionType9 = new CustomizerInteractionType("MSG_HANDLE_ANIMATE_AREA", 8, 103);
        MSG_HANDLE_ANIMATE_AREA = customizerInteractionType9;
        CustomizerInteractionType[] customizerInteractionTypeArr = {customizerInteractionType, customizerInteractionType2, customizerInteractionType3, customizerInteractionType4, customizerInteractionType5, customizerInteractionType6, customizerInteractionType7, customizerInteractionType8, customizerInteractionType9};
        $VALUES = customizerInteractionTypeArr;
        EnumEntriesKt.enumEntries(customizerInteractionTypeArr);
    }

    private CustomizerInteractionType(String str, int i, int i2) {
        this.value = i2;
    }

    public static CustomizerInteractionType valueOf(String str) {
        return (CustomizerInteractionType) Enum.valueOf(CustomizerInteractionType.class, str);
    }

    public static CustomizerInteractionType[] values() {
        return (CustomizerInteractionType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
