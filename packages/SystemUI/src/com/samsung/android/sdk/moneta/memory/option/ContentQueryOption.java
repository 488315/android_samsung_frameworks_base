package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class ContentQueryOption implements Parcelable {
    public static final Parcelable.Creator<ContentQueryOption> CREATOR = new Creator();
    private final ContentType contentType;
    private final String engramId;
    private final int limit;
    private final int offset;
    private final ContentQueryType queryType;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ContentQueryOption(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 0 ? null : ContentType.valueOf(parcel.readString()), ContentQueryType.valueOf(parcel.readString()), null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ContentQueryOption[i];
        }
    }

    public final class WrapBuilder {
        public final String activityId;
        public final ContentType contentType;
        public final int limit;
        public final int offset;
        public final ContentQueryType queryType;

        public WrapBuilder() {
            this(null, 0, 0, null, null, 31, null);
        }

        public WrapBuilder(String str, int i, int i2, ContentType contentType, ContentQueryType contentQueryType) {
            this.activityId = str;
            this.limit = i;
            this.offset = i2;
            this.contentType = contentType;
            this.queryType = contentQueryType;
        }

        public /* synthetic */ WrapBuilder(String str, int i, int i2, ContentType contentType, ContentQueryType contentQueryType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? 100 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? null : contentType, (i3 & 16) != 0 ? ContentQueryType.BY_ENGRAM_ID : contentQueryType);
        }
    }

    public /* synthetic */ ContentQueryOption(String str, int i, int i2, ContentType contentType, ContentQueryType contentQueryType, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, contentType, contentQueryType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final ContentType getContentType() {
        return this.contentType;
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

    public final ContentQueryType getQueryType() {
        return this.queryType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.engramId);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        ContentType contentType = this.contentType;
        if (contentType == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(contentType.name());
        }
        parcel.writeString(this.queryType.name());
    }

    private ContentQueryOption(String str, int i, int i2, ContentType contentType, ContentQueryType contentQueryType) {
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.contentType = contentType;
        this.queryType = contentQueryType;
    }

    public /* synthetic */ ContentQueryOption(String str, int i, int i2, ContentType contentType, ContentQueryType contentQueryType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? 100 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? null : contentType, (i3 & 16) != 0 ? ContentQueryType.BY_ENGRAM_ID : contentQueryType);
    }
}
