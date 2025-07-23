package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
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
    public static final Parcelable.Creator<VirtualMouseButtonEvent> CREATOR = new Parcelable.Creator<VirtualMouseButtonEvent>() { // from class: android.hardware.input.VirtualMouseButtonEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseButtonEvent createFromParcel(Parcel parcel) {
            return new VirtualMouseButtonEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualMouseButtonEvent[] newArray(int i) {
            return new VirtualMouseButtonEvent[i];
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

    private VirtualMouseButtonEvent(int i, int i2, long j) {
        this.mAction = i;
        this.mButtonCode = i2;
        this.mEventTimeNanos = j;
    }

    private VirtualMouseButtonEvent(Parcel parcel) {
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

    public String toString() {
        return "VirtualMouseButtonEvent( action=" + MotionEvent.actionToString(this.mAction) + " button=" + MotionEvent.buttonStateToString(this.mButtonCode) + " eventTime(ns)=" + this.mEventTimeNanos;
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
                throw new IllegalArgumentException("Cannot build virtual mouse button event with unset fields");
            }
            return new VirtualMouseButtonEvent(this.mAction, this.mButtonCode, this.mEventTimeNanos);
        }

        public Builder setButtonCode(int i) {
            if (i != 1 && i != 4 && i != 2 && i != 8 && i != 16) {
                throw new IllegalArgumentException("Unsupported mouse button code");
            }
            this.mButtonCode = i;
            return this;
        }

        public Builder setAction(int i) {
            if (i != 11 && i != 12) {
                throw new IllegalArgumentException("Unsupported mouse button action type");
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
