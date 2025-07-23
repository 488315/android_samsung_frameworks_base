package android.media.audio.common;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioDeviceAddress implements Parcelable {
    public static final Parcelable.Creator<AudioDeviceAddress> CREATOR = new Parcelable.Creator<AudioDeviceAddress>() { // from class: android.media.audio.common.AudioDeviceAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceAddress createFromParcel(Parcel parcel) {
            return new AudioDeviceAddress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceAddress[] newArray(int i) {
            return new AudioDeviceAddress[i];
        }
    };
    public static final int alsa = 4;
    public static final int id = 0;
    public static final int ipv4 = 2;
    public static final int ipv6 = 3;
    public static final int mac = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int alsa = 4;
        public static final int id = 0;
        public static final int ipv4 = 2;
        public static final int ipv6 = 3;
        public static final int mac = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioDeviceAddress() {
        this._tag = 0;
        this._value = null;
    }

    private AudioDeviceAddress(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioDeviceAddress(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioDeviceAddress id(String str) {
        return new AudioDeviceAddress(0, str);
    }

    public String getId() {
        _assertTag(0);
        return (String) this._value;
    }

    public void setId(String str) {
        _set(0, str);
    }

    public static AudioDeviceAddress mac(byte[] bArr) {
        return new AudioDeviceAddress(1, bArr);
    }

    public byte[] getMac() {
        _assertTag(1);
        return (byte[]) this._value;
    }

    public void setMac(byte[] bArr) {
        _set(1, bArr);
    }

    public static AudioDeviceAddress ipv4(byte[] bArr) {
        return new AudioDeviceAddress(2, bArr);
    }

    public byte[] getIpv4() {
        _assertTag(2);
        return (byte[]) this._value;
    }

    public void setIpv4(byte[] bArr) {
        _set(2, bArr);
    }

    public static AudioDeviceAddress ipv6(int[] iArr) {
        return new AudioDeviceAddress(3, iArr);
    }

    public int[] getIpv6() {
        _assertTag(3);
        return (int[]) this._value;
    }

    public void setIpv6(int[] iArr) {
        _set(3, iArr);
    }

    public static AudioDeviceAddress alsa(int[] iArr) {
        return new AudioDeviceAddress(4, iArr);
    }

    public int[] getAlsa() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setAlsa(int[] iArr) {
        _set(4, iArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeString(getId());
            return;
        }
        if (i2 == 1) {
            parcel.writeByteArray(getMac());
            return;
        }
        if (i2 == 2) {
            parcel.writeByteArray(getIpv4());
        } else if (i2 == 3) {
            parcel.writeIntArray(getIpv6());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeIntArray(getAlsa());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, parcel.readString());
            return;
        }
        if (readInt == 1) {
            _set(readInt, parcel.createByteArray());
            return;
        }
        if (readInt == 2) {
            _set(readInt, parcel.createByteArray());
            return;
        }
        if (readInt == 3) {
            _set(readInt, parcel.createIntArray());
        } else if (readInt == 4) {
            _set(readInt, parcel.createIntArray());
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    public String toString() {
        int i = this._tag;
        if (i == 0) {
            return "AudioDeviceAddress.id(" + Objects.toString(getId()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AudioDeviceAddress.mac(" + Arrays.toString(getMac()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "AudioDeviceAddress.ipv4(" + Arrays.toString(getIpv4()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "AudioDeviceAddress.ipv6(" + Arrays.toString(getIpv6()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "AudioDeviceAddress.alsa(" + Arrays.toString(getAlsa()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioDeviceAddress)) {
            return false;
        }
        AudioDeviceAddress audioDeviceAddress = (AudioDeviceAddress) obj;
        return this._tag == audioDeviceAddress._tag && Objects.deepEquals(this._value, audioDeviceAddress._value);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this._tag), this._value).toArray());
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "id";
        }
        if (i == 1) {
            return "mac";
        }
        if (i == 2) {
            return "ipv4";
        }
        if (i == 3) {
            return "ipv6";
        }
        if (i == 4) {
            return "alsa";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
