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
        public SContextStepLevelMonitor createFromParcel(Parcel parcel) {
            return new SContextStepLevelMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepLevelMonitor[] newArray(int i) {
            return new SContextStepLevelMonitor[i];
        }
    };

    SContextStepLevelMonitor() {
        this.mContext = new Bundle();
        this.mInfo = new Bundle();
        this.mMode = 0;
    }

    SContextStepLevelMonitor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getCount() {
        return this.mContext.getInt("DataCount");
    }

    public long[] getTimeStamp() {
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
        int[] intArray = this.mInfo.getIntArray("DurationArray");
        long[] jArr = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                jArr[i3] = this.mContext.getLong("TimeStamp");
            } else {
                jArr[i3] = jArr[i3 - 1] + intArray[r4];
            }
        }
        return jArr;
    }

    public int[] getDuration() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("DurationArray");
    }

    public int[] getStepLevel() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("StepTypeArray");
    }

    public int[] getStepCount() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_INTS;
        }
        return bundle.getIntArray("StepCountArray");
    }

    public double[] getDistance() {
        Bundle bundle = this.mInfo;
        if (bundle == null) {
            return NO_DOUBLES;
        }
        return bundle.getDoubleArray("DistanceArray");
    }

    public double[] getCalorie() {
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

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
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
        this.mContext = parcel.readBundle();
        this.mInfo = parcel.readBundle();
        this.mMode = parcel.readInt();
    }
}
