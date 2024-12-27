package android.media;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AudioTimestamp implements Parcelable {
    public static final Parcelable.Creator<AudioTimestamp> CREATOR =
            new Parcelable.Creator<AudioTimestamp>() { // from class: android.media.AudioTimestamp.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioTimestamp createFromParcel(Parcel in) {
                    return new AudioTimestamp(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AudioTimestamp[] newArray(int size) {
                    return new AudioTimestamp[size];
                }
            };
    public static final int TIMEBASE_BOOTTIME = 1;
    public static final int TIMEBASE_MONOTONIC = 0;
    public long framePosition;
    public long nanoTime;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Timebase {}

    public AudioTimestamp() {}

    private AudioTimestamp(Parcel in) {
        this.framePosition = in.readLong();
        this.nanoTime = in.readLong();
    }

    public String toString() {
        return "AudioTimeStamp: framePos=" + this.framePosition + " nanoTime=" + this.nanoTime;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.framePosition);
        dest.writeLong(this.nanoTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
