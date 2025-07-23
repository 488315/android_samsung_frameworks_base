package android.media.audio.common;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioChannelLayout implements Parcelable {
    public static final int CHANNEL_BACK_CENTER = 256;
    public static final int CHANNEL_BACK_LEFT = 16;
    public static final int CHANNEL_BACK_RIGHT = 32;
    public static final int CHANNEL_BOTTOM_FRONT_CENTER = 2097152;
    public static final int CHANNEL_BOTTOM_FRONT_LEFT = 1048576;
    public static final int CHANNEL_BOTTOM_FRONT_RIGHT = 4194304;
    public static final int CHANNEL_FRONT_CENTER = 4;
    public static final int CHANNEL_FRONT_LEFT = 1;
    public static final int CHANNEL_FRONT_LEFT_OF_CENTER = 64;
    public static final int CHANNEL_FRONT_RIGHT = 2;
    public static final int CHANNEL_FRONT_RIGHT_OF_CENTER = 128;
    public static final int CHANNEL_FRONT_WIDE_LEFT = 16777216;
    public static final int CHANNEL_FRONT_WIDE_RIGHT = 33554432;
    public static final int CHANNEL_HAPTIC_A = 1073741824;
    public static final int CHANNEL_HAPTIC_B = 536870912;
    public static final int CHANNEL_LOW_FREQUENCY = 8;
    public static final int CHANNEL_LOW_FREQUENCY_2 = 8388608;
    public static final int CHANNEL_SIDE_LEFT = 512;
    public static final int CHANNEL_SIDE_RIGHT = 1024;
    public static final int CHANNEL_TOP_BACK_CENTER = 65536;
    public static final int CHANNEL_TOP_BACK_LEFT = 32768;
    public static final int CHANNEL_TOP_BACK_RIGHT = 131072;
    public static final int CHANNEL_TOP_CENTER = 2048;
    public static final int CHANNEL_TOP_FRONT_CENTER = 8192;
    public static final int CHANNEL_TOP_FRONT_LEFT = 4096;
    public static final int CHANNEL_TOP_FRONT_RIGHT = 16384;
    public static final int CHANNEL_TOP_SIDE_LEFT = 262144;
    public static final int CHANNEL_TOP_SIDE_RIGHT = 524288;
    public static final int CHANNEL_VOICE_DNLINK = 32768;
    public static final int CHANNEL_VOICE_UPLINK = 16384;
    public static final Parcelable.Creator<AudioChannelLayout> CREATOR = new Parcelable.Creator<AudioChannelLayout>() { // from class: android.media.audio.common.AudioChannelLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioChannelLayout createFromParcel(Parcel parcel) {
            return new AudioChannelLayout(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioChannelLayout[] newArray(int i) {
            return new AudioChannelLayout[i];
        }
    };
    public static final int INDEX_MASK_1 = 1;
    public static final int INDEX_MASK_10 = 1023;
    public static final int INDEX_MASK_11 = 2047;
    public static final int INDEX_MASK_12 = 4095;
    public static final int INDEX_MASK_13 = 8191;
    public static final int INDEX_MASK_14 = 16383;
    public static final int INDEX_MASK_15 = 32767;
    public static final int INDEX_MASK_16 = 65535;
    public static final int INDEX_MASK_17 = 131071;
    public static final int INDEX_MASK_18 = 262143;
    public static final int INDEX_MASK_19 = 524287;
    public static final int INDEX_MASK_2 = 3;
    public static final int INDEX_MASK_20 = 1048575;
    public static final int INDEX_MASK_21 = 2097151;
    public static final int INDEX_MASK_22 = 4194303;
    public static final int INDEX_MASK_23 = 8388607;
    public static final int INDEX_MASK_24 = 16777215;
    public static final int INDEX_MASK_3 = 7;
    public static final int INDEX_MASK_4 = 15;
    public static final int INDEX_MASK_5 = 31;
    public static final int INDEX_MASK_6 = 63;
    public static final int INDEX_MASK_7 = 127;
    public static final int INDEX_MASK_8 = 255;
    public static final int INDEX_MASK_9 = 511;
    public static final int INTERLEAVE_LEFT = 0;
    public static final int INTERLEAVE_RIGHT = 1;
    public static final int LAYOUT_13POINT0 = 7534087;
    public static final int LAYOUT_13POINT_360RA = 7534087;
    public static final int LAYOUT_22POINT2 = 16777215;
    public static final int LAYOUT_2POINT0POINT2 = 786435;
    public static final int LAYOUT_2POINT1 = 11;
    public static final int LAYOUT_2POINT1POINT2 = 786443;
    public static final int LAYOUT_3POINT0POINT2 = 786439;
    public static final int LAYOUT_3POINT1 = 15;
    public static final int LAYOUT_3POINT1POINT2 = 786447;
    public static final int LAYOUT_5POINT1 = 63;
    public static final int LAYOUT_5POINT1POINT2 = 786495;
    public static final int LAYOUT_5POINT1POINT4 = 184383;
    public static final int LAYOUT_5POINT1_SIDE = 1551;
    public static final int LAYOUT_6POINT1 = 319;
    public static final int LAYOUT_7POINT1 = 1599;
    public static final int LAYOUT_7POINT1POINT2 = 788031;
    public static final int LAYOUT_7POINT1POINT4 = 185919;
    public static final int LAYOUT_9POINT1POINT4 = 50517567;
    public static final int LAYOUT_9POINT1POINT6 = 51303999;
    public static final int LAYOUT_FRONT_BACK = 260;
    public static final int LAYOUT_HAPTIC_AB = 1610612736;
    public static final int LAYOUT_MONO = 1;
    public static final int LAYOUT_MONO_HAPTIC_A = 1073741825;
    public static final int LAYOUT_MONO_HAPTIC_AB = 1610612737;
    public static final int LAYOUT_PENTA = 55;
    public static final int LAYOUT_QUAD = 51;
    public static final int LAYOUT_QUAD_SIDE = 1539;
    public static final int LAYOUT_STEREO = 3;
    public static final int LAYOUT_STEREO_HAPTIC_A = 1073741827;
    public static final int LAYOUT_STEREO_HAPTIC_AB = 1610612739;
    public static final int LAYOUT_SURROUND = 263;
    public static final int LAYOUT_TRI = 7;
    public static final int LAYOUT_TRI_BACK = 259;
    public static final int VOICE_CALL_MONO = 49152;
    public static final int VOICE_DNLINK_MONO = 32768;
    public static final int VOICE_UPLINK_MONO = 16384;
    public static final int indexMask = 2;
    public static final int invalid = 1;
    public static final int layoutMask = 3;
    public static final int none = 0;
    public static final int voiceMask = 4;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int indexMask = 2;
        public static final int invalid = 1;
        public static final int layoutMask = 3;
        public static final int none = 0;
        public static final int voiceMask = 4;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioChannelLayout() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioChannelLayout(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioChannelLayout(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioChannelLayout none(int i) {
        return new AudioChannelLayout(0, Integer.valueOf(i));
    }

    public int getNone() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setNone(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static AudioChannelLayout invalid(int i) {
        return new AudioChannelLayout(1, Integer.valueOf(i));
    }

    public int getInvalid() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setInvalid(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static AudioChannelLayout indexMask(int i) {
        return new AudioChannelLayout(2, Integer.valueOf(i));
    }

    public int getIndexMask() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setIndexMask(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static AudioChannelLayout layoutMask(int i) {
        return new AudioChannelLayout(3, Integer.valueOf(i));
    }

    public int getLayoutMask() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setLayoutMask(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static AudioChannelLayout voiceMask(int i) {
        return new AudioChannelLayout(4, Integer.valueOf(i));
    }

    public int getVoiceMask() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setVoiceMask(int i) {
        _set(4, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getNone());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getInvalid());
            return;
        }
        if (i2 == 2) {
            parcel.writeInt(getIndexMask());
        } else if (i2 == 3) {
            parcel.writeInt(getLayoutMask());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeInt(getVoiceMask());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 1) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 2) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 3) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else if (readInt == 4) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
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
            return "AudioChannelLayout.none(" + getNone() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AudioChannelLayout.invalid(" + getInvalid() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "AudioChannelLayout.indexMask(" + getIndexMask() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "AudioChannelLayout.layoutMask(" + getLayoutMask() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "AudioChannelLayout.voiceMask(" + getVoiceMask() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioChannelLayout)) {
            return false;
        }
        AudioChannelLayout audioChannelLayout = (AudioChannelLayout) obj;
        return this._tag == audioChannelLayout._tag && Objects.deepEquals(this._value, audioChannelLayout._value);
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
            return "none";
        }
        if (i == 1) {
            return "invalid";
        }
        if (i == 2) {
            return "indexMask";
        }
        if (i == 3) {
            return "layoutMask";
        }
        if (i == 4) {
            return "voiceMask";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
