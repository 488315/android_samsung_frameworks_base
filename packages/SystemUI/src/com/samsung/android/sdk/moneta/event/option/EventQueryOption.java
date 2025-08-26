package com.samsung.android.sdk.moneta.event.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.event.entity.event.EventType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EventQueryOption implements Parcelable {
    public static final Parcelable.Creator<EventQueryOption> CREATOR = new Creator();
    private final Long endTimestamp;
    private final String eventCategory;
    private final EventType eventType;
    private final Long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EventQueryOption(parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() != 0 ? EventType.CREATOR.createFromParcel(parcel) : null, parcel.readString(), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EventQueryOption[i];
        }
    }

    public /* synthetic */ EventQueryOption(Long l, Long l2, EventType eventType, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(l, l2, eventType, str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getEventCategory() {
        return this.eventCategory;
    }

    public final EventType getEventType() {
        return this.eventType;
    }

    public final Long getStartTimestamp() {
        return this.startTimestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Long l = this.startTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        Long l2 = this.endTimestamp;
        if (l2 == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l2);
        }
        EventType eventType = this.eventType;
        if (eventType == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            eventType.writeToParcel(parcel, i);
        }
        parcel.writeString(this.eventCategory);
    }

    private EventQueryOption(Long l, Long l2, EventType eventType, String str) {
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.eventType = eventType;
        this.eventCategory = str;
    }

    public /* synthetic */ EventQueryOption(Long l, Long l2, EventType eventType, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : l, (i & 2) != 0 ? null : l2, (i & 4) != 0 ? null : eventType, (i & 8) != 0 ? null : str);
    }
}
