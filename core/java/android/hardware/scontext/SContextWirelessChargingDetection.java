package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWirelessChargingDetection extends SContextEventContext {
    public static final Parcelable.Creator<SContextWirelessChargingDetection> CREATOR = new Parcelable.Creator<SContextWirelessChargingDetection>() { // from class: android.hardware.scontext.SContextWirelessChargingDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWirelessChargingDetection createFromParcel(Parcel in) {
            return new SContextWirelessChargingDetection(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
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

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
