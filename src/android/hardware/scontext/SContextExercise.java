package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextExercise extends SContextEventContext {
    public static final Parcelable.Creator<SContextExercise> CREATOR = new Parcelable.Creator<SContextExercise>() { // from class: android.hardware.scontext.SContextExercise.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextExercise createFromParcel(Parcel parcel) {
            return new SContextExercise(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextExercise[] newArray(int i) {
            return new SContextExercise[i];
        }
    };
    private Bundle mContext;
    private int mMode;

    SContextExercise() {
        this.mContext = new Bundle();
        this.mMode = 0;
    }

    SContextExercise(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
    }

    public int getLoggingSize() {
        if (this.mMode == 0) {
            return this.mContext.getInt("DataCount");
        }
        return 0;
    }

    public long[] getTimeStamp() {
        if (this.mMode == 0) {
            return this.mContext.getLongArray("TimeStampArray");
        }
        return null;
    }

    public double[] getLatitude() {
        if (this.mMode == 0) {
            return this.mContext.getDoubleArray("LatitudeArray");
        }
        return null;
    }

    public double[] getLongitude() {
        if (this.mMode == 0) {
            return this.mContext.getDoubleArray("LongitudeArray");
        }
        return null;
    }

    public float[] getAltitude() {
        if (this.mMode == 0) {
            return this.mContext.getFloatArray("AltitudeArray");
        }
        return null;
    }

    public float[] getPressure() {
        if (this.mMode == 0) {
            return this.mContext.getFloatArray("PressureArray");
        }
        return null;
    }

    public float[] getSpeed() {
        if (this.mMode == 0) {
            return this.mContext.getFloatArray("SpeedArray");
        }
        return null;
    }

    public double[] getPedoDistance() {
        if (this.mMode == 0) {
            return this.mContext.getDoubleArray("PedoDistanceDiffArray");
        }
        return null;
    }

    public double[] getPedoSpeed() {
        if (this.mMode == 0) {
            return this.mContext.getDoubleArray("PedoSpeedArray");
        }
        return null;
    }

    public long[] getStepCount() {
        if (this.mMode == 0) {
            return this.mContext.getLongArray("StepCountDiffArray");
        }
        return null;
    }

    public int getStatus() {
        if (this.mMode == 1) {
            return this.mContext.getInt("GpsStatus");
        }
        return 0;
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
        this.mMode = bundle.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
        parcel.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle();
        this.mMode = parcel.readInt();
    }
}
