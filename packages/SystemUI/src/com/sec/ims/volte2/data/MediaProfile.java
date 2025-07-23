package com.sec.ims.volte2.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MediaProfile implements Parcelable {
    public static final Parcelable.Creator<MediaProfile> CREATOR = new Parcelable.Creator<MediaProfile>() { // from class: com.sec.ims.volte2.data.MediaProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProfile createFromParcel(Parcel parcel) {
            return new MediaProfile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProfile[] newArray(int i) {
            return new MediaProfile[i];
        }
    };
    public static final int RTT_MODE_DISABLED = 0;
    public static final int RTT_MODE_FULL = 1;
    private String mAudioBitRate;
    private VolteConstants.AudioCodecType mAudioCodec;
    private int mAudioQuality;
    private int mHeight;
    private int mRttMode;
    private int mVideoOrientation;
    private boolean mVideoPause;
    private int mVideoQuality;
    private int mWidth;

    public /* synthetic */ MediaProfile(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAudioBitRate() {
        return this.mAudioBitRate;
    }

    public VolteConstants.AudioCodecType getAudioCodec() {
        return this.mAudioCodec;
    }

    public int getAudioQuality() {
        return this.mAudioQuality;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getRttMode() {
        return this.mRttMode;
    }

    public int getVideoOrientation() {
        return this.mVideoOrientation;
    }

    public boolean getVideoPause() {
        return this.mVideoPause;
    }

    public int getVideoQuality() {
        return this.mVideoQuality;
    }

    public String getVideoSize() {
        int i = this.mWidth;
        return (i == 720 && this.mHeight == 1280) ? "HD720" : (i == 1280 && this.mHeight == 720) ? "HD720LAND" : (i == 480 && this.mHeight == 640) ? "VGA" : (i == 640 && this.mHeight == 480) ? "VGALAND" : (i == 240 && this.mHeight == 320) ? "QVGA" : (i == 320 && this.mHeight == 240) ? "QVGALAND" : (i == 144 && this.mHeight == 176) ? "QCIF" : (i == 176 && this.mHeight == 144) ? "QCIFLAND" : (i == 0 && this.mHeight == 0) ? "UNSUPPORTED" : "VGA";
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setAudioBitRate(String str) {
        this.mAudioBitRate = str;
    }

    public void setAudioCodec(VolteConstants.AudioCodecType audioCodecType) {
        this.mAudioCodec = audioCodecType;
    }

    public void setAudioQuality(int i) {
        this.mAudioQuality = i;
    }

    public void setRttMode(int i) {
        this.mRttMode = i;
    }

    public void setVideoOrientation(int i) {
        this.mVideoOrientation = i;
    }

    public void setVideoPause(boolean z) {
        this.mVideoPause = z;
    }

    public void setVideoQuality(int i) {
        this.mVideoQuality = i;
    }

    public void setVideoSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        VolteConstants.AudioCodecType audioCodecType = this.mAudioCodec;
        if (audioCodecType != null) {
            parcel.writeString(audioCodecType.toString());
        } else {
            parcel.writeString("");
        }
        parcel.writeString(this.mAudioBitRate);
        parcel.writeInt(this.mVideoOrientation);
        parcel.writeInt(this.mVideoQuality);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mVideoPause ? 1 : 0);
        parcel.writeInt(this.mRttMode);
    }

    public MediaProfile() {
        this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_NONE;
        this.mAudioBitRate = "";
        this.mAudioQuality = 0;
        this.mVideoQuality = 0;
        this.mVideoOrientation = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mVideoPause = false;
        this.mRttMode = 0;
    }

    public MediaProfile(VolteConstants.AudioCodecType audioCodecType, String str) {
        VolteConstants.AudioCodecType audioCodecType2 = VolteConstants.AudioCodecType.AUDIO_CODEC_NONE;
        this.mAudioQuality = 0;
        this.mVideoQuality = 0;
        this.mVideoOrientation = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mVideoPause = false;
        this.mRttMode = 0;
        this.mAudioCodec = audioCodecType;
        this.mAudioBitRate = str;
    }

    private MediaProfile(Parcel parcel) {
        VolteConstants.AudioCodecType audioCodecType = VolteConstants.AudioCodecType.AUDIO_CODEC_NONE;
        this.mAudioCodec = audioCodecType;
        this.mAudioBitRate = "";
        this.mAudioQuality = 0;
        this.mVideoQuality = 0;
        this.mVideoOrientation = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mVideoPause = false;
        this.mRttMode = 0;
        String readString = parcel.readString();
        if (readString == null) {
            this.mAudioCodec = audioCodecType;
        } else if ("AMR-WB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_AMRWB;
        } else if ("AMR-NB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_AMRNB;
        } else if ("EVS-FB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_EVSFB;
        } else if ("EVS-SWB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_EVSSWB;
        } else if ("EVS-WB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_EVSWB;
        } else if ("EVS-NB".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_EVSNB;
        } else if ("EVS".equals(readString)) {
            this.mAudioCodec = VolteConstants.AudioCodecType.AUDIO_CODEC_EVS;
        } else {
            this.mAudioCodec = audioCodecType;
        }
        this.mAudioBitRate = parcel.readString();
        this.mVideoOrientation = parcel.readInt();
        this.mVideoQuality = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mWidth = parcel.readInt();
        this.mVideoPause = parcel.readInt() == 1;
        this.mRttMode = parcel.readInt();
    }
}
