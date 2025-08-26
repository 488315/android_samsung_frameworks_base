package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.ActivityType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class ActivityQueryOption implements Parcelable {
    public static final Parcelable.Creator<ActivityQueryOption> CREATOR = new Creator();
    private final ActivityType activityType;
    private final boolean contentFill;
    private final Long endTimestamp;
    private final String engramId;
    private final int limit;
    private final int offset;
    private final ActivityQueryType queryType;
    private final Long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ActivityQueryOption(parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0 ? ActivityType.valueOf(parcel.readString()) : null, ActivityQueryType.valueOf(parcel.readString()), parcel.readInt() != 0, null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ActivityQueryOption[i];
        }
    }

    public final class WrapBuilder {
        public final ActivityType activityType;
        public final boolean contentFill;
        public final Long endTimestamp;
        public final String engramId;
        public final int limit;
        public final int offset;
        public final ActivityQueryType queryType;
        public final Long startTimestamp;

        public WrapBuilder() {
            this(null, null, null, 0, 0, null, null, false, 255, null);
        }

        public WrapBuilder(Long l, Long l2, String str, int i, int i2, ActivityType activityType, ActivityQueryType activityQueryType, boolean z) {
            this.startTimestamp = l;
            this.endTimestamp = l2;
            this.engramId = str;
            this.limit = i;
            this.offset = i2;
            this.activityType = activityType;
            this.queryType = activityQueryType;
            this.contentFill = z;
        }

        public /* synthetic */ WrapBuilder(Long l, Long l2, String str, int i, int i2, ActivityType activityType, ActivityQueryType activityQueryType, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? null : l, (i3 & 2) != 0 ? null : l2, (i3 & 4) != 0 ? null : str, (i3 & 8) != 0 ? 100 : i, (i3 & 16) != 0 ? 0 : i2, (i3 & 32) != 0 ? null : activityType, (i3 & 64) != 0 ? ActivityQueryType.BETWEEN_TIMESTAMP : activityQueryType, (i3 & 128) != 0 ? false : z);
        }
    }

    public /* synthetic */ ActivityQueryOption(Long l, Long l2, String str, int i, int i2, ActivityType activityType, ActivityQueryType activityQueryType, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(l, l2, str, i, i2, activityType, activityQueryType, z);
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

    public final ActivityQueryType getQueryType() {
        return this.queryType;
    }

    public final Long getStartTimestamp() {
        return this.startTimestamp;
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
        parcel.writeString(this.queryType.name());
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    private ActivityQueryOption(Long l, Long l2, String str, int i, int i2, ActivityType activityType, ActivityQueryType activityQueryType, boolean z) {
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.engramId = str;
        this.limit = i;
        this.offset = i2;
        this.activityType = activityType;
        this.queryType = activityQueryType;
        this.contentFill = z;
    }

    public /* synthetic */ ActivityQueryOption(Long l, Long l2, String str, int i, int i2, ActivityType activityType, ActivityQueryType activityQueryType, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : l, (i3 & 2) != 0 ? null : l2, (i3 & 4) != 0 ? null : str, (i3 & 8) != 0 ? 100 : i, (i3 & 16) != 0 ? 0 : i2, (i3 & 32) != 0 ? null : activityType, (i3 & 64) != 0 ? ActivityQueryType.BETWEEN_TIMESTAMP : activityQueryType, (i3 & 128) != 0 ? true : z);
    }
}
