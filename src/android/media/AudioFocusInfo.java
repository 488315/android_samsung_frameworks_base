package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class AudioFocusInfo implements Parcelable {
    public static final Parcelable.Creator<AudioFocusInfo> CREATOR = new Parcelable.Creator<AudioFocusInfo>() { // from class: android.media.AudioFocusInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFocusInfo createFromParcel(Parcel parcel) {
            AudioFocusInfo audioFocusInfo = new AudioFocusInfo(AudioAttributes.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            audioFocusInfo.setGen(parcel.readLong());
            return audioFocusInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioFocusInfo[] newArray(int i) {
            return new AudioFocusInfo[i];
        }
    };
    private final AudioAttributes mAttributes;
    private final String mClientId;
    private final int mClientUid;
    private int mFlags;
    private int mGainRequest;
    private long mGenCount = -1;
    private int mLossReceived;
    private final String mPackageName;
    private final int mSdkTarget;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioFocusInfo(AudioAttributes audioAttributes, int i, String str, String str2, int i2, int i3, int i4, int i5) {
        this.mAttributes = audioAttributes == null ? new AudioAttributes.Builder().build() : audioAttributes;
        this.mClientUid = i;
        this.mClientId = str == null ? "" : str;
        this.mPackageName = str2 == null ? "" : str2;
        this.mGainRequest = i2;
        this.mLossReceived = i3;
        this.mFlags = i4;
        this.mSdkTarget = i5;
    }

    public void setGen(long j) {
        this.mGenCount = j;
    }

    public long getGen() {
        return this.mGenCount;
    }

    public AudioAttributes getAttributes() {
        return this.mAttributes;
    }

    public int getClientUid() {
        return this.mClientUid;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getGainRequest() {
        return this.mGainRequest;
    }

    public int getLossReceived() {
        return this.mLossReceived;
    }

    public int getSdkTarget() {
        return this.mSdkTarget;
    }

    public void clearLossReceived() {
        this.mLossReceived = 0;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mAttributes.writeToParcel(parcel, i);
        parcel.writeInt(this.mClientUid);
        parcel.writeString(this.mClientId);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mGainRequest);
        parcel.writeInt(this.mLossReceived);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSdkTarget);
        parcel.writeLong(this.mGenCount);
    }

    public int hashCode() {
        return Objects.hash(this.mAttributes, Integer.valueOf(this.mClientUid), this.mClientId, this.mPackageName, Integer.valueOf(this.mGainRequest), Integer.valueOf(this.mFlags));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioFocusInfo audioFocusInfo = (AudioFocusInfo) obj;
        return this.mAttributes.equals(audioFocusInfo.mAttributes) && this.mClientUid == audioFocusInfo.mClientUid && this.mClientId.equals(audioFocusInfo.mClientId) && this.mPackageName.equals(audioFocusInfo.mPackageName) && this.mGainRequest == audioFocusInfo.mGainRequest && this.mLossReceived == audioFocusInfo.mLossReceived && this.mFlags == audioFocusInfo.mFlags && this.mSdkTarget == audioFocusInfo.mSdkTarget;
    }
}
