package android.media.audio.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes2.dex */
public final class AudioPolicyForceUse implements Parcelable {
    public static final Parcelable.Creator<AudioPolicyForceUse> CREATOR = new Parcelable.Creator<AudioPolicyForceUse>() { // from class: android.media.audio.common.AudioPolicyForceUse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyForceUse createFromParcel(Parcel parcel) {
            return new AudioPolicyForceUse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyForceUse[] newArray(int i) {
            return new AudioPolicyForceUse[i];
        }
    };
    public static final int dock = 4;
    public static final int encodedSurround = 7;
    public static final int forCommunication = 1;
    public static final int forMedia = 0;
    public static final int forRecord = 2;
    public static final int forVibrateRinging = 3;
    public static final int hdmiSystemAudio = 6;
    public static final int systemSounds = 5;
    private int _tag;
    private Object _value;

    public @interface CommunicationDeviceCategory {
        public static final byte BT_BLE = 3;
        public static final byte BT_SCO = 2;
        public static final byte NONE = 0;
        public static final byte SPEAKER = 1;
        public static final byte WIRED_ACCESSORY = 4;
    }

    public @interface DockType {
        public static final byte ANALOG_DOCK = 3;
        public static final byte BT_CAR_DOCK = 1;
        public static final byte BT_DESK_DOCK = 2;
        public static final byte DIGITAL_DOCK = 4;
        public static final byte NONE = 0;
        public static final byte WIRED_ACCESSORY = 5;
    }

    public @interface EncodedSurroundConfig {
        public static final byte ALWAYS = 2;
        public static final byte MANUAL = 3;
        public static final byte NEVER = 1;
        public static final byte UNSPECIFIED = 0;
    }

    public @interface MediaDeviceCategory {
        public static final byte ANALOG_DOCK = 4;
        public static final byte BT_A2DP = 3;
        public static final byte DIGITAL_DOCK = 5;
        public static final byte HEADPHONES = 2;
        public static final byte NONE = 0;
        public static final byte NO_BT_A2DP = 7;
        public static final byte SPEAKER = 1;
        public static final byte WIRED_ACCESSORY = 6;
    }

    public @interface Tag {
        public static final int dock = 4;
        public static final int encodedSurround = 7;
        public static final int forCommunication = 1;
        public static final int forMedia = 0;
        public static final int forRecord = 2;
        public static final int forVibrateRinging = 3;
        public static final int hdmiSystemAudio = 6;
        public static final int systemSounds = 5;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioPolicyForceUse() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private AudioPolicyForceUse(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPolicyForceUse(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioPolicyForceUse forMedia(byte b) {
        return new AudioPolicyForceUse(0, java.lang.Byte.valueOf(b));
    }

    public byte getForMedia() {
        _assertTag(0);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setForMedia(byte b) {
        _set(0, java.lang.Byte.valueOf(b));
    }

    public static AudioPolicyForceUse forCommunication(byte b) {
        return new AudioPolicyForceUse(1, java.lang.Byte.valueOf(b));
    }

    public byte getForCommunication() {
        _assertTag(1);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setForCommunication(byte b) {
        _set(1, java.lang.Byte.valueOf(b));
    }

    public static AudioPolicyForceUse forRecord(byte b) {
        return new AudioPolicyForceUse(2, java.lang.Byte.valueOf(b));
    }

    public byte getForRecord() {
        _assertTag(2);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setForRecord(byte b) {
        _set(2, java.lang.Byte.valueOf(b));
    }

    public static AudioPolicyForceUse forVibrateRinging(byte b) {
        return new AudioPolicyForceUse(3, java.lang.Byte.valueOf(b));
    }

    public byte getForVibrateRinging() {
        _assertTag(3);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setForVibrateRinging(byte b) {
        _set(3, java.lang.Byte.valueOf(b));
    }

    public static AudioPolicyForceUse dock(byte b) {
        return new AudioPolicyForceUse(4, java.lang.Byte.valueOf(b));
    }

    public byte getDock() {
        _assertTag(4);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setDock(byte b) {
        _set(4, java.lang.Byte.valueOf(b));
    }

    public static AudioPolicyForceUse systemSounds(boolean z) {
        return new AudioPolicyForceUse(5, java.lang.Boolean.valueOf(z));
    }

    public boolean getSystemSounds() {
        _assertTag(5);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setSystemSounds(boolean z) {
        _set(5, java.lang.Boolean.valueOf(z));
    }

    public static AudioPolicyForceUse hdmiSystemAudio(boolean z) {
        return new AudioPolicyForceUse(6, java.lang.Boolean.valueOf(z));
    }

    public boolean getHdmiSystemAudio() {
        _assertTag(6);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setHdmiSystemAudio(boolean z) {
        _set(6, java.lang.Boolean.valueOf(z));
    }

    public static AudioPolicyForceUse encodedSurround(byte b) {
        return new AudioPolicyForceUse(7, java.lang.Byte.valueOf(b));
    }

    public byte getEncodedSurround() {
        _assertTag(7);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setEncodedSurround(byte b) {
        _set(7, java.lang.Byte.valueOf(b));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeByte(getForMedia());
                break;
            case 1:
                parcel.writeByte(getForCommunication());
                break;
            case 2:
                parcel.writeByte(getForRecord());
                break;
            case 3:
                parcel.writeByte(getForVibrateRinging());
                break;
            case 4:
                parcel.writeByte(getDock());
                break;
            case 5:
                parcel.writeBoolean(getSystemSounds());
                break;
            case 6:
                parcel.writeBoolean(getHdmiSystemAudio());
                break;
            case 7:
                parcel.writeByte(getEncodedSurround());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        switch (i) {
            case 0:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 1:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 2:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 3:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 4:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 5:
                _set(i, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 6:
                _set(i, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 7:
                _set(i, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        switch (i) {
            case 0:
                return "forMedia";
            case 1:
                return "forCommunication";
            case 2:
                return "forRecord";
            case 3:
                return "forVibrateRinging";
            case 4:
                return AudioParameter.VALUE_DOCK;
            case 5:
                return "systemSounds";
            case 6:
                return "hdmiSystemAudio";
            case 7:
                return "encodedSurround";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
