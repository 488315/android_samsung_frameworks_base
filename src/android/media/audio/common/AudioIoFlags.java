package android.media.audio.common;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioIoFlags implements Parcelable {
    public static final Parcelable.Creator<AudioIoFlags> CREATOR = new Parcelable.Creator<AudioIoFlags>() { // from class: android.media.audio.common.AudioIoFlags.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioIoFlags createFromParcel(Parcel parcel) {
            return new AudioIoFlags(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioIoFlags[] newArray(int i) {
            return new AudioIoFlags[i];
        }
    };
    public static final int input = 0;
    public static final int output = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int input = 0;
        public static final int output = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioIoFlags() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioIoFlags(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioIoFlags(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioIoFlags input(int i) {
        return new AudioIoFlags(0, Integer.valueOf(i));
    }

    public int getInput() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setInput(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static AudioIoFlags output(int i) {
        return new AudioIoFlags(1, Integer.valueOf(i));
    }

    public int getOutput() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setOutput(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getInput());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getOutput());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
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
            return "AudioIoFlags.input(" + getInput() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AudioIoFlags.output(" + getOutput() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioIoFlags)) {
            return false;
        }
        AudioIoFlags audioIoFlags = (AudioIoFlags) obj;
        return this._tag == audioIoFlags._tag && Objects.deepEquals(this._value, audioIoFlags._value);
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
            return "input";
        }
        if (i == 1) {
            return "output";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
