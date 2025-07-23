package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoRotation extends SContextEventContext {
    public static final Parcelable.Creator<SContextAutoRotation> CREATOR = new Parcelable.Creator<SContextAutoRotation>() { // from class: android.hardware.scontext.SContextAutoRotation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation createFromParcel(Parcel parcel) {
            return new SContextAutoRotation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation[] newArray(int i) {
            return new SContextAutoRotation[i];
        }
    };
    private Bundle mContext;

    SContextAutoRotation() {
        this.mContext = new Bundle();
    }

    SContextAutoRotation(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAngle() {
        return this.mContext.getInt("Angle");
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
