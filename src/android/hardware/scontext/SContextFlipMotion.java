package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlipMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextFlipMotion> CREATOR = new Parcelable.Creator<SContextFlipMotion>() { // from class: android.hardware.scontext.SContextFlipMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlipMotion createFromParcel(Parcel parcel) {
            return new SContextFlipMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlipMotion[] newArray(int i) {
            return new SContextFlipMotion[i];
        }
    };
    private Bundle mContext;

    SContextFlipMotion() {
        this.mContext = new Bundle();
    }

    SContextFlipMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getStatus() {
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
