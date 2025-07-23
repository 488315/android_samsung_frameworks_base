package com.samsung.android.sdk.moneta.memory.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.Activity;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Engram implements Parcelable {
    public static final Parcelable.Creator<Engram> CREATOR = new Creator();
    private final List<Activity> activities;
    private final long endTimestamp;
    private final String id;
    private final List<String> specialMoments;
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
                i = Engram$Creator$$ExternalSyntheticOutline0.m(Engram.class, parcel, arrayList, i, 1);
            }
            return new Engram(readString, arrayList, parcel.readLong(), parcel.readLong(), parcel.createStringArrayList());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Engram[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Engram(String str, List<? extends Activity> list, long j, long j2, List<String> list2) {
        this.id = str;
        this.activities = list;
        this.startTimestamp = j;
        this.endTimestamp = j2;
        this.specialMoments = list2;
    }

    public static /* synthetic */ Engram copy$default(Engram engram, String str, List list, long j, long j2, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = engram.id;
        }
        if ((i & 2) != 0) {
            list = engram.activities;
        }
        if ((i & 4) != 0) {
            j = engram.startTimestamp;
        }
        if ((i & 8) != 0) {
            j2 = engram.endTimestamp;
        }
        if ((i & 16) != 0) {
            list2 = engram.specialMoments;
        }
        List list3 = list2;
        long j3 = j2;
        return engram.copy(str, list, j, j3, list3);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Activity> component2() {
        return this.activities;
    }

    public final long component3() {
        return this.startTimestamp;
    }

    public final long component4() {
        return this.endTimestamp;
    }

    public final List<String> component5() {
        return this.specialMoments;
    }

    public final Engram copy(String str, List<? extends Activity> list, long j, long j2, List<String> list2) {
        return new Engram(str, list, j, j2, list2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Engram)) {
            return false;
        }
        Engram engram = (Engram) obj;
        return Intrinsics.areEqual(this.id, engram.id) && Intrinsics.areEqual(this.activities, engram.activities) && this.startTimestamp == engram.startTimestamp && this.endTimestamp == engram.endTimestamp && Intrinsics.areEqual(this.specialMoments, engram.specialMoments);
    }

    public final List<Activity> getActivities() {
        return this.activities;
    }

    public final long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getId() {
        return this.id;
    }

    public final List<String> getSpecialMoments() {
        return this.specialMoments;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        return this.specialMoments.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.activities, this.id.hashCode() * 31, 31), 31, this.startTimestamp), 31, this.endTimestamp);
    }

    public String toString() {
        return "Engram(id=" + this.id + ", activities=" + this.activities + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ", specialMoments=" + this.specialMoments + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.activities);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        parcel.writeLong(this.startTimestamp);
        parcel.writeLong(this.endTimestamp);
        parcel.writeStringList(this.specialMoments);
    }

    public Engram(String str, List list, long j, long j2, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, j, j2, (i & 16) != 0 ? EmptyList.INSTANCE : list2);
    }
}
