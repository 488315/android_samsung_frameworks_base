package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes6.dex */
public class SemContextStepLevelMonitor extends SemContextEventContext {
    public static final int HISTORY_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int STEP_LEVEL_NORMAL = 3;
    public static final int STEP_LEVEL_POWER = 4;
    public static final int STEP_LEVEL_SEDENTARY = 2;
    public static final int STEP_LEVEL_STATIONARY = 1;
    private Bundle mContext;
    private Bundle mInfo;
    private int mMode;
    private static final int[] NO_INTS = new int[0];
    private static final double[] NO_DOUBLES = new double[0];
    private static final long[] NO_LONGS = new long[0];
    public static final Parcelable.Creator<SemContextStepLevelMonitor> CREATOR =
            new Parcelable.Creator<
                    SemContextStepLevelMonitor>() { // from class:
                                                    // com.samsung.android.hardware.context.SemContextStepLevelMonitor.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextStepLevelMonitor createFromParcel(Parcel in) {
                    return new SemContextStepLevelMonitor(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemContextStepLevelMonitor[] newArray(int size) {
                    return new SemContextStepLevelMonitor[size];
                }
            };

    SemContextStepLevelMonitor() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
        this.mMode = 0;
    }

    SemContextStepLevelMonitor(Parcel src) {
        readFromParcel(src);
    }

    public int getCount() {
        return this.mContext.getInt("DataCount");
    }

    public long[] getTimeStampArray() {
        if (this.mMode == 0) {
            if (this.mInfo == null) {
                return NO_LONGS;
            }
            int size = this.mContext.getInt("DataCount");
            int[] duration = this.mInfo.getIntArray("DurationArray");
            if (duration == null) {
                return null;
            }
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

    public int[] getDurationArray() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("DurationArray");
    }

    public int[] getStepLevelArray() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("StepTypeArray");
    }

    public int[] getStepCountArray() {
        if (this.mInfo == null) {
            return NO_INTS;
        }
        return this.mInfo.getIntArray("StepCountArray");
    }

    public double[] getDistanceArray() {
        if (this.mInfo == null) {
            return NO_DOUBLES;
        }
        return this.mInfo.getDoubleArray("DistanceArray");
    }

    public double[] getCalorieArray() {
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

    @Override // com.samsung.android.hardware.context.SemContextEventContext
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
        this.mContext = src.readBundle(getClass().getClassLoader());
        this.mInfo = src.readBundle(getClass().getClassLoader());
        this.mMode = src.readInt();
    }
}
