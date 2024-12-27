package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextActivityLocationLogging extends SContextEventContext {
    public static final Parcelable.Creator<SContextActivityLocationLogging> CREATOR =
            new Parcelable.Creator<SContextActivityLocationLogging>() { // from class:
                // android.hardware.scontext.SContextActivityLocationLogging.1
                @Override // android.os.Parcelable.Creator
                public SContextActivityLocationLogging createFromParcel(Parcel in) {
                    return new SContextActivityLocationLogging(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextActivityLocationLogging[] newArray(int size) {
                    return new SContextActivityLocationLogging[size];
                }
            };
    private Bundle mContext;
    private Bundle mInfo;
    private int mType;

    SContextActivityLocationLogging() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
    }

    SContextActivityLocationLogging(Parcel src) {
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

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
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
        this.mContext = src.readBundle();
        this.mInfo = src.readBundle();
        this.mType = src.readInt();
    }
}
