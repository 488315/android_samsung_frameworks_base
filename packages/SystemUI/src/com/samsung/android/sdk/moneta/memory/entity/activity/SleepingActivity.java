package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SleepingActivity extends Activity {
    public static final Parcelable.Creator<SleepingActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final int sleepScore;
    private final long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(SleepingActivity.class, parcel, arrayList, iM, 1);
            }
            return new SleepingActivity(string, arrayList, parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SleepingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SleepingActivity(String str, List<? extends Content> list, long j, Long l, int i) {
        super(ActivityType.Sleeping);
        this.id = str;
        this.contents = list;
        this.startTimestamp = j;
        this.endTimestamp = l;
        this.sleepScore = i;
    }

    public static /* synthetic */ SleepingActivity copy$default(SleepingActivity sleepingActivity, String str, List list, long j, Long l, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = sleepingActivity.id;
        }
        if ((i2 & 2) != 0) {
            list = sleepingActivity.contents;
        }
        if ((i2 & 4) != 0) {
            j = sleepingActivity.startTimestamp;
        }
        if ((i2 & 8) != 0) {
            l = sleepingActivity.endTimestamp;
        }
        if ((i2 & 16) != 0) {
            i = sleepingActivity.sleepScore;
        }
        long j2 = j;
        return sleepingActivity.copy(str, list, j2, l, i);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final long component3() {
        return this.startTimestamp;
    }

    public final Long component4() {
        return this.endTimestamp;
    }

    public final int component5() {
        return this.sleepScore;
    }

    public final SleepingActivity copy(String str, List<? extends Content> list, long j, Long l, int i) {
        return new SleepingActivity(str, list, j, l, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SleepingActivity)) {
            return false;
        }
        SleepingActivity sleepingActivity = (SleepingActivity) obj;
        return Intrinsics.areEqual(this.id, sleepingActivity.id) && Intrinsics.areEqual(this.contents, sleepingActivity.contents) && this.startTimestamp == sleepingActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, sleepingActivity.endTimestamp) && this.sleepScore == sleepingActivity.sleepScore;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final int getSleepScore() {
        return this.sleepScore;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int iM = MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return Integer.hashCode(this.sleepScore) + ((iM + (l == null ? 0 : l.hashCode())) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SleepingActivity(id=");
        sb.append(this.id);
        sb.append(", contents=");
        sb.append(this.contents);
        sb.append(", startTimestamp=");
        sb.append(this.startTimestamp);
        sb.append(", endTimestamp=");
        sb.append(this.endTimestamp);
        sb.append(", sleepScore=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.sleepScore, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        parcel.writeInt(this.sleepScore);
    }
}
