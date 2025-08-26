package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.Activity;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.EngramWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramWrapperV1 extends EngramWrapper {
    private final List<Activity> activities;
    private final long endTimestamp;
    private final String id;
    private final List<String> specialMoments;
    private final long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(EngramWrapperV1.class, parcel, arrayList, iM, 1);
            }
            return new EngramWrapperV1(string, arrayList, parcel.readLong(), parcel.readLong(), parcel.createStringArrayList());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EngramWrapperV1(String str, List<? extends Activity> list, long j, long j2, List<String> list2) {
        this.id = str;
        this.activities = list;
        this.startTimestamp = j;
        this.endTimestamp = j2;
        this.specialMoments = list2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.EngramWrapper
    public Engram toEngram() {
        return new Engram(this.id, this.activities, this.startTimestamp, this.endTimestamp, this.specialMoments);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.activities);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeLong(this.startTimestamp);
        parcel.writeLong(this.endTimestamp);
        parcel.writeStringList(this.specialMoments);
    }

    public EngramWrapperV1(String str, List list, long j, long j2, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, j, j2, (i & 16) != 0 ? EmptyList.INSTANCE : list2);
    }
}
