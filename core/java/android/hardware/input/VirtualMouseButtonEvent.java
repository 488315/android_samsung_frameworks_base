package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class VirtualMouseButtonEvent implements Parcelable {
    public static final int ACTION_BUTTON_PRESS = 11;
    public static final int ACTION_BUTTON_RELEASE = 12;
    public static final int ACTION_UNKNOWN = -1;
    public static final int BUTTON_BACK = 8;
    public static final int BUTTON_FORWARD = 16;
    public static final int BUTTON_PRIMARY = 1;
    public static final int BUTTON_SECONDARY = 2;
    public static final int BUTTON_TERTIARY = 4;
    public static final int BUTTON_UNKNOWN = -1;
    public static final Parcelable.Creator<VirtualMouseButtonEvent> CREATOR =
            new Parcelable.Creator<VirtualMouseButtonEvent>() { // from class:
                // android.hardware.input.VirtualMouseButtonEvent.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseButtonEvent createFromParcel(Parcel source) {
                    return new VirtualMouseButtonEvent(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualMouseButtonEvent[] newArray(int size) {
                    return new VirtualMouseButtonEvent[size];
                }
            };
    private final int mAction;
    private final int mButtonCode;
    private final long mEventTimeNanos;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Button {}

    private VirtualMouseButtonEvent(int action, int buttonCode, long eventTimeNanos) {
        this.mAction = action;
        this.mButtonCode = buttonCode;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualMouseButtonEvent(Parcel parcel) {
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

    public String toString() {
        return "VirtualMouseButtonEvent( action="
                + MotionEvent.actionToString(this.mAction)
                + " button="
                + MotionEvent.buttonStateToString(this.mButtonCode)
                + " eventTime(ns)="
                + this.mEventTimeNanos;
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

        public VirtualMouseButtonEvent build() {
            if (this.mAction == -1 || this.mButtonCode == -1) {
                throw new IllegalArgumentException(
                        "Cannot build virtual mouse button event with unset fields");
            }
            return new VirtualMouseButtonEvent(
                    this.mAction, this.mButtonCode, this.mEventTimeNanos);
        }

        public Builder setButtonCode(int buttonCode) {
            if (buttonCode != 1
                    && buttonCode != 4
                    && buttonCode != 2
                    && buttonCode != 8
                    && buttonCode != 16) {
                throw new IllegalArgumentException("Unsupported mouse button code");
            }
            this.mButtonCode = buttonCode;
            return this;
        }

        public Builder setAction(int action) {
            if (action != 11 && action != 12) {
                throw new IllegalArgumentException("Unsupported mouse button action type");
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
