package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SemContextActivityLocationLogging extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextActivityLocationLogging> CREATOR =
            new Parcelable.Creator<
                    SemContextActivityLocationLogging>() { // from class:
                                                           // com.samsung.android.hardware.context.SemContextActivityLocationLogging.1
                @Override // android.os.Parcelable.Creator
                public SemContextActivityLocationLogging createFromParcel(Parcel in) {
                    return new SemContextActivityLocationLogging(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemContextActivityLocationLogging[] newArray(int size) {
                    return new SemContextActivityLocationLogging[size];
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

    SemContextActivityLocationLogging(Parcel src) {
        readFromParcel(src);
    }

    public int getType() {
        return this.mType;
    }

    public int getLoggingSize() {
        if (this.mType == 1) {
            int size = this.mInfo.getInt("StayingAreaCount");
            return size;
        }
        if (this.mType == 2) {
            int size2 = this.mInfo.getInt("MovingCount");
            return size2;
        }
        if (this.mType != 3) {
            return 0;
        }
        int size3 = this.mInfo.getInt("TrajectoryCount");
        return size3;
    }

    public long[] getTimestamp() {
        if (this.mType == 1) {
            return this.mInfo.getLongArray("StayingAreaTimeStamp");
        }
        if (this.mType == 2) {
            int[] duration = this.mInfo.getIntArray("MovingTimeDuration");
            if (duration == null) {
                return null;
            }
            long[] timestamp = new long[duration.length];
            for (int i = 0; i < duration.length; i++) {
                if (i == 0) {
                    timestamp[i] = this.mInfo.getLong("MovingTimeStamp");
                } else {
                    timestamp[i] = timestamp[i - 1] + duration[i - 1];
                }
            }
            return timestamp;
        }
        if (this.mType != 3) {
            return null;
        }
        return this.mInfo.getLongArray("TrajectoryTimeStamp");
    }

    public double[] getLatitude() {
        if (this.mType == 1) {
            double[] latitude = this.mInfo.getDoubleArray("StayingAreaLatitude");
            return latitude;
        }
        if (this.mType != 3) {
            return null;
        }
        double[] latitude2 = this.mInfo.getDoubleArray("TrajectoryLatitude");
        return latitude2;
    }

    public double[] getLongitude() {
        if (this.mType == 1) {
            double[] longitude = this.mInfo.getDoubleArray("StayingAreaLongitude");
            return longitude;
        }
        if (this.mType != 3) {
            return null;
        }
        double[] longitude2 = this.mInfo.getDoubleArray("TrajectoryLongitude");
        return longitude2;
    }

    public double[] getAltitude() {
        if (this.mType == 1) {
            double[] altitude = this.mInfo.getDoubleArray("StayingAreaAltitude");
            return altitude;
        }
        if (this.mType != 3) {
            return null;
        }
        double[] altitude2 = this.mInfo.getDoubleArray("TrajectoryAltitude");
        return altitude2;
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
    public void setValues(Bundle context) {
        Bundle bundle = context.getBundle("LoggingBundle");
        if (bundle != null) {
            this.mInfo = bundle;
            this.mType = context.getInt("LoggingType");
            this.mContext = context;
        }
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
        dest.writeBundle(this.mInfo);
        dest.writeInt(this.mType);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
        this.mInfo = src.readBundle(getClass().getClassLoader());
        this.mType = src.readInt();
    }
}
