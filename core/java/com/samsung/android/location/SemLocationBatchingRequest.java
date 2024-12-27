package com.samsung.android.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.ParcelableCallAnalytics;

import com.samsung.android.lock.LsConstants;

public class SemLocationBatchingRequest implements Parcelable {
    public static final Parcelable.Creator<SemLocationBatchingRequest> CREATOR =
            new Parcelable.Creator<
                    SemLocationBatchingRequest>() { // from class:
                                                    // com.samsung.android.location.SemLocationBatchingRequest.1
                @Override // android.os.Parcelable.Creator
                public SemLocationBatchingRequest createFromParcel(Parcel in) {
                    return new SemLocationBatchingRequest(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemLocationBatchingRequest[] newArray(int size) {
                    return new SemLocationBatchingRequest[size];
                }
            };
    private long maxInterval;
    private int maxNumUpdates;
    private long maxWaitTime;
    private float minDisplacement;
    private long minInterval;

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

    public SemLocationBatchingRequest setMinInterval(long millis) {
        this.minInterval = millis;
        return this;
    }

    public SemLocationBatchingRequest setMaxInterval(long millis) {
        this.maxInterval = millis;
        return this;
    }

    public SemLocationBatchingRequest setMaxWaitTime(long millis) {
        this.maxWaitTime = millis;
        return this;
    }

    public SemLocationBatchingRequest setMaxNumUpdates(int numUpdates) {
        this.maxNumUpdates = numUpdates;
        return this;
    }

    public SemLocationBatchingRequest setMinDisplacement(float displacement) {
        this.minDisplacement = displacement;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(this.minInterval);
        parcel.writeLong(this.maxInterval);
        parcel.writeLong(this.maxWaitTime);
        parcel.writeInt(this.maxNumUpdates);
        parcel.writeFloat(this.minDisplacement);
    }

    private SemLocationBatchingRequest(Parcel in) {
        this.minInterval = ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        this.maxInterval = LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT;
        this.maxWaitTime = 36000000L;
        this.maxNumUpdates = 100;
        this.minDisplacement = 500.0f;
        this.minInterval = in.readLong();
        this.maxInterval = in.readLong();
        this.maxWaitTime = in.readLong();
        this.maxNumUpdates = in.readInt();
        this.minDisplacement = in.readFloat();
    }
}
