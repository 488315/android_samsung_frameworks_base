package com.samsung.android.sdk.moneta.memory.option;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class EngramQueryType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ EngramQueryType[] $VALUES;
    public static final EngramQueryType BETWEEN_TIMESTAMP = new EngramQueryType("BETWEEN_TIMESTAMP", 0, 0);
    public static final EngramQueryType BETWEEN_TIMESTAMP_LIST = new EngramQueryType("BETWEEN_TIMESTAMP_LIST", 1, 1);
    public static final EngramQueryType BY_CONTENT_ID = new EngramQueryType("BY_CONTENT_ID", 2, 2);
    public static final EngramQueryType BY_ENGRAM_ID = new EngramQueryType("BY_ENGRAM_ID", 3, 3);
    public static final EngramQueryType BY_LOCATION = new EngramQueryType("BY_LOCATION", 4, 4);
    public static final Companion Companion;
    private final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ EngramQueryType[] $values() {
        return new EngramQueryType[]{BETWEEN_TIMESTAMP, BETWEEN_TIMESTAMP_LIST, BY_CONTENT_ID, BY_ENGRAM_ID, BY_LOCATION};
    }

    static {
        EngramQueryType[] engramQueryTypeArr$values = $values();
        $VALUES = engramQueryTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(engramQueryTypeArr$values);
        Companion = new Companion(null);
    }

    private EngramQueryType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static EngramQueryType valueOf(String str) {
        return (EngramQueryType) Enum.valueOf(EngramQueryType.class, str);
    }

    public static EngramQueryType[] values() {
        return (EngramQueryType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
