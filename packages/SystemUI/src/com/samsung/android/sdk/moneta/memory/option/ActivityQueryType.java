package com.samsung.android.sdk.moneta.memory.option;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ActivityQueryType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ActivityQueryType[] $VALUES;
    public static final ActivityQueryType BETWEEN_TIMESTAMP = new ActivityQueryType("BETWEEN_TIMESTAMP", 0, 0);
    public static final ActivityQueryType BY_ENGRAM_ID = new ActivityQueryType("BY_ENGRAM_ID", 1, 1);
    public static final Companion Companion;
    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ ActivityQueryType[] $values() {
        return new ActivityQueryType[]{BETWEEN_TIMESTAMP, BY_ENGRAM_ID};
    }

    static {
        ActivityQueryType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
    }

    private ActivityQueryType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static ActivityQueryType valueOf(String str) {
        return (ActivityQueryType) Enum.valueOf(ActivityQueryType.class, str);
    }

    public static ActivityQueryType[] values() {
        return (ActivityQueryType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
