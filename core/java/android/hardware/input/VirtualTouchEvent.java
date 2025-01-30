package android.hardware.input;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualTouchEvent implements Parcelable {
    public static final int ACTION_CANCEL = 3;
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_UP = 1;
    public static final Parcelable.Creator<VirtualTouchEvent> CREATOR = new Parcelable.Creator<VirtualTouchEvent>() { // from class: android.hardware.input.VirtualTouchEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualTouchEvent createFromParcel(Parcel source) {
            return new VirtualTouchEvent(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualTouchEvent[] newArray(int size) {
            return new VirtualTouchEvent[size];
        }
    };
    private static final int MAX_POINTERS = 16;
    public static final int TOOL_TYPE_FINGER = 1;
    public static final int TOOL_TYPE_PALM = 5;
    public static final int TOOL_TYPE_UNKNOWN = 0;
    private final int mAction;
    private final long mEventTimeNanos;
    private final float mMajorAxisSize;
    private final int mPointerId;
    private final float mPressure;
    private final int mToolType;

    /* renamed from: mX */
    private final float f163mX;

    /* renamed from: mY */
    private final float f164mY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ToolType {
    }

    private VirtualTouchEvent(int pointerId, int toolType, int action, float x, float y, float pressure, float majorAxisSize, long eventTimeNanos) {
        this.mPointerId = pointerId;
        this.mToolType = toolType;
        this.mAction = action;
        this.f163mX = x;
        this.f164mY = y;
        this.mPressure = pressure;
        this.mMajorAxisSize = majorAxisSize;
        this.mEventTimeNanos = eventTimeNanos;
    }

    private VirtualTouchEvent(Parcel parcel) {
        this.mPointerId = parcel.readInt();
        this.mToolType = parcel.readInt();
        this.mAction = parcel.readInt();
        this.f163mX = parcel.readFloat();
        this.f164mY = parcel.readFloat();
        this.mPressure = parcel.readFloat();
        this.mMajorAxisSize = parcel.readFloat();
        this.mEventTimeNanos = parcel.readLong();
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPointerId);
        dest.writeInt(this.mToolType);
        dest.writeInt(this.mAction);
        dest.writeFloat(this.f163mX);
        dest.writeFloat(this.f164mY);
        dest.writeFloat(this.mPressure);
        dest.writeFloat(this.mMajorAxisSize);
        dest.writeLong(this.mEventTimeNanos);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getPointerId() {
        return this.mPointerId;
    }

    public int getToolType() {
        return this.mToolType;
    }

    public int getAction() {
        return this.mAction;
    }

    public float getX() {
        return this.f163mX;
    }

    public float getY() {
        return this.f164mY;
    }

    public float getPressure() {
        return this.mPressure;
    }

    public float getMajorAxisSize() {
        return this.mMajorAxisSize;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public static final class Builder {
        private int mToolType = 0;
        private int mPointerId = -1;
        private int mAction = -1;

        /* renamed from: mX */
        private float f165mX = Float.NaN;

        /* renamed from: mY */
        private float f166mY = Float.NaN;
        private float mPressure = Float.NaN;
        private float mMajorAxisSize = Float.NaN;
        private long mEventTimeNanos = 0;

        public VirtualTouchEvent build() {
            if (this.mToolType == 0 || this.mPointerId == -1 || this.mAction == -1 || Float.isNaN(this.f165mX) || Float.isNaN(this.f166mY)) {
                throw new IllegalArgumentException("Cannot build virtual touch event with unset required fields");
            }
            int i = this.mToolType;
            if ((i == 5 && this.mAction != 3) || (this.mAction == 3 && i != 5)) {
                throw new IllegalArgumentException("ACTION_CANCEL and TOOL_TYPE_PALM must always appear together");
            }
            return new VirtualTouchEvent(this.mPointerId, this.mToolType, this.mAction, this.f165mX, this.f166mY, this.mPressure, this.mMajorAxisSize, this.mEventTimeNanos);
        }

        public Builder setPointerId(int pointerId) {
            if (pointerId < 0 || pointerId > 15) {
                throw new IllegalArgumentException("The pointer id must be in the range 0 - 15inclusive, but was: " + pointerId);
            }
            this.mPointerId = pointerId;
            return this;
        }

        public Builder setToolType(int toolType) {
            if (toolType != 1 && toolType != 5) {
                throw new IllegalArgumentException("Unsupported touch event tool type");
            }
            this.mToolType = toolType;
            return this;
        }

        public Builder setAction(int action) {
            if (action != 0 && action != 1 && action != 2 && action != 3) {
                throw new IllegalArgumentException("Unsupported touch event action type");
            }
            this.mAction = action;
            return this;
        }

        public Builder setX(float absX) {
            this.f165mX = absX;
            return this;
        }

        public Builder setY(float absY) {
            this.f166mY = absY;
            return this;
        }

        public Builder setPressure(float pressure) {
            if (pressure < 0.0f) {
                throw new IllegalArgumentException("Touch event pressure cannot be negative");
            }
            this.mPressure = pressure;
            return this;
        }

        public Builder setMajorAxisSize(float majorAxisSize) {
            if (majorAxisSize < 0.0f) {
                throw new IllegalArgumentException("Touch event major axis size cannot be negative");
            }
            this.mMajorAxisSize = majorAxisSize;
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
