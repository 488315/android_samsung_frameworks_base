package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import com.samsung.android.sdk.moneta.memory.option.EngramSearchContentOption;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchContentOptionWrapperV1 implements Parcelable {
    private final int contentType;
    private final String keywords;
    private final int limit;
    private final int offset;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramSearchContentOptionWrapperV1> CREATOR = new Creator();

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
            return new EngramSearchContentOptionWrapperV1(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchContentOptionWrapperV1[i];
        }
    }

    public EngramSearchContentOptionWrapperV1(String str, int i, int i2, int i3) {
        this.keywords = str;
        this.limit = i;
        this.offset = i2;
        this.contentType = i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getContentType() {
        return this.contentType;
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

    public final EngramSearchContentOption toOption() {
        String str = this.keywords;
        int i = this.limit;
        int i2 = this.offset;
        ContentType.Companion companion = ContentType.Companion;
        Integer valueOf = Integer.valueOf(this.contentType);
        companion.getClass();
        ContentType fromInt = ContentType.Companion.fromInt(valueOf);
        fromInt.getClass();
        EngramSearchContentOption.WrapBuilder wrapBuilder = new EngramSearchContentOption.WrapBuilder(str, i, i2, fromInt);
        return new EngramSearchContentOption(wrapBuilder.keywords, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.contentType, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeInt(this.contentType);
    }

    public /* synthetic */ EngramSearchContentOptionWrapperV1(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i4 & 2) != 0 ? 20 : i, (i4 & 4) != 0 ? 0 : i2, i3);
    }
}
