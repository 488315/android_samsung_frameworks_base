package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDevicePosition extends SContextEventContext {
    public static final Parcelable.Creator<SContextDevicePosition> CREATOR = new Parcelable.Creator<SContextDevicePosition>() { // from class: android.hardware.scontext.SContextDevicePosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDevicePosition createFromParcel(Parcel parcel) {
            return new SContextDevicePosition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDevicePosition[] newArray(int i) {
            return new SContextDevicePosition[i];
        }
    };
    private Bundle mContext;

    SContextDevicePosition() {
        this.mContext = new Bundle();
    }

    SContextDevicePosition(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getPosition() {
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
