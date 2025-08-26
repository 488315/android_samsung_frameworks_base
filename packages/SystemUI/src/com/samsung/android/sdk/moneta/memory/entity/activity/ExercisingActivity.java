package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.ExerciseType;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ExercisingActivity extends Activity {
    public static final Parcelable.Creator<ExercisingActivity> CREATOR = new Creator();
    private final Float calorie;
    private final List<Content> contents;
    private final Long endTimestamp;
    private final ExerciseType exerciseType;
    private final String id;
    private final Place location;
    private final Float maxHeartRate;
    private final Float meanHeartRate;
    private final Float minHeartRate;
    private final String name;
    private final long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(ExercisingActivity.class, parcel, arrayList, iM, 1);
            }
            return new ExercisingActivity(string, arrayList, parcel.readString(), parcel.readInt() == 0 ? null : Place.CREATOR.createFromParcel(parcel), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readLong(), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null, ExerciseType.valueOf(parcel.readString()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ExercisingActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ExercisingActivity(String str, List<? extends Content> list, String str2, Place place, Float f, Float f2, Float f3, Float f4, long j, Long l, ExerciseType exerciseType) {
        super(ActivityType.Exercising);
        this.id = str;
        this.contents = list;
        this.name = str2;
        this.location = place;
        this.calorie = f;
        this.maxHeartRate = f2;
        this.meanHeartRate = f3;
        this.minHeartRate = f4;
        this.startTimestamp = j;
        this.endTimestamp = l;
        this.exerciseType = exerciseType;
    }

    public static /* synthetic */ ExercisingActivity copy$default(ExercisingActivity exercisingActivity, String str, List list, String str2, Place place, Float f, Float f2, Float f3, Float f4, long j, Long l, ExerciseType exerciseType, int i, Object obj) {
        if ((i & 1) != 0) {
            str = exercisingActivity.id;
        }
        if ((i & 2) != 0) {
            list = exercisingActivity.contents;
        }
        if ((i & 4) != 0) {
            str2 = exercisingActivity.name;
        }
        if ((i & 8) != 0) {
            place = exercisingActivity.location;
        }
        if ((i & 16) != 0) {
            f = exercisingActivity.calorie;
        }
        if ((i & 32) != 0) {
            f2 = exercisingActivity.maxHeartRate;
        }
        if ((i & 64) != 0) {
            f3 = exercisingActivity.meanHeartRate;
        }
        if ((i & 128) != 0) {
            f4 = exercisingActivity.minHeartRate;
        }
        if ((i & 256) != 0) {
            j = exercisingActivity.startTimestamp;
        }
        if ((i & 512) != 0) {
            l = exercisingActivity.endTimestamp;
        }
        if ((i & 1024) != 0) {
            exerciseType = exercisingActivity.exerciseType;
        }
        long j2 = j;
        Float f5 = f3;
        Float f6 = f4;
        Float f7 = f;
        Float f8 = f2;
        String str3 = str2;
        Place place2 = place;
        return exercisingActivity.copy(str, list, str3, place2, f7, f8, f5, f6, j2, l, exerciseType);
    }

    public final String component1() {
        return this.id;
    }

    public final Long component10() {
        return this.endTimestamp;
    }

    public final ExerciseType component11() {
        return this.exerciseType;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final String component3() {
        return this.name;
    }

    public final Place component4() {
        return this.location;
    }

    public final Float component5() {
        return this.calorie;
    }

    public final Float component6() {
        return this.maxHeartRate;
    }

    public final Float component7() {
        return this.meanHeartRate;
    }

    public final Float component8() {
        return this.minHeartRate;
    }

    public final long component9() {
        return this.startTimestamp;
    }

    public final ExercisingActivity copy(String str, List<? extends Content> list, String str2, Place place, Float f, Float f2, Float f3, Float f4, long j, Long l, ExerciseType exerciseType) {
        return new ExercisingActivity(str, list, str2, place, f, f2, f3, f4, j, l, exerciseType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ExercisingActivity)) {
            return false;
        }
        ExercisingActivity exercisingActivity = (ExercisingActivity) obj;
        return Intrinsics.areEqual(this.id, exercisingActivity.id) && Intrinsics.areEqual(this.contents, exercisingActivity.contents) && Intrinsics.areEqual(this.name, exercisingActivity.name) && Intrinsics.areEqual(this.location, exercisingActivity.location) && Intrinsics.areEqual(this.calorie, exercisingActivity.calorie) && Intrinsics.areEqual(this.maxHeartRate, exercisingActivity.maxHeartRate) && Intrinsics.areEqual(this.meanHeartRate, exercisingActivity.meanHeartRate) && Intrinsics.areEqual(this.minHeartRate, exercisingActivity.minHeartRate) && this.startTimestamp == exercisingActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, exercisingActivity.endTimestamp) && this.exerciseType == exercisingActivity.exerciseType;
    }

    public final Float getCalorie() {
        return this.calorie;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final ExerciseType getExerciseType() {
        return this.exerciseType;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final Place getLocation() {
        return this.location;
    }

    public final Float getMaxHeartRate() {
        return this.maxHeartRate;
    }

    public final Float getMeanHeartRate() {
        return this.meanHeartRate;
    }

    public final Float getMinHeartRate() {
        return this.minHeartRate;
    }

    public final String getName() {
        return this.name;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.name);
        Place place = this.location;
        int iHashCode = (iM + (place == null ? 0 : place.hashCode())) * 31;
        Float f = this.calorie;
        int iHashCode2 = (iHashCode + (f == null ? 0 : f.hashCode())) * 31;
        Float f2 = this.maxHeartRate;
        int iHashCode3 = (iHashCode2 + (f2 == null ? 0 : f2.hashCode())) * 31;
        Float f3 = this.meanHeartRate;
        int iHashCode4 = (iHashCode3 + (f3 == null ? 0 : f3.hashCode())) * 31;
        Float f4 = this.minHeartRate;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m((iHashCode4 + (f4 == null ? 0 : f4.hashCode())) * 31, 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return this.exerciseType.hashCode() + ((iM2 + (l != null ? l.hashCode() : 0)) * 31);
    }

    public String toString() {
        return "ExercisingActivity(id=" + this.id + ", contents=" + this.contents + ", name=" + this.name + ", location=" + this.location + ", calorie=" + this.calorie + ", maxHeartRate=" + this.maxHeartRate + ", meanHeartRate=" + this.meanHeartRate + ", minHeartRate=" + this.minHeartRate + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ", exerciseType=" + this.exerciseType + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeString(this.name);
        Place place = this.location;
        if (place == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            place.writeToParcel(parcel, i);
        }
        Float f = this.calorie;
        if (f == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeFloat(f.floatValue());
        }
        Float f2 = this.maxHeartRate;
        if (f2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeFloat(f2.floatValue());
        }
        Float f3 = this.meanHeartRate;
        if (f3 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeFloat(f3.floatValue());
        }
        Float f4 = this.minHeartRate;
        if (f4 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeFloat(f4.floatValue());
        }
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        parcel.writeString(this.exerciseType.name());
    }
}
