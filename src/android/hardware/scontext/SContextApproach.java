package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextApproach extends SContextEventContext {
    public static final Parcelable.Creator<SContextApproach> CREATOR = new Parcelable.Creator<SContextApproach>() { // from class: android.hardware.scontext.SContextApproach.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextApproach createFromParcel(Parcel parcel) {
            return new SContextApproach(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextApproach[] newArray(int i) {
            return new SContextApproach[i];
        }
    };
    private Bundle mContext;

    SContextApproach() {
        this.mContext = new Bundle();
    }

    SContextApproach(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getApproach() {
        return this.mContext.getInt("Proximity");
    }

    public int getUserID() {
        return this.mContext.getInt("UserID");
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
