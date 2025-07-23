package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSleepMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextSleepMonitor> CREATOR = new Parcelable.Creator<SContextSleepMonitor>() { // from class: android.hardware.scontext.SContextSleepMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSleepMonitor createFromParcel(Parcel parcel) {
            return new SContextSleepMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSleepMonitor[] newArray(int i) {
            return new SContextSleepMonitor[i];
        }
    };
    private Bundle mContext;

    SContextSleepMonitor() {
        this.mContext = new Bundle();
    }

    SContextSleepMonitor(Parcel parcel) {
        readFromParcel(parcel);
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

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle();
    }
}
