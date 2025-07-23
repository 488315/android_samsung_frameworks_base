package android.media;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class AudioDeviceAttributes implements Parcelable {
    public static final Parcelable.Creator<AudioDeviceAttributes> CREATOR = new Parcelable.Creator<AudioDeviceAttributes>() { // from class: android.media.AudioDeviceAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceAttributes createFromParcel(Parcel parcel) {
            return new AudioDeviceAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceAttributes[] newArray(int i) {
            return new AudioDeviceAttributes[i];
        }
    };
    public static final int ROLE_INPUT = 1;
    public static final int ROLE_OUTPUT = 2;
    private String mAddress;
    private final List<AudioDescriptor> mAudioDescriptors;
    private final List<AudioProfile> mAudioProfiles;
    private final String mName;
    private final int mNativeType;
    private final int mRole;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Role {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public AudioDeviceAttributes(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo);
        this.mRole = audioDeviceInfo.isSink() ? 2 : 1;
        this.mType = audioDeviceInfo.getType();
        this.mAddress = audioDeviceInfo.getAddress();
        this.mName = String.valueOf(audioDeviceInfo.getProductName());
        this.mNativeType = audioDeviceInfo.getInternalType();
        this.mAudioProfiles = audioDeviceInfo.getAudioProfiles();
        this.mAudioDescriptors = audioDeviceInfo.getAudioDescriptors();
    }

    @SystemApi
    public AudioDeviceAttributes(int i, int i2, String str) {
        this(i, i2, str, "", new ArrayList(), new ArrayList());
    }

    @SystemApi
    public AudioDeviceAttributes(int i, int i2, String str, String str2, List<AudioProfile> list, List<AudioDescriptor> list2) {
        Objects.requireNonNull(str);
        if (i != 2 && i != 1) {
            throw new IllegalArgumentException("Invalid role " + i);
        }
        if (i == 2) {
            AudioDeviceInfo.enforceValidAudioDeviceTypeOut(i2);
            this.mNativeType = AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2);
        } else if (i == 1) {
            AudioDeviceInfo.enforceValidAudioDeviceTypeIn(i2);
            this.mNativeType = AudioDeviceInfo.convertDeviceTypeToInternalInputDevice(i2, str);
        } else {
            this.mNativeType = 0;
        }
        this.mRole = i;
        this.mType = i2;
        this.mAddress = str;
        this.mName = str2;
        this.mAudioProfiles = list;
        this.mAudioDescriptors = list2;
    }

    public AudioDeviceAttributes(int i, String str) {
        this(i, str, "");
    }

    public AudioDeviceAttributes(int i, String str, String str2) {
        this.mRole = AudioSystem.isInputDevice(i) ? 1 : 2;
        this.mType = AudioDeviceInfo.convertInternalDeviceToDeviceType(i);
        this.mAddress = str;
        this.mName = str2;
        this.mNativeType = i;
        this.mAudioProfiles = new ArrayList();
        this.mAudioDescriptors = new ArrayList();
    }

    public AudioDeviceAttributes(AudioDeviceAttributes audioDeviceAttributes) {
        this.mRole = audioDeviceAttributes.getRole();
        this.mType = audioDeviceAttributes.getType();
        this.mAddress = audioDeviceAttributes.getAddress();
        this.mName = audioDeviceAttributes.getName();
        this.mNativeType = audioDeviceAttributes.getInternalType();
        this.mAudioProfiles = audioDeviceAttributes.getAudioProfiles();
        this.mAudioDescriptors = audioDeviceAttributes.getAudioDescriptors();
    }

    @SystemApi
    public int getRole() {
        return this.mRole;
    }

    @SystemApi
    public int getType() {
        return this.mType;
    }

    @SystemApi
    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String str) {
        Objects.requireNonNull(str);
        this.mAddress = str;
    }

    @SystemApi
    public String getName() {
        return this.mName;
    }

    public int getInternalType() {
        return this.mNativeType;
    }

    @SystemApi
    public List<AudioProfile> getAudioProfiles() {
        return this.mAudioProfiles;
    }

    @SystemApi
    public List<AudioDescriptor> getAudioDescriptors() {
        return this.mAudioDescriptors;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRole), Integer.valueOf(this.mType), this.mAddress, this.mName, this.mAudioProfiles, this.mAudioDescriptors);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) obj;
            if (this.mRole == audioDeviceAttributes.mRole && this.mType == audioDeviceAttributes.mType && this.mAddress.equals(audioDeviceAttributes.mAddress) && this.mName.equals(audioDeviceAttributes.mName) && this.mAudioProfiles.equals(audioDeviceAttributes.mAudioProfiles) && this.mAudioDescriptors.equals(audioDeviceAttributes.mAudioDescriptors)) {
                return true;
            }
        }
        return false;
    }

    public boolean equalTypeAddress(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) obj;
            if (this.mRole == audioDeviceAttributes.mRole && this.mType == audioDeviceAttributes.mType && this.mAddress.equals(audioDeviceAttributes.mAddress)) {
                return true;
            }
        }
        return false;
    }

    public static String roleToString(int i) {
        return i == 2 ? "output" : "input";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioDeviceAttributes: role:");
        sb.append(roleToString(this.mRole));
        sb.append(" type:");
        sb.append(this.mRole == 2 ? AudioSystem.getOutputDeviceName(this.mNativeType) : AudioSystem.getInputDeviceName(this.mNativeType));
        sb.append(" addr:");
        sb.append(Utils.anonymizeBluetoothAddress(this.mNativeType, this.mAddress));
        sb.append(" name:");
        sb.append(this.mName);
        sb.append(" profiles:");
        sb.append(this.mAudioProfiles.toString());
        sb.append(" descriptors:");
        sb.append(this.mAudioDescriptors.toString());
        return new String(sb.toString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRole);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mAddress);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNativeType);
        List<AudioProfile> list = this.mAudioProfiles;
        parcel.writeParcelableArray((AudioProfile[]) list.toArray(new AudioProfile[list.size()]), i);
        List<AudioDescriptor> list2 = this.mAudioDescriptors;
        parcel.writeParcelableArray((AudioDescriptor[]) list2.toArray(new AudioDescriptor[list2.size()]), i);
    }

    private AudioDeviceAttributes(Parcel parcel) {
        this.mRole = parcel.readInt();
        this.mType = parcel.readInt();
        this.mAddress = parcel.readString();
        this.mName = parcel.readString();
        this.mNativeType = parcel.readInt();
        this.mAudioProfiles = new ArrayList(Arrays.asList((AudioProfile[]) parcel.readParcelableArray(AudioProfile.class.getClassLoader(), AudioProfile.class)));
        this.mAudioDescriptors = new ArrayList(Arrays.asList((AudioDescriptor[]) parcel.readParcelableArray(AudioDescriptor.class.getClassLoader(), AudioDescriptor.class)));
    }
}
