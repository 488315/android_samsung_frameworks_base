package android.hardware.input;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualStylusMotionEvent implements Parcelable {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_UP = 1;
    public static final Parcelable.Creator<VirtualStylusMotionEvent> CREATOR = new Parcelable.Creator<VirtualStylusMotionEvent>() { // from class: android.hardware.input.VirtualStylusMotionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualStylusMotionEvent createFromParcel(Parcel parcel) {
            return new VirtualStylusMotionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualStylusMotionEvent[] newArray(int i) {
            return new VirtualStylusMotionEvent[i];
        }
    };
    private static final int PRESSURE_MAX = 255;
    private static final int PRESSURE_MIN = 0;
    private static final int TILT_MAX = 90;
    private static final int TILT_MIN = -90;
    public static final int TOOL_TYPE_ERASER = 4;
    public static final int TOOL_TYPE_STYLUS = 2;
    public static final int TOOL_TYPE_UNKNOWN = 0;
    private final int mAction;
    private final long mEventTimeNanos;
    private final int mPressure;
    private final int mTiltX;
    private final int mTiltY;
    private final int mToolType;
    private final int mX;
    private final int mY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ToolType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualStylusMotionEvent(int i, int i2, int i3, int i4, int i5, int i6, int i7, long j) {
        this.mToolType = i;
        this.mAction = i2;
        this.mX = i3;
        this.mY = i4;
        this.mPressure = i5;
        this.mTiltX = i6;
        this.mTiltY = i7;
        this.mEventTimeNanos = j;
    }

    private VirtualStylusMotionEvent(Parcel parcel) {
        this.mToolType = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mX = parcel.readInt();
        this.mY = parcel.readInt();
        this.mPressure = parcel.readInt();
        this.mTiltX = parcel.readInt();
        this.mTiltY = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mToolType);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mX);
        parcel.writeInt(this.mY);
        parcel.writeInt(this.mPressure);
        parcel.writeInt(this.mTiltX);
        parcel.writeInt(this.mTiltY);
        parcel.writeLong(this.mEventTimeNanos);
    }

    public int getToolType() {
        return this.mToolType;
    }

    public int getAction() {
        return this.mAction;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getPressure() {
        return this.mPressure;
    }

    public int getTiltX() {
        return this.mTiltX;
    }

    public int getTiltY() {
        return this.mTiltY;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mToolType = 0;
        private int mAction = -1;
        private int mX = 0;
        private int mY = 0;
        private boolean mIsXSet = false;
        private boolean mIsYSet = false;
        private int mPressure = 255;
        private int mTiltX = 0;
        private int mTiltY = 0;
        private long mEventTimeNanos = 0;

        public VirtualStylusMotionEvent build() {
            if (this.mToolType == 0) {
                throw new IllegalArgumentException("Cannot build stylus motion event with unset tool type");
            }
            if (this.mAction == -1) {
                throw new IllegalArgumentException("Cannot build stylus motion event with unset action");
            }
            if (!this.mIsXSet) {
                throw new IllegalArgumentException("Cannot build stylus motion event with unset x-axis location");
            }
            if (!this.mIsYSet) {
                throw new IllegalArgumentException("Cannot build stylus motion event with unset y-axis location");
            }
            return new VirtualStylusMotionEvent(this.mToolType, this.mAction, this.mX, this.mY, this.mPressure, this.mTiltX, this.mTiltY, this.mEventTimeNanos);
        }

        public Builder setToolType(int i) {
            if (i != 2 && i != 4) {
                throw new IllegalArgumentException("Unsupported stylus tool type: " + i);
            }
            this.mToolType = i;
            return this;
        }

        public Builder setAction(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("Unsupported stylus action : " + i);
            }
            this.mAction = i;
            return this;
        }

        public Builder setX(int i) {
            this.mX = i;
            this.mIsXSet = true;
            return this;
        }

        public Builder setY(int i) {
            this.mY = i;
            this.mIsYSet = true;
            return this;
        }

        public Builder setPressure(int i) {
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException("Pressure should be between 0 and 255");
            }
            this.mPressure = i;
            return this;
        }

        public Builder setTiltX(int i) {
            validateTilt(i);
            this.mTiltX = i;
            return this;
        }

        public Builder setTiltY(int i) {
            validateTilt(i);
            this.mTiltY = i;
            return this;
        }

        public Builder setEventTimeNanos(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("Event time cannot be negative");
            }
            this.mEventTimeNanos = j;
            return this;
        }

        private void validateTilt(int i) {
            if (i < -90 || i > 90) {
                throw new IllegalArgumentException("Tilt must be between -90 and 90");
            }
        }
    }
}
