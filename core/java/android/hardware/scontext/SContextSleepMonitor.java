package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextSleepMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextSleepMonitor> CREATOR =
            new Parcelable.Creator<SContextSleepMonitor>() { // from class:
                // android.hardware.scontext.SContextSleepMonitor.1
                @Override // android.os.Parcelable.Creator
                public SContextSleepMonitor createFromParcel(Parcel in) {
                    return new SContextSleepMonitor(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextSleepMonitor[] newArray(int size) {
                    return new SContextSleepMonitor[size];
                }
            };
    private Bundle mContext;

    SContextSleepMonitor() {
        this.mContext = new Bundle();
    }

    SContextSleepMonitor(Parcel src) {
        readFromParcel(src);
    }

    public int[] getStatus() {
        return this.mContext.getIntArray("SleepStatus");
    }

    public float[] getPIM() {
        return this.mContext.getFloatArray("PIM");
    }

    public int[] getZCM() {
        return this.mContext.getIntArray("ZCM");
    }

    public int[] getStage() {
        return this.mContext.getIntArray("Stage");
    }

    public int[] getWrist() {
        return this.mContext.getIntArray("Wrist");
    }

    public int[] getFlag() {
        return this.mContext.getIntArray("Flag");
    }

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
