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
        public VirtualStylusButtonEvent createFromParcel(Parcel source) {
            return new VirtualStylusButtonEvent(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualStylusButtonEvent[] newArray(int size) {
            return new VirtualStylusButtonEvent[size];
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

    private VirtualStylusButtonEvent(int action, int buttonCode, long eventTimeNanos) {
        this.mAction = action;
        this.mButtonCode = buttonCode;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualStylusButtonEvent(Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mButtonCode = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mButtonCode);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

        public Builder setButtonCode(int buttonCode) {
            if (buttonCode != 32 && buttonCode != 64) {
                throw new IllegalArgumentException("Unsupported stylus button code : " + buttonCode);
            }
            this.mButtonCode = buttonCode;
            return this;
        }

        public Builder setAction(int action) {
            if (action != 11 && action != 12) {
                throw new IllegalArgumentException("Unsupported stylus button action : " + action);
            }
            this.mAction = action;
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
