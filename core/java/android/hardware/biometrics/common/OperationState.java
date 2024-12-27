package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableHolder;

/* loaded from: classes2.dex */
public final class OperationState implements Parcelable {
    public static final Parcelable.Creator<OperationState> CREATOR =
            new Parcelable.Creator<OperationState>() { // from class:
                // android.hardware.biometrics.common.OperationState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OperationState createFromParcel(Parcel _aidl_source) {
                    return new OperationState(_aidl_source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OperationState[] newArray(int _aidl_size) {
                    return new OperationState[_aidl_size];
                }
            };
    public static final int faceOperationState = 1;
    public static final int fingerprintOperationState = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int faceOperationState = 1;
        public static final int fingerprintOperationState = 0;
    }

    public OperationState() {
        this._tag = 0;
        this._value = null;
    }

    private OperationState(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private OperationState(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static OperationState fingerprintOperationState(FingerprintOperationState _value) {
        return new OperationState(0, _value);
    }

    public FingerprintOperationState getFingerprintOperationState() {
        _assertTag(0);
        return (FingerprintOperationState) this._value;
    }

    public void setFingerprintOperationState(FingerprintOperationState _value) {
        _set(0, _value);
    }

    public static OperationState faceOperationState(FaceOperationState _value) {
        return new OperationState(1, _value);
    }

    public FaceOperationState getFaceOperationState() {
        _assertTag(1);
        return (FaceOperationState) this._value;
    }

    public void setFaceOperationState(FaceOperationState _value) {
        _set(1, _value);
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        _aidl_parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                _aidl_parcel.writeTypedObject(getFingerprintOperationState(), _aidl_flag);
                break;
            case 1:
                _aidl_parcel.writeTypedObject(getFaceOperationState(), _aidl_flag);
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                FingerprintOperationState _aidl_value =
                        (FingerprintOperationState)
                                _aidl_parcel.readTypedObject(FingerprintOperationState.CREATOR);
                _set(_aidl_tag, _aidl_value);
                return;
            case 1:
                FaceOperationState _aidl_value2 =
                        (FaceOperationState)
                                _aidl_parcel.readTypedObject(FaceOperationState.CREATOR);
                _set(_aidl_tag, _aidl_value2);
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                int _mask = 0 | describeContents(getFingerprintOperationState());
                return _mask;
            case 1:
                int _mask2 = 0 | describeContents(getFaceOperationState());
                return _mask2;
            default:
                return 0;
        }
    }

    private int describeContents(Object _v) {
        if (_v == null || !(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }

    private void _assertTag(int tag) {
        if (getTag() != tag) {
            throw new IllegalStateException(
                    "bad access: "
                            + _tagString(tag)
                            + ", "
                            + _tagString(getTag())
                            + " is available.");
        }
    }

    private String _tagString(int _tag) {
        switch (_tag) {
            case 0:
                return "fingerprintOperationState";
            case 1:
                return "faceOperationState";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public static class FingerprintOperationState implements Parcelable {
        public static final Parcelable.Creator<FingerprintOperationState> CREATOR =
                new Parcelable.Creator<FingerprintOperationState>() { // from class:
                    // android.hardware.biometrics.common.OperationState.FingerprintOperationState.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FingerprintOperationState createFromParcel(Parcel _aidl_source) {
                        FingerprintOperationState _aidl_out = new FingerprintOperationState();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FingerprintOperationState[] newArray(int _aidl_size) {
                        return new FingerprintOperationState[_aidl_size];
                    }
                };
        public final ParcelableHolder extension = new ParcelableHolder(1);
        public boolean isHardwareIgnoringTouches = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeTypedObject(this.extension, 0);
            _aidl_parcel.writeBoolean(this.isHardwareIgnoringTouches);
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
                if (_aidl_parcel.readInt() != 0) {
                    this.extension.readFromParcel(_aidl_parcel);
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.isHardwareIgnoringTouches = _aidl_parcel.readBoolean();
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
            int _mask = 0 | describeContents(this.extension);
            return _mask;
        }

        private int describeContents(Object _v) {
            if (_v == null || !(_v instanceof Parcelable)) {
                return 0;
            }
            return ((Parcelable) _v).describeContents();
        }
    }

    public static class FaceOperationState implements Parcelable {
        public static final Parcelable.Creator<FaceOperationState> CREATOR =
                new Parcelable.Creator<FaceOperationState>() { // from class:
                    // android.hardware.biometrics.common.OperationState.FaceOperationState.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FaceOperationState createFromParcel(Parcel _aidl_source) {
                        FaceOperationState _aidl_out = new FaceOperationState();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FaceOperationState[] newArray(int _aidl_size) {
                        return new FaceOperationState[_aidl_size];
                    }
                };
        public final ParcelableHolder extension = new ParcelableHolder(1);

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeTypedObject(this.extension, 0);
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
                    if (_aidl_parcel.readInt() != 0) {
                        this.extension.readFromParcel(_aidl_parcel);
                    }
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
            int _mask = 0 | describeContents(this.extension);
            return _mask;
        }

        private int describeContents(Object _v) {
            if (_v == null || !(_v instanceof Parcelable)) {
                return 0;
            }
            return ((Parcelable) _v).describeContents();
        }
    }
}
