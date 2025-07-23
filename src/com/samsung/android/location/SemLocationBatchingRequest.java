package com.samsung.android.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.ParcelableCallAnalytics;
import com.samsung.android.lock.LsConstants;

/* loaded from: classes6.dex */
public class SemLocationBatchingRequest implements Parcelable {
    public static final Parcelable.Creator<SemLocationBatchingRequest> CREATOR = new Parcelable.Creator<SemLocationBatchingRequest>() { // from class: com.samsung.android.location.SemLocationBatchingRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemLocationBatchingRequest createFromParcel(Parcel parcel) {
            return new SemLocationBatchingRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemLocationBatchingRequest[] newArray(int i) {
            return new SemLocationBatchingRequest[i];
        }
    };
    private long maxInterval;
    private int maxNumUpdates;
    private long maxWaitTime;
    private float minDisplacement;
    private long minInterval;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemLocationBatchingRequest() {
        this.minInterval = ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        this.maxInterval = LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT;
        this.maxWaitTime = 36000000L;
        this.maxNumUpdates = 100;
        this.minDisplacement = 500.0f;
    }

    public long getMinInterval() {
        return this.minInterval;
    }

    public long getMaxInterval() {
        return this.maxInterval;
    }

    public long getMaxWaitTime() {
        return this.maxWaitTime;
    }

    public int getMaxNumUpdates() {
        return this.maxNumUpdates;
    }

    public float getMinDisplacement() {
        return this.minDisplacement;
    }

    public SemLocationBatchingRequest setMinInterval(long j) {
        this.minInterval = j;
        return this;
    }

    public SemLocationBatchingRequest setMaxInterval(long j) {
        this.maxInterval = j;
        return this;
    }

    public SemLocationBatchingRequest setMaxWaitTime(long j) {
        this.maxWaitTime = j;
        return this;
    }

    public SemLocationBatchingRequest setMaxNumUpdates(int i) {
        this.maxNumUpdates = i;
        return this;
    }

    public SemLocationBatchingRequest setMinDisplacement(float f) {
        this.minDisplacement = f;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.minInterval);
        parcel.writeLong(this.maxInterval);
        parcel.writeLong(this.maxWaitTime);
        parcel.writeInt(this.maxNumUpdates);
        parcel.writeFloat(this.minDisplacement);
    }

    private SemLocationBatchingRequest(Parcel parcel) {
        this.minInterval = ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        this.maxInterval = LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT;
        this.maxWaitTime = 36000000L;
        this.maxNumUpdates = 100;
        this.minDisplacement = 500.0f;
        this.minInterval = parcel.readLong();
        this.maxInterval = parcel.readLong();
        this.maxWaitTime = parcel.readLong();
        this.maxNumUpdates = parcel.readInt();
        this.minDisplacement = parcel.readFloat();
    }
}
