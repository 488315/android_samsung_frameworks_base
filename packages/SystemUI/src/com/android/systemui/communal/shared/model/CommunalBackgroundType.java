package com.android.systemui.communal.shared.model;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalBackgroundType {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ CommunalBackgroundType[] $VALUES;
    public static final CommunalBackgroundType ANIMATED;
    public static final CommunalBackgroundType DEFAULT;
    public static final CommunalBackgroundType NONE;
    public static final CommunalBackgroundType STATIC_GRADIENT;
    private final int value;

    static {
        CommunalBackgroundType communalBackgroundType = new CommunalBackgroundType("DEFAULT", 0, 0);
        DEFAULT = communalBackgroundType;
        CommunalBackgroundType communalBackgroundType2 = new CommunalBackgroundType("STATIC_GRADIENT", 1, 1);
        STATIC_GRADIENT = communalBackgroundType2;
        CommunalBackgroundType communalBackgroundType3 = new CommunalBackgroundType("ANIMATED", 2, 2);
        ANIMATED = communalBackgroundType3;
        CommunalBackgroundType communalBackgroundType4 = new CommunalBackgroundType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 3, 3);
        NONE = communalBackgroundType4;
        CommunalBackgroundType[] communalBackgroundTypeArr = {communalBackgroundType, communalBackgroundType2, communalBackgroundType3, communalBackgroundType4};
        $VALUES = communalBackgroundTypeArr;
        $ENTRIES = EnumEntriesKt.enumEntries(communalBackgroundTypeArr);
    }

    private CommunalBackgroundType(String str, int i, int i2) {
        this.value = i2;
    }

    public static CommunalBackgroundType valueOf(String str) {
        return (CommunalBackgroundType) Enum.valueOf(CommunalBackgroundType.class, str);
    }

    public static CommunalBackgroundType[] values() {
        return (CommunalBackgroundType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
