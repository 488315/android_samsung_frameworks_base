package android.media;

import android.annotation.SystemApi;
import android.media.IAudioService;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class VolumeInfo implements Parcelable {
    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>() { // from class: android.media.VolumeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeInfo createFromParcel(Parcel parcel) {
            return new VolumeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeInfo[] newArray(int i) {
            return new VolumeInfo[i];
        }
    };
    public static final int INDEX_NOT_SET = -100;
    private static final String TAG = "VolumeInfo";
    private static VolumeInfo sDefaultVolumeInfo;
    private static IAudioService sService;
    private final boolean mHasMuteCommand;
    private final boolean mIsMuted;
    private final int mMaxVolIndex;
    private final int mMinVolIndex;
    private final int mStreamType;
    private final boolean mUsesStreamType;
    private final android.media.audiopolicy.AudioVolumeGroup mVolGroup;
    private final int mVolIndex;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VolumeInfo(boolean z, boolean z2, boolean z3, int i, int i2, int i3, android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup, int i4) {
        this.mUsesStreamType = z;
        this.mHasMuteCommand = z2;
        this.mIsMuted = z3;
        this.mVolIndex = i;
        this.mMinVolIndex = i2;
        this.mMaxVolIndex = i3;
        this.mVolGroup = audioVolumeGroup;
        this.mStreamType = i4;
    }

    public boolean hasStreamType() {
        return this.mUsesStreamType;
    }

    public int getStreamType() {
        if (!this.mUsesStreamType) {
            throw new IllegalStateException("VolumeInfo doesn't use stream types");
        }
        return this.mStreamType;
    }

    public boolean hasVolumeGroup() {
        return !this.mUsesStreamType;
    }

    public android.media.audiopolicy.AudioVolumeGroup getVolumeGroup() {
        if (this.mUsesStreamType) {
            throw new IllegalStateException("VolumeInfo doesn't use AudioVolumeGroup");
        }
        return this.mVolGroup;
    }

    public boolean hasMuteCommand() {
        return this.mHasMuteCommand;
    }

    public boolean isMuted() {
        return this.mIsMuted;
    }

    public int getVolumeIndex() {
        return this.mVolIndex;
    }

    public int getMinVolumeIndex() {
        return this.mMinVolIndex;
    }

    public int getMaxVolumeIndex() {
        return this.mMaxVolIndex;
    }

    public static VolumeInfo getDefaultVolumeInfo() {
        if (sService == null) {
            sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        }
        if (sDefaultVolumeInfo == null) {
            try {
                sDefaultVolumeInfo = sService.getDefaultVolumeInfo();
            } catch (RemoteException e) {
                Log.e(TAG, "Error calling getDefaultVolumeInfo", e);
                return new Builder(3).build();
            }
        }
        return sDefaultVolumeInfo;
    }

    public static final class Builder {
        private boolean mHasMuteCommand;
        private boolean mIsMuted;
        private int mMaxVolIndex;
        private int mMinVolIndex;
        private int mStreamType;
        private boolean mUsesStreamType;
        private android.media.audiopolicy.AudioVolumeGroup mVolGroup;
        private int mVolIndex;

        public Builder(int i) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            if (!AudioManager.isPublicStreamType(i)) {
                throw new IllegalArgumentException("Not a valid public stream type " + i);
            }
            this.mUsesStreamType = true;
            this.mStreamType = i;
        }

        public Builder(android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            Objects.requireNonNull(audioVolumeGroup);
            this.mUsesStreamType = false;
            this.mStreamType = Integer.MIN_VALUE;
            this.mVolGroup = audioVolumeGroup;
        }

        public Builder(VolumeInfo volumeInfo) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            Objects.requireNonNull(volumeInfo);
            this.mUsesStreamType = volumeInfo.mUsesStreamType;
            this.mStreamType = volumeInfo.mStreamType;
            this.mHasMuteCommand = volumeInfo.mHasMuteCommand;
            this.mIsMuted = volumeInfo.mIsMuted;
            this.mVolIndex = volumeInfo.mVolIndex;
            this.mMinVolIndex = volumeInfo.mMinVolIndex;
            this.mMaxVolIndex = volumeInfo.mMaxVolIndex;
            this.mVolGroup = volumeInfo.mVolGroup;
        }

        public Builder setMuted(boolean z) {
            this.mHasMuteCommand = true;
            this.mIsMuted = z;
            return this;
        }

        public Builder setVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new IllegalArgumentException("Volume index cannot be negative");
            }
            this.mVolIndex = i;
            return this;
        }

        public Builder setMinVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new IllegalArgumentException("Min volume index cannot be negative");
            }
            this.mMinVolIndex = i;
            return this;
        }

        public Builder setMaxVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new IllegalArgumentException("Max volume index cannot be negative");
            }
            this.mMaxVolIndex = i;
            return this;
        }

        public VolumeInfo build() {
            int i;
            int i2 = this.mVolIndex;
            if (i2 != -100) {
                int i3 = this.mMinVolIndex;
                if (i3 != -100 && i2 < i3) {
                    throw new IllegalArgumentException("Volume index:" + this.mVolIndex + " lower than min index:" + this.mMinVolIndex);
                }
                int i4 = this.mMaxVolIndex;
                if (i4 != -100 && i2 > i4) {
                    throw new IllegalArgumentException("Volume index:" + this.mVolIndex + " greater than max index:" + this.mMaxVolIndex);
                }
            }
            int i5 = this.mMinVolIndex;
            if (i5 != -100 && (i = this.mMaxVolIndex) != -100 && i5 > i) {
                throw new IllegalArgumentException("Min volume index:" + this.mMinVolIndex + " greater than max index:" + this.mMaxVolIndex);
            }
            return new VolumeInfo(this.mUsesStreamType, this.mHasMuteCommand, this.mIsMuted, this.mVolIndex, this.mMinVolIndex, this.mMaxVolIndex, this.mVolGroup, this.mStreamType);
        }
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mUsesStreamType), Boolean.valueOf(this.mHasMuteCommand), Integer.valueOf(this.mStreamType), Boolean.valueOf(this.mIsMuted), Integer.valueOf(this.mVolIndex), Integer.valueOf(this.mMinVolIndex), Integer.valueOf(this.mMaxVolIndex), this.mVolGroup);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VolumeInfo volumeInfo = (VolumeInfo) obj;
            if (this.mUsesStreamType == volumeInfo.mUsesStreamType && this.mStreamType == volumeInfo.mStreamType && this.mHasMuteCommand == volumeInfo.mHasMuteCommand && this.mIsMuted == volumeInfo.mIsMuted && this.mVolIndex == volumeInfo.mVolIndex && this.mMinVolIndex == volumeInfo.mMinVolIndex && this.mMaxVolIndex == volumeInfo.mMaxVolIndex && Objects.equals(this.mVolGroup, volumeInfo.mVolGroup)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String str;
        String str2;
        String str3;
        String str4;
        StringBuilder sb = new StringBuilder("VolumeInfo:");
        if (this.mUsesStreamType) {
            str = " streamType:" + this.mStreamType;
        } else {
            str = " volGroup:" + this.mVolGroup;
        }
        sb.append(str);
        if (this.mHasMuteCommand) {
            str2 = " muted:" + this.mIsMuted;
        } else {
            str2 = "[no mute cmd]";
        }
        sb.append(str2);
        String str5 = "";
        if (this.mVolIndex != -100) {
            str3 = " volIndex:" + this.mVolIndex;
        } else {
            str3 = "";
        }
        sb.append(str3);
        if (this.mMinVolIndex != -100) {
            str4 = " min:" + this.mMinVolIndex;
        } else {
            str4 = "";
        }
        sb.append(str4);
        if (this.mMaxVolIndex != -100) {
            str5 = " max:" + this.mMaxVolIndex;
        }
        sb.append(str5);
        return new String(sb.toString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mUsesStreamType);
        parcel.writeInt(this.mStreamType);
        parcel.writeBoolean(this.mHasMuteCommand);
        parcel.writeBoolean(this.mIsMuted);
        parcel.writeInt(this.mVolIndex);
        parcel.writeInt(this.mMinVolIndex);
        parcel.writeInt(this.mMaxVolIndex);
        if (this.mUsesStreamType) {
            return;
        }
        this.mVolGroup.writeToParcel(parcel, 0);
    }

    private VolumeInfo(Parcel parcel) {
        boolean z = parcel.readBoolean();
        this.mUsesStreamType = z;
        this.mStreamType = parcel.readInt();
        this.mHasMuteCommand = parcel.readBoolean();
        this.mIsMuted = parcel.readBoolean();
        this.mVolIndex = parcel.readInt();
        this.mMinVolIndex = parcel.readInt();
        this.mMaxVolIndex = parcel.readInt();
        if (!z) {
            this.mVolGroup = android.media.audiopolicy.AudioVolumeGroup.CREATOR.createFromParcel(parcel);
        } else {
            this.mVolGroup = null;
        }
    }
}
