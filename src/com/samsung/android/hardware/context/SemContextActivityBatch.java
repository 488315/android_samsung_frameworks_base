package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemContextActivityBatch extends SemContextEventContext {
    public static final int ACCURACY_HIGH = 2;
    public static final int ACCURACY_LOW = 0;
    public static final int ACCURACY_MID = 1;
    public static final Parcelable.Creator<SemContextActivityBatch> CREATOR = new Parcelable.Creator<SemContextActivityBatch>() { // from class: com.samsung.android.hardware.context.SemContextActivityBatch.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityBatch createFromParcel(Parcel parcel) {
            return new SemContextActivityBatch(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityBatch[] newArray(int i) {
            return new SemContextActivityBatch[i];
        }
    };
    public static final int HISTORY_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int STATUS_BIKE = 5;
    public static final int STATUS_RUN = 3;
    public static final int STATUS_STATIONARY = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_VEHICLE = 4;
    public static final int STATUS_WALK = 2;
    private Bundle mContext;
    private int mMode;

    SemContextActivityBatch() {
        this.mContext = new Bundle();
        this.mMode = 0;
    }

    SemContextActivityBatch(Parcel parcel) {
        readFromParcel(parcel);
    }

    public long[] getTimeStampArray() {
        int i = this.mMode;
        if (i != 0) {
            if (i == 1) {
                return this.mContext.getLongArray("TimeStampArray");
            }
            return null;
        }
        int i2 = this.mContext.getInt("Count");
        long[] longArray = this.mContext.getLongArray("Duration");
        if (longArray == null) {
            return null;
        }
        long[] jArr = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == 0) {
                jArr[i3] = this.mContext.getLong("TimeStamp");
            } else {
                int i4 = i3 - 1;
                jArr[i3] = jArr[i4] + longArray[i4];
            }
        }
        return jArr;
    }

    public int[] getStatusArray() {
        return this.mContext.getIntArray("ActivityType");
    }

    public int getMostActivity() {
        return this.mContext.getInt("MostActivity");
    }

    public int[] getAccuracyArray() {
        return this.mContext.getIntArray("Accuracy");
    }

    public int getMode() {
        return this.mMode;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
        this.mMode = bundle.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
        parcel.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle(getClass().getClassLoader());
        this.mMode = parcel.readInt();
    }
}
