package android.media.audio.common;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioPortExt implements Parcelable {
    public static final Parcelable.Creator<AudioPortExt> CREATOR = new Parcelable.Creator<AudioPortExt>() { // from class: android.media.audio.common.AudioPortExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortExt createFromParcel(Parcel parcel) {
            return new AudioPortExt(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortExt[] newArray(int i) {
            return new AudioPortExt[i];
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

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioPortExt() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortExt(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortExt(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioPortExt unspecified(boolean z) {
        return new AudioPortExt(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static AudioPortExt device(AudioPortDeviceExt audioPortDeviceExt) {
        return new AudioPortExt(1, audioPortDeviceExt);
    }

    public AudioPortDeviceExt getDevice() {
        _assertTag(1);
        return (AudioPortDeviceExt) this._value;
    }

    public void setDevice(AudioPortDeviceExt audioPortDeviceExt) {
        _set(1, audioPortDeviceExt);
    }

    public static AudioPortExt mix(AudioPortMixExt audioPortMixExt) {
        return new AudioPortExt(2, audioPortMixExt);
    }

    public AudioPortMixExt getMix() {
        _assertTag(2);
        return (AudioPortMixExt) this._value;
    }

    public void setMix(AudioPortMixExt audioPortMixExt) {
        _set(2, audioPortMixExt);
    }

    public static AudioPortExt session(int i) {
        return new AudioPortExt(3, Integer.valueOf(i));
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
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, java.lang.Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, (AudioPortDeviceExt) parcel.readTypedObject(AudioPortDeviceExt.CREATOR));
            return;
        }
        if (i == 2) {
            _set(i, (AudioPortMixExt) parcel.readTypedObject(AudioPortMixExt.CREATOR));
        } else if (i == 3) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
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

    public String toString() {
        int i = this._tag;
        if (i == 0) {
            return "AudioPortExt.unspecified(" + getUnspecified() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AudioPortExt.device(" + Objects.toString(getDevice()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "AudioPortExt.mix(" + Objects.toString(getMix()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "AudioPortExt.session(" + getSession() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPortExt)) {
            return false;
        }
        AudioPortExt audioPortExt = (AudioPortExt) obj;
        return this._tag == audioPortExt._tag && Objects.deepEquals(this._value, audioPortExt._value);
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
