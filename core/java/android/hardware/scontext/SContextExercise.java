package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextExercise extends SContextEventContext {
    public static final Parcelable.Creator<SContextExercise> CREATOR =
            new Parcelable.Creator<SContextExercise>() { // from class:
                // android.hardware.scontext.SContextExercise.1
                @Override // android.os.Parcelable.Creator
                public SContextExercise createFromParcel(Parcel in) {
                    return new SContextExercise(in);
                }

                @Override // android.os.Parcelable.Creator
                public SContextExercise[] newArray(int size) {
                    return new SContextExercise[size];
                }
            };
    private Bundle mContext;
    private int mMode;

    SContextExercise() {
        this.mContext = new Bundle();
        this.mMode = 0;
    }

    SContextExercise(Parcel src) {
        readFromParcel(src);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
    }

    public int getLoggingSize() {
        if (this.mMode != 0) {
            return 0;
        }
        int res = this.mContext.getInt("DataCount");
        return res;
    }

    public long[] getTimeStamp() {
        if (this.mMode != 0) {
            return null;
        }
        long[] res = this.mContext.getLongArray("TimeStampArray");
        return res;
    }

    public double[] getLatitude() {
        if (this.mMode != 0) {
            return null;
        }
        double[] res = this.mContext.getDoubleArray("LatitudeArray");
        return res;
    }

    public double[] getLongitude() {
        if (this.mMode != 0) {
            return null;
        }
        double[] res = this.mContext.getDoubleArray("LongitudeArray");
        return res;
    }

    public float[] getAltitude() {
        if (this.mMode != 0) {
            return null;
        }
        float[] res = this.mContext.getFloatArray("AltitudeArray");
        return res;
    }

    public float[] getPressure() {
        if (this.mMode != 0) {
            return null;
        }
        float[] res = this.mContext.getFloatArray("PressureArray");
        return res;
    }

    public float[] getSpeed() {
        if (this.mMode != 0) {
            return null;
        }
        float[] res = this.mContext.getFloatArray("SpeedArray");
        return res;
    }

    public double[] getPedoDistance() {
        if (this.mMode != 0) {
            return null;
        }
        double[] res = this.mContext.getDoubleArray("PedoDistanceDiffArray");
        return res;
    }

    public double[] getPedoSpeed() {
        if (this.mMode != 0) {
            return null;
        }
        double[] res = this.mContext.getDoubleArray("PedoSpeedArray");
        return res;
    }

    public long[] getStepCount() {
        if (this.mMode != 0) {
            return null;
        }
        long[] res = this.mContext.getLongArray("StepCountDiffArray");
        return res;
    }

    public int getStatus() {
        if (this.mMode != 1) {
            return 0;
        }
        int res = this.mContext.getInt("GpsStatus");
        return res;
    }

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
        this.mMode = context.getInt("Mode");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
        dest.writeInt(this.mMode);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
        this.mMode = src.readInt();
    }
}
