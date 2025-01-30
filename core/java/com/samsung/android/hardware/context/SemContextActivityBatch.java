package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextActivityBatch extends SemContextEventContext {
    public static final int ACCURACY_HIGH = 2;
    public static final int ACCURACY_LOW = 0;
    public static final int ACCURACY_MID = 1;
    public static final Parcelable.Creator<SemContextActivityBatch> CREATOR = new Parcelable.Creator<SemContextActivityBatch>() { // from class: com.samsung.android.hardware.context.SemContextActivityBatch.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityBatch createFromParcel(Parcel in) {
            return new SemContextActivityBatch(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityBatch[] newArray(int size) {
            return new SemContextActivityBatch[size];
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

    SemContextActivityBatch(Parcel src) {
        readFromParcel(src);
    }

    public long[] getTimeStampArray() {
        int i = this.mMode;
        if (i == 0) {
            int size = this.mContext.getInt("Count");
            long[] duration = this.mContext.getLongArray("Duration");
            if (duration == null) {
                return null;
            }
            long[] timestamp = new long[size];
            for (int i2 = 0; i2 < size; i2++) {
                if (i2 == 0) {
                    timestamp[i2] = this.mContext.getLong("TimeStamp");
                } else {
                    timestamp[i2] = timestamp[i2 - 1] + duration[i2 - 1];
                }
            }
            return timestamp;
        }
        if (i != 1) {
            return null;
        }
        return this.mContext.getLongArray("TimeStampArray");
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
    public void setValues(Bundle context) {
        this.mContext = context;
        this.mMode = context.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
        dest.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
        this.mMode = src.readInt();
    }
}
