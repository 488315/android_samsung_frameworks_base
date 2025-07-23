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
        public SContextSensorStatusCheck createFromParcel(Parcel parcel) {
            return new SContextSensorStatusCheck(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSensorStatusCheck[] newArray(int i) {
            return new SContextSensorStatusCheck[i];
        }
    };
    private Bundle mContext;

    SContextSensorStatusCheck() {
        this.mContext = new Bundle();
    }

    SContextSensorStatusCheck(Parcel parcel) {
        readFromParcel(parcel);
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
