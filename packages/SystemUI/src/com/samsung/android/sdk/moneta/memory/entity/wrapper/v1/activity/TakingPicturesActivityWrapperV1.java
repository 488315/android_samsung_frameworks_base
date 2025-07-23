package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.TakingPicturesActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.context.Place;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TakingPicturesActivityWrapperV1 extends ActivityWrapper {
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final List<Place> locations;
    private final long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<TakingPicturesActivityWrapperV1> CREATOR = new Creator();

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
                i = Engram$Creator$$ExternalSyntheticOutline0.m(TakingPicturesActivityWrapperV1.class, parcel, arrayList, i, 1);
            }
            int readInt2 = parcel.readInt();
            ArrayList arrayList2 = new ArrayList(readInt2);
            for (int i2 = 0; i2 != readInt2; i2++) {
                arrayList2.add(Place.CREATOR.createFromParcel(parcel));
            }
            return new TakingPicturesActivityWrapperV1(readString, arrayList, arrayList2, parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TakingPicturesActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TakingPicturesActivityWrapperV1(String str, List<? extends Content> list, List<Place> list2, long j, Long l) {
        this.id = str;
        this.contents = list;
        this.locations = list2;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getId() {
        return this.id;
    }

    public final List<Place> getLocations() {
        return this.locations;
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

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public TakingPicturesActivity toActivity() {
        return new TakingPicturesActivity(this.id, this.contents, this.locations, this.startTimestamp, this.endTimestamp);
    }
}
