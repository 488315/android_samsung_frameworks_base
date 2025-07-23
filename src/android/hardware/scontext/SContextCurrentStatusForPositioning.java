package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCurrentStatusForPositioning extends SContextEventContext {
    public static final Parcelable.Creator<SContextCurrentStatusForPositioning> CREATOR = new Parcelable.Creator<SContextCurrentStatusForPositioning>() { // from class: android.hardware.scontext.SContextCurrentStatusForPositioning.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCurrentStatusForPositioning createFromParcel(Parcel parcel) {
            return new SContextCurrentStatusForPositioning(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCurrentStatusForPositioning[] newArray(int i) {
            return new SContextCurrentStatusForPositioning[i];
        }
    };
    private Bundle mContext;

    SContextCurrentStatusForPositioning() {
        this.mContext = new Bundle();
    }

    SContextCurrentStatusForPositioning(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getStatus() {
        return this.mContext.getInt("Status");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle();
    }
}
