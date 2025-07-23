package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StayingActivity extends Activity {
    public static final Parcelable.Creator<StayingActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final Place location;
    private final long startTimestamp;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            int i = 0;
            while (i != readInt) {
                i = Engram$Creator$$ExternalSyntheticOutline0.m(StayingActivity.class, parcel, arrayList, i, 1);
            }
            return new StayingActivity(readString, arrayList, Place.CREATOR.createFromParcel(parcel), parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new StayingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StayingActivity(String str, List<? extends Content> list, Place place, long j, Long l) {
        super(ActivityType.Staying);
        this.id = str;
        this.contents = list;
        this.location = place;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ StayingActivity copy$default(StayingActivity stayingActivity, String str, List list, Place place, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = stayingActivity.id;
        }
        if ((i & 2) != 0) {
            list = stayingActivity.contents;
        }
        if ((i & 4) != 0) {
            place = stayingActivity.location;
        }
        if ((i & 8) != 0) {
            j = stayingActivity.startTimestamp;
        }
        if ((i & 16) != 0) {
            l = stayingActivity.endTimestamp;
        }
        Long l2 = l;
        Place place2 = place;
        return stayingActivity.copy(str, list, place2, j, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final Place component3() {
        return this.location;
    }

    public final long component4() {
        return this.startTimestamp;
    }

    public final Long component5() {
        return this.endTimestamp;
    }

    public final StayingActivity copy(String str, List<? extends Content> list, Place place, long j, Long l) {
        return new StayingActivity(str, list, place, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StayingActivity)) {
            return false;
        }
        StayingActivity stayingActivity = (StayingActivity) obj;
        return Intrinsics.areEqual(this.id, stayingActivity.id) && Intrinsics.areEqual(this.contents, stayingActivity.contents) && Intrinsics.areEqual(this.location, stayingActivity.location) && this.startTimestamp == stayingActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, stayingActivity.endTimestamp);
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

    public final Place getLocation() {
        return this.location;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int m = MoveResult$$ExternalSyntheticOutline0.m((this.location.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31)) * 31, 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return m + (l == null ? 0 : l.hashCode());
    }

    public String toString() {
        return "StayingActivity(id=" + this.id + ", contents=" + this.contents + ", location=" + this.location + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        this.location.writeToParcel(parcel, i);
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
    }
}
