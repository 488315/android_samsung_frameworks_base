package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramSearchContentOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchContentOption> CREATOR = new Creator();
    private final ContentType contentType;
    private final String keywords;
    private final int limit;
    private final int offset;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchContentOption(parcel.readString(), parcel.readInt(), parcel.readInt(), ContentType.valueOf(parcel.readString()), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchContentOption[i];
        }
    }

    public /* synthetic */ EngramSearchContentOption(String str, int i, int i2, ContentType contentType, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, contentType);
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
        parcel.writeString(this.contentType.name());
    }

    private EngramSearchContentOption(String str, int i, int i2, ContentType contentType) {
        this.keywords = str;
        this.limit = i;
        this.offset = i2;
        this.contentType = contentType;
    }

    public final class WrapBuilder {
        public final ContentType contentType;
        public final String keywords;
        public final int limit;
        public final int offset;

        public WrapBuilder(String str, int i, int i2, ContentType contentType) {
            this.keywords = str;
            this.limit = i;
            this.offset = i2;
            this.contentType = contentType;
        }

        public /* synthetic */ WrapBuilder(String str, int i, int i2, ContentType contentType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, contentType);
        }
    }

    public /* synthetic */ EngramSearchContentOption(String str, int i, int i2, ContentType contentType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, contentType);
    }
}
