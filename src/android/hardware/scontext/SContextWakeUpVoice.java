package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWakeUpVoice extends SContextEventContext {
    public static final Parcelable.Creator<SContextWakeUpVoice> CREATOR = new Parcelable.Creator<SContextWakeUpVoice>() { // from class: android.hardware.scontext.SContextWakeUpVoice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice createFromParcel(Parcel parcel) {
            return new SContextWakeUpVoice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice[] newArray(int i) {
            return new SContextWakeUpVoice[i];
        }
    };
    private Bundle mContext;

    SContextWakeUpVoice() {
        this.mContext = new Bundle();
    }

    SContextWakeUpVoice(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
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
