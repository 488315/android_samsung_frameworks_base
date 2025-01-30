package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDualDisplayAngle extends SContextEventContext {
    public static final Parcelable.Creator<SContextDualDisplayAngle> CREATOR = new Parcelable.Creator<SContextDualDisplayAngle>() { // from class: android.hardware.scontext.SContextDualDisplayAngle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDualDisplayAngle createFromParcel(Parcel in) {
            return new SContextDualDisplayAngle(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDualDisplayAngle[] newArray(int size) {
            return new SContextDualDisplayAngle[size];
        }
    };
    private Bundle mContext;

    public SContextDualDisplayAngle() {
        this.mContext = new Bundle();
    }

    public SContextDualDisplayAngle(Parcel src) {
        readFromParcel(src);
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
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
