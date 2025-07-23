package com.samsung.android.sdk.moneta.preference.entity;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ContactChannel {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ContactChannel[] $VALUES;
    public static final Companion Companion;
    private final int value;
    public static final ContactChannel CALL = new ContactChannel("CALL", 0, 1);
    public static final ContactChannel MESSAGE = new ContactChannel("MESSAGE", 1, 2);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ ContactChannel[] $values() {
        return new ContactChannel[]{CALL, MESSAGE};
    }

    static {
        ContactChannel[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
    }

    private ContactChannel(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static ContactChannel valueOf(String str) {
        return (ContactChannel) Enum.valueOf(ContactChannel.class, str);
    }

    public static ContactChannel[] values() {
        return (ContactChannel[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
