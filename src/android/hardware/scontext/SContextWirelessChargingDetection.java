package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWirelessChargingDetection extends SContextEventContext {
    public static final Parcelable.Creator<SContextWirelessChargingDetection> CREATOR = new Parcelable.Creator<SContextWirelessChargingDetection>() { // from class: android.hardware.scontext.SContextWirelessChargingDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWirelessChargingDetection createFromParcel(Parcel parcel) {
            return new SContextWirelessChargingDetection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWirelessChargingDetection[] newArray(int i) {
            return new SContextWirelessChargingDetection[i];
        }
    };
    private Bundle mContext;

    SContextWirelessChargingDetection() {
        this.mContext = new Bundle();
    }

    SContextWirelessChargingDetection(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Status");
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
