package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSpecificPoseAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextSpecificPoseAlert> CREATOR = new Parcelable.Creator<SContextSpecificPoseAlert>() { // from class: android.hardware.scontext.SContextSpecificPoseAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSpecificPoseAlert createFromParcel(Parcel parcel) {
            return new SContextSpecificPoseAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSpecificPoseAlert[] newArray(int i) {
            return new SContextSpecificPoseAlert[i];
        }
    };
    private Bundle mContext;

    SContextSpecificPoseAlert() {
        this.mContext = new Bundle();
    }

    SContextSpecificPoseAlert(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
