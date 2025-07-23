package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualRotaryEncoderScrollEvent implements Parcelable {
    public static final Parcelable.Creator<VirtualRotaryEncoderScrollEvent> CREATOR = new Parcelable.Creator<VirtualRotaryEncoderScrollEvent>() { // from class: android.hardware.input.VirtualRotaryEncoderScrollEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualRotaryEncoderScrollEvent createFromParcel(Parcel parcel) {
            return new VirtualRotaryEncoderScrollEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualRotaryEncoderScrollEvent[] newArray(int i) {
            return new VirtualRotaryEncoderScrollEvent[i];
        }
    };
    private final long mEventTimeNanos;
    private final float mScrollAmount;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualRotaryEncoderScrollEvent(float f, long j) {
        this.mScrollAmount = f;
        this.mEventTimeNanos = j;
    }

    private VirtualRotaryEncoderScrollEvent(Parcel parcel) {
        this.mScrollAmount = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mScrollAmount);
        parcel.writeLong(this.mEventTimeNanos);
    }

    public String toString() {
        return "VirtualRotaryScrollEvent( scrollAmount=" + this.mScrollAmount + " eventTime(ns)=" + this.mEventTimeNanos;
    }

    public float getScrollAmount() {
        return this.mScrollAmount;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private float mScrollAmount = 0.0f;
        private long mEventTimeNanos = 0;

        public VirtualRotaryEncoderScrollEvent build() {
            return new VirtualRotaryEncoderScrollEvent(this.mScrollAmount, this.mEventTimeNanos);
        }

        public Builder setScrollAmount(float f) {
            Preconditions.checkArgumentInRange(f, -1.0f, 1.0f, "scrollAmount");
            this.mScrollAmount = f;
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
