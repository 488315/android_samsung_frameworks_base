package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualStylusButtonEvent implements Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_UNKNOWN = -1;
    public static final int BUTTON_PRIMARY = 32;
    public static final int BUTTON_SECONDARY = 64;
    public static final int BUTTON_UNKNOWN = -1;
    public static final Parcelable.Creator<VirtualStylusButtonEvent> CREATOR = new Parcelable.Creator<VirtualStylusButtonEvent>() { // from class: android.hardware.input.VirtualStylusButtonEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualStylusButtonEvent createFromParcel(Parcel parcel) {
            return new VirtualStylusButtonEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualStylusButtonEvent[] newArray(int i) {
            return new VirtualStylusButtonEvent[i];
        }
    };
    private final int mAction;
    private final int mButtonCode;
    private final long mEventTimeNanos;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Button {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualStylusButtonEvent(int i, int i2, long j) {
        this.mAction = i;
        this.mButtonCode = i2;
        this.mEventTimeNanos = j;
    }

    private VirtualStylusButtonEvent(Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mButtonCode = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mButtonCode);
        parcel.writeLong(this.mEventTimeNanos);
    }

    public int getButtonCode() {
        return this.mButtonCode;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mAction = -1;
        private int mButtonCode = -1;
        private long mEventTimeNanos = 0;

        public VirtualStylusButtonEvent build() {
            if (this.mAction == -1) {
                throw new IllegalArgumentException("Cannot build stylus button event with unset action");
            }
            if (this.mButtonCode == -1) {
                throw new IllegalArgumentException("Cannot build stylus button event with unset button code");
            }
            return new VirtualStylusButtonEvent(this.mAction, this.mButtonCode, this.mEventTimeNanos);
        }

        public Builder setButtonCode(int i) {
            if (i != 32 && i != 64) {
                throw new IllegalArgumentException("Unsupported stylus button code : " + i);
            }
            this.mButtonCode = i;
            return this;
        }

        public Builder setAction(int i) {
            if (i != 11 && i != 12) {
                throw new IllegalArgumentException("Unsupported stylus button action : " + i);
            }
            this.mAction = i;
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
