package com.samsung.android.sdk.moneta.basicdomain.entity;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RelationShip implements Parcelable {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ RelationShip[] $VALUES;
    public static final Parcelable.Creator<RelationShip> CREATOR;
    public static final Companion Companion;
    public static final RelationShip DAUGHTER;
    public static final RelationShip DAUGHTER_IN_LAW;
    public static final RelationShip FAMILY_UNKNOWN;
    public static final RelationShip FATHER;
    public static final RelationShip FATHER_IN_LAW_HUSBAND;
    public static final RelationShip FATHER_IN_LAW_WIFE;
    public static final RelationShip HUSBAND;
    public static final RelationShip MATERNAL_GRANDFATHER;
    public static final RelationShip MATERNAL_GRANDMOTHER;
    public static final RelationShip MOTHER;
    public static final RelationShip MOTHER_IN_LAW_HUSBAND;
    public static final RelationShip MOTHER_IN_LAW_WIFE;
    public static final RelationShip OLDER_BROTHER_FEMALE;
    public static final RelationShip OLDER_BROTHER_MALE;
    public static final RelationShip OLDER_SISTER_FEMALE;
    public static final RelationShip OLDER_SISTER_MALE;
    public static final RelationShip PATERNAL_GRANDFATHER;
    public static final RelationShip PATERNAL_GRANDMOTHER;
    public static final RelationShip SON;
    public static final RelationShip SON_IN_LAW;
    public static final RelationShip UNKNOWN;
    public static final RelationShip WIFE;
    public static final RelationShip YOUNGER_BROTHER;
    public static final RelationShip YOUNGER_SISTER;
    private final RelationGroup relationGroup;
    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ RelationShip[] $values() {
        return new RelationShip[]{FAMILY_UNKNOWN, FATHER, MOTHER, HUSBAND, WIFE, SON, DAUGHTER, OLDER_SISTER_MALE, OLDER_SISTER_FEMALE, YOUNGER_SISTER, OLDER_BROTHER_MALE, OLDER_BROTHER_FEMALE, YOUNGER_BROTHER, PATERNAL_GRANDMOTHER, MATERNAL_GRANDMOTHER, PATERNAL_GRANDFATHER, MATERNAL_GRANDFATHER, MOTHER_IN_LAW_HUSBAND, MOTHER_IN_LAW_WIFE, FATHER_IN_LAW_HUSBAND, FATHER_IN_LAW_WIFE, DAUGHTER_IN_LAW, SON_IN_LAW, UNKNOWN};
    }

    static {
        RelationGroup relationGroup = RelationGroup.FAMILY;
        FAMILY_UNKNOWN = new RelationShip("FAMILY_UNKNOWN", 0, relationGroup, 0);
        FATHER = new RelationShip("FATHER", 1, relationGroup, 1);
        MOTHER = new RelationShip("MOTHER", 2, relationGroup, 2);
        HUSBAND = new RelationShip("HUSBAND", 3, relationGroup, 3);
        WIFE = new RelationShip("WIFE", 4, relationGroup, 4);
        SON = new RelationShip("SON", 5, relationGroup, 5);
        DAUGHTER = new RelationShip("DAUGHTER", 6, relationGroup, 6);
        OLDER_SISTER_MALE = new RelationShip("OLDER_SISTER_MALE", 7, relationGroup, 7);
        OLDER_SISTER_FEMALE = new RelationShip("OLDER_SISTER_FEMALE", 8, relationGroup, 8);
        YOUNGER_SISTER = new RelationShip("YOUNGER_SISTER", 9, relationGroup, 9);
        OLDER_BROTHER_MALE = new RelationShip("OLDER_BROTHER_MALE", 10, relationGroup, 10);
        OLDER_BROTHER_FEMALE = new RelationShip("OLDER_BROTHER_FEMALE", 11, relationGroup, 11);
        YOUNGER_BROTHER = new RelationShip("YOUNGER_BROTHER", 12, relationGroup, 12);
        PATERNAL_GRANDMOTHER = new RelationShip("PATERNAL_GRANDMOTHER", 13, relationGroup, 13);
        MATERNAL_GRANDMOTHER = new RelationShip("MATERNAL_GRANDMOTHER", 14, relationGroup, 14);
        PATERNAL_GRANDFATHER = new RelationShip("PATERNAL_GRANDFATHER", 15, relationGroup, 15);
        MATERNAL_GRANDFATHER = new RelationShip("MATERNAL_GRANDFATHER", 16, relationGroup, 16);
        MOTHER_IN_LAW_HUSBAND = new RelationShip("MOTHER_IN_LAW_HUSBAND", 17, relationGroup, 17);
        MOTHER_IN_LAW_WIFE = new RelationShip("MOTHER_IN_LAW_WIFE", 18, relationGroup, 18);
        FATHER_IN_LAW_HUSBAND = new RelationShip("FATHER_IN_LAW_HUSBAND", 19, relationGroup, 19);
        FATHER_IN_LAW_WIFE = new RelationShip("FATHER_IN_LAW_WIFE", 20, relationGroup, 20);
        DAUGHTER_IN_LAW = new RelationShip("DAUGHTER_IN_LAW", 21, relationGroup, 21);
        SON_IN_LAW = new RelationShip("SON_IN_LAW", 22, relationGroup, 22);
        UNKNOWN = new RelationShip("UNKNOWN", 23, RelationGroup.UNKNOWN, -1);
        RelationShip[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sdk.moneta.basicdomain.entity.RelationShip.Creator
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return RelationShip.valueOf(parcel.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new RelationShip[i];
            }
        };
    }

    private RelationShip(String str, int i, RelationGroup relationGroup, int i2) {
        this.relationGroup = relationGroup;
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static RelationShip valueOf(String str) {
        return (RelationShip) Enum.valueOf(RelationShip.class, str);
    }

    public static RelationShip[] values() {
        return (RelationShip[]) $VALUES.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final RelationGroup getRelationGroup() {
        return this.relationGroup;
    }

    public final int getValue() {
        return this.value;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
