package com.samsung.android.sdk.moneta.memory.option;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PlaceQueryType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PlaceQueryType[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final PlaceQueryType BY_ENGRAM_ID = new PlaceQueryType("BY_ENGRAM_ID", 0, 0);
    public static final PlaceQueryType FREQUENTLY_VISITED_PLACES = new PlaceQueryType("FREQUENTLY_VISITED_PLACES", 1, 1);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        PlaceQueryType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
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
