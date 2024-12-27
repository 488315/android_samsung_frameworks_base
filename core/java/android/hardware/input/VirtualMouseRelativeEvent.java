package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class VirtualMouseRelativeEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseRelativeEvent> CREATOR =
            new Parcelable.Creator<VirtualMouseRelativeEvent>() { // from class:
                // android.hardware.input.VirtualMouseRelativeEvent.1
                @Override // android.os.Parcelable.Creator
                public VirtualMouseRelativeEvent createFromParcel(Parcel source) {
                    return new VirtualMouseRelativeEvent(source);
                }

                @Override // android.os.Parcelable.Creator
                public VirtualMouseRelativeEvent[] newArray(int size) {
                    return new VirtualMouseRelativeEvent[size];
                }
            };
    private final long mEventTimeNanos;
    private final float mRelativeX;
    private final float mRelativeY;

    private VirtualMouseRelativeEvent(float relativeX, float relativeY, long eventTimeNanos) {
        this.mRelativeX = relativeX;
        this.mRelativeY = relativeY;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualMouseRelativeEvent(Parcel parcel) {
        this.mRelativeX = parcel.readFloat();
        this.mRelativeY = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeFloat(this.mRelativeX);
        parcel.writeFloat(this.mRelativeY);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "VirtualMouseRelativeEvent( x="
                + this.mRelativeX
                + " y="
                + this.mRelativeY
                + " eventTime(ns)="
                + this.mEventTimeNanos;
    }

    public float getRelativeX() {
        return this.mRelativeX;
    }

    public float getRelativeY() {
        return this.mRelativeY;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private long mEventTimeNanos = 0;
        private float mRelativeX;
        private float mRelativeY;

        public VirtualMouseRelativeEvent build() {
            return new VirtualMouseRelativeEvent(
                    this.mRelativeX, this.mRelativeY, this.mEventTimeNanos);
        }

        public Builder setRelativeX(float relativeX) {
            this.mRelativeX = relativeX;
            return this;
        }

        public Builder setRelativeY(float relativeY) {
            this.mRelativeY = relativeY;
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
