package android.media.metrics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PlaybackErrorEvent extends Event implements Parcelable {
    public static final Parcelable.Creator<PlaybackErrorEvent> CREATOR = new Parcelable.Creator<PlaybackErrorEvent>() { // from class: android.media.metrics.PlaybackErrorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackErrorEvent[] newArray(int i) {
            return new PlaybackErrorEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackErrorEvent createFromParcel(Parcel parcel) {
            return new PlaybackErrorEvent(parcel);
        }
    };
    public static final int ERROR_AUDIO_TRACK_INIT_FAILED = 17;
    public static final int ERROR_AUDIO_TRACK_OTHER = 19;
    public static final int ERROR_AUDIO_TRACK_WRITE_FAILED = 18;
    public static final int ERROR_DECODER_INIT_FAILED = 13;
    public static final int ERROR_DECODING_FAILED = 14;
    public static final int ERROR_DECODING_FORMAT_EXCEEDS_CAPABILITIES = 15;
    public static final int ERROR_DECODING_FORMAT_UNSUPPORTED = 35;
    public static final int ERROR_DECODING_OTHER = 16;
    public static final int ERROR_DRM_CONTENT_ERROR = 28;
    public static final int ERROR_DRM_DEVICE_REVOKED = 29;
    public static final int ERROR_DRM_DISALLOWED_OPERATION = 26;
    public static final int ERROR_DRM_LICENSE_ACQUISITION_FAILED = 25;
    public static final int ERROR_DRM_OTHER = 30;
    public static final int ERROR_DRM_PROVISIONING_FAILED = 24;
    public static final int ERROR_DRM_SCHEME_UNSUPPORTED = 23;
    public static final int ERROR_DRM_SYSTEM_ERROR = 27;
    public static final int ERROR_IO_BAD_HTTP_STATUS = 5;
    public static final int ERROR_IO_CONNECTION_CLOSED = 8;
    public static final int ERROR_IO_CONNECTION_TIMEOUT = 7;
    public static final int ERROR_IO_DNS_FAILED = 6;
    public static final int ERROR_IO_FILE_NOT_FOUND = 31;
    public static final int ERROR_IO_NETWORK_CONNECTION_FAILED = 4;
    public static final int ERROR_IO_NETWORK_UNAVAILABLE = 3;
    public static final int ERROR_IO_NO_PERMISSION = 32;
    public static final int ERROR_IO_OTHER = 9;
    public static final int ERROR_OTHER = 1;
    public static final int ERROR_PARSING_CONTAINER_MALFORMED = 11;
    public static final int ERROR_PARSING_CONTAINER_UNSUPPORTED = 34;
    public static final int ERROR_PARSING_MANIFEST_MALFORMED = 10;
    public static final int ERROR_PARSING_MANIFEST_UNSUPPORTED = 33;
    public static final int ERROR_PARSING_OTHER = 12;
    public static final int ERROR_PLAYER_BEHIND_LIVE_WINDOW = 21;
    public static final int ERROR_PLAYER_OTHER = 22;
    public static final int ERROR_PLAYER_REMOTE = 20;
    public static final int ERROR_RUNTIME = 2;
    public static final int ERROR_UNKNOWN = 0;
    private final int mErrorCode;
    private final String mExceptionStack;
    private final int mSubErrorCode;
    private final long mTimeSinceCreatedMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PlaybackErrorEvent(String str, int i, int i2, long j, Bundle bundle) {
        this.mExceptionStack = str;
        this.mErrorCode = i;
        this.mSubErrorCode = i2;
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public String getExceptionStack() {
        return this.mExceptionStack;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getSubErrorCode() {
        return this.mSubErrorCode;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public String toString() {
        return "PlaybackErrorEvent { exceptionStack = " + this.mExceptionStack + ", errorCode = " + this.mErrorCode + ", subErrorCode = " + this.mSubErrorCode + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PlaybackErrorEvent playbackErrorEvent = (PlaybackErrorEvent) obj;
            if (Objects.equals(this.mExceptionStack, playbackErrorEvent.mExceptionStack) && this.mErrorCode == playbackErrorEvent.mErrorCode && this.mSubErrorCode == playbackErrorEvent.mSubErrorCode && this.mTimeSinceCreatedMillis == playbackErrorEvent.mTimeSinceCreatedMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mExceptionStack, Integer.valueOf(this.mErrorCode), Integer.valueOf(this.mSubErrorCode), Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mExceptionStack != null ? (byte) 1 : (byte) 0);
        String str = this.mExceptionStack;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.mErrorCode);
        parcel.writeInt(this.mSubErrorCode);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    private PlaybackErrorEvent(Parcel parcel) {
        String string = (parcel.readByte() & 1) == 0 ? null : parcel.readString();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        long j = parcel.readLong();
        Bundle bundle = parcel.readBundle();
        this.mExceptionStack = string;
        this.mErrorCode = i;
        this.mSubErrorCode = i2;
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle;
    }

    public static final class Builder {
        private Exception mException;
        private int mSubErrorCode;
        private int mErrorCode = 0;
        private long mTimeSinceCreatedMillis = -1;
        private Bundle mMetricsBundle = new Bundle();

        public Builder setException(Exception exc) {
            this.mException = exc;
            return this;
        }

        public Builder setErrorCode(int i) {
            this.mErrorCode = i;
            return this;
        }

        public Builder setSubErrorCode(int i) {
            this.mSubErrorCode = i;
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

        public PlaybackErrorEvent build() {
            return new PlaybackErrorEvent((this.mException.getStackTrace() == null || this.mException.getStackTrace().length <= 0) ? null : this.mException.getStackTrace()[0].toString(), this.mErrorCode, this.mSubErrorCode, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}
