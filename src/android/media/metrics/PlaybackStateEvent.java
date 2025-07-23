package android.media.metrics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PlaybackStateEvent extends Event implements Parcelable {
    public static final Parcelable.Creator<PlaybackStateEvent> CREATOR = new Parcelable.Creator<PlaybackStateEvent>() { // from class: android.media.metrics.PlaybackStateEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackStateEvent[] newArray(int i) {
            return new PlaybackStateEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackStateEvent createFromParcel(Parcel parcel) {
            return new PlaybackStateEvent(parcel);
        }
    };
    public static final int STATE_ABANDONED = 15;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_ENDED = 11;
    public static final int STATE_FAILED = 13;
    public static final int STATE_INTERRUPTED_BY_AD = 14;
    public static final int STATE_JOINING_BACKGROUND = 1;
    public static final int STATE_JOINING_FOREGROUND = 2;
    public static final int STATE_NOT_STARTED = 0;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PAUSED_BUFFERING = 7;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_SEEKING = 5;
    public static final int STATE_STOPPED = 12;
    public static final int STATE_SUPPRESSED = 9;
    public static final int STATE_SUPPRESSED_BUFFERING = 10;
    private final int mState;
    private final long mTimeSinceCreatedMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String stateToString(int i) {
        switch (i) {
            case 0:
                return "STATE_NOT_STARTED";
            case 1:
                return "STATE_JOINING_BACKGROUND";
            case 2:
                return "STATE_JOINING_FOREGROUND";
            case 3:
                return "STATE_PLAYING";
            case 4:
                return "STATE_PAUSED";
            case 5:
                return "STATE_SEEKING";
            case 6:
                return "STATE_BUFFERING";
            case 7:
                return "STATE_PAUSED_BUFFERING";
            case 8:
            default:
                return Integer.toHexString(i);
            case 9:
                return "STATE_SUPPRESSED";
            case 10:
                return "STATE_SUPPRESSED_BUFFERING";
            case 11:
                return "STATE_ENDED";
            case 12:
                return "STATE_STOPPED";
            case 13:
                return "STATE_FAILED";
            case 14:
                return "STATE_INTERRUPTED_BY_AD";
            case 15:
                return "STATE_ABANDONED";
        }
    }

    private PlaybackStateEvent(int i, long j, Bundle bundle) {
        this.mTimeSinceCreatedMillis = j;
        this.mState = i;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PlaybackStateEvent playbackStateEvent = (PlaybackStateEvent) obj;
            if (this.mState == playbackStateEvent.mState && this.mTimeSinceCreatedMillis == playbackStateEvent.mTimeSinceCreatedMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mState), Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    private PlaybackStateEvent(Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        Bundle readBundle = parcel.readBundle();
        this.mState = readInt;
        this.mTimeSinceCreatedMillis = readLong;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private int mState = 0;
        private long mTimeSinceCreatedMillis = -1;
        private Bundle mMetricsBundle = new Bundle();

        public Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public Builder setMetricsBundle(Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public PlaybackStateEvent build() {
            return new PlaybackStateEvent(this.mState, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}
