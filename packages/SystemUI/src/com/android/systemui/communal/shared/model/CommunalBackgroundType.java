package com.android.systemui.communal.shared.model;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalBackgroundType {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ CommunalBackgroundType[] $VALUES;
    public static final CommunalBackgroundType ANIMATED;
    public static final CommunalBackgroundType BLUR;
    public static final CommunalBackgroundType NONE;
    public static final CommunalBackgroundType SCRIM;
    public static final CommunalBackgroundType STATIC;
    public static final CommunalBackgroundType STATIC_GRADIENT;
    private final boolean opaque;
    private final int value;

    static {
        CommunalBackgroundType communalBackgroundType = new CommunalBackgroundType("STATIC", 0, 0, true);
        STATIC = communalBackgroundType;
        CommunalBackgroundType communalBackgroundType2 = new CommunalBackgroundType("STATIC_GRADIENT", 1, 1, true);
        STATIC_GRADIENT = communalBackgroundType2;
        CommunalBackgroundType communalBackgroundType3 = new CommunalBackgroundType("ANIMATED", 2, 2, true);
        ANIMATED = communalBackgroundType3;
        CommunalBackgroundType communalBackgroundType4 = new CommunalBackgroundType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 3, 3, false);
        NONE = communalBackgroundType4;
        CommunalBackgroundType communalBackgroundType5 = new CommunalBackgroundType("BLUR", 4, 4, false);
        BLUR = communalBackgroundType5;
        CommunalBackgroundType communalBackgroundType6 = new CommunalBackgroundType("SCRIM", 5, 5, false);
        SCRIM = communalBackgroundType6;
        CommunalBackgroundType[] communalBackgroundTypeArr = {communalBackgroundType, communalBackgroundType2, communalBackgroundType3, communalBackgroundType4, communalBackgroundType5, communalBackgroundType6};
        $VALUES = communalBackgroundTypeArr;
        $ENTRIES = EnumEntriesKt.enumEntries(communalBackgroundTypeArr);
    }

    private CommunalBackgroundType(String str, int i, int i2, boolean z) {
        this.value = i2;
        this.opaque = z;
    }

    public static CommunalBackgroundType valueOf(String str) {
        return (CommunalBackgroundType) Enum.valueOf(CommunalBackgroundType.class, str);
    }

    public static CommunalBackgroundType[] values() {
        return (CommunalBackgroundType[]) $VALUES.clone();
    }

    public final boolean getOpaque() {
        return this.opaque;
    }

    public final int getValue() {
        return this.value;
    }
}
