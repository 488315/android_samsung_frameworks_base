package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextTestFlatMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextTestFlatMotion> CREATOR = new Parcelable.Creator<SContextTestFlatMotion>() { // from class: android.hardware.scontext.SContextTestFlatMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTestFlatMotion createFromParcel(Parcel parcel) {
            return new SContextTestFlatMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextTestFlatMotion[] newArray(int i) {
            return new SContextTestFlatMotion[i];
        }
    };
    private Bundle mContext;

    SContextTestFlatMotion() {
        this.mContext = new Bundle();
    }

    SContextTestFlatMotion(Parcel parcel) {
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
