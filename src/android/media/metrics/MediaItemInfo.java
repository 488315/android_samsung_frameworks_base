package android.media.metrics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaItemInfo implements Parcelable {
    public static final Parcelable.Creator<MediaItemInfo> CREATOR = new Parcelable.Creator<MediaItemInfo>() { // from class: android.media.metrics.MediaItemInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaItemInfo[] newArray(int i) {
            return new MediaItemInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaItemInfo createFromParcel(Parcel parcel) {
            return new MediaItemInfo(parcel);
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
    public @interface DataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SourceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MediaItemInfo(int i, long j, long j2, long j3, String str, List<String> list, List<String> list2, int i2, int i3, long j4, Size size, int i4, float f, long j5) {
        this.mSourceType = i;
        this.mDataTypes = j;
        this.mDurationMillis = j2;
        this.mClipDurationMillis = j3;
        this.mContainerMimeType = str;
        this.mSampleMimeTypes = list;
        this.mCodecNames = list2;
        this.mAudioSampleRateHz = i2;
        this.mAudioChannelCount = i3;
        this.mAudioSampleCount = j4;
        this.mVideoSize = size;
        this.mVideoDataSpace = i4;
        this.mVideoFrameRate = f;
        this.mVideoSampleCount = j5;
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

        public Builder setSourceType(int i) {
            this.mSourceType = i;
            return this;
        }

        public Builder addDataType(long j) {
            this.mDataTypes = j | this.mDataTypes;
            return this;
        }

        public Builder setDurationMillis(long j) {
            this.mDurationMillis = j;
            return this;
        }

        public Builder setClipDurationMillis(long j) {
            this.mClipDurationMillis = j;
            return this;
        }

        public Builder setContainerMimeType(String str) {
            this.mContainerMimeType = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder addSampleMimeType(String str) {
            this.mSampleMimeTypes.add((String) Objects.requireNonNull(str));
            return this;
        }

        public Builder addCodecName(String str) {
            this.mCodecNames.add((String) Objects.requireNonNull(str));
            return this;
        }

        public Builder setAudioSampleRateHz(int i) {
            this.mAudioSampleRateHz = i;
            return this;
        }

        public Builder setAudioChannelCount(int i) {
            this.mAudioChannelCount = i;
            return this;
        }

        public Builder setAudioSampleCount(long j) {
            this.mAudioSampleCount = j;
            return this;
        }

        public Builder setVideoSize(Size size) {
            this.mVideoSize = (Size) Objects.requireNonNull(size);
            return this;
        }

        public Builder setVideoDataSpace(int i) {
            this.mVideoDataSpace = i;
            return this;
        }

        public Builder setVideoFrameRate(float f) {
            this.mVideoFrameRate = f;
            return this;
        }

        public Builder setVideoSampleCount(long j) {
            this.mVideoSampleCount = j;
            return this;
        }

        public MediaItemInfo build() {
            return new MediaItemInfo(this.mSourceType, this.mDataTypes, this.mDurationMillis, this.mClipDurationMillis, this.mContainerMimeType, this.mSampleMimeTypes, this.mCodecNames, this.mAudioSampleRateHz, this.mAudioChannelCount, this.mAudioSampleCount, this.mVideoSize, this.mVideoDataSpace, this.mVideoFrameRate, this.mVideoSampleCount);
        }
    }

    public String toString() {
        return "MediaItemInfo { sourceType = " + this.mSourceType + ", dataTypes = " + this.mDataTypes + ", durationMillis = " + this.mDurationMillis + ", clipDurationMillis = " + this.mClipDurationMillis + ", containerMimeType = " + this.mContainerMimeType + ", sampleMimeTypes = " + this.mSampleMimeTypes + ", codecNames = " + this.mCodecNames + ", audioSampleRateHz = " + this.mAudioSampleRateHz + ", audioChannelCount = " + this.mAudioChannelCount + ", audioSampleCount = " + this.mAudioSampleCount + ", videoSize = " + this.mVideoSize + ", videoDataSpace = " + this.mVideoDataSpace + ", videoFrameRate = " + this.mVideoFrameRate + ", videoSampleCount = " + this.mVideoSampleCount + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MediaItemInfo mediaItemInfo = (MediaItemInfo) obj;
            if (this.mSourceType == mediaItemInfo.mSourceType && this.mDataTypes == mediaItemInfo.mDataTypes && this.mDurationMillis == mediaItemInfo.mDurationMillis && this.mClipDurationMillis == mediaItemInfo.mClipDurationMillis && Objects.equals(this.mContainerMimeType, mediaItemInfo.mContainerMimeType) && this.mSampleMimeTypes.equals(mediaItemInfo.mSampleMimeTypes) && this.mCodecNames.equals(mediaItemInfo.mCodecNames) && this.mAudioSampleRateHz == mediaItemInfo.mAudioSampleRateHz && this.mAudioChannelCount == mediaItemInfo.mAudioChannelCount && this.mAudioSampleCount == mediaItemInfo.mAudioSampleCount && Objects.equals(this.mVideoSize, mediaItemInfo.mVideoSize) && Objects.equals(Integer.valueOf(this.mVideoDataSpace), Integer.valueOf(mediaItemInfo.mVideoDataSpace)) && this.mVideoFrameRate == mediaItemInfo.mVideoFrameRate && this.mVideoSampleCount == mediaItemInfo.mVideoSampleCount) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSourceType), Long.valueOf(this.mDataTypes));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSourceType);
        parcel.writeLong(this.mDataTypes);
        parcel.writeLong(this.mDurationMillis);
        parcel.writeLong(this.mClipDurationMillis);
        parcel.writeString(this.mContainerMimeType);
        parcel.writeStringList(this.mSampleMimeTypes);
        parcel.writeStringList(this.mCodecNames);
        parcel.writeInt(this.mAudioSampleRateHz);
        parcel.writeInt(this.mAudioChannelCount);
        parcel.writeLong(this.mAudioSampleCount);
        parcel.writeInt(this.mVideoSize.getWidth());
        parcel.writeInt(this.mVideoSize.getHeight());
        parcel.writeInt(this.mVideoDataSpace);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeLong(this.mVideoSampleCount);
    }

    private MediaItemInfo(Parcel parcel) {
        this.mSourceType = parcel.readInt();
        this.mDataTypes = parcel.readLong();
        this.mDurationMillis = parcel.readLong();
        this.mClipDurationMillis = parcel.readLong();
        this.mContainerMimeType = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mSampleMimeTypes = arrayList;
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        this.mCodecNames = arrayList2;
        parcel.readStringList(arrayList2);
        this.mAudioSampleRateHz = parcel.readInt();
        this.mAudioChannelCount = parcel.readInt();
        this.mAudioSampleCount = parcel.readLong();
        this.mVideoSize = new Size(parcel.readInt(), parcel.readInt());
        this.mVideoDataSpace = parcel.readInt();
        this.mVideoFrameRate = parcel.readFloat();
        this.mVideoSampleCount = parcel.readLong();
    }
}
