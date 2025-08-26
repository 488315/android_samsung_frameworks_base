package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.ActivityType;
import com.samsung.android.sdk.moneta.memory.option.ActivityQueryOption;
import com.samsung.android.sdk.moneta.memory.option.ActivityQueryType;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class ActivityQueryOptionWrapperV1 implements Parcelable {
    private final ActivityType activityType;
    private final boolean contentFill;
    private final Long endTimestamp;
    private final String engramId;
    private final int limit;
    private final int offset;
    private final int queryType;
    private final Long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<ActivityQueryOptionWrapperV1> CREATOR = new Creator();

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
            return new ActivityQueryOptionWrapperV1(parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0 ? ActivityType.valueOf(parcel.readString()) : null, parcel.readInt(), parcel.readInt() != 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ActivityQueryOptionWrapperV1[i];
        }
    }

    public ActivityQueryOptionWrapperV1() {
        this(null, null, null, 0, 0, null, 0, false, 255, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final ActivityType getActivityType() {
        return this.activityType;
    }

    public final boolean getContentFill() {
        return this.contentFill;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
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

    public final Long getStartTimestamp() {
        return this.startTimestamp;
    }

    public final ActivityQueryOption toOption() {
        Object next;
        Long l = this.startTimestamp;
        Long l2 = this.endTimestamp;
        String str = this.engramId;
        int i = this.limit;
        int i2 = this.offset;
        ActivityType activityType = this.activityType;
        ActivityQueryType.Companion companion = ActivityQueryType.Companion;
        int i3 = this.queryType;
        companion.getClass();
        Iterator<E> it = ActivityQueryType.getEntries().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((ActivityQueryType) next).getValue() == i3) {
                break;
            }
        }
        ActivityQueryType activityQueryType = (ActivityQueryType) next;
        if (activityQueryType == null) {
            activityQueryType = ActivityQueryType.BETWEEN_TIMESTAMP;
        }
        ActivityQueryOption.WrapBuilder wrapBuilder = new ActivityQueryOption.WrapBuilder(l, l2, str, i, i2, activityType, activityQueryType, this.contentFill);
        return new ActivityQueryOption(wrapBuilder.startTimestamp, wrapBuilder.endTimestamp, wrapBuilder.engramId, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.activityType, wrapBuilder.queryType, wrapBuilder.contentFill, null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Long l = this.startTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        Long l2 = this.endTimestamp;
        if (l2 == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l2);
        }
        parcel.writeString(this.engramId);
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        ActivityType activityType = this.activityType;
        if (activityType == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(activityType.name());
        }
        parcel.writeInt(this.queryType);
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    public ActivityQueryOptionWrapperV1(Long l, Long l2, String str, int i, int i2, ActivityType activityType, int i3, boolean z) {
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.activityType = activityType;
        this.queryType = i3;
        this.contentFill = z;
    }

    public /* synthetic */ ActivityQueryOptionWrapperV1(Long l, Long l2, String str, int i, int i2, ActivityType activityType, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : l, (i4 & 2) != 0 ? null : l2, (i4 & 4) != 0 ? null : str, (i4 & 8) != 0 ? 100 : i, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? null : activityType, (i4 & 64) != 0 ? ActivityQueryType.BETWEEN_TIMESTAMP.getValue() : i3, (i4 & 128) != 0 ? true : z);
    }
}
