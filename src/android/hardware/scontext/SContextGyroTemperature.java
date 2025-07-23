package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextGyroTemperature extends SContextEventContext {
    public static final Parcelable.Creator<SContextGyroTemperature> CREATOR = new Parcelable.Creator<SContextGyroTemperature>() { // from class: android.hardware.scontext.SContextGyroTemperature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextGyroTemperature createFromParcel(Parcel parcel) {
            return new SContextGyroTemperature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextGyroTemperature[] newArray(int i) {
            return new SContextGyroTemperature[i];
        }
    };
    private Bundle mContext;

    SContextGyroTemperature() {
        this.mContext = new Bundle();
    }

    SContextGyroTemperature(Parcel parcel) {
        readFromParcel(parcel);
    }

    public double getGyroTemperature() {
        return this.mContext.getDouble("GyroTemperature");
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
