package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextTemperatureAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextTemperatureAlert> CREATOR = new Parcelable.Creator<SContextTemperatureAlert>() { // from class: android.hardware.scontext.SContextTemperatureAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTemperatureAlert createFromParcel(Parcel parcel) {
            return new SContextTemperatureAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTemperatureAlert[] newArray(int i) {
            return new SContextTemperatureAlert[i];
        }
    };
    private Bundle mContext;

    SContextTemperatureAlert() {
        this.mContext = new Bundle();
    }

    SContextTemperatureAlert(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
