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
    public static final Parcelable.Creator<SemContextStepLevelMonitor> CREATOR = new Parcelable.Creator<SemContextStepLevelMonitor>() { // from class: com.samsung.android.hardware.context.SemContextStepLevelMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitor createFromParcel(Parcel parcel) {
            return new SemContextStepLevelMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitor[] newArray(int i) {
            return new SemContextStepLevelMonitor[i];
        }
    };

    SemContextStepLevelMonitor() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
        this.mMode = 0;
    }

    SemContextStepLevelMonitor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getCount() {
        return this.mContext.getInt("DataCount");
    }

    public long[] getTimeStampArray() {
        int i = this.mMode;
        if (i != 0) {
            if (i == 1) {
                return this.mContext.getLongArray("TimeStampArray");
            }
            return null;
        }
        if (this.mInfo == null) {
            return NO_LONGS;
        }
        int i2 = this.mContext.getInt("DataCount");
        if (this.mInfo.getIntArray("DurationArray") == null) {
            return null;
        }
        long[] jArr = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                jArr[i3] = this.mContext.getLong("TimeStamp");
            } else {
                jArr[i3] = jArr[i3 - 1] + r1[r4];
            }
        }
        return jArr;
    }

    public int[] getDurationArray() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("DurationArray");
    }

    public int[] getStepLevelArray() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("StepTypeArray");
    }

    public int[] getStepCountArray() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("StepCountArray");
    }

    public double[] getDistanceArray() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_DOUBLES;
        }
        return bundle.getDoubleArray("DistanceArray");
    }

    public double[] getCalorieArray() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_DOUBLES;
        }
        return bundle.getDoubleArray("CalorieArray");
    }

    public int getMode() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return 0;
        }
        return bundle.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
        this.mInfo = bundle.getBundle("DataBundle");
        this.mMode = bundle.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
        parcel.writeBundle(this.mInfo);
        parcel.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
        this.mInfo = parcel.readBundle(getClass().getClassLoader());
        this.mMode = parcel.readInt();
    }
}
