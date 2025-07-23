package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseRelativeEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualMouseRelativeEvent> CREATOR = new Parcelable.Creator<VirtualMouseRelativeEvent>() { // from class: android.hardware.input.VirtualMouseRelativeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseRelativeEvent createFromParcel(Parcel parcel) {
            return new VirtualMouseRelativeEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseRelativeEvent[] newArray(int i) {
            return new VirtualMouseRelativeEvent[i];
        }
    };
    private final long mEventTimeNanos;
    private final float mRelativeX;
    private final float mRelativeY;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualMouseRelativeEvent(float f, float f2, long j) {
        this.mRelativeX = f;
        this.mRelativeY = f2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseRelativeEvent(Parcel parcel) {
        this.mRelativeX = parcel.readFloat();
        this.mRelativeY = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mRelativeX);
        parcel.writeFloat(this.mRelativeY);
        parcel.writeLong(this.mEventTimeNanos);
    }

    public String toString() {
        return "VirtualMouseRelativeEvent( x=" + this.mRelativeX + " y=" + this.mRelativeY + " eventTime(ns)=" + this.mEventTimeNanos;
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
            return new VirtualMouseRelativeEvent(this.mRelativeX, this.mRelativeY, this.mEventTimeNanos);
        }

        public Builder setRelativeX(float f) {
            this.mRelativeX = f;
            return this;
        }

        public Builder setRelativeY(float f) {
            this.mRelativeY = f;
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
