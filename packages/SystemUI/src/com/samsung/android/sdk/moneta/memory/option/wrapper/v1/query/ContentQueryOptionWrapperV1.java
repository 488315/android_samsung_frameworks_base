package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.ContentType;
import com.samsung.android.sdk.moneta.memory.option.ContentQueryOption;
import com.samsung.android.sdk.moneta.memory.option.ContentQueryType;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class ContentQueryOptionWrapperV1 implements Parcelable {
    private final Integer contentType;
    private final String engramId;
    private final int limit;
    private final int offset;
    private final int queryType;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<ContentQueryOptionWrapperV1> CREATOR = new Creator();

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
            return new ContentQueryOptionWrapperV1(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt()), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ContentQueryOptionWrapperV1[i];
        }
    }

    public ContentQueryOptionWrapperV1() {
        this(null, 0, 0, null, 0, 31, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final Integer getContentType() {
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

    public final int getQueryType() {
        return this.queryType;
    }

    public final ContentQueryOption toOption() {
        Object next;
        String str = this.engramId;
        int i = this.limit;
        int i2 = this.offset;
        ContentType.Companion companion = ContentType.Companion;
        Integer num = this.contentType;
        companion.getClass();
        ContentType contentTypeFromInt = ContentType.Companion.fromInt(num);
        ContentQueryType.Companion companion2 = ContentQueryType.Companion;
        int i3 = this.queryType;
        companion2.getClass();
        Iterator<E> it = ContentQueryType.getEntries().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((ContentQueryType) next).getValue() == i3) {
                break;
            }
        }
        ContentQueryType contentQueryType = (ContentQueryType) next;
        if (contentQueryType == null) {
            contentQueryType = ContentQueryType.BY_ENGRAM_ID;
        }
        ContentQueryOption.WrapBuilder wrapBuilder = new ContentQueryOption.WrapBuilder(str, i, i2, contentTypeFromInt, contentQueryType);
        return new ContentQueryOption(wrapBuilder.activityId, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.contentType, wrapBuilder.queryType, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iIntValue;
        parcel.writeString(this.engramId);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        Integer num = this.contentType;
        if (num == null) {
            iIntValue = 0;
        } else {
            parcel.writeInt(1);
            iIntValue = num.intValue();
        }
        parcel.writeInt(iIntValue);
        parcel.writeInt(this.queryType);
    }

    public ContentQueryOptionWrapperV1(String str, int i, int i2, Integer num, int i3) {
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.contentType = num;
        this.queryType = i3;
    }

    public /* synthetic */ ContentQueryOptionWrapperV1(String str, int i, int i2, Integer num, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : str, (i4 & 2) != 0 ? 100 : i, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? null : num, (i4 & 16) != 0 ? ContentQueryType.BY_ENGRAM_ID.getValue() : i3);
    }
}
