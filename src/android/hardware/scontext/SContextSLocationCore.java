package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSLocationCore extends SContextEventContext {
    public static final Parcelable.Creator<SContextSLocationCore> CREATOR = new Parcelable.Creator<SContextSLocationCore>() { // from class: android.hardware.scontext.SContextSLocationCore.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSLocationCore createFromParcel(Parcel parcel) {
            return new SContextSLocationCore(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextSLocationCore[] newArray(int i) {
            return new SContextSLocationCore[i];
        }
    };
    private Bundle mContext;

    SContextSLocationCore() {
        this.mContext = new Bundle();
    }

    SContextSLocationCore(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    public int getFenceID() {
        return this.mContext.getInt("GeoFenceId");
    }

    public int getStatus() {
        return this.mContext.getInt("GeoFenceStatus");
    }

    public long getTimeStamp() {
        return this.mContext.getLong("Timestamp");
    }

    public double getLatitude() {
        return this.mContext.getDouble("Latitude");
    }

    public double getLongitude() {
        return this.mContext.getDouble("Longitude");
    }

    public int getAccuracy() {
        return this.mContext.getInt("Accuracy");
    }

    public int getTotalGpsCount() {
        return this.mContext.getInt("TotalGpsCount");
    }

    public int getSuccessGpsCount() {
        return this.mContext.getInt("SuccessGpsCount");
    }

    public int getFuncType() {
        return this.mContext.getInt("FunctionType");
    }

    public int getErrorCode() {
        return this.mContext.getInt("ErrorCode");
    }

    public float getDistance() {
        return this.mContext.getFloat("Distance");
    }

    public int getDataSize() {
        return this.mContext.getInt("DataCount");
    }

    public int[] getTypeArray() {
        return this.mContext.getIntArray("EventTypeArray");
    }

    public int[] getStatusArray() {
        return this.mContext.getIntArray("EventStatusArray");
    }

    public long[] getTimeStampArray() {
        return this.mContext.getLongArray("TimeStampArray");
    }

    public int[] getDataArray() {
        return this.mContext.getIntArray("DataArray");
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
