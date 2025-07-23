package android.media.metrics;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TrackChangeEvent extends Event implements Parcelable {
    public static final Parcelable.Creator<TrackChangeEvent> CREATOR = new Parcelable.Creator<TrackChangeEvent>() { // from class: android.media.metrics.TrackChangeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackChangeEvent[] newArray(int i) {
            return new TrackChangeEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackChangeEvent createFromParcel(Parcel parcel) {
            return new TrackChangeEvent(parcel);
        }
    };
    public static final int TRACK_CHANGE_REASON_ADAPTIVE = 4;
    public static final int TRACK_CHANGE_REASON_INITIAL = 2;
    public static final int TRACK_CHANGE_REASON_MANUAL = 3;
    public static final int TRACK_CHANGE_REASON_OTHER = 1;
    public static final int TRACK_CHANGE_REASON_UNKNOWN = 0;
    public static final int TRACK_STATE_OFF = 0;
    public static final int TRACK_STATE_ON = 1;
    public static final int TRACK_TYPE_AUDIO = 0;
    public static final int TRACK_TYPE_TEXT = 2;
    public static final int TRACK_TYPE_VIDEO = 1;
    private final int mAudioSampleRate;
    private final int mBitrate;
    private final int mChannelCount;
    private final String mCodecName;
    private final String mContainerMimeType;
    private final int mHeight;
    private final String mLanguage;
    private final String mLanguageRegion;
    private final int mReason;
    private final String mSampleMimeType;
    private final int mState;
    private final long mTimeSinceCreatedMillis;
    private final int mType;
    private final float mVideoFrameRate;
    private final int mWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TrackChangeReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TrackState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TrackType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TrackChangeEvent(int i, int i2, String str, String str2, String str3, int i3, long j, int i4, String str4, String str5, int i5, int i6, int i7, int i8, float f, Bundle bundle) {
        this.mState = i;
        this.mReason = i2;
        this.mContainerMimeType = str;
        this.mSampleMimeType = str2;
        this.mCodecName = str3;
        this.mBitrate = i3;
        this.mTimeSinceCreatedMillis = j;
        this.mType = i4;
        this.mLanguage = str4;
        this.mLanguageRegion = str5;
        this.mChannelCount = i5;
        this.mAudioSampleRate = i6;
        this.mWidth = i7;
        this.mHeight = i8;
        this.mVideoFrameRate = f;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public int getTrackState() {
        return this.mState;
    }

    public int getTrackChangeReason() {
        return this.mReason;
    }

    public String getContainerMimeType() {
        return this.mContainerMimeType;
    }

    public String getSampleMimeType() {
        return this.mSampleMimeType;
    }

    public String getCodecName() {
        return this.mCodecName;
    }

    public int getBitrate() {
        return this.mBitrate;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public int getTrackType() {
        return this.mType;
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public String getLanguageRegion() {
        return this.mLanguageRegion;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getAudioSampleRate() {
        return this.mAudioSampleRate;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public float getVideoFrameRate() {
        return this.mVideoFrameRate;
    }

    @Override // android.media.metrics.Event
    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.mContainerMimeType != null ? 4 : 0;
        if (this.mSampleMimeType != null) {
            i2 |= 8;
        }
        if (this.mCodecName != null) {
            i2 |= 16;
        }
        if (this.mLanguage != null) {
            i2 |= 256;
        }
        if (this.mLanguageRegion != null) {
            i2 |= 512;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mReason);
        String str = this.mContainerMimeType;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mSampleMimeType;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        String str3 = this.mCodecName;
        if (str3 != null) {
            parcel.writeString(str3);
        }
        parcel.writeInt(this.mBitrate);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeInt(this.mType);
        String str4 = this.mLanguage;
        if (str4 != null) {
            parcel.writeString(str4);
        }
        String str5 = this.mLanguageRegion;
        if (str5 != null) {
            parcel.writeString(str5);
        }
        parcel.writeInt(this.mChannelCount);
        parcel.writeInt(this.mAudioSampleRate);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeBundle(this.mMetricsBundle);
    }

    private TrackChangeEvent(Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        String readString = (readInt & 4) == 0 ? null : parcel.readString();
        String readString2 = (readInt & 8) == 0 ? null : parcel.readString();
        String readString3 = (readInt & 16) == 0 ? null : parcel.readString();
        int readInt4 = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt5 = parcel.readInt();
        String readString4 = (readInt & 256) == 0 ? null : parcel.readString();
        String readString5 = (readInt & 512) != 0 ? parcel.readString() : null;
        int readInt6 = parcel.readInt();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        int readInt9 = parcel.readInt();
        float readFloat = parcel.readFloat();
        Bundle readBundle = parcel.readBundle();
        this.mState = readInt2;
        this.mReason = readInt3;
        this.mContainerMimeType = readString;
        this.mSampleMimeType = readString2;
        this.mCodecName = readString3;
        this.mBitrate = readInt4;
        this.mTimeSinceCreatedMillis = readLong;
        this.mType = readInt5;
        this.mLanguage = readString4;
        this.mLanguageRegion = readString5;
        this.mChannelCount = readInt6;
        this.mAudioSampleRate = readInt7;
        this.mWidth = readInt8;
        this.mHeight = readInt9;
        this.mVideoFrameRate = readFloat;
        this.mMetricsBundle = readBundle;
    }

    public String toString() {
        return "TrackChangeEvent { state = " + this.mState + ", reason = " + this.mReason + ", containerMimeType = " + this.mContainerMimeType + ", sampleMimeType = " + this.mSampleMimeType + ", codecName = " + this.mCodecName + ", bitrate = " + this.mBitrate + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + ", type = " + this.mType + ", language = " + this.mLanguage + ", languageRegion = " + this.mLanguageRegion + ", channelCount = " + this.mChannelCount + ", sampleRate = " + this.mAudioSampleRate + ", width = " + this.mWidth + ", height = " + this.mHeight + ", videoFrameRate = " + this.mVideoFrameRate + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TrackChangeEvent trackChangeEvent = (TrackChangeEvent) obj;
            if (this.mState == trackChangeEvent.mState && this.mReason == trackChangeEvent.mReason && Objects.equals(this.mContainerMimeType, trackChangeEvent.mContainerMimeType) && Objects.equals(this.mSampleMimeType, trackChangeEvent.mSampleMimeType) && Objects.equals(this.mCodecName, trackChangeEvent.mCodecName) && this.mBitrate == trackChangeEvent.mBitrate && this.mTimeSinceCreatedMillis == trackChangeEvent.mTimeSinceCreatedMillis && this.mType == trackChangeEvent.mType && Objects.equals(this.mLanguage, trackChangeEvent.mLanguage) && Objects.equals(this.mLanguageRegion, trackChangeEvent.mLanguageRegion) && this.mChannelCount == trackChangeEvent.mChannelCount && this.mAudioSampleRate == trackChangeEvent.mAudioSampleRate && this.mWidth == trackChangeEvent.mWidth && this.mHeight == trackChangeEvent.mHeight && this.mVideoFrameRate == trackChangeEvent.mVideoFrameRate) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mState), Integer.valueOf(this.mReason), this.mContainerMimeType, this.mSampleMimeType, this.mCodecName, Integer.valueOf(this.mBitrate), Long.valueOf(this.mTimeSinceCreatedMillis), Integer.valueOf(this.mType), this.mLanguage, this.mLanguageRegion, Integer.valueOf(this.mChannelCount), Integer.valueOf(this.mAudioSampleRate), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Float.valueOf(this.mVideoFrameRate));
    }

    public static final class Builder {
        private String mCodecName;
        private String mContainerMimeType;
        private String mLanguage;
        private String mLanguageRegion;
        private String mSampleMimeType;
        private final int mType;
        private int mState = 0;
        private int mReason = 0;
        private int mBitrate = -1;
        private long mTimeSinceCreatedMillis = -1;
        private int mChannelCount = -1;
        private int mAudioSampleRate = -1;
        private int mWidth = -1;
        private int mHeight = -1;
        private float mVideoFrameRate = -1.0f;
        private Bundle mMetricsBundle = new Bundle();
        private long mBuilderFieldsSet = 0;

        public Builder(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("track type must be one of TRACK_TYPE_AUDIO, TRACK_TYPE_VIDEO, TRACK_TYPE_TEXT.");
            }
            this.mType = i;
        }

        public Builder setTrackState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mState = i;
            return this;
        }

        public Builder setTrackChangeReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mReason = i;
            return this;
        }

        public Builder setContainerMimeType(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mContainerMimeType = str;
            return this;
        }

        public Builder setSampleMimeType(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mSampleMimeType = str;
            return this;
        }

        public Builder setCodecName(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mCodecName = str;
            return this;
        }

        public Builder setBitrate(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mBitrate = i;
            return this;
        }

        public Builder setTimeSinceCreatedMillis(long j) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public Builder setLanguage(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            this.mLanguage = str;
            return this;
        }

        public Builder setLanguageRegion(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 512;
            this.mLanguageRegion = str;
            return this;
        }

        public Builder setChannelCount(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1024;
            this.mChannelCount = i;
            return this;
        }

        public Builder setAudioSampleRate(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2048;
            this.mAudioSampleRate = i;
            return this;
        }

        public Builder setWidth(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4096;
            this.mWidth = i;
            return this;
        }

        public Builder setHeight(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8192;
            this.mHeight = i;
            return this;
        }

        public Builder setVideoFrameRate(float f) {
            checkNotUsed();
            this.mVideoFrameRate = f;
            return this;
        }

        public Builder setMetricsBundle(Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public TrackChangeEvent build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16384;
            return new TrackChangeEvent(this.mState, this.mReason, this.mContainerMimeType, this.mSampleMimeType, this.mCodecName, this.mBitrate, this.mTimeSinceCreatedMillis, this.mType, this.mLanguage, this.mLanguageRegion, this.mChannelCount, this.mAudioSampleRate, this.mWidth, this.mHeight, this.mVideoFrameRate, this.mMetricsBundle);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16384) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
