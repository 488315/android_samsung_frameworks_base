package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironment extends SContextEventContext {
    public static final Parcelable.Creator<SContextEnvironment> CREATOR = new Parcelable.Creator<SContextEnvironment>() { // from class: android.hardware.scontext.SContextEnvironment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironment createFromParcel(Parcel parcel) {
            return new SContextEnvironment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironment[] newArray(int i) {
            return new SContextEnvironment[i];
        }
    };
    private Bundle mContext;

    SContextEnvironment() {
        this.mContext = new Bundle();
    }

    SContextEnvironment(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getSensorType() {
        return this.mContext.getInt("EnvSensorType");
    }

    public double[] getData(int i) {
        if (this.mContext.getInt("EnvSensorType") == 1) {
            return getTemperatureHumidityData(i);
        }
        return null;
    }

    private double[] getTemperatureHumidityData(int i) {
        if (i == 0) {
            return this.mContext.getDoubleArray("Temperature");
        }
        if (i == 1) {
            return this.mContext.getDoubleArray("Humidity");
        }
        return null;
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
