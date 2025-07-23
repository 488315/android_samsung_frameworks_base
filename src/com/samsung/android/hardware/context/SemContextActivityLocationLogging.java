package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityLocationLogging extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActivityLocationLogging> CREATOR = new Parcelable.Creator<SemContextActivityLocationLogging>() { // from class: com.samsung.android.hardware.context.SemContextActivityLocationLogging.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityLocationLogging createFromParcel(Parcel parcel) {
            return new SemContextActivityLocationLogging(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityLocationLogging[] newArray(int i) {
            return new SemContextActivityLocationLogging[i];
        }
    };
    public static final int LPP_RESOLUTION_HIGH = 2;
    public static final int LPP_RESOLUTION_LOW = 0;
    public static final int LPP_RESOLUTION_MID = 1;
    public static final int TYPE_MOVING = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_STAYING = 1;
    public static final int TYPE_TRAJECTORY = 3;
    private Bundle mContext;
    private Bundle mInfo;
    private int mType;

    SemContextActivityLocationLogging() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
    }

    SemContextActivityLocationLogging(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getType() {
        return this.mType;
    }

    public int getLoggingSize() {
        int i = this.mType;
        if (i == 1) {
            return this.mInfo.getInt("StayingAreaCount");
        }
        if (i == 2) {
            return this.mInfo.getInt("MovingCount");
        }
        if (i == 3) {
            return this.mInfo.getInt("TrajectoryCount");
        }
        return 0;
    }

    public long[] getTimestamp() {
        int i = this.mType;
        if (i == 1) {
            return this.mInfo.getLongArray("StayingAreaTimeStamp");
        }
        if (i != 2) {
            if (i == 3) {
                return this.mInfo.getLongArray("TrajectoryTimeStamp");
            }
            return null;
        }
        int[] intArray = this.mInfo.getIntArray("MovingTimeDuration");
        if (intArray == null) {
            return null;
        }
        long[] jArr = new long[intArray.length];
        for (int i2 = 0; i2 < intArray.length; i2++) {
            if (i2 == 0) {
                jArr[i2] = this.mInfo.getLong("MovingTimeStamp");
            } else {
                jArr[i2] = jArr[i2 - 1] + intArray[r3];
            }
        }
        return jArr;
    }

    public double[] getLatitude() {
        int i = this.mType;
        if (i == 1) {
            return this.mInfo.getDoubleArray("StayingAreaLatitude");
        }
        if (i == 3) {
            return this.mInfo.getDoubleArray("TrajectoryLatitude");
        }
        return null;
    }

    public double[] getLongitude() {
        int i = this.mType;
        if (i == 1) {
            return this.mInfo.getDoubleArray("StayingAreaLongitude");
        }
        if (i == 3) {
            return this.mInfo.getDoubleArray("TrajectoryLongitude");
        }
        return null;
    }

    public double[] getAltitude() {
        int i = this.mType;
        if (i == 1) {
            return this.mInfo.getDoubleArray("StayingAreaAltitude");
        }
        if (i == 3) {
            return this.mInfo.getDoubleArray("TrajectoryAltitude");
        }
        return null;
    }

    public int[] getStayingTimeDuration() {
        return this.mInfo.getIntArray("StayingAreaTimeDuration");
    }

    public int[] getStayingAreaRadius() {
        return this.mInfo.getIntArray("StayingAreaRadius");
    }

    public int[] getStayingAreaStatus() {
        return this.mInfo.getIntArray("StayingAreaStatus");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("LoggingBundle");
        if (bundle2 != null) {
            this.mInfo = bundle2;
            this.mType = bundle.getInt("LoggingType");
            this.mContext = bundle;
        }
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
        parcel.writeBundle(this.mInfo);
        parcel.writeInt(this.mType);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
        this.mInfo = parcel.readBundle(getClass().getClassLoader());
        this.mType = parcel.readInt();
    }
}
