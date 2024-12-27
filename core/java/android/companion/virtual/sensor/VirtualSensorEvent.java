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
        public VirtualSensorEvent createFromParcel(Parcel source) {
            return new VirtualSensorEvent(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualSensorEvent[] newArray(int size) {
            return new VirtualSensorEvent[size];
        }
    };
    private long mTimestampNanos;
    private float[] mValues;

    private VirtualSensorEvent(float[] values, long timestampNanos) {
        this.mValues = values;
        this.mTimestampNanos = timestampNanos;
    }

    private VirtualSensorEvent(Parcel parcel) {
        int valuesLength = parcel.readInt();
        this.mValues = new float[valuesLength];
        parcel.readFloatArray(this.mValues);
        this.mTimestampNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeInt(this.mValues.length);
        parcel.writeFloatArray(this.mValues);
        parcel.writeLong(this.mTimestampNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

        public Builder(float[] values) {
            this.mValues = values;
        }

        public VirtualSensorEvent build() {
            if (this.mValues == null || this.mValues.length == 0) {
                throw new IllegalArgumentException("Cannot build virtual sensor event with no values.");
            }
            if (this.mTimestampNanos <= 0) {
                this.mTimestampNanos = SystemClock.elapsedRealtimeNanos();
            }
            return new VirtualSensorEvent(this.mValues, this.mTimestampNanos);
        }

        public Builder setTimestampNanos(long timestampNanos) {
            this.mTimestampNanos = timestampNanos;
            return this;
        }
    }
}
