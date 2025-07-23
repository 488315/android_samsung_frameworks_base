package com.samsung.android.sdk.moneta.preference.entity;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PreferenceLevel {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PreferenceLevel[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final PreferenceLevel LOW = new PreferenceLevel("LOW", 0, 1);
    public static final PreferenceLevel MIDDLE = new PreferenceLevel("MIDDLE", 1, 2);
    public static final PreferenceLevel HIGH = new PreferenceLevel("HIGH", 2, 3);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ PreferenceLevel[] $values() {
        return new PreferenceLevel[]{LOW, MIDDLE, HIGH};
    }

    static {
        PreferenceLevel[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
    }

    private PreferenceLevel(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static PreferenceLevel valueOf(String str) {
        return (PreferenceLevel) Enum.valueOf(PreferenceLevel.class, str);
    }

    public static PreferenceLevel[] values() {
        return (PreferenceLevel[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
