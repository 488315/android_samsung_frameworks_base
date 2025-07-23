package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoBrightness extends SContextEventContext {
    public static final Parcelable.Creator<SContextAutoBrightness> CREATOR = new Parcelable.Creator<SContextAutoBrightness>() { // from class: android.hardware.scontext.SContextAutoBrightness.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAutoBrightness createFromParcel(Parcel parcel) {
            return new SContextAutoBrightness(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAutoBrightness[] newArray(int i) {
            return new SContextAutoBrightness[i];
        }
    };
    private Bundle mContext;

    SContextAutoBrightness() {
        this.mContext = new Bundle();
    }

    SContextAutoBrightness(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAmbientLux() {
        return this.mContext.getInt("AmbientLux");
    }

    public int getCandela() {
        return this.mContext.getInt("Candela");
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
