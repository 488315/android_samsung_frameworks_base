package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.PlaceQueryOption;
import com.samsung.android.sdk.moneta.memory.option.PlaceQueryType;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class PlaceQueryOptionWrapperV1 implements Parcelable {
    private final String engramId;
    private final int limit;
    private final int offset;
    private final int queryType;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<PlaceQueryOptionWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new PlaceQueryOptionWrapperV1(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PlaceQueryOptionWrapperV1[i];
        }
    }

    public PlaceQueryOptionWrapperV1() {
        this(null, 0, 0, 0, 15, null);
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

    public final int getQueryType() {
        return this.queryType;
    }

    public final PlaceQueryOption toOption() {
        Object next;
        String str = this.engramId;
        int i = this.limit;
        int i2 = this.offset;
        PlaceQueryType.Companion companion = PlaceQueryType.Companion;
        int i3 = this.queryType;
        companion.getClass();
        Iterator<E> it = PlaceQueryType.getEntries().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((PlaceQueryType) next).getValue() == i3) {
                break;
            }
        }
        PlaceQueryType placeQueryType = (PlaceQueryType) next;
        if (placeQueryType == null) {
            placeQueryType = PlaceQueryType.BY_ENGRAM_ID;
        }
        PlaceQueryOption.WrapBuilder wrapBuilder = new PlaceQueryOption.WrapBuilder(str, i, i2, placeQueryType);
        return new PlaceQueryOption(wrapBuilder.engramId, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.queryType, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.engramId);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeInt(this.queryType);
    }

    public PlaceQueryOptionWrapperV1(String str, int i, int i2, int i3) {
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.queryType = i3;
    }

    public /* synthetic */ PlaceQueryOptionWrapperV1(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : str, (i4 & 2) != 0 ? 100 : i, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? PlaceQueryType.BY_ENGRAM_ID.getValue() : i3);
    }
}
