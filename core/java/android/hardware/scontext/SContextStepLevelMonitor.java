package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextStepLevelMonitor extends SContextEventContext {
    private Bundle mContext;
    private Bundle mInfo;
    private int mMode;
    private static final int[] NO_INTS = new int[0];
    private static final double[] NO_DOUBLES = new double[0];
    private static final long[] NO_LONGS = new long[0];
    public static final Parcelable.Creator<SContextStepLevelMonitor> CREATOR = new Parcelable.Creator<SContextStepLevelMonitor>() { // from class: android.hardware.scontext.SContextStepLevelMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepLevelMonitor createFromParcel(Parcel in) {
            return new SContextStepLevelMonitor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepLevelMonitor[] newArray(int size) {
            return new SContextStepLevelMonitor[size];
        }
    };

    SContextStepLevelMonitor() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
        this.mMode = 0;
    }

    SContextStepLevelMonitor(Parcel src) {
        readFromParcel(src);
    }

    public int getCount() {
        return this.mContext.getInt("DataCount");
    }

    public long[] getTimeStamp() {
        if (this.mMode == 0) {
            if (this.mInfo == null) {
                return NO_LONGS;
            }
            int size = this.mContext.getInt("DataCount");
            int[] duration = this.mInfo.getIntArray("DurationArray");
            long[] timestamp = new long[size];
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    timestamp[i] = this.mContext.getLong("TimeStamp");
                } else {
                    timestamp[i] = timestamp[i - 1] + duration[i - 1];
                }
            }
            return timestamp;
        }
        if (this.mMode != 1) {
            return null;
        }
        return this.mContext.getLongArray("TimeStampArray");
    }

    public int[] getDuration() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("DurationArray");
    }

    public int[] getStepLevel() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("StepTypeArray");
    }

    public int[] getStepCount() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("StepCountArray");
    }

    public double[] getDistance() {
        if (this.mInfo == null) {
            return NO_DOUBLES;
        }
        return this.mInfo.getDoubleArray("DistanceArray");
    }

    public double[] getCalorie() {
        if (this.mInfo == null) {
            return NO_DOUBLES;
        }
        return this.mInfo.getDoubleArray("CalorieArray");
    }

    public int getMode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.getInt("Mode");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
        this.mInfo = context.getBundle("DataBundle");
        this.mMode = context.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
        dest.writeBundle(this.mInfo);
        dest.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
        this.mInfo = src.readBundle();
        this.mMode = src.readInt();
    }
}
