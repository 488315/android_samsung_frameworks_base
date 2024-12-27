package android.media.metrics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaItemInfo implements Parcelable {
    public static final Parcelable.Creator<MediaItemInfo> CREATOR =
            new Parcelable.Creator<
                    MediaItemInfo>() { // from class: android.media.metrics.MediaItemInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaItemInfo[] newArray(int size) {
                    return new MediaItemInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaItemInfo createFromParcel(Parcel in) {
                    return new MediaItemInfo(in);
                }
            };
    public static final long DATA_TYPE_AUDIO = 4;
    public static final long DATA_TYPE_DEPTH = 16;
    public static final long DATA_TYPE_GAIN_MAP = 32;
    public static final long DATA_TYPE_GAPLESS = 256;
    public static final long DATA_TYPE_HIGH_DYNAMIC_RANGE_VIDEO = 1024;
    public static final long DATA_TYPE_HIGH_FRAME_RATE = 64;
    public static final long DATA_TYPE_IMAGE = 1;
    public static final long DATA_TYPE_METADATA = 8;
    public static final long DATA_TYPE_SPATIAL_AUDIO = 512;
    public static final long DATA_TYPE_SPEED_SETTING_CUE_POINTS = 128;
    public static final long DATA_TYPE_VIDEO = 2;
    public static final int SOURCE_TYPE_CAMERA = 2;
    public static final int SOURCE_TYPE_EDITING_SESSION = 3;
    public static final int SOURCE_TYPE_GALLERY = 1;
    public static final int SOURCE_TYPE_GENERATED = 7;
    public static final int SOURCE_TYPE_LOCAL_FILE = 4;
    public static final int SOURCE_TYPE_REMOTE_FILE = 5;
    public static final int SOURCE_TYPE_REMOTE_LIVE_STREAM = 6;
    public static final int SOURCE_TYPE_UNSPECIFIED = 0;
    public static final int VALUE_UNSPECIFIED = -1;
    private final int mAudioChannelCount;
    private final long mAudioSampleCount;
    private final int mAudioSampleRateHz;
    private final long mClipDurationMillis;
    private final List<String> mCodecNames;
    private final String mContainerMimeType;
    private final long mDataTypes;
    private final long mDurationMillis;
    private final List<String> mSampleMimeTypes;
    private final int mSourceType;
    private final int mVideoDataSpace;
    private final float mVideoFrameRate;
    private final long mVideoSampleCount;
    private final Size mVideoSize;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SourceType {}

    private MediaItemInfo(
            int sourceType,
            long dataTypes,
            long durationMillis,
            long clipDurationMillis,
            String containerMimeType,
            List<String> sampleMimeTypes,
            List<String> codecNames,
            int audioSampleRateHz,
            int audioChannelCount,
            long audioSampleCount,
            Size videoSize,
            int videoDataSpace,
            float videoFrameRate,
            long videoSampleCount) {
        this.mSourceType = sourceType;
        this.mDataTypes = dataTypes;
        this.mDurationMillis = durationMillis;
        this.mClipDurationMillis = clipDurationMillis;
        this.mContainerMimeType = containerMimeType;
        this.mSampleMimeTypes = sampleMimeTypes;
        this.mCodecNames = codecNames;
        this.mAudioSampleRateHz = audioSampleRateHz;
        this.mAudioChannelCount = audioChannelCount;
        this.mAudioSampleCount = audioSampleCount;
        this.mVideoSize = videoSize;
        this.mVideoDataSpace = videoDataSpace;
        this.mVideoFrameRate = videoFrameRate;
        this.mVideoSampleCount = videoSampleCount;
    }

    public int getSourceType() {
        return this.mSourceType;
    }

    public long getDataTypes() {
        return this.mDataTypes;
    }

    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    public long getClipDurationMillis() {
        return this.mClipDurationMillis;
    }

    public String getContainerMimeType() {
        return this.mContainerMimeType;
    }

    public List<String> getSampleMimeTypes() {
        return new ArrayList(this.mSampleMimeTypes);
    }

    public List<String> getCodecNames() {
        return new ArrayList(this.mCodecNames);
    }

    public int getAudioSampleRateHz() {
        return this.mAudioSampleRateHz;
    }

    public int getAudioChannelCount() {
        return this.mAudioChannelCount;
    }

    public long getAudioSampleCount() {
        return this.mAudioSampleCount;
    }

    public Size getVideoSize() {
        return this.mVideoSize;
    }

    public int getVideoDataSpace() {
        return this.mVideoDataSpace;
    }

    public float getVideoFrameRate() {
        return this.mVideoFrameRate;
    }

    public long getVideoSampleCount() {
        return this.mVideoSampleCount;
    }

    public static final class Builder {
        private String mContainerMimeType;
        private long mDataTypes;
        private int mVideoDataSpace;
        private int mSourceType = 0;
        private long mDurationMillis = -1;
        private long mClipDurationMillis = -1;
        private final ArrayList<String> mSampleMimeTypes = new ArrayList<>();
        private final ArrayList<String> mCodecNames = new ArrayList<>();
        private int mAudioSampleRateHz = -1;
        private int mAudioChannelCount = -1;
        private long mAudioSampleCount = -1;
        private Size mVideoSize = new Size(-1, -1);
        private float mVideoFrameRate = -1.0f;
        private long mVideoSampleCount = -1;

        public Builder setSourceType(int sourceType) {
            this.mSourceType = sourceType;
            return this;
        }

        public Builder addDataType(long dataType) {
            this.mDataTypes |= dataType;
            return this;
        }

        public Builder setDurationMillis(long durationMillis) {
            this.mDurationMillis = durationMillis;
            return this;
        }

        public Builder setClipDurationMillis(long clipDurationMillis) {
            this.mClipDurationMillis = clipDurationMillis;
            return this;
        }

        public Builder setContainerMimeType(String containerMimeType) {
            this.mContainerMimeType = (String) Objects.requireNonNull(containerMimeType);
            return this;
        }

        public Builder addSampleMimeType(String mimeType) {
            this.mSampleMimeTypes.add((String) Objects.requireNonNull(mimeType));
            return this;
        }

        public Builder addCodecName(String codecName) {
            this.mCodecNames.add((String) Objects.requireNonNull(codecName));
            return this;
        }

        public Builder setAudioSampleRateHz(int audioSampleRateHz) {
            this.mAudioSampleRateHz = audioSampleRateHz;
            return this;
        }

        public Builder setAudioChannelCount(int audioChannelCount) {
            this.mAudioChannelCount = audioChannelCount;
            return this;
        }

        public Builder setAudioSampleCount(long audioSampleCount) {
            this.mAudioSampleCount = audioSampleCount;
            return this;
        }

        public Builder setVideoSize(Size videoSize) {
            this.mVideoSize = (Size) Objects.requireNonNull(videoSize);
            return this;
        }

        public Builder setVideoDataSpace(int videoDataSpace) {
            this.mVideoDataSpace = videoDataSpace;
            return this;
        }

        public Builder setVideoFrameRate(float videoFrameRate) {
            this.mVideoFrameRate = videoFrameRate;
            return this;
        }

        public Builder setVideoSampleCount(long videoSampleCount) {
            this.mVideoSampleCount = videoSampleCount;
            return this;
        }

        public MediaItemInfo build() {
            return new MediaItemInfo(
                    this.mSourceType,
                    this.mDataTypes,
                    this.mDurationMillis,
                    this.mClipDurationMillis,
                    this.mContainerMimeType,
                    this.mSampleMimeTypes,
                    this.mCodecNames,
                    this.mAudioSampleRateHz,
                    this.mAudioChannelCount,
                    this.mAudioSampleCount,
                    this.mVideoSize,
                    this.mVideoDataSpace,
                    this.mVideoFrameRate,
                    this.mVideoSampleCount);
        }
    }

    public String toString() {
        return "MediaItemInfo { sourceType = "
                + this.mSourceType
                + ", dataTypes = "
                + this.mDataTypes
                + ", durationMillis = "
                + this.mDurationMillis
                + ", clipDurationMillis = "
                + this.mClipDurationMillis
                + ", containerMimeType = "
                + this.mContainerMimeType
                + ", sampleMimeTypes = "
                + this.mSampleMimeTypes
                + ", codecNames = "
                + this.mCodecNames
                + ", audioSampleRateHz = "
                + this.mAudioSampleRateHz
                + ", audioChannelCount = "
                + this.mAudioChannelCount
                + ", audioSampleCount = "
                + this.mAudioSampleCount
                + ", videoSize = "
                + this.mVideoSize
                + ", videoDataSpace = "
                + this.mVideoDataSpace
                + ", videoFrameRate = "
                + this.mVideoFrameRate
                + ", videoSampleCount = "
                + this.mVideoSampleCount
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaItemInfo that = (MediaItemInfo) o;
        if (this.mSourceType == that.mSourceType
                && this.mDataTypes == that.mDataTypes
                && this.mDurationMillis == that.mDurationMillis
                && this.mClipDurationMillis == that.mClipDurationMillis
                && Objects.equals(this.mContainerMimeType, that.mContainerMimeType)
                && this.mSampleMimeTypes.equals(that.mSampleMimeTypes)
                && this.mCodecNames.equals(that.mCodecNames)
                && this.mAudioSampleRateHz == that.mAudioSampleRateHz
                && this.mAudioChannelCount == that.mAudioChannelCount
                && this.mAudioSampleCount == that.mAudioSampleCount
                && Objects.equals(this.mVideoSize, that.mVideoSize)
                && Objects.equals(
                        Integer.valueOf(this.mVideoDataSpace),
                        Integer.valueOf(that.mVideoDataSpace))
                && this.mVideoFrameRate == that.mVideoFrameRate
                && this.mVideoSampleCount == that.mVideoSampleCount) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSourceType), Long.valueOf(this.mDataTypes));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mSourceType);
        dest.writeLong(this.mDataTypes);
        dest.writeLong(this.mDurationMillis);
        dest.writeLong(this.mClipDurationMillis);
        dest.writeString(this.mContainerMimeType);
        dest.writeStringList(this.mSampleMimeTypes);
        dest.writeStringList(this.mCodecNames);
        dest.writeInt(this.mAudioSampleRateHz);
        dest.writeInt(this.mAudioChannelCount);
        dest.writeLong(this.mAudioSampleCount);
        dest.writeInt(this.mVideoSize.getWidth());
        dest.writeInt(this.mVideoSize.getHeight());
        dest.writeInt(this.mVideoDataSpace);
        dest.writeFloat(this.mVideoFrameRate);
        dest.writeLong(this.mVideoSampleCount);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MediaItemInfo(Parcel in) {
        this.mSourceType = in.readInt();
        this.mDataTypes = in.readLong();
        this.mDurationMillis = in.readLong();
        this.mClipDurationMillis = in.readLong();
        this.mContainerMimeType = in.readString();
        this.mSampleMimeTypes = new ArrayList();
        in.readStringList(this.mSampleMimeTypes);
        this.mCodecNames = new ArrayList();
        in.readStringList(this.mCodecNames);
        this.mAudioSampleRateHz = in.readInt();
        this.mAudioChannelCount = in.readInt();
        this.mAudioSampleCount = in.readLong();
        int videoSizeWidth = in.readInt();
        int videoSizeHeight = in.readInt();
        this.mVideoSize = new Size(videoSizeWidth, videoSizeHeight);
        this.mVideoDataSpace = in.readInt();
        this.mVideoFrameRate = in.readFloat();
        this.mVideoSampleCount = in.readLong();
    }
}
