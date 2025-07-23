package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EventCategory implements Parcelable {
    public static final Parcelable.Creator<EventCategory> CREATOR = new Creator();
    private final int categoryId;
    private final String categoryName;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EventCategory(parcel.readInt(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EventCategory[i];
        }
    }

    public EventCategory(int i, String str) {
        this.categoryId = i;
        this.categoryName = str;
    }

    public static /* synthetic */ EventCategory copy$default(EventCategory eventCategory, int i, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = eventCategory.categoryId;
        }
        if ((i2 & 2) != 0) {
            str = eventCategory.categoryName;
        }
        return eventCategory.copy(i, str);
    }

    public final int component1() {
        return this.categoryId;
    }

    public final String component2() {
        return this.categoryName;
    }

    public final EventCategory copy(int i, String str) {
        return new EventCategory(i, str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventCategory)) {
            return false;
        }
        EventCategory eventCategory = (EventCategory) obj;
        return this.categoryId == eventCategory.categoryId && Intrinsics.areEqual(this.categoryName, eventCategory.categoryName);
    }

    public final int getCategoryId() {
        return this.categoryId;
    }

    public final String getCategoryName() {
        return this.categoryName;
    }

    public int hashCode() {
        return this.categoryName.hashCode() + (Integer.hashCode(this.categoryId) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("EventCategory(categoryId=");
        sb.append(this.categoryId);
        sb.append(", categoryName=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.categoryName, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.categoryId);
        parcel.writeString(this.categoryName);
    }
}
