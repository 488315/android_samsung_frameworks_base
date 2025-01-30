package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWakeUpVoice extends SContextEventContext {
    public static final Parcelable.Creator<SContextWakeUpVoice> CREATOR = new Parcelable.Creator<SContextWakeUpVoice>() { // from class: android.hardware.scontext.SContextWakeUpVoice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice createFromParcel(Parcel in) {
            return new SContextWakeUpVoice(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice[] newArray(int size) {
            return new SContextWakeUpVoice[size];
        }
    };
    private Bundle mContext;

    SContextWakeUpVoice() {
        this.mContext = new Bundle();
    }

    SContextWakeUpVoice(Parcel src) {
        readFromParcel(src);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
