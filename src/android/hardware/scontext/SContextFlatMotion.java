package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlatMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextFlatMotion> CREATOR = new Parcelable.Creator<SContextFlatMotion>() { // from class: android.hardware.scontext.SContextFlatMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotion createFromParcel(Parcel parcel) {
            return new SContextFlatMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotion[] newArray(int i) {
            return new SContextFlatMotion[i];
        }
    };
    private Bundle mContext;

    SContextFlatMotion() {
        this.mContext = new Bundle();
    }

    SContextFlatMotion(Parcel parcel) {
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
