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
public final class TakingPicturesActivity extends Activity {
    public static final Parcelable.Creator<TakingPicturesActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final List<Place> locations;
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
                i = Engram$Creator$$ExternalSyntheticOutline0.m(TakingPicturesActivity.class, parcel, arrayList, i, 1);
            }
            int readInt2 = parcel.readInt();
            ArrayList arrayList2 = new ArrayList(readInt2);
            for (int i2 = 0; i2 != readInt2; i2++) {
                arrayList2.add(Place.CREATOR.createFromParcel(parcel));
            }
            return new TakingPicturesActivity(readString, arrayList, arrayList2, parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TakingPicturesActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TakingPicturesActivity(String str, List<? extends Content> list, List<Place> list2, long j, Long l) {
        super(ActivityType.TakingPictures);
        this.id = str;
        this.contents = list;
        this.locations = list2;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ TakingPicturesActivity copy$default(TakingPicturesActivity takingPicturesActivity, String str, List list, List list2, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = takingPicturesActivity.id;
        }
        if ((i & 2) != 0) {
            list = takingPicturesActivity.contents;
        }
        if ((i & 4) != 0) {
            list2 = takingPicturesActivity.locations;
        }
        if ((i & 8) != 0) {
            j = takingPicturesActivity.startTimestamp;
        }
        if ((i & 16) != 0) {
            l = takingPicturesActivity.endTimestamp;
        }
        Long l2 = l;
        List list3 = list2;
        return takingPicturesActivity.copy(str, list, list3, j, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final List<Place> component3() {
        return this.locations;
    }

    public final long component4() {
        return this.startTimestamp;
    }

    public final Long component5() {
        return this.endTimestamp;
    }

    public final TakingPicturesActivity copy(String str, List<? extends Content> list, List<Place> list2, long j, Long l) {
        return new TakingPicturesActivity(str, list, list2, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TakingPicturesActivity)) {
            return false;
        }
        TakingPicturesActivity takingPicturesActivity = (TakingPicturesActivity) obj;
        return Intrinsics.areEqual(this.id, takingPicturesActivity.id) && Intrinsics.areEqual(this.contents, takingPicturesActivity.contents) && Intrinsics.areEqual(this.locations, takingPicturesActivity.locations) && this.startTimestamp == takingPicturesActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, takingPicturesActivity.endTimestamp);
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

    public final List<Place> getLocations() {
        return this.locations;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int m = MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.locations, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31), 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return m + (l == null ? 0 : l.hashCode());
    }

    public String toString() {
        return "TakingPicturesActivity(id=" + this.id + ", contents=" + this.contents + ", locations=" + this.locations + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        Iterator m2 = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.locations);
        while (m2.hasNext()) {
            ((Place) m2.next()).writeToParcel(parcel, i);
        }
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
    }
}
