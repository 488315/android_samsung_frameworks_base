package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityBatch extends SContextEventContext {
    public static final Parcelable.Creator<SContextActivityBatch> CREATOR = new Parcelable.Creator<SContextActivityBatch>() { // from class: android.hardware.scontext.SContextActivityBatch.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityBatch createFromParcel(Parcel parcel) {
            return new SContextActivityBatch(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityBatch[] newArray(int i) {
            return new SContextActivityBatch[i];
        }
    };
    private Bundle mContext;
    private int mMode;

    SContextActivityBatch() {
        this.mContext = new Bundle();
        this.mMode = 0;
    }

    SContextActivityBatch(Parcel parcel) {
        readFromParcel(parcel);
    }

    public long[] getTimeStamp() {
        int i = this.mMode;
        if (i != 0) {
            if (i == 1) {
                return this.mContext.getLongArray("TimeStampArray");
            }
            return null;
        }
        int i2 = this.mContext.getInt("Count");
        long[] longArray = this.mContext.getLongArray("Duration");
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

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
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
        this.mContext = parcel.readBundle();
        this.mMode = parcel.readInt();
    }
}
