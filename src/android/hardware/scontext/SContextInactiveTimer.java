package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextInactiveTimer extends SContextEventContext {
    public static final Parcelable.Creator<SContextInactiveTimer> CREATOR = new Parcelable.Creator<SContextInactiveTimer>() { // from class: android.hardware.scontext.SContextInactiveTimer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextInactiveTimer createFromParcel(Parcel parcel) {
            return new SContextInactiveTimer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextInactiveTimer[] newArray(int i) {
            return new SContextInactiveTimer[i];
        }
    };
    private Bundle mContext;

    SContextInactiveTimer() {
        this.mContext = new Bundle();
    }

    SContextInactiveTimer(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getDuration() {
        return this.mContext.getInt("InactiveTimeDuration");
    }

    public int getStatus() {
        return this.mContext.getInt("InactiveStatus");
    }

    public boolean isTimeOutExpired() {
        return this.mContext.getBoolean("IsTimeOut");
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
