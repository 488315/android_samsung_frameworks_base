package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextStepCountAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextStepCountAlert> CREATOR = new Parcelable.Creator<SContextStepCountAlert>() { // from class: android.hardware.scontext.SContextStepCountAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepCountAlert createFromParcel(Parcel parcel) {
            return new SContextStepCountAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepCountAlert[] newArray(int i) {
            return new SContextStepCountAlert[i];
        }
    };
    private Bundle mContext;

    SContextStepCountAlert() {
        this.mContext = new Bundle();
    }

    SContextStepCountAlert(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAlert() {
        return this.mContext.getInt("Action") == 1 ? 1 : 0;
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
