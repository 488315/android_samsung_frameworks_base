package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextPhoneStatusMonitor extends SContextEventContext {
    public static final Parcelable.Creator<SContextPhoneStatusMonitor> CREATOR = new Parcelable.Creator<SContextPhoneStatusMonitor>() { // from class: android.hardware.scontext.SContextPhoneStatusMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextPhoneStatusMonitor createFromParcel(Parcel in) {
            return new SContextPhoneStatusMonitor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextPhoneStatusMonitor[] newArray(int size) {
            return new SContextPhoneStatusMonitor[size];
        }
    };
    private Bundle mContext;

    SContextPhoneStatusMonitor() {
        this.mContext = new Bundle();
    }

    SContextPhoneStatusMonitor(Parcel src) {
        readFromParcel(src);
    }

    public boolean isInSuroundingEnvironment() {
        return this.mContext.getBoolean("lcdOffRecommend");
    }

    public int getProximity() {
        return this.mContext.getInt("embower");
    }

    public int getLcdDirection() {
        return this.mContext.getInt("lcddirect");
    }

    public long getTimeStamp() {
        return this.mContext.getLong("timestamp");
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
