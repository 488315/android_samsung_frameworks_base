package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchOption> CREATOR = new Creator();
    private final boolean contentFill;
    private final String keywords;
    private final int limit;
    private final int offset;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchOption(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0, null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchOption[i];
        }
    }

    public /* synthetic */ EngramSearchOption(String str, int i, int i2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, z);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final boolean getContentFill() {
        return this.contentFill;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    public final int getLimit() {
        return this.limit;
    }

    public final int getOffset() {
        return this.offset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    private EngramSearchOption(String str, int i, int i2, boolean z) {
        this.keywords = str;
        this.limit = i;
        this.offset = i2;
        this.contentFill = z;
    }

    public final class WrapBuilder {
        public final boolean contentFill;
        public final String keywords;
        public final int limit;
        public final int offset;

        public WrapBuilder(String str, int i, int i2, boolean z) {
            this.keywords = str;
            this.limit = i;
            this.offset = i2;
            this.contentFill = z;
        }

        public /* synthetic */ WrapBuilder(String str, int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? false : z);
        }
    }

    public /* synthetic */ EngramSearchOption(String str, int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? false : z);
    }
}
