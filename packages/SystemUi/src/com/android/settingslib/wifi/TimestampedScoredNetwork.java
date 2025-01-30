package com.android.settingslib.wifi;

import android.net.ScoredNetwork;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TimestampedScoredNetwork implements Parcelable {
    public static final Parcelable.Creator<TimestampedScoredNetwork> CREATOR = new Parcelable.Creator() { // from class: com.android.settingslib.wifi.TimestampedScoredNetwork.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new TimestampedScoredNetwork(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TimestampedScoredNetwork[i];
        }
    };
    public ScoredNetwork mScore;
    public long mUpdatedTimestampMillis;

    public TimestampedScoredNetwork(ScoredNetwork scoredNetwork, long j) {
        this.mScore = scoredNetwork;
        this.mUpdatedTimestampMillis = j;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mScore, i);
        parcel.writeLong(this.mUpdatedTimestampMillis);
    }

    public TimestampedScoredNetwork(Parcel parcel) {
        this.mScore = parcel.readParcelable(ScoredNetwork.class.getClassLoader());
        this.mUpdatedTimestampMillis = parcel.readLong();
    }
}
