package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCallPose extends SContextEventContext {
    public static final Parcelable.Creator<SContextCallPose> CREATOR = new Parcelable.Creator<SContextCallPose>() { // from class: android.hardware.scontext.SContextCallPose.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallPose createFromParcel(Parcel parcel) {
            return new SContextCallPose(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallPose[] newArray(int i) {
            return new SContextCallPose[i];
        }
    };
    private Bundle mContext;

    SContextCallPose() {
        this.mContext = new Bundle();
    }

    SContextCallPose(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getPose() {
        return this.mContext.getInt("Pose");
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
