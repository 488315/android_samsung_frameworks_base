package com.samsung.android.sdk.moneta.memory.option;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class PlaceQueryType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PlaceQueryType[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final PlaceQueryType BY_ENGRAM_ID = new PlaceQueryType("BY_ENGRAM_ID", 0, 0);
    public static final PlaceQueryType FREQUENTLY_VISITED_PLACES = new PlaceQueryType("FREQUENTLY_VISITED_PLACES", 1, 1);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ PlaceQueryType[] $values() {
        return new PlaceQueryType[]{BY_ENGRAM_ID, FREQUENTLY_VISITED_PLACES};
    }

    static {
        PlaceQueryType[] placeQueryTypeArr$values = $values();
        $VALUES = placeQueryTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(placeQueryTypeArr$values);
        Companion = new Companion(null);
    }

    private PlaceQueryType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static PlaceQueryType valueOf(String str) {
        return (PlaceQueryType) Enum.valueOf(PlaceQueryType.class, str);
    }

    public static PlaceQueryType[] values() {
        return (PlaceQueryType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
