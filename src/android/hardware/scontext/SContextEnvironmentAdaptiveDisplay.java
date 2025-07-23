package android.hardware.scontext;

import android.hardware.gnss.GnssSignalType;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironmentAdaptiveDisplay extends SContextEventContext {
    public static final Parcelable.Creator<SContextEnvironmentAdaptiveDisplay> CREATOR = new Parcelable.Creator<SContextEnvironmentAdaptiveDisplay>() { // from class: android.hardware.scontext.SContextEnvironmentAdaptiveDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironmentAdaptiveDisplay createFromParcel(Parcel parcel) {
            return new SContextEnvironmentAdaptiveDisplay(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEnvironmentAdaptiveDisplay[] newArray(int i) {
            return new SContextEnvironmentAdaptiveDisplay[i];
        }
    };
    private Bundle mContext;

    SContextEnvironmentAdaptiveDisplay() {
        this.mContext = new Bundle();
    }

    SContextEnvironmentAdaptiveDisplay(Parcel parcel) {
        readFromParcel(parcel);
    }

    public float getRed() {
        return this.mContext.getFloat("R");
    }

    public float getGreen() {
        return this.mContext.getFloat("G");
    }

    public float getBlue() {
        return this.mContext.getFloat(GnssSignalType.CODE_TYPE_B);
    }

    public long getLux() {
        return this.mContext.getLong("Lux");
    }

    public int getCCT() {
        return this.mContext.getInt("CCT");
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
