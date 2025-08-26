package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class PlaceQueryOption implements Parcelable {
    public static final Parcelable.Creator<PlaceQueryOption> CREATOR = new Creator();
    private final String engramId;
    private final int limit;
    private final int offset;
    private final PlaceQueryType queryType;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new PlaceQueryOption(parcel.readString(), parcel.readInt(), parcel.readInt(), PlaceQueryType.valueOf(parcel.readString()), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PlaceQueryOption[i];
        }
    }

    public /* synthetic */ PlaceQueryOption(String str, int i, int i2, PlaceQueryType placeQueryType, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, placeQueryType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getEngramId() {
        return this.engramId;
    }

    public final int getLimit() {
        return this.limit;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final PlaceQueryType getQueryType() {
        return this.queryType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.engramId);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeString(this.queryType.name());
    }

    private PlaceQueryOption(String str, int i, int i2, PlaceQueryType placeQueryType) {
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.queryType = placeQueryType;
    }

    public final class WrapBuilder {
        public final String engramId;
        public final int limit;
        public final int offset;
        public final PlaceQueryType queryType;

        public WrapBuilder(String str, int i, int i2, PlaceQueryType placeQueryType) {
            this.engramId = str;
            this.limit = i;
            this.offset = i2;
            this.queryType = placeQueryType;
        }

        public /* synthetic */ WrapBuilder(String str, int i, int i2, PlaceQueryType placeQueryType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i3 & 2) != 0 ? 100 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? PlaceQueryType.BY_ENGRAM_ID : placeQueryType);
        }
    }

    public /* synthetic */ PlaceQueryOption(String str, int i, int i2, PlaceQueryType placeQueryType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? 100 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? PlaceQueryType.BY_ENGRAM_ID : placeQueryType);
    }
}
