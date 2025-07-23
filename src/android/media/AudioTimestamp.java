package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class AudioTimestamp implements Parcelable {
    public static final Parcelable.Creator<AudioTimestamp> CREATOR = new Parcelable.Creator<AudioTimestamp>() { // from class: android.media.AudioTimestamp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioTimestamp createFromParcel(Parcel parcel) {
            return new AudioTimestamp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioTimestamp[] newArray(int i) {
            return new AudioTimestamp[i];
        }
    };
    public static final int TIMEBASE_BOOTTIME = 1;
    public static final int TIMEBASE_MONOTONIC = 0;
    public long framePosition;
    public long nanoTime;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Timebase {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioTimestamp() {
    }

    private AudioTimestamp(Parcel parcel) {
        this.framePosition = parcel.readLong();
        this.nanoTime = parcel.readLong();
    }

    public String toString() {
        return "AudioTimeStamp: framePos=" + this.framePosition + " nanoTime=" + this.nanoTime;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.framePosition);
        parcel.writeLong(this.nanoTime);
    }
}
