package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextDevicePhysicalContextMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextDevicePhysicalContextMonitor> CREATOR =
            new Parcelable.Creator<SContextDevicePhysicalContextMonitor>() { // from class:
                // android.hardware.scontext.SContextDevicePhysicalContextMonitor.1
                @Override // android.os.Parcelable.Creator
                public SContextDevicePhysicalContextMonitor createFromParcel(Parcel in) {
                    return new SContextDevicePhysicalContextMonitor(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextDevicePhysicalContextMonitor[] newArray(int size) {
                    return new SContextDevicePhysicalContextMonitor[size];
                }
            };
    private Bundle mContext;

    SContextDevicePhysicalContextMonitor() {
        this.mContext = new Bundle();
    }

    SContextDevicePhysicalContextMonitor(Parcel src) {
        readFromParcel(src);
    }

    public int getAODStatus() {
        return this.mContext.getInt("AODStatus");
    }

    public int getAODReason() {
        return this.mContext.getInt("AODReason");
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
