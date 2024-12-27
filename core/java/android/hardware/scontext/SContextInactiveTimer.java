package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextInactiveTimer extends SContextEventContext {
    public static final Parcelable.Creator<SContextInactiveTimer> CREATOR =
            new Parcelable.Creator<SContextInactiveTimer>() { // from class:
                // android.hardware.scontext.SContextInactiveTimer.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextInactiveTimer createFromParcel(Parcel in) {
                    return new SContextInactiveTimer(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextInactiveTimer[] newArray(int size) {
                    return new SContextInactiveTimer[size];
                }
            };
    private Bundle mContext;

    SContextInactiveTimer() {
        this.mContext = new Bundle();
    }

    SContextInactiveTimer(Parcel src) {
        readFromParcel(src);
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

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
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
