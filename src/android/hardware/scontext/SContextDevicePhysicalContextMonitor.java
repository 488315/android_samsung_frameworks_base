package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDevicePhysicalContextMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextDevicePhysicalContextMonitor> CREATOR = new Parcelable.Creator<SContextDevicePhysicalContextMonitor>() { // from class: android.hardware.scontext.SContextDevicePhysicalContextMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDevicePhysicalContextMonitor createFromParcel(Parcel parcel) {
            return new SContextDevicePhysicalContextMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDevicePhysicalContextMonitor[] newArray(int i) {
            return new SContextDevicePhysicalContextMonitor[i];
        }
    };
    private Bundle mContext;

    SContextDevicePhysicalContextMonitor() {
        this.mContext = new Bundle();
    }

    SContextDevicePhysicalContextMonitor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAODStatus() {
        return this.mContext.getInt("AODStatus");
    }

    public int getAODReason() {
        return this.mContext.getInt("AODReason");
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
