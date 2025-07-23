package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActiveTimeMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextActiveTimeMonitor> CREATOR = new Parcelable.Creator<SContextActiveTimeMonitor>() { // from class: android.hardware.scontext.SContextActiveTimeMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActiveTimeMonitor createFromParcel(Parcel parcel) {
            return new SContextActiveTimeMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActiveTimeMonitor[] newArray(int i) {
            return new SContextActiveTimeMonitor[i];
        }
    };
    private Bundle mContext;

    SContextActiveTimeMonitor() {
        this.mContext = new Bundle();
    }

    SContextActiveTimeMonitor(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getDuration() {
        return this.mContext.getInt("ActiveTimeDuration");
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
