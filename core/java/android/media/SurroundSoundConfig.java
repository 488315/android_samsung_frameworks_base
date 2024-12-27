package android.media;

import android.media.audio.common.AudioFormatDescription;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class SurroundSoundConfig implements Parcelable {
    public static final Parcelable.Creator<SurroundSoundConfig> CREATOR =
            new Parcelable.Creator<
                    SurroundSoundConfig>() { // from class: android.media.SurroundSoundConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SurroundSoundConfig createFromParcel(Parcel _aidl_source) {
                    SurroundSoundConfig _aidl_out = new SurroundSoundConfig();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SurroundSoundConfig[] newArray(int _aidl_size) {
                    return new SurroundSoundConfig[_aidl_size];
                }
            };
    public SurroundFormatFamily[] formatFamilies;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeTypedArray(this.formatFamilies, _aidl_flag);
        int _aidl_end_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.setDataPosition(_aidl_start_pos);
        _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
        _aidl_parcel.setDataPosition(_aidl_end_pos);
    }

    public final void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        int _aidl_parcelable_size = _aidl_parcel.readInt();
        try {
            if (_aidl_parcelable_size < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.formatFamilies =
                        (SurroundFormatFamily[])
                                _aidl_parcel.createTypedArray(SurroundFormatFamily.CREATOR);
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            }
        } catch (Throwable th) {
            if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.formatFamilies);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null) {
            return 0;
        }
        if (_v instanceof Object[]) {
            int _mask = 0;
            for (Object o : (Object[]) _v) {
                _mask |= describeContents(o);
            }
            return _mask;
        }
        if (!(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }

    public static class SurroundFormatFamily implements Parcelable {
        public static final Parcelable.Creator<SurroundFormatFamily> CREATOR =
                new Parcelable.Creator<SurroundFormatFamily>() { // from class:
                    // android.media.SurroundSoundConfig.SurroundFormatFamily.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SurroundFormatFamily createFromParcel(Parcel _aidl_source) {
                        SurroundFormatFamily _aidl_out = new SurroundFormatFamily();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SurroundFormatFamily[] newArray(int _aidl_size) {
                        return new SurroundFormatFamily[_aidl_size];
                    }
                };
        public AudioFormatDescription primaryFormat;
        public AudioFormatDescription[] subFormats;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeTypedObject(this.primaryFormat, _aidl_flag);
            _aidl_parcel.writeTypedArray(this.subFormats, _aidl_flag);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.primaryFormat =
                        (AudioFormatDescription)
                                _aidl_parcel.readTypedObject(AudioFormatDescription.CREATOR);
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.subFormats =
                            (AudioFormatDescription[])
                                    _aidl_parcel.createTypedArray(AudioFormatDescription.CREATOR);
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            int _mask = 0 | describeContents(this.primaryFormat);
            return _mask | describeContents(this.subFormats);
        }

        private int describeContents(Object _v) {
            if (_v == null) {
                return 0;
            }
            if (_v instanceof Object[]) {
                int _mask = 0;
                for (Object o : (Object[]) _v) {
                    _mask |= describeContents(o);
                }
                return _mask;
            }
            if (!(_v instanceof Parcelable)) {
                return 0;
            }
            return ((Parcelable) _v).describeContents();
        }
    }
}
