package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.activity.ActivityType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EngramSearchActivityOption implements Parcelable {
    public static final Parcelable.Creator<EngramSearchActivityOption> CREATOR = new Creator();
    private final ActivityType activityType;
    private final boolean contentFill;
    private final String keywords;
    private final int limit;
    private final int offset;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new EngramSearchActivityOption(parcel.readString(), parcel.readInt(), parcel.readInt(), ActivityType.valueOf(parcel.readString()), parcel.readInt() != 0, null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramSearchActivityOption[i];
        }
    }

    public /* synthetic */ EngramSearchActivityOption(String str, int i, int i2, ActivityType activityType, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, activityType, z);
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
        parcel.writeString(this.activityType.name());
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    private EngramSearchActivityOption(String str, int i, int i2, ActivityType activityType, boolean z) {
        this.keywords = str;
        this.limit = i;
        this.offset = i2;
        this.activityType = activityType;
        this.contentFill = z;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapBuilder {
        public final ActivityType activityType;
        public final boolean contentFill;
        public final String keywords;
        public final int limit;
        public final int offset;

        public WrapBuilder(String str, int i, int i2, ActivityType activityType, boolean z) {
            this.keywords = str;
            this.limit = i;
            this.offset = i2;
            this.activityType = activityType;
            this.contentFill = z;
        }

        public /* synthetic */ WrapBuilder(String str, int i, int i2, ActivityType activityType, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, activityType, z);
        }
    }

    public /* synthetic */ EngramSearchActivityOption(String str, int i, int i2, ActivityType activityType, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? 20 : i, (i3 & 4) != 0 ? 0 : i2, activityType, (i3 & 16) != 0 ? false : z);
    }
}
