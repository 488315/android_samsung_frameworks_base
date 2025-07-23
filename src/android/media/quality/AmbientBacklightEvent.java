package android.media.quality;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class AmbientBacklightEvent implements Parcelable {
    public static final int AMBIENT_BACKLIGHT_EVENT_DISABLED = 2;
    public static final int AMBIENT_BACKLIGHT_EVENT_ENABLED = 1;
    public static final int AMBIENT_BACKLIGHT_EVENT_INTERRUPTED = 4;
    public static final int AMBIENT_BACKLIGHT_EVENT_METADATA_AVAILABLE = 3;
    public static final Parcelable.Creator<AmbientBacklightEvent> CREATOR = new Parcelable.Creator<AmbientBacklightEvent>() { // from class: android.media.quality.AmbientBacklightEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightEvent createFromParcel(Parcel parcel) {
            return new AmbientBacklightEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightEvent[] newArray(int i) {
            return new AmbientBacklightEvent[i];
        }
    };
    private final int mEventType;
    private final AmbientBacklightMetadata mMetadata;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AmbientBacklightEvent(int i, AmbientBacklightMetadata ambientBacklightMetadata) {
        this.mEventType = i;
        this.mMetadata = ambientBacklightMetadata;
    }

    private AmbientBacklightEvent(Parcel parcel) {
        this.mEventType = parcel.readInt();
        this.mMetadata = (AmbientBacklightMetadata) parcel.readParcelable(AmbientBacklightMetadata.class.getClassLoader());
    }

    public int getEventType() {
        return this.mEventType;
    }

    public AmbientBacklightMetadata getMetadata() {
        return this.mMetadata;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        parcel.writeParcelable(this.mMetadata, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AmbientBacklightEvent)) {
            return false;
        }
        AmbientBacklightEvent ambientBacklightEvent = (AmbientBacklightEvent) obj;
        return this.mEventType == ambientBacklightEvent.mEventType && Objects.equals(this.mMetadata, ambientBacklightEvent.mMetadata);
    }

    public int hashCode() {
        int i = this.mEventType * 31;
        AmbientBacklightMetadata ambientBacklightMetadata = this.mMetadata;
        return i + (ambientBacklightMetadata != null ? ambientBacklightMetadata.hashCode() : 0);
    }

    public String toString() {
        return "AmbientBacklightEvent{mEventType=" + this.mEventType + ", mMetadata=" + this.mMetadata + '}';
    }
}
