package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SContextMainScreenDetection extends SContextEventContext {
    public static final Parcelable.Creator<SContextMainScreenDetection> CREATOR = new Parcelable.Creator<SContextMainScreenDetection>() { // from class: android.hardware.scontext.SContextMainScreenDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection createFromParcel(Parcel parcel) {
            return new SContextMainScreenDetection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection[] newArray(int i) {
            return new SContextMainScreenDetection[i];
        }
    };
    private Bundle mContext;

    SContextMainScreenDetection() {
        this.mContext = new Bundle();
    }

    SContextMainScreenDetection(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getScreenStatus() {
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
