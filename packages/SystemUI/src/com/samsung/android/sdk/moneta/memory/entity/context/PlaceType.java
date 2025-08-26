package com.samsung.android.sdk.moneta.memory.entity.context;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class PlaceType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PlaceType[] $VALUES;
    public static final PlaceType Home = new PlaceType("Home", 0, 2);
    public static final PlaceType WorkPlace = new PlaceType("WorkPlace", 1, 3);
    public static final PlaceType FrequentlyVisitedPlace = new PlaceType("FrequentlyVisitedPlace", 2, 4);
    public static final PlaceType Other = new PlaceType("Other", 3, 1);

    private static final /* synthetic */ PlaceType[] $values() {
        return new PlaceType[]{Home, WorkPlace, FrequentlyVisitedPlace, Other};
    }

    static {
        PlaceType[] placeTypeArr$values = $values();
        $VALUES = placeTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(placeTypeArr$values);
    }

    private PlaceType(String str, int i, int i2) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static PlaceType valueOf(String str) {
        return (PlaceType) Enum.valueOf(PlaceType.class, str);
    }

    public static PlaceType[] values() {
        return (PlaceType[]) $VALUES.clone();
    }
}
