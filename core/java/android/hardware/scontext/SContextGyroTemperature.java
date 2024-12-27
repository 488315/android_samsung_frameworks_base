package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextGyroTemperature extends SContextEventContext {
    public static final Parcelable.Creator<SContextGyroTemperature> CREATOR =
            new Parcelable.Creator<SContextGyroTemperature>() { // from class:
                // android.hardware.scontext.SContextGyroTemperature.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextGyroTemperature createFromParcel(Parcel in) {
                    return new SContextGyroTemperature(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextGyroTemperature[] newArray(int size) {
                    return new SContextGyroTemperature[size];
                }
            };
    private Bundle mContext;

    SContextGyroTemperature() {
        this.mContext = new Bundle();
    }

    SContextGyroTemperature(Parcel src) {
        readFromParcel(src);
    }

    public double getGyroTemperature() {
        return this.mContext.getDouble("GyroTemperature");
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
