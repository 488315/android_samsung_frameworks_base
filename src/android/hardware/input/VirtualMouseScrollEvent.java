package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseScrollEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseScrollEvent> CREATOR = new Parcelable.Creator<VirtualMouseScrollEvent>() { // from class: android.hardware.input.VirtualMouseScrollEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseScrollEvent createFromParcel(Parcel parcel) {
            return new VirtualMouseScrollEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseScrollEvent[] newArray(int i) {
            return new VirtualMouseScrollEvent[i];
        }
    };
    private final long mEventTimeNanos;
    private final float mXAxisMovement;
    private final float mYAxisMovement;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualMouseScrollEvent(float f, float f2, long j) {
        this.mXAxisMovement = f;
        this.mYAxisMovement = f2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseScrollEvent(Parcel parcel) {
        this.mXAxisMovement = parcel.readFloat();
        this.mYAxisMovement = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mXAxisMovement);
        parcel.writeFloat(this.mYAxisMovement);
        parcel.writeLong(this.mEventTimeNanos);
    }

    public String toString() {
        return "VirtualMouseScrollEvent( x=" + this.mXAxisMovement + " y=" + this.mYAxisMovement + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public float getXAxisMovement() {
        return this.mXAxisMovement;
    }

    public float getYAxisMovement() {
        return this.mYAxisMovement;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private long mEventTimeNanos = 0;
        private float mXAxisMovement;
        private float mYAxisMovement;

        public VirtualMouseScrollEvent build() {
            return new VirtualMouseScrollEvent(this.mXAxisMovement, this.mYAxisMovement, this.mEventTimeNanos);
        }

        public Builder setXAxisMovement(float f) {
            Preconditions.checkArgumentInRange(f, -1.0f, 1.0f, "xAxisMovement");
            this.mXAxisMovement = f;
            return this;
        }

        public Builder setYAxisMovement(float f) {
            Preconditions.checkArgumentInRange(f, -1.0f, 1.0f, "yAxisMovement");
            this.mYAxisMovement = f;
            return this;
        }

        public Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }
    }
}
