package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityNotificationForLocation extends SemContextEventContext {
    public static final int ACCURACY_HIGH = 2;
    public static final int ACCURACY_LOW = 0;
    public static final int ACCURACY_MID = 1;
    public static final Parcelable.Creator<SemContextActivityNotificationForLocation> CREATOR = new Parcelable.Creator<SemContextActivityNotificationForLocation>() { // from class: com.samsung.android.hardware.context.SemContextActivityNotificationForLocation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationForLocation createFromParcel(Parcel parcel) {
            return new SemContextActivityNotificationForLocation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationForLocation[] newArray(int i) {
            return new SemContextActivityNotificationForLocation[i];
        }
    };
    public static final int STATUS_CYCLE = 5;
    public static final int STATUS_MOVEMENT = 30;
    public static final int STATUS_RUN = 3;
    public static final int STATUS_STATIONARY = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_VEHICLE = 4;
    public static final int STATUS_WALK = 2;
    private Bundle mContext;

    SemContextActivityNotificationForLocation() {
        this.mContext = new Bundle();
    }

    SemContextActivityNotificationForLocation(Parcel parcel) {
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
