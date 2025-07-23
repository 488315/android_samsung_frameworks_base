package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchContentStatOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchContentStatOption> CREATOR = new Creator();
    private final ContentType contentType;
    private final String keywords;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchContentStatOption(parcel.readString(), ContentType.valueOf(parcel.readString()), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchContentStatOption[i];
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapBuilder {
        public final ContentType contentType;
        public final String keywords;

        public WrapBuilder(String str, ContentType contentType) {
            this.keywords = str;
            this.contentType = contentType;
        }
    }

    public /* synthetic */ EngramSearchContentStatOption(String str, ContentType contentType, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, contentType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final ContentType getContentType() {
        return this.contentType;
    }

    public final String getKeywords() {
        return this.keywords;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.keywords);
        parcel.writeString(this.contentType.name());
    }

    private EngramSearchContentStatOption(String str, ContentType contentType) {
        this.keywords = str;
        this.contentType = contentType;
    }
}
