package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextActivityBatch extends SContextEventContext {
    public static final Parcelable.Creator<SContextActivityBatch> CREATOR =
            new Parcelable.Creator<SContextActivityBatch>() { // from class:
                // android.hardware.scontext.SContextActivityBatch.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextActivityBatch createFromParcel(Parcel in) {
                    return new SContextActivityBatch(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextActivityBatch[] newArray(int size) {
                    return new SContextActivityBatch[size];
                }
            };
    private Bundle mContext;
    private int mMode;

    SContextActivityBatch() {
        this.mContext = new Bundle();
        this.mMode = 0;
    }

    SContextActivityBatch(Parcel src) {
        readFromParcel(src);
    }

    public long[] getTimeStamp() {
        if (this.mMode == 0) {
            int size = this.mContext.getInt("Count");
            long[] duration = this.mContext.getLongArray("Duration");
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

    public int[] getStatus() {
        return this.mContext.getIntArray("ActivityType");
    }

    public int getMostActivity() {
        return this.mContext.getInt("MostActivity");
    }

    public int[] getAccuracy() {
        return this.mContext.getIntArray("Accuracy");
    }

    @Deprecated
    public int getMode() {
        return this.mMode;
    }

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
        this.mMode = this.mContext.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
        dest.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
        this.mMode = src.readInt();
    }
}
