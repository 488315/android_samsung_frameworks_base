package com.samsung.android.sdk.moneta.preference.entity;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RelationDetails {
    public static final /* synthetic */ RelationDetails[] $VALUES;
    public static final RelationDetails UNKNOWN;
    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        RelationDetails relationDetails = new RelationDetails("FATHER", 0, 1);
        RelationDetails relationDetails2 = new RelationDetails("MOTHER", 1, 2);
        RelationDetails relationDetails3 = new RelationDetails("SPOUSE", 2, 3);
        RelationDetails relationDetails4 = new RelationDetails("SON", 3, 4);
        RelationDetails relationDetails5 = new RelationDetails("DAUGHTER", 4, 5);
        RelationDetails relationDetails6 = new RelationDetails("GRANDFATHER", 5, 6);
        RelationDetails relationDetails7 = new RelationDetails("GRANDMOTHER", 6, 7);
        RelationDetails relationDetails8 = new RelationDetails("GRANDSON", 7, 8);
        RelationDetails relationDetails9 = new RelationDetails("GRANDDAUGHTER", 8, 9);
        RelationDetails relationDetails10 = new RelationDetails("UNKNOWN", 9, -1);
        UNKNOWN = relationDetails10;
        RelationDetails[] relationDetailsArr = {relationDetails, relationDetails2, relationDetails3, relationDetails4, relationDetails5, relationDetails6, relationDetails7, relationDetails8, relationDetails9, relationDetails10};
        $VALUES = relationDetailsArr;
        EnumEntriesKt.enumEntries(relationDetailsArr);
        new Companion(null);
    }

    private RelationDetails(String str, int i, int i2) {
        this.value = i2;
    }

    public static RelationDetails valueOf(String str) {
        return (RelationDetails) Enum.valueOf(RelationDetails.class, str);
    }

    public static RelationDetails[] values() {
        return (RelationDetails[]) $VALUES.clone();
    }
}
