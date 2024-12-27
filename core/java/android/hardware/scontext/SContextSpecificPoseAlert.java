package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextSpecificPoseAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextSpecificPoseAlert> CREATOR =
            new Parcelable.Creator<SContextSpecificPoseAlert>() { // from class:
                // android.hardware.scontext.SContextSpecificPoseAlert.1
                @Override // android.os.Parcelable.Creator
                public SContextSpecificPoseAlert createFromParcel(Parcel in) {
                    return new SContextSpecificPoseAlert(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextSpecificPoseAlert[] newArray(int size) {
                    return new SContextSpecificPoseAlert[size];
                }
            };
    private Bundle mContext;

    SContextSpecificPoseAlert() {
        this.mContext = new Bundle();
    }

    SContextSpecificPoseAlert(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
