package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
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
public final class MovingActivity extends Activity {
    public static final Parcelable.Creator<MovingActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Place endLocation;
    private final Long endTimestamp;
    private final String id;
    private final float movingSpeed;
    private final Place startLocation;
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
                i = Engram$Creator$$ExternalSyntheticOutline0.m(MovingActivity.class, parcel, arrayList, i, 1);
            }
            Parcelable.Creator<Place> creator = Place.CREATOR;
            return new MovingActivity(readString, arrayList, creator.createFromParcel(parcel), parcel.readInt() == 0 ? null : creator.createFromParcel(parcel), parcel.readFloat(), parcel.readLong(), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MovingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MovingActivity(String str, List<? extends Content> list, Place place, Place place2, float f, long j, Long l) {
        super(ActivityType.Moving);
        this.id = str;
        this.contents = list;
        this.startLocation = place;
        this.endLocation = place2;
        this.movingSpeed = f;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ MovingActivity copy$default(MovingActivity movingActivity, String str, List list, Place place, Place place2, float f, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = movingActivity.id;
        }
        if ((i & 2) != 0) {
            list = movingActivity.contents;
        }
        if ((i & 4) != 0) {
            place = movingActivity.startLocation;
        }
        if ((i & 8) != 0) {
            place2 = movingActivity.endLocation;
        }
        if ((i & 16) != 0) {
            f = movingActivity.movingSpeed;
        }
        if ((i & 32) != 0) {
            j = movingActivity.startTimestamp;
        }
        if ((i & 64) != 0) {
            l = movingActivity.endTimestamp;
        }
        Long l2 = l;
        long j2 = j;
        float f2 = f;
        Place place3 = place;
        return movingActivity.copy(str, list, place3, place2, f2, j2, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final Place component3() {
        return this.startLocation;
    }

    public final Place component4() {
        return this.endLocation;
    }

    public final float component5() {
        return this.movingSpeed;
    }

    public final long component6() {
        return this.startTimestamp;
    }

    public final Long component7() {
        return this.endTimestamp;
    }

    public final MovingActivity copy(String str, List<? extends Content> list, Place place, Place place2, float f, long j, Long l) {
        return new MovingActivity(str, list, place, place2, f, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MovingActivity)) {
            return false;
        }
        MovingActivity movingActivity = (MovingActivity) obj;
        return Intrinsics.areEqual(this.id, movingActivity.id) && Intrinsics.areEqual(this.contents, movingActivity.contents) && Intrinsics.areEqual(this.startLocation, movingActivity.startLocation) && Intrinsics.areEqual(this.endLocation, movingActivity.endLocation) && Float.compare(this.movingSpeed, movingActivity.movingSpeed) == 0 && this.startTimestamp == movingActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, movingActivity.endTimestamp);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Place getEndLocation() {
        return this.endLocation;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final float getMovingSpeed() {
        return this.movingSpeed;
    }

    public final Place getStartLocation() {
        return this.startLocation;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int hashCode = (this.startLocation.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31)) * 31;
        Place place = this.endLocation;
        int m = MoveResult$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.movingSpeed, (hashCode + (place == null ? 0 : place.hashCode())) * 31, 31), 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return m + (l != null ? l.hashCode() : 0);
    }

    public String toString() {
        return "MovingActivity(id=" + this.id + ", contents=" + this.contents + ", startLocation=" + this.startLocation + ", endLocation=" + this.endLocation + ", movingSpeed=" + this.movingSpeed + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        this.startLocation.writeToParcel(parcel, i);
        Place place = this.endLocation;
        if (place == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            place.writeToParcel(parcel, i);
        }
        parcel.writeFloat(this.movingSpeed);
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
    }
}
