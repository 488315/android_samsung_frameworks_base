package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextHallSensor extends SContextEventContext {
    public static final Parcelable.Creator<SContextHallSensor> CREATOR = new Parcelable.Creator<SContextHallSensor>() { // from class: android.hardware.scontext.SContextHallSensor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextHallSensor createFromParcel(Parcel parcel) {
            return new SContextHallSensor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextHallSensor[] newArray(int i) {
            return new SContextHallSensor[i];
        }
    };
    private Bundle mContext;

    public SContextHallSensor() {
        this.mContext = new Bundle();
    }

    public SContextHallSensor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public short getAngle() {
        return this.mContext.getShort("Angle");
    }

    public short getType() {
        return this.mContext.getShort("Type");
    }

    public short getIntensity() {
        return this.mContext.getShort("Intensity");
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
