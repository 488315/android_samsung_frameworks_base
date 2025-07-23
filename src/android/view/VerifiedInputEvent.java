package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public abstract class VerifiedInputEvent implements Parcelable {
    public static final Parcelable.Creator<VerifiedInputEvent> CREATOR = new Parcelable.Creator<VerifiedInputEvent>() { // from class: android.view.VerifiedInputEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedInputEvent[] newArray(int i) {
            return new VerifiedInputEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifiedInputEvent createFromParcel(Parcel parcel) {
            int peekInt = VerifiedInputEvent.peekInt(parcel);
            if (peekInt == 1) {
                return VerifiedKeyEvent.CREATOR.createFromParcel(parcel);
            }
            if (peekInt == 2) {
                return VerifiedMotionEvent.CREATOR.createFromParcel(parcel);
            }
            throw new IllegalArgumentException("Unexpected input event type in parcel.");
        }
    };
    private static final String TAG = "VerifiedInputEvent";
    protected static final int VERIFIED_KEY = 1;
    protected static final int VERIFIED_MOTION = 2;
    private int mDeviceId;
    private int mDisplayId;
    private long mEventTimeNanos;
    private int mSource;
    private int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerifiedInputEventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected VerifiedInputEvent(int i, int i2, long j, int i3, int i4) {
        this.mType = i;
        this.mDeviceId = i2;
        this.mEventTimeNanos = j;
        this.mSource = i3;
        this.mDisplayId = i4;
    }

    protected VerifiedInputEvent(Parcel parcel, int i) {
        int readInt = parcel.readInt();
        this.mType = readInt;
        if (readInt != i) {
            throw new IllegalArgumentException("Unexpected input event type token in parcel.");
        }
        this.mDeviceId = parcel.readInt();
        this.mEventTimeNanos = parcel.readLong();
        this.mSource = parcel.readInt();
        this.mDisplayId = parcel.readInt();
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public long getEventTimeNanos() {
        return this.mEventTimeNanos;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mDeviceId);
        parcel.writeLong(this.mEventTimeNanos);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mDisplayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int peekInt(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        parcel.setDataPosition(dataPosition);
        return readInt;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VerifiedInputEvent verifiedInputEvent = (VerifiedInputEvent) obj;
            if (this.mType == verifiedInputEvent.mType && getDeviceId() == verifiedInputEvent.getDeviceId() && getEventTimeNanos() == verifiedInputEvent.getEventTimeNanos() && getSource() == verifiedInputEvent.getSource() && getDisplayId() == verifiedInputEvent.getDisplayId()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((this.mType + 31) * 31) + getDeviceId()) * 31) + Long.hashCode(getEventTimeNanos())) * 31) + getSource()) * 31) + getDisplayId();
    }
}
