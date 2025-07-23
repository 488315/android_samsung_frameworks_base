package com.samsung.android.sdk.moneta.memory.option;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ContentQueryType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ContentQueryType[] $VALUES;
    public static final ContentQueryType BY_ENGRAM_ID = new ContentQueryType("BY_ENGRAM_ID", 0, 0);
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

    private static final /* synthetic */ ContentQueryType[] $values() {
        return new ContentQueryType[]{BY_ENGRAM_ID};
    }

    static {
        ContentQueryType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
    }

    private ContentQueryType(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static ContentQueryType valueOf(String str) {
        return (ContentQueryType) Enum.valueOf(ContentQueryType.class, str);
    }

    public static ContentQueryType[] values() {
        return (ContentQueryType[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
