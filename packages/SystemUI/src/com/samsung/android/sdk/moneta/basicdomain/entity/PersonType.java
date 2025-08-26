package com.samsung.android.sdk.moneta.basicdomain.entity;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class PersonType implements Parcelable {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PersonType[] $VALUES;
    public static final Parcelable.Creator<PersonType> CREATOR;
    public static final PersonType CONTACT = new PersonType("CONTACT", 0);
    public static final PersonType GALLERY = new PersonType("GALLERY", 1);

    private static final /* synthetic */ PersonType[] $values() {
        return new PersonType[]{CONTACT, GALLERY};
    }

    static {
        PersonType[] personTypeArr$values = $values();
        $VALUES = personTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(personTypeArr$values);
        CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sdk.moneta.basicdomain.entity.PersonType.Creator
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return PersonType.valueOf(parcel.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new PersonType[i];
            }
        };
    }

    private PersonType(String str, int i) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static PersonType valueOf(String str) {
        return (PersonType) Enum.valueOf(PersonType.class, str);
    }

    public static PersonType[] values() {
        return (PersonType[]) $VALUES.clone();
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
