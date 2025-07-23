package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovementAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextMovementAlert> CREATOR = new Parcelable.Creator<SContextMovementAlert>() { // from class: android.hardware.scontext.SContextMovementAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementAlert createFromParcel(Parcel parcel) {
            return new SContextMovementAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementAlert[] newArray(int i) {
            return new SContextMovementAlert[i];
        }
    };
    private Bundle mContext;

    SContextMovementAlert() {
        this.mContext = new Bundle();
    }

    SContextMovementAlert(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle();
    }
}
