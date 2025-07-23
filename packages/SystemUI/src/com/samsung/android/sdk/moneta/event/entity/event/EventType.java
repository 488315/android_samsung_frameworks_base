package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EventType implements Parcelable {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ EventType[] $VALUES;
    public static final Parcelable.Creator<EventType> CREATOR;
    public static final EventType Type1 = new EventType("Type1", 0);
    public static final EventType Type2 = new EventType("Type2", 1);
    public static final EventType Type3 = new EventType("Type3", 2);
    public static final EventType Type4 = new EventType("Type4", 3);

    private static final /* synthetic */ EventType[] $values() {
        return new EventType[]{Type1, Type2, Type3, Type4};
    }

    static {
        EventType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
        CREATOR = new Parcelable.Creator() { // from class: com.samsung.android.sdk.moneta.event.entity.event.EventType.Creator
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return EventType.valueOf(parcel.readString());
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new EventType[i];
            }
        };
    }

    private EventType(String str, int i) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static EventType valueOf(String str) {
        return (EventType) Enum.valueOf(EventType.class, str);
    }

    public static EventType[] values() {
        return (EventType[]) $VALUES.clone();
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
