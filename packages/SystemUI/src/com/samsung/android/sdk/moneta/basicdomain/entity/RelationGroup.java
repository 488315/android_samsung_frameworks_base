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
public final class RelationGroup implements Parcelable {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ RelationGroup[] $VALUES;
    public static final Parcelable.Creator<RelationGroup> CREATOR;
    public static final Companion Companion;
    public static final RelationGroup FAMILY = new RelationGroup("FAMILY", 0);
    public static final RelationGroup UNKNOWN = new RelationGroup("UNKNOWN", 1);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ RelationGroup[] $values() {
        return new RelationGroup[]{FAMILY, UNKNOWN};
    }

    static {
        RelationGroup[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        Companion = new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sdk.moneta.basicdomain.entity.RelationGroup.Creator
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return RelationGroup.valueOf(parcel.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new RelationGroup[i];
            }
        };
    }

    private RelationGroup(String str, int i) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static RelationGroup valueOf(String str) {
        return (RelationGroup) Enum.valueOf(RelationGroup.class, str);
    }

    public static RelationGroup[] values() {
        return (RelationGroup[]) $VALUES.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
