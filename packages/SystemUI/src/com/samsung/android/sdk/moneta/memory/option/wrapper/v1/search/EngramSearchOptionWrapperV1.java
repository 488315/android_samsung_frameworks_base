package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchOptionWrapperV1 implements Parcelable {
    private final boolean contentFill;
    private final String keywords;
    private final int limit;
    private final int offset;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchOptionWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchOptionWrapperV1(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchOptionWrapperV1[i];
        }
    }

    public EngramSearchOptionWrapperV1(String str, int i, int i2, boolean z) {
        this.keywords = str;
        this.limit = i;
        this.offset = i2;
        this.contentFill = z;
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

    public final EngramSearchOption toOption() {
        EngramSearchOption.WrapBuilder wrapBuilder = new EngramSearchOption.WrapBuilder(this.keywords, this.limit, this.offset, this.contentFill);
        return new EngramSearchOption(wrapBuilder.keywords, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.contentFill, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    public /* synthetic */ EngramSearchOptionWrapperV1(String str, int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, z);
    }
}
