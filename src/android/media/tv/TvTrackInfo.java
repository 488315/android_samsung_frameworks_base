package android.media.tv;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TvTrackInfo implements Parcelable {
    public static final Parcelable.Creator<TvTrackInfo> CREATOR = new Parcelable.Creator<TvTrackInfo>() { // from class: android.media.tv.TvTrackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvTrackInfo createFromParcel(Parcel parcel) {
            return new TvTrackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvTrackInfo[] newArray(int i) {
            return new TvTrackInfo[i];
        }
    };
    public static final String EXTRA_BUNDLE_KEY_COMPONENT_TAG = "component_tag";
    public static final String EXTRA_BUNDLE_KEY_PID = "pid";
    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_VIDEO = 1;
    private final int mAudioChannelCount;
    private final boolean mAudioDescription;
    private final int mAudioSampleRate;
    private final CharSequence mDescription;
    private final String mEncoding;
    private final boolean mEncrypted;
    private final Bundle mExtra;
    private final boolean mHardOfHearing;
    private final String mId;
    private final String mLanguage;
    private final boolean mSpokenSubtitle;
    private final int mType;
    private final byte mVideoActiveFormatDescription;
    private final float mVideoFrameRate;
    private final int mVideoHeight;
    private final float mVideoPixelAspectRatio;
    private final int mVideoWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TvTrackInfo(int i, String str, String str2, CharSequence charSequence, String str3, boolean z, int i2, int i3, boolean z2, boolean z3, boolean z4, int i4, int i5, float f, float f2, byte b, Bundle bundle) {
        this.mType = i;
        this.mId = str;
        this.mLanguage = str2;
        this.mDescription = charSequence;
        this.mEncoding = str3;
        this.mEncrypted = z;
        this.mAudioChannelCount = i2;
        this.mAudioSampleRate = i3;
        this.mAudioDescription = z2;
        this.mHardOfHearing = z3;
        this.mSpokenSubtitle = z4;
        this.mVideoWidth = i4;
        this.mVideoHeight = i5;
        this.mVideoFrameRate = f;
        this.mVideoPixelAspectRatio = f2;
        this.mVideoActiveFormatDescription = b;
        this.mExtra = bundle;
    }

    private TvTrackInfo(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readString();
        this.mLanguage = parcel.readString();
        this.mDescription = parcel.readString();
        this.mEncoding = parcel.readString();
        this.mEncrypted = parcel.readInt() != 0;
        this.mAudioChannelCount = parcel.readInt();
        this.mAudioSampleRate = parcel.readInt();
        this.mAudioDescription = parcel.readInt() != 0;
        this.mHardOfHearing = parcel.readInt() != 0;
        this.mSpokenSubtitle = parcel.readInt() != 0;
        this.mVideoWidth = parcel.readInt();
        this.mVideoHeight = parcel.readInt();
        this.mVideoFrameRate = parcel.readFloat();
        this.mVideoPixelAspectRatio = parcel.readFloat();
        this.mVideoActiveFormatDescription = parcel.readByte();
        this.mExtra = parcel.readBundle();
    }

    public final int getType() {
        return this.mType;
    }

    public final String getId() {
        return this.mId;
    }

    public final String getLanguage() {
        return this.mLanguage;
    }

    public final CharSequence getDescription() {
        return this.mDescription;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public boolean isEncrypted() {
        return this.mEncrypted;
    }

    public final int getAudioChannelCount() {
        if (this.mType != 0) {
            throw new IllegalStateException("Not an audio track");
        }
        return this.mAudioChannelCount;
    }

    public final int getAudioSampleRate() {
        if (this.mType != 0) {
            throw new IllegalStateException("Not an audio track");
        }
        return this.mAudioSampleRate;
    }

    public boolean isAudioDescription() {
        if (this.mType != 0) {
            throw new IllegalStateException("Not an audio track");
        }
        return this.mAudioDescription;
    }

    public boolean isHardOfHearing() {
        int i = this.mType;
        if (i != 0 && i != 2) {
            throw new IllegalStateException("Not an audio or a subtitle track");
        }
        return this.mHardOfHearing;
    }

    public boolean isSpokenSubtitle() {
        if (this.mType != 0) {
            throw new IllegalStateException("Not an audio track");
        }
        return this.mSpokenSubtitle;
    }

    public final int getVideoWidth() {
        if (this.mType != 1) {
            throw new IllegalStateException("Not a video track");
        }
        return this.mVideoWidth;
    }

    public final int getVideoHeight() {
        if (this.mType != 1) {
            throw new IllegalStateException("Not a video track");
        }
        return this.mVideoHeight;
    }

    public final float getVideoFrameRate() {
        if (this.mType != 1) {
            throw new IllegalStateException("Not a video track");
        }
        return this.mVideoFrameRate;
    }

    public final float getVideoPixelAspectRatio() {
        if (this.mType != 1) {
            throw new IllegalStateException("Not a video track");
        }
        return this.mVideoPixelAspectRatio;
    }

    public final byte getVideoActiveFormatDescription() {
        if (this.mType != 1) {
            throw new IllegalStateException("Not a video track");
        }
        return this.mVideoActiveFormatDescription;
    }

    public final Bundle getExtra() {
        return this.mExtra;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Preconditions.checkNotNull(parcel);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mId);
        parcel.writeString(this.mLanguage);
        CharSequence charSequence = this.mDescription;
        parcel.writeString(charSequence != null ? charSequence.toString() : null);
        parcel.writeString(this.mEncoding);
        parcel.writeInt(this.mEncrypted ? 1 : 0);
        parcel.writeInt(this.mAudioChannelCount);
        parcel.writeInt(this.mAudioSampleRate);
        parcel.writeInt(this.mAudioDescription ? 1 : 0);
        parcel.writeInt(this.mHardOfHearing ? 1 : 0);
        parcel.writeInt(this.mSpokenSubtitle ? 1 : 0);
        parcel.writeInt(this.mVideoWidth);
        parcel.writeInt(this.mVideoHeight);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeFloat(this.mVideoPixelAspectRatio);
        parcel.writeByte(this.mVideoActiveFormatDescription);
        parcel.writeBundle(this.mExtra);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TvTrackInfo)) {
            return false;
        }
        TvTrackInfo tvTrackInfo = (TvTrackInfo) obj;
        if (TextUtils.equals(this.mId, tvTrackInfo.mId) && this.mType == tvTrackInfo.mType && TextUtils.equals(this.mLanguage, tvTrackInfo.mLanguage) && TextUtils.equals(this.mDescription, tvTrackInfo.mDescription) && TextUtils.equals(this.mEncoding, tvTrackInfo.mEncoding) && this.mEncrypted == tvTrackInfo.mEncrypted) {
            int i = this.mType;
            if (i != 0) {
                return i != 1 ? i != 2 || this.mHardOfHearing == tvTrackInfo.mHardOfHearing : this.mVideoWidth == tvTrackInfo.mVideoWidth && this.mVideoHeight == tvTrackInfo.mVideoHeight && this.mVideoFrameRate == tvTrackInfo.mVideoFrameRate && this.mVideoPixelAspectRatio == tvTrackInfo.mVideoPixelAspectRatio && this.mVideoActiveFormatDescription == tvTrackInfo.mVideoActiveFormatDescription;
            }
            if (this.mAudioChannelCount == tvTrackInfo.mAudioChannelCount && this.mAudioSampleRate == tvTrackInfo.mAudioSampleRate && this.mAudioDescription == tvTrackInfo.mAudioDescription && this.mHardOfHearing == tvTrackInfo.mHardOfHearing && this.mSpokenSubtitle == tvTrackInfo.mSpokenSubtitle) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = Objects.hash(this.mId, Integer.valueOf(this.mType), this.mLanguage, this.mDescription);
        int i = this.mType;
        if (i == 0) {
            return Objects.hash(Integer.valueOf(hash), Integer.valueOf(this.mAudioChannelCount), Integer.valueOf(this.mAudioSampleRate));
        }
        return i == 1 ? Objects.hash(Integer.valueOf(hash), Integer.valueOf(this.mVideoWidth), Integer.valueOf(this.mVideoHeight), Float.valueOf(this.mVideoFrameRate), Float.valueOf(this.mVideoPixelAspectRatio)) : hash;
    }

    public static final class Builder {
        private int mAudioChannelCount;
        private boolean mAudioDescription;
        private int mAudioSampleRate;
        private CharSequence mDescription;
        private String mEncoding;
        private boolean mEncrypted;
        private Bundle mExtra;
        private boolean mHardOfHearing;
        private final String mId;
        private String mLanguage;
        private boolean mSpokenSubtitle;
        private final int mType;
        private byte mVideoActiveFormatDescription;
        private float mVideoFrameRate;
        private int mVideoHeight;
        private float mVideoPixelAspectRatio = 1.0f;
        private int mVideoWidth;

        public Builder(int i, String str) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("Unknown type: " + i);
            }
            Preconditions.checkNotNull(str);
            this.mType = i;
            this.mId = str;
        }

        public Builder setLanguage(String str) {
            Preconditions.checkNotNull(str);
            this.mLanguage = str;
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            this.mDescription = charSequence;
            return this;
        }

        public Builder setEncoding(String str) {
            this.mEncoding = str;
            return this;
        }

        public Builder setEncrypted(boolean z) {
            this.mEncrypted = z;
            return this;
        }

        public Builder setAudioChannelCount(int i) {
            if (this.mType != 0) {
                throw new IllegalStateException("Not an audio track");
            }
            this.mAudioChannelCount = i;
            return this;
        }

        public Builder setAudioSampleRate(int i) {
            if (this.mType != 0) {
                throw new IllegalStateException("Not an audio track");
            }
            this.mAudioSampleRate = i;
            return this;
        }

        public Builder setAudioDescription(boolean z) {
            if (this.mType != 0) {
                throw new IllegalStateException("Not an audio track");
            }
            this.mAudioDescription = z;
            return this;
        }

        public Builder setHardOfHearing(boolean z) {
            int i = this.mType;
            if (i != 0 && i != 2) {
                throw new IllegalStateException("Not an audio track or a subtitle track");
            }
            this.mHardOfHearing = z;
            return this;
        }

        public Builder setSpokenSubtitle(boolean z) {
            if (this.mType != 0) {
                throw new IllegalStateException("Not an audio track");
            }
            this.mSpokenSubtitle = z;
            return this;
        }

        public Builder setVideoWidth(int i) {
            if (this.mType != 1) {
                throw new IllegalStateException("Not a video track");
            }
            this.mVideoWidth = i;
            return this;
        }

        public Builder setVideoHeight(int i) {
            if (this.mType != 1) {
                throw new IllegalStateException("Not a video track");
            }
            this.mVideoHeight = i;
            return this;
        }

        public Builder setVideoFrameRate(float f) {
            if (this.mType != 1) {
                throw new IllegalStateException("Not a video track");
            }
            this.mVideoFrameRate = f;
            return this;
        }

        public Builder setVideoPixelAspectRatio(float f) {
            if (this.mType != 1) {
                throw new IllegalStateException("Not a video track");
            }
            this.mVideoPixelAspectRatio = f;
            return this;
        }

        public Builder setVideoActiveFormatDescription(byte b) {
            if (this.mType != 1) {
                throw new IllegalStateException("Not a video track");
            }
            this.mVideoActiveFormatDescription = b;
            return this;
        }

        public Builder setExtra(Bundle bundle) {
            Preconditions.checkNotNull(bundle);
            this.mExtra = new Bundle(bundle);
            return this;
        }

        public TvTrackInfo build() {
            return new TvTrackInfo(this.mType, this.mId, this.mLanguage, this.mDescription, this.mEncoding, this.mEncrypted, this.mAudioChannelCount, this.mAudioSampleRate, this.mAudioDescription, this.mHardOfHearing, this.mSpokenSubtitle, this.mVideoWidth, this.mVideoHeight, this.mVideoFrameRate, this.mVideoPixelAspectRatio, this.mVideoActiveFormatDescription, this.mExtra);
        }
    }
}
