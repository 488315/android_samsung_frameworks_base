package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAbnormalPressure extends SContextEventContext {
    public static final Parcelable.Creator<SContextAbnormalPressure> CREATOR = new Parcelable.Creator<SContextAbnormalPressure>() { // from class: android.hardware.scontext.SContextAbnormalPressure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAbnormalPressure createFromParcel(Parcel parcel) {
            return new SContextAbnormalPressure(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAbnormalPressure[] newArray(int i) {
            return new SContextAbnormalPressure[i];
        }
    };
    private Bundle mContext;

    SContextAbnormalPressure() {
        this.mContext = new Bundle();
    }

    SContextAbnormalPressure(Parcel parcel) {
        readFromParcel(parcel);
    }

    public float getPressure() {
        return this.mContext.getFloat("barometer");
    }

    public float getAccX() {
        return this.mContext.getFloat("xaxis");
    }

    public float getAccY() {
        return this.mContext.getFloat("yaxis");
    }

    public float getAccZ() {
        return this.mContext.getFloat("zaxis");
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
