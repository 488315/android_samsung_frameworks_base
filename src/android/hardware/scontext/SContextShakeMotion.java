package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextShakeMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextShakeMotion> CREATOR = new Parcelable.Creator<SContextShakeMotion>() { // from class: android.hardware.scontext.SContextShakeMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextShakeMotion createFromParcel(Parcel parcel) {
            return new SContextShakeMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextShakeMotion[] newArray(int i) {
            return new SContextShakeMotion[i];
        }
    };
    private Bundle mContext;

    SContextShakeMotion() {
        this.mContext = new Bundle();
    }

    SContextShakeMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getShakeStatus() {
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
