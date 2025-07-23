package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWristUpMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextWristUpMotion> CREATOR = new Parcelable.Creator<SContextWristUpMotion>() { // from class: android.hardware.scontext.SContextWristUpMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWristUpMotion createFromParcel(Parcel parcel) {
            return new SContextWristUpMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWristUpMotion[] newArray(int i) {
            return new SContextWristUpMotion[i];
        }
    };
    private Bundle mContext;

    SContextWristUpMotion() {
        this.mContext = new Bundle();
    }

    SContextWristUpMotion(Parcel parcel) {
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
