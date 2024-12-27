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
        public SContextTemperatureAlert createFromParcel(Parcel in) {
            return new SContextTemperatureAlert(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTemperatureAlert[] newArray(int size) {
            return new SContextTemperatureAlert[size];
        }
    };
    private Bundle mContext;

    SContextTemperatureAlert() {
        this.mContext = new Bundle();
    }

    SContextTemperatureAlert(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
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
