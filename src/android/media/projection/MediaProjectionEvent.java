package android.media.projection;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaProjectionEvent implements Parcelable {
    public static final Parcelable.Creator<MediaProjectionEvent> CREATOR = new Parcelable.Creator<MediaProjectionEvent>() { // from class: android.media.projection.MediaProjectionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionEvent createFromParcel(Parcel parcel) {
            return new MediaProjectionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionEvent[] newArray(int i) {
            return new MediaProjectionEvent[i];
        }
    };
    public static final int PROJECTION_STARTED_DURING_CALL_AND_ACTIVE_POST_CALL = 0;
    private final int mEventType;
    private final long mTimestampMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaProjectionEvent(int i, long j) {
        this.mEventType = i;
        this.mTimestampMillis = j;
    }

    private MediaProjectionEvent(Parcel parcel) {
        this.mEventType = parcel.readInt();
        this.mTimestampMillis = parcel.readLong();
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MediaProjectionEvent) {
            MediaProjectionEvent mediaProjectionEvent = (MediaProjectionEvent) obj;
            if (this.mEventType == mediaProjectionEvent.mEventType && this.mTimestampMillis == mediaProjectionEvent.mTimestampMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mEventType), Long.valueOf(this.mTimestampMillis));
    }

    public String toString() {
        return "MediaProjectionEvent{mEventType=" + this.mEventType + ", mTimestampMillis=" + this.mTimestampMillis + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mTimestampMillis);
    }
}
