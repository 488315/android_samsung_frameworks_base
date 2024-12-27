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
        public SContextAutoBrightness createFromParcel(Parcel in) {
            return new SContextAutoBrightness(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAutoBrightness[] newArray(int size) {
            return new SContextAutoBrightness[size];
        }
    };
    private Bundle mContext;

    SContextAutoBrightness() {
        this.mContext = new Bundle();
    }

    SContextAutoBrightness(Parcel src) {
        readFromParcel(src);
    }

    public int getAmbientLux() {
        return this.mContext.getInt("AmbientLux");
    }

    public int getCandela() {
        return this.mContext.getInt("Candela");
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
