package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSensorStatusCheck extends SContextEventContext {
    public static final Parcelable.Creator<SContextSensorStatusCheck> CREATOR = new Parcelable.Creator<SContextSensorStatusCheck>() { // from class: android.hardware.scontext.SContextSensorStatusCheck.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSensorStatusCheck createFromParcel(Parcel in) {
            return new SContextSensorStatusCheck(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSensorStatusCheck[] newArray(int size) {
            return new SContextSensorStatusCheck[size];
        }
    };
    private Bundle mContext;

    SContextSensorStatusCheck() {
        this.mContext = new Bundle();
    }

    SContextSensorStatusCheck(Parcel src) {
        readFromParcel(src);
    }

    public int getXAxis() {
        return this.mContext.getInt("XAxis");
    }

    public int getYAxis() {
        return this.mContext.getInt("YAxis");
    }

    public int getZAxis() {
        return this.mContext.getInt("ZAxis");
    }

    public int getStatus() {
        return this.mContext.getInt("Status");
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
