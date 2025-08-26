package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class When implements Parcelable {
    public static final Parcelable.Creator<When> CREATOR = new Creator();
    private final long endTime;
    private final String sourcePackage;
    private final String sourceUri;
    private final long startTime;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new When(parcel.readLong(), parcel.readLong(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new When[i];
        }
    }

    public When(long j, long j2, String str, String str2) {
        this.startTime = j;
        this.endTime = j2;
        this.sourcePackage = str;
        this.sourceUri = str2;
    }

    public static /* synthetic */ When copy$default(When when, long j, long j2, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = when.startTime;
        }
        long j3 = j;
        if ((i & 2) != 0) {
            j2 = when.endTime;
        }
        long j4 = j2;
        if ((i & 4) != 0) {
            str = when.sourcePackage;
        }
        String str3 = str;
        if ((i & 8) != 0) {
            str2 = when.sourceUri;
        }
        return when.copy(j3, j4, str3, str2);
    }

    public final long component1() {
        return this.startTime;
    }

    public final long component2() {
        return this.endTime;
    }

    public final String component3() {
        return this.sourcePackage;
    }

    public final String component4() {
        return this.sourceUri;
    }

    public final When copy(long j, long j2, String str, String str2) {
        return new When(j, j2, str, str2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof When)) {
            return false;
        }
        When when = (When) obj;
        return this.startTime == when.startTime && this.endTime == when.endTime && Intrinsics.areEqual(this.sourcePackage, when.sourcePackage) && Intrinsics.areEqual(this.sourceUri, when.sourceUri);
    }

    public final long getEndTime() {
        return this.endTime;
    }

    public final String getSourcePackage() {
        return this.sourcePackage;
    }

    public final String getSourceUri() {
        return this.sourceUri;
    }

    public final long getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return this.sourceUri.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.startTime) * 31, 31, this.endTime), 31, this.sourcePackage);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("When(startTime=");
        sb.append(this.startTime);
        sb.append(", endTime=");
        sb.append(this.endTime);
        sb.append(", sourcePackage=");
        sb.append(this.sourcePackage);
        sb.append(", sourceUri=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.sourceUri, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.startTime);
        parcel.writeLong(this.endTime);
        parcel.writeString(this.sourcePackage);
        parcel.writeString(this.sourceUri);
    }
}
