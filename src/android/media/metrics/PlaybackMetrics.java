package android.media.metrics;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PlaybackMetrics implements Parcelable {
    public static final int CONTENT_TYPE_AD = 2;
    public static final int CONTENT_TYPE_MAIN = 1;
    public static final int CONTENT_TYPE_OTHER = 3;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final Parcelable.Creator<PlaybackMetrics> CREATOR = new Parcelable.Creator<PlaybackMetrics>() { // from class: android.media.metrics.PlaybackMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackMetrics[] newArray(int i) {
            return new PlaybackMetrics[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackMetrics createFromParcel(Parcel parcel) {
            return new PlaybackMetrics(parcel);
        }
    };
    public static final int DRM_TYPE_CLEARKEY = 6;
    public static final int DRM_TYPE_NONE = 0;
    public static final int DRM_TYPE_OTHER = 1;
    public static final int DRM_TYPE_PLAY_READY = 2;
    public static final int DRM_TYPE_WIDEVINE_L1 = 3;
    public static final int DRM_TYPE_WIDEVINE_L3 = 4;
    public static final int DRM_TYPE_WV_L3_FALLBACK = 5;
    public static final int PLAYBACK_TYPE_LIVE = 2;
    public static final int PLAYBACK_TYPE_OTHER = 3;
    public static final int PLAYBACK_TYPE_UNKNOWN = 0;
    public static final int PLAYBACK_TYPE_VOD = 1;
    public static final int STREAM_SOURCE_DEVICE = 2;
    public static final int STREAM_SOURCE_MIXED = 3;
    public static final int STREAM_SOURCE_NETWORK = 1;
    public static final int STREAM_SOURCE_UNKNOWN = 0;
    public static final int STREAM_TYPE_DASH = 3;
    public static final int STREAM_TYPE_HLS = 4;
    public static final int STREAM_TYPE_OTHER = 1;
    public static final int STREAM_TYPE_PROGRESSIVE = 2;
    public static final int STREAM_TYPE_SS = 5;
    public static final int STREAM_TYPE_UNKNOWN = 0;
    private final int mAudioUnderrunCount;
    private final int mContentType;
    private final byte[] mDrmSessionId;
    private final int mDrmType;
    private final long[] mExperimentIds;
    private final long mLocalBytesRead;
    private final long mMediaDurationMillis;
    private final Bundle mMetricsBundle;
    private final long mNetworkBytesRead;
    private final long mNetworkTransferDurationMillis;
    private final int mPlaybackType;
    private final String mPlayerName;
    private final String mPlayerVersion;
    private final int mStreamSource;
    private final int mStreamType;
    private final int mVideoFramesDropped;
    private final int mVideoFramesPlayed;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrmType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PlaybackMetrics(long j, int i, int i2, int i3, int i4, int i5, String str, String str2, long[] jArr, int i6, int i7, int i8, long j2, long j3, long j4, byte[] bArr, Bundle bundle) {
        this.mMediaDurationMillis = j;
        this.mStreamSource = i;
        this.mStreamType = i2;
        this.mPlaybackType = i3;
        this.mDrmType = i4;
        this.mContentType = i5;
        this.mPlayerName = str;
        this.mPlayerVersion = str2;
        this.mExperimentIds = jArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) jArr);
        this.mVideoFramesPlayed = i6;
        this.mVideoFramesDropped = i7;
        this.mAudioUnderrunCount = i8;
        this.mNetworkBytesRead = j2;
        this.mLocalBytesRead = j3;
        this.mNetworkTransferDurationMillis = j4;
        this.mDrmSessionId = bArr;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public long getMediaDurationMillis() {
        return this.mMediaDurationMillis;
    }

    public int getStreamSource() {
        return this.mStreamSource;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public int getPlaybackType() {
        return this.mPlaybackType;
    }

    public int getDrmType() {
        return this.mDrmType;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public String getPlayerName() {
        return this.mPlayerName;
    }

    public String getPlayerVersion() {
        return this.mPlayerVersion;
    }

    public long[] getExperimentIds() {
        long[] jArr = this.mExperimentIds;
        return Arrays.copyOf(jArr, jArr.length);
    }

    public int getVideoFramesPlayed() {
        return this.mVideoFramesPlayed;
    }

    public int getVideoFramesDropped() {
        return this.mVideoFramesDropped;
    }

    public int getAudioUnderrunCount() {
        return this.mAudioUnderrunCount;
    }

    public long getNetworkBytesRead() {
        return this.mNetworkBytesRead;
    }

    public long getLocalBytesRead() {
        return this.mLocalBytesRead;
    }

    public long getNetworkTransferDurationMillis() {
        return this.mNetworkTransferDurationMillis;
    }

    public byte[] getDrmSessionId() {
        return this.mDrmSessionId;
    }

    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public String toString() {
        return "PlaybackMetrics { mediaDurationMillis = " + this.mMediaDurationMillis + ", streamSource = " + this.mStreamSource + ", streamType = " + this.mStreamType + ", playbackType = " + this.mPlaybackType + ", drmType = " + this.mDrmType + ", contentType = " + this.mContentType + ", playerName = " + this.mPlayerName + ", playerVersion = " + this.mPlayerVersion + ", experimentIds = " + Arrays.toString(this.mExperimentIds) + ", videoFramesPlayed = " + this.mVideoFramesPlayed + ", videoFramesDropped = " + this.mVideoFramesDropped + ", audioUnderrunCount = " + this.mAudioUnderrunCount + ", networkBytesRead = " + this.mNetworkBytesRead + ", localBytesRead = " + this.mLocalBytesRead + ", networkTransferDurationMillis = " + this.mNetworkTransferDurationMillis + "drmSessionId = " + Arrays.toString(this.mDrmSessionId) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PlaybackMetrics playbackMetrics = (PlaybackMetrics) obj;
            if (this.mMediaDurationMillis == playbackMetrics.mMediaDurationMillis && this.mStreamSource == playbackMetrics.mStreamSource && this.mStreamType == playbackMetrics.mStreamType && this.mPlaybackType == playbackMetrics.mPlaybackType && this.mDrmType == playbackMetrics.mDrmType && this.mContentType == playbackMetrics.mContentType && Objects.equals(this.mPlayerName, playbackMetrics.mPlayerName) && Objects.equals(this.mPlayerVersion, playbackMetrics.mPlayerVersion) && Arrays.equals(this.mExperimentIds, playbackMetrics.mExperimentIds) && this.mVideoFramesPlayed == playbackMetrics.mVideoFramesPlayed && this.mVideoFramesDropped == playbackMetrics.mVideoFramesDropped && this.mAudioUnderrunCount == playbackMetrics.mAudioUnderrunCount && this.mNetworkBytesRead == playbackMetrics.mNetworkBytesRead && this.mLocalBytesRead == playbackMetrics.mLocalBytesRead && this.mNetworkTransferDurationMillis == playbackMetrics.mNetworkTransferDurationMillis && Arrays.equals(this.mDrmSessionId, playbackMetrics.mDrmSessionId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mMediaDurationMillis), Integer.valueOf(this.mStreamSource), Integer.valueOf(this.mStreamType), Integer.valueOf(this.mPlaybackType), Integer.valueOf(this.mDrmType), Integer.valueOf(this.mContentType), this.mPlayerName, this.mPlayerVersion, Integer.valueOf(Arrays.hashCode(this.mExperimentIds)), Integer.valueOf(this.mVideoFramesPlayed), Integer.valueOf(this.mVideoFramesDropped), Integer.valueOf(this.mAudioUnderrunCount), Long.valueOf(this.mNetworkBytesRead), Long.valueOf(this.mLocalBytesRead), Long.valueOf(this.mNetworkTransferDurationMillis), Integer.valueOf(Arrays.hashCode(this.mDrmSessionId)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        long j = this.mPlayerName != null ? 128L : 0L;
        if (this.mPlayerVersion != null) {
            j |= 256;
        }
        parcel.writeLong(j);
        parcel.writeLong(this.mMediaDurationMillis);
        parcel.writeInt(this.mStreamSource);
        parcel.writeInt(this.mStreamType);
        parcel.writeInt(this.mPlaybackType);
        parcel.writeInt(this.mDrmType);
        parcel.writeInt(this.mContentType);
        String str = this.mPlayerName;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mPlayerVersion;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeLongArray(this.mExperimentIds);
        parcel.writeInt(this.mVideoFramesPlayed);
        parcel.writeInt(this.mVideoFramesDropped);
        parcel.writeInt(this.mAudioUnderrunCount);
        parcel.writeLong(this.mNetworkBytesRead);
        parcel.writeLong(this.mLocalBytesRead);
        parcel.writeLong(this.mNetworkTransferDurationMillis);
        parcel.writeInt(this.mDrmSessionId.length);
        parcel.writeByteArray(this.mDrmSessionId);
        parcel.writeBundle(this.mMetricsBundle);
    }

    PlaybackMetrics(Parcel parcel) {
        long j = parcel.readLong();
        long j2 = parcel.readLong();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        int i4 = parcel.readInt();
        int i5 = parcel.readInt();
        String string = (128 & j) == 0 ? null : parcel.readString();
        String string2 = (j & 256) == 0 ? null : parcel.readString();
        long[] jArrCreateLongArray = parcel.createLongArray();
        int i6 = parcel.readInt();
        int i7 = parcel.readInt();
        int i8 = parcel.readInt();
        long j3 = parcel.readLong();
        long j4 = parcel.readLong();
        long j5 = parcel.readLong();
        byte[] bArr = new byte[parcel.readInt()];
        parcel.readByteArray(bArr);
        Bundle bundle = parcel.readBundle();
        this.mMediaDurationMillis = j2;
        this.mStreamSource = i;
        this.mStreamType = i2;
        this.mPlaybackType = i3;
        this.mDrmType = i4;
        this.mContentType = i5;
        this.mPlayerName = string;
        this.mPlayerVersion = string2;
        this.mExperimentIds = jArrCreateLongArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) jArrCreateLongArray);
        this.mVideoFramesPlayed = i6;
        this.mVideoFramesDropped = i7;
        this.mAudioUnderrunCount = i8;
        this.mNetworkBytesRead = j3;
        this.mLocalBytesRead = j4;
        this.mNetworkTransferDurationMillis = j5;
        this.mDrmSessionId = bArr;
        this.mMetricsBundle = bundle;
    }

    public static final class Builder {
        private String mPlayerName;
        private String mPlayerVersion;
        private long mMediaDurationMillis = -1;
        private int mStreamSource = 0;
        private int mStreamType = 0;
        private int mPlaybackType = 0;
        private int mDrmType = 0;
        private int mContentType = 0;
        private List<Long> mExperimentIds = new ArrayList();
        private int mVideoFramesPlayed = -1;
        private int mVideoFramesDropped = -1;
        private int mAudioUnderrunCount = -1;
        private long mNetworkBytesRead = -1;
        private long mLocalBytesRead = -1;
        private long mNetworkTransferDurationMillis = -1;
        private byte[] mDrmSessionId = new byte[0];
        private Bundle mMetricsBundle = new Bundle();

        public Builder setMediaDurationMillis(long j) {
            this.mMediaDurationMillis = j;
            return this;
        }

        public Builder setStreamSource(int i) {
            this.mStreamSource = i;
            return this;
        }

        public Builder setStreamType(int i) {
            this.mStreamType = i;
            return this;
        }

        public Builder setPlaybackType(int i) {
            this.mPlaybackType = i;
            return this;
        }

        public Builder setDrmType(int i) {
            this.mDrmType = i;
            return this;
        }

        public Builder setContentType(int i) {
            this.mContentType = i;
            return this;
        }

        public Builder setPlayerName(String str) {
            this.mPlayerName = str;
            return this;
        }

        public Builder setPlayerVersion(String str) {
            this.mPlayerVersion = str;
            return this;
        }

        public Builder addExperimentId(long j) {
            this.mExperimentIds.add(Long.valueOf(j));
            return this;
        }

        public Builder setVideoFramesPlayed(int i) {
            this.mVideoFramesPlayed = i;
            return this;
        }

        public Builder setVideoFramesDropped(int i) {
            this.mVideoFramesDropped = i;
            return this;
        }

        public Builder setAudioUnderrunCount(int i) {
            this.mAudioUnderrunCount = i;
            return this;
        }

        public Builder setNetworkBytesRead(long j) {
            this.mNetworkBytesRead = j;
            return this;
        }

        public Builder setLocalBytesRead(long j) {
            this.mLocalBytesRead = j;
            return this;
        }

        public Builder setNetworkTransferDurationMillis(long j) {
            this.mNetworkTransferDurationMillis = j;
            return this;
        }

        public Builder setDrmSessionId(byte[] bArr) {
            this.mDrmSessionId = bArr;
            return this;
        }

        public Builder setMetricsBundle(Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public PlaybackMetrics build() {
            return new PlaybackMetrics(this.mMediaDurationMillis, this.mStreamSource, this.mStreamType, this.mPlaybackType, this.mDrmType, this.mContentType, this.mPlayerName, this.mPlayerVersion, idsToLongArray(), this.mVideoFramesPlayed, this.mVideoFramesDropped, this.mAudioUnderrunCount, this.mNetworkBytesRead, this.mLocalBytesRead, this.mNetworkTransferDurationMillis, this.mDrmSessionId, this.mMetricsBundle);
        }

        private long[] idsToLongArray() {
            long[] jArr = new long[this.mExperimentIds.size()];
            for (int i = 0; i < this.mExperimentIds.size(); i++) {
                jArr[i] = this.mExperimentIds.get(i).longValue();
            }
            return jArr;
        }
    }
}
