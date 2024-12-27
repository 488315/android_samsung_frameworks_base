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
        public SContextStepCountAlert createFromParcel(Parcel in) {
            return new SContextStepCountAlert(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextStepCountAlert[] newArray(int size) {
            return new SContextStepCountAlert[size];
        }
    };
    private Bundle mContext;

    SContextStepCountAlert() {
        this.mContext = new Bundle();
    }

    SContextStepCountAlert(Parcel src) {
        readFromParcel(src);
    }

    public int getAlert() {
        return this.mContext.getInt("Action") == 1 ? 1 : 0;
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
