package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MediaSyncEvent implements Parcelable {
    public static final Parcelable.Creator<MediaSyncEvent> CREATOR = new Parcelable.Creator<MediaSyncEvent>() { // from class: android.media.MediaSyncEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaSyncEvent createFromParcel(Parcel parcel) {
            return new MediaSyncEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaSyncEvent[] newArray(int i) {
            return new MediaSyncEvent[i];
        }
    };
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;

    @SystemApi
    public static final int SYNC_EVENT_SHARE_AUDIO_HISTORY = 100;
    private int mAudioSession;
    private final int mType;

    private static boolean isValidType(int i) {
        return i == 0 || i == 1 || i == 100;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static MediaSyncEvent createEvent(int i) throws IllegalArgumentException {
        if (!isValidType(i)) {
            throw new IllegalArgumentException(i + "is not a valid MediaSyncEvent type.");
        }
        return new MediaSyncEvent(i);
    }

    private MediaSyncEvent(int i) {
        this.mAudioSession = 0;
        this.mType = i;
    }

    public MediaSyncEvent setAudioSessionId(int i) throws IllegalArgumentException {
        if (i > 0) {
            this.mAudioSession = i;
            return this;
        }
        throw new IllegalArgumentException(i + " is not a valid session ID.");
    }

    public int getType() {
        return this.mType;
    }

    public int getAudioSessionId() {
        return this.mAudioSession;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Objects.requireNonNull(parcel);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAudioSession);
    }

    private MediaSyncEvent(Parcel parcel) {
        this.mAudioSession = 0;
        this.mType = parcel.readInt();
        this.mAudioSession = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MediaSyncEvent mediaSyncEvent = (MediaSyncEvent) obj;
            if (this.mType == mediaSyncEvent.mType && this.mAudioSession == mediaSyncEvent.mAudioSession) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), Integer.valueOf(this.mAudioSession));
    }

    public String toString() {
        return new String("MediaSyncEvent: type=" + typeToString(this.mType) + " session=" + this.mAudioSession);
    }

    public static String typeToString(int i) {
        if (i == 0) {
            return "SYNC_EVENT_NONE";
        }
        if (i == 1) {
            return "SYNC_EVENT_PRESENTATION_COMPLETE";
        }
        if (i == 100) {
            return "SYNC_EVENT_SHARE_AUDIO_HISTORY";
        }
        return "unknown event type " + i;
    }
}
