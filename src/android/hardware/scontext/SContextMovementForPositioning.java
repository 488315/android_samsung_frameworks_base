package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovementForPositioning extends SContextEventContext {
    public static final Parcelable.Creator<SContextMovementForPositioning> CREATOR = new Parcelable.Creator<SContextMovementForPositioning>() { // from class: android.hardware.scontext.SContextMovementForPositioning.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementForPositioning createFromParcel(Parcel parcel) {
            return new SContextMovementForPositioning(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementForPositioning[] newArray(int i) {
            return new SContextMovementForPositioning[i];
        }
    };
    private Bundle mContext;

    SContextMovementForPositioning() {
        this.mContext = new Bundle();
    }

    SContextMovementForPositioning(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAlert() {
        return this.mContext.getInt("Alert");
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
