package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityNotification extends SemContextEventContext {
    public static final int ACCURACY_HIGH = 2;
    public static final int ACCURACY_LOW = 0;
    public static final int ACCURACY_MID = 1;
    public static final Parcelable.Creator<SemContextActivityNotification> CREATOR = new Parcelable.Creator<SemContextActivityNotification>() { // from class: com.samsung.android.hardware.context.SemContextActivityNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotification createFromParcel(Parcel parcel) {
            return new SemContextActivityNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotification[] newArray(int i) {
            return new SemContextActivityNotification[i];
        }
    };
    public static final int STATUS_BIKE = 5;
    public static final int STATUS_GET_VERSION = 127;
    public static final int STATUS_RUN = 3;
    public static final int STATUS_STATIONARY = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_VEHICLE = 4;
    public static final int STATUS_WALK = 2;
    private Bundle mContext;

    SemContextActivityNotification() {
        this.mContext = new Bundle();
    }

    SemContextActivityNotification(Parcel parcel) {
        readFromParcel(parcel);
    }

    public long getTimeStamp() {
        return this.mContext.getLong("TimeStamp");
    }

    public int getStatus() {
        return this.mContext.getInt("ActivityType");
    }

    public int getAccuracy() {
        return this.mContext.getInt("Accuracy");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
    }
}
