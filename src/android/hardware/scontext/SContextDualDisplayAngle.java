package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDualDisplayAngle extends SContextEventContext {
    public static final Parcelable.Creator<SContextDualDisplayAngle> CREATOR = new Parcelable.Creator<SContextDualDisplayAngle>() { // from class: android.hardware.scontext.SContextDualDisplayAngle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDualDisplayAngle createFromParcel(Parcel parcel) {
            return new SContextDualDisplayAngle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDualDisplayAngle[] newArray(int i) {
            return new SContextDualDisplayAngle[i];
        }
    };
    private Bundle mContext;

    public SContextDualDisplayAngle() {
        this.mContext = new Bundle();
    }

    public SContextDualDisplayAngle(Parcel parcel) {
        readFromParcel(parcel);
    }

    public short getDualAngle() {
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
