package android.hardware.input;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualKeyEvent implements Parcelable {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_UP = 1;
    public static final Parcelable.Creator<VirtualKeyEvent> CREATOR = new Parcelable.Creator<VirtualKeyEvent>() { // from class: android.hardware.input.VirtualKeyEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualKeyEvent createFromParcel(Parcel source) {
            return new VirtualKeyEvent(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualKeyEvent[] newArray(int size) {
            return new VirtualKeyEvent[size];
        }
    };
    private final int mAction;
    private final long mEventTimeNanos;
    private final int mKeyCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SupportedKeycode {
    }

    private VirtualKeyEvent(int action, int keyCode, long eventTimeNanos) {
        this.mAction = action;
        this.mKeyCode = keyCode;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualKeyEvent(Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mKeyCode = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mKeyCode);
        parcel.writeLong(this.mEventTimeNanos);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getKeyCode() {
        return this.mKeyCode;
    }

    public int getAction() {
        return this.mAction;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mAction = -1;
        private int mKeyCode = -1;
        private long mEventTimeNanos = 0;

        public VirtualKeyEvent build() {
            if (this.mAction == -1 || this.mKeyCode == -1) {
                throw new IllegalArgumentException("Cannot build virtual key event with unset fields");
            }
            return new VirtualKeyEvent(this.mAction, this.mKeyCode, this.mEventTimeNanos);
        }

        public Builder setKeyCode(int keyCode) {
            this.mKeyCode = keyCode;
            return this;
        }

        public Builder setAction(int action) {
            if (action != 0 && action != 1) {
                throw new IllegalArgumentException("Unsupported action type");
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
