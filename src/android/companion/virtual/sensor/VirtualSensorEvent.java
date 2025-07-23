package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualSensorEvent> CREATOR = new Parcelable.Creator<VirtualSensorEvent>() { // from class: android.companion.virtual.sensor.VirtualSensorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorEvent createFromParcel(Parcel parcel) {
            return new VirtualSensorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorEvent[] newArray(int i) {
            return new VirtualSensorEvent[i];
        }
    };
    private long mTimestampNanos;
    private float[] mValues;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualSensorEvent(float[] fArr, long j) {
        this.mValues = fArr;
        this.mTimestampNanos = j;
    }

    private VirtualSensorEvent(Parcel parcel) {
        float[] fArr = new float[parcel.readInt()];
        this.mValues = fArr;
        parcel.readFloatArray(fArr);
        this.mTimestampNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mValues.length);
        parcel.writeFloatArray(this.mValues);
        parcel.writeLong(this.mTimestampNanos);
    }

    public float[] getValues() {
        return this.mValues;
    }

    public long getTimestampNanos() {
        return this.mTimestampNanos;
    }

    public static final class Builder {
        private long mTimestampNanos = 0;
        private float[] mValues;

        public Builder(float[] fArr) {
            this.mValues = fArr;
        }

        public VirtualSensorEvent build() {
            float[] fArr = this.mValues;
            if (fArr == null || fArr.length == 0) {
                throw new IllegalArgumentException("Cannot build virtual sensor event with no values.");
            }
            if (this.mTimestampNanos <= 0) {
                this.mTimestampNanos = SystemClock.elapsedRealtimeNanos();
            }
            return new VirtualSensorEvent(this.mValues, this.mTimestampNanos);
        }

        public Builder setTimestampNanos(long j) {
            this.mTimestampNanos = j;
            return this;
        }
    }
}
