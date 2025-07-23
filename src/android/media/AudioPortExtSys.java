package android.media;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AudioPortExtSys implements Parcelable {
    public static final Parcelable.Creator<AudioPortExtSys> CREATOR = new Parcelable.Creator<AudioPortExtSys>() { // from class: android.media.AudioPortExtSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortExtSys createFromParcel(Parcel parcel) {
            return new AudioPortExtSys(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortExtSys[] newArray(int i) {
            return new AudioPortExtSys[i];
        }
    };
    public static final int device = 1;
    public static final int mix = 2;
    public static final int session = 3;
    public static final int unspecified = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int device = 1;
        public static final int mix = 2;
        public static final int session = 3;
        public static final int unspecified = 0;
    }

    public AudioPortExtSys() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortExtSys(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortExtSys(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioPortExtSys unspecified(boolean z) {
        return new AudioPortExtSys(0, Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static AudioPortExtSys device(AudioPortDeviceExtSys audioPortDeviceExtSys) {
        return new AudioPortExtSys(1, audioPortDeviceExtSys);
    }

    public AudioPortDeviceExtSys getDevice() {
        _assertTag(1);
        return (AudioPortDeviceExtSys) this._value;
    }

    public void setDevice(AudioPortDeviceExtSys audioPortDeviceExtSys) {
        _set(1, audioPortDeviceExtSys);
    }

    public static AudioPortExtSys mix(AudioPortMixExtSys audioPortMixExtSys) {
        return new AudioPortExtSys(2, audioPortMixExtSys);
    }

    public AudioPortMixExtSys getMix() {
        _assertTag(2);
        return (AudioPortMixExtSys) this._value;
    }

    public void setMix(AudioPortMixExtSys audioPortMixExtSys) {
        _set(2, audioPortMixExtSys);
    }

    public static AudioPortExtSys session(int i) {
        return new AudioPortExtSys(3, Integer.valueOf(i));
    }

    public int getSession() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setSession(int i) {
        _set(3, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getUnspecified());
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getDevice(), i);
        } else if (i2 == 2) {
            parcel.writeTypedObject(getMix(), i);
        } else {
            if (i2 != 3) {
                return;
            }
            parcel.writeInt(getSession());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (readInt == 1) {
            _set(readInt, (AudioPortDeviceExtSys) parcel.readTypedObject(AudioPortDeviceExtSys.CREATOR));
            return;
        }
        if (readInt == 2) {
            _set(readInt, (AudioPortMixExtSys) parcel.readTypedObject(AudioPortMixExtSys.CREATOR));
        } else if (readInt == 3) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 1) {
            return describeContents(getDevice());
        }
        if (tag != 2) {
            return 0;
        }
        return describeContents(getMix());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "unspecified";
        }
        if (i == 1) {
            return "device";
        }
        if (i == 2) {
            return "mix";
        }
        if (i == 3) {
            return "session";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
