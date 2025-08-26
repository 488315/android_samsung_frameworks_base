package com.samsung.android.sdk.moneta.preference.entity;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class AgeGroup implements Parcelable {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ AgeGroup[] $VALUES;
    public static final Parcelable.Creator<AgeGroup> CREATOR;
    public static final Companion Companion;
    private final int value;
    public static final AgeGroup MINOR_LEVEL_1 = new AgeGroup("MINOR_LEVEL_1", 0, 1);
    public static final AgeGroup MINOR_LEVEL_2 = new AgeGroup("MINOR_LEVEL_2", 1, 2);
    public static final AgeGroup ADULT_LEVEL_1 = new AgeGroup("ADULT_LEVEL_1", 2, 3);
    public static final AgeGroup ADULT_LEVEL_2 = new AgeGroup("ADULT_LEVEL_2", 3, 4);
    public static final AgeGroup ADULT_LEVEL_3 = new AgeGroup("ADULT_LEVEL_3", 4, 5);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private static final /* synthetic */ AgeGroup[] $values() {
        return new AgeGroup[]{MINOR_LEVEL_1, MINOR_LEVEL_2, ADULT_LEVEL_1, ADULT_LEVEL_2, ADULT_LEVEL_3};
    }

    static {
        AgeGroup[] ageGroupArr$values = $values();
        $VALUES = ageGroupArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(ageGroupArr$values);
        Companion = new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sdk.moneta.preference.entity.AgeGroup.Creator
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return AgeGroup.valueOf(parcel.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new AgeGroup[i];
            }
        };
    }

    private AgeGroup(String str, int i, int i2) {
        this.value = i2;
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static AgeGroup valueOf(String str) {
        return (AgeGroup) Enum.valueOf(AgeGroup.class, str);
    }

    public static AgeGroup[] values() {
        return (AgeGroup[]) $VALUES.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getValue() {
        return this.value;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
