package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.ExercisingActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.ExerciseType;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ExercisingActivityWrapperV1 extends ActivityWrapper {
    private final Float calorie;
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String exerciseType;
    private final String id;
    private final Place location;
    private final Float maxHeartRate;
    private final Float meanHeartRate;
    private final Float minHeartRate;
    private final String name;
    private final long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<ExercisingActivityWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            int i = 0;
            while (i != readInt) {
                i = Engram$Creator$$ExternalSyntheticOutline0.m(ExercisingActivityWrapperV1.class, parcel, arrayList, i, 1);
            }
            return new ExercisingActivityWrapperV1(readString, arrayList, parcel.readString(), parcel.readInt() == 0 ? null : Place.CREATOR.createFromParcel(parcel), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readInt() == 0 ? null : Float.valueOf(parcel.readFloat()), parcel.readLong(), parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null, parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ExercisingActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ExercisingActivityWrapperV1(String str, List<? extends Content> list, String str2, Place place, Float f, Float f2, Float f3, Float f4, long j, Long l, String str3) {
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
        this.exerciseType = str3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final Float getCalorie() {
        return this.calorie;
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getExerciseType() {
        return this.exerciseType;
    }

    public final String getId() {
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
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
        parcel.writeString(this.exerciseType);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public ExercisingActivity toActivity() {
        String str = this.id;
        List<Content> list = this.contents;
        String str2 = this.name;
        Place place = this.location;
        Float f = this.calorie;
        Float f2 = this.maxHeartRate;
        Float f3 = this.meanHeartRate;
        Float f4 = this.minHeartRate;
        long j = this.startTimestamp;
        Long l = this.endTimestamp;
        ExerciseType.Companion companion = ExerciseType.Companion;
        String str3 = this.exerciseType;
        companion.getClass();
        ExerciseType exerciseType = (ExerciseType) ((LinkedHashMap) ExerciseType.map).get(str3);
        if (exerciseType == null) {
            exerciseType = ExerciseType.OTHER;
        }
        return new ExercisingActivity(str, list, str2, place, f, f2, f3, f4, j, l, exerciseType);
    }
}
