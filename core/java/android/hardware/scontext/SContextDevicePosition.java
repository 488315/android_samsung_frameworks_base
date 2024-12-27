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
        public SContextDevicePosition createFromParcel(Parcel in) {
            return new SContextDevicePosition(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextDevicePosition[] newArray(int size) {
            return new SContextDevicePosition[size];
        }
    };
    private Bundle mContext;

    SContextDevicePosition() {
        this.mContext = new Bundle();
    }

    SContextDevicePosition(Parcel src) {
        readFromParcel(src);
    }

    public int getPosition() {
        return this.mContext.getInt("Action");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
