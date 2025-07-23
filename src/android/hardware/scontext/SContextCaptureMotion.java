package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCaptureMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextCaptureMotion> CREATOR = new Parcelable.Creator<SContextCaptureMotion>() { // from class: android.hardware.scontext.SContextCaptureMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCaptureMotion createFromParcel(Parcel parcel) {
            return new SContextCaptureMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCaptureMotion[] newArray(int i) {
            return new SContextCaptureMotion[i];
        }
    };
    private Bundle mContext;

    SContextCaptureMotion() {
        this.mContext = new Bundle();
    }

    SContextCaptureMotion(Parcel parcel) {
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
