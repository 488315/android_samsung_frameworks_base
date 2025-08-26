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

/* loaded from: classes4.dex */
public final class EatingActivity extends Activity {
    public static final Parcelable.Creator<EatingActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final List<String> foods;
    private final String id;
    private final Place location;
    private final long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(EatingActivity.class, parcel, arrayList, iM, 1);
            }
            return new EatingActivity(string, arrayList, parcel.createStringArrayList(), parcel.readInt() == 0 ? null : Place.CREATOR.createFromParcel(parcel), parcel.readLong(), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EatingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EatingActivity(String str, List<? extends Content> list, List<String> list2, Place place, long j, Long l) {
        super(ActivityType.Eating);
        this.id = str;
        this.contents = list;
        this.foods = list2;
        this.location = place;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ EatingActivity copy$default(EatingActivity eatingActivity, String str, List list, List list2, Place place, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = eatingActivity.id;
        }
        if ((i & 2) != 0) {
            list = eatingActivity.contents;
        }
        if ((i & 4) != 0) {
            list2 = eatingActivity.foods;
        }
        if ((i & 8) != 0) {
            place = eatingActivity.location;
        }
        if ((i & 16) != 0) {
            j = eatingActivity.startTimestamp;
        }
        if ((i & 32) != 0) {
            l = eatingActivity.endTimestamp;
        }
        Long l2 = l;
        long j2 = j;
        return eatingActivity.copy(str, list, list2, place, j2, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final List<String> component3() {
        return this.foods;
    }

    public final Place component4() {
        return this.location;
    }

    public final long component5() {
        return this.startTimestamp;
    }

    public final Long component6() {
        return this.endTimestamp;
    }

    public final EatingActivity copy(String str, List<? extends Content> list, List<String> list2, Place place, long j, Long l) {
        return new EatingActivity(str, list, list2, place, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EatingActivity)) {
            return false;
        }
        EatingActivity eatingActivity = (EatingActivity) obj;
        return Intrinsics.areEqual(this.id, eatingActivity.id) && Intrinsics.areEqual(this.contents, eatingActivity.contents) && Intrinsics.areEqual(this.foods, eatingActivity.foods) && Intrinsics.areEqual(this.location, eatingActivity.location) && this.startTimestamp == eatingActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, eatingActivity.endTimestamp);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final List<String> getFoods() {
        return this.foods;
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
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.foods, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31);
        Place place = this.location;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m((iM + (place == null ? 0 : place.hashCode())) * 31, 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return iM2 + (l != null ? l.hashCode() : 0);
    }

    public String toString() {
        return "EatingActivity(id=" + this.id + ", contents=" + this.contents + ", foods=" + this.foods + ", location=" + this.location + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeStringList(this.foods);
        Place place = this.location;
        if (place == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            place.writeToParcel(parcel, i);
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
