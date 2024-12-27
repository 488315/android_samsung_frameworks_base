package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseScrollEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseScrollEvent> CREATOR =
            new Parcelable.Creator<VirtualMouseScrollEvent>() { // from class:
                // android.hardware.input.VirtualMouseScrollEvent.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseScrollEvent createFromParcel(Parcel source) {
                    return new VirtualMouseScrollEvent(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseScrollEvent[] newArray(int size) {
                    return new VirtualMouseScrollEvent[size];
                }
            };
    private final long mEventTimeNanos;
    private final float mXAxisMovement;
    private final float mYAxisMovement;

    private VirtualMouseScrollEvent(float xAxisMovement, float yAxisMovement, long eventTimeNanos) {
        this.mXAxisMovement = xAxisMovement;
        this.mYAxisMovement = yAxisMovement;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualMouseScrollEvent(Parcel parcel) {
        this.mXAxisMovement = parcel.readFloat();
        this.mYAxisMovement = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeFloat(this.mXAxisMovement);
        parcel.writeFloat(this.mYAxisMovement);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "VirtualMouseScrollEvent( x="
                + this.mXAxisMovement
                + " y="
                + this.mYAxisMovement
                + " eventTime(ns)="
                + this.mEventTimeNanos;
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
            return new VirtualMouseScrollEvent(
                    this.mXAxisMovement, this.mYAxisMovement, this.mEventTimeNanos);
        }

        public Builder setXAxisMovement(float xAxisMovement) {
            Preconditions.checkArgumentInRange(xAxisMovement, -1.0f, 1.0f, "xAxisMovement");
            this.mXAxisMovement = xAxisMovement;
            return this;
        }

        public Builder setYAxisMovement(float yAxisMovement) {
            Preconditions.checkArgumentInRange(yAxisMovement, -1.0f, 1.0f, "yAxisMovement");
            this.mYAxisMovement = yAxisMovement;
            return this;
        }

        public Builder setEventTimeNanos(long eventTimeNanos) {
            if (eventTimeNanos < 0) {
                throw new IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = eventTimeNanos;
            return this;
        }
    }
}
