package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextWirelessChargingDetection extends SContextEventContext {
    public static final Parcelable.Creator<SContextWirelessChargingDetection> CREATOR =
            new Parcelable.Creator<SContextWirelessChargingDetection>() { // from class:
                // android.hardware.scontext.SContextWirelessChargingDetection.1
                @Override // android.os.Parcelable.Creator
                public SContextWirelessChargingDetection createFromParcel(Parcel in) {
                    return new SContextWirelessChargingDetection(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextWirelessChargingDetection[] newArray(int size) {
                    return new SContextWirelessChargingDetection[size];
                }
            };
    private Bundle mContext;

    SContextWirelessChargingDetection() {
        this.mContext = new Bundle();
    }

    SContextWirelessChargingDetection(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Status");
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
