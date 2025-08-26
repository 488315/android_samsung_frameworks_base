package android.media.audio.common;

import android.app.slice.Slice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioPortMixExtUseCase implements Parcelable {
    public static final Parcelable.Creator<AudioPortMixExtUseCase> CREATOR = new Parcelable.Creator<AudioPortMixExtUseCase>() { // from class: android.media.audio.common.AudioPortMixExtUseCase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortMixExtUseCase createFromParcel(Parcel parcel) {
            return new AudioPortMixExtUseCase(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPortMixExtUseCase[] newArray(int i) {
            return new AudioPortMixExtUseCase[i];
        }
    };
    public static final int source = 2;
    public static final int stream = 1;
    public static final int unspecified = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int source = 2;
        public static final int stream = 1;
        public static final int unspecified = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioPortMixExtUseCase() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortMixExtUseCase(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortMixExtUseCase(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioPortMixExtUseCase unspecified(boolean z) {
        return new AudioPortMixExtUseCase(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static AudioPortMixExtUseCase stream(int i) {
        return new AudioPortMixExtUseCase(1, Integer.valueOf(i));
    }

    public int getStream() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setStream(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static AudioPortMixExtUseCase source(int i) {
        return new AudioPortMixExtUseCase(2, Integer.valueOf(i));
    }

    public int getSource() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setSource(int i) {
        _set(2, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getUnspecified());
        } else if (i2 == 1) {
            parcel.writeInt(getStream());
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(getSource());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, java.lang.Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 2) {
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
            return "AudioPortMixExtUseCase.unspecified(" + getUnspecified() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AudioPortMixExtUseCase.stream(" + getStream() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "AudioPortMixExtUseCase.source(" + getSource() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioPortMixExtUseCase)) {
            return false;
        }
        AudioPortMixExtUseCase audioPortMixExtUseCase = (AudioPortMixExtUseCase) obj;
        return this._tag == audioPortMixExtUseCase._tag && Objects.deepEquals(this._value, audioPortMixExtUseCase._value);
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
            return "stream";
        }
        if (i == 2) {
            return Slice.SUBTYPE_SOURCE;
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
