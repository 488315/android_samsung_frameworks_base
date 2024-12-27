package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextHallSensor extends SContextEventContext {
    public static final Parcelable.Creator<SContextHallSensor> CREATOR =
            new Parcelable.Creator<SContextHallSensor>() { // from class:
                // android.hardware.scontext.SContextHallSensor.1
                @Override // android.os.Parcelable.Creator
                public SContextHallSensor createFromParcel(Parcel in) {
                    return new SContextHallSensor(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextHallSensor[] newArray(int size) {
                    return new SContextHallSensor[size];
                }
            };
    private Bundle mContext;

    public SContextHallSensor() {
        this.mContext = new Bundle();
    }

    public SContextHallSensor(Parcel src) {
        readFromParcel(src);
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
