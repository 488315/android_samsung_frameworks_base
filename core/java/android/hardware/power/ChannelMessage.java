package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ChannelMessage implements Parcelable {
    public static final Parcelable.Creator<ChannelMessage> CREATOR =
            new Parcelable.Creator<
                    ChannelMessage>() { // from class: android.hardware.power.ChannelMessage.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ChannelMessage createFromParcel(Parcel _aidl_source) {
                    ChannelMessage _aidl_out = new ChannelMessage();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ChannelMessage[] newArray(int _aidl_size) {
                    return new ChannelMessage[_aidl_size];
                }
            };
    public ChannelMessageContents data;
    public int sessionID = 0;
    public long timeStampNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeInt(this.sessionID);
        _aidl_parcel.writeLong(this.timeStampNanos);
        _aidl_parcel.writeTypedObject(this.data, _aidl_flag);
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
            this.sessionID = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.timeStampNanos = _aidl_parcel.readLong();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.data =
                        (ChannelMessageContents)
                                _aidl_parcel.readTypedObject(ChannelMessageContents.CREATOR);
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
        int _mask = 0 | describeContents(this.data);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null || !(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }

    public static final class ChannelMessageContents implements Parcelable {
        public static final Parcelable.Creator<ChannelMessageContents> CREATOR =
                new Parcelable.Creator<ChannelMessageContents>() { // from class:
                    // android.hardware.power.ChannelMessage.ChannelMessageContents.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ChannelMessageContents createFromParcel(Parcel _aidl_source) {
                        return new ChannelMessageContents(_aidl_source);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ChannelMessageContents[] newArray(int _aidl_size) {
                        return new ChannelMessageContents[_aidl_size];
                    }
                };
        public static final int hint = 2;
        public static final int mode = 3;
        public static final int reserved = 0;
        public static final int targetDuration = 1;
        public static final int workDuration = 4;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final byte hint = 2;
            public static final byte mode = 3;
            public static final byte reserved = 0;
            public static final byte targetDuration = 1;
            public static final byte workDuration = 4;
        }

        public ChannelMessageContents() {
            long[] _value = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            this._tag = 0;
            this._value = _value;
        }

        private ChannelMessageContents(Parcel _aidl_parcel) {
            readFromParcel(_aidl_parcel);
        }

        private ChannelMessageContents(int _tag, Object _value) {
            this._tag = _tag;
            this._value = _value;
        }

        public int getTag() {
            return this._tag;
        }

        public static ChannelMessageContents reserved(long[] _value) {
            return new ChannelMessageContents(0, _value);
        }

        public long[] getReserved() {
            _assertTag(0);
            return (long[]) this._value;
        }

        public void setReserved(long[] _value) {
            _set(0, _value);
        }

        public static ChannelMessageContents targetDuration(long _value) {
            return new ChannelMessageContents(1, Long.valueOf(_value));
        }

        public long getTargetDuration() {
            _assertTag(1);
            return ((Long) this._value).longValue();
        }

        public void setTargetDuration(long _value) {
            _set(1, Long.valueOf(_value));
        }

        public static ChannelMessageContents hint(int _value) {
            return new ChannelMessageContents(2, Integer.valueOf(_value));
        }

        public int getHint() {
            _assertTag(2);
            return ((Integer) this._value).intValue();
        }

        public void setHint(int _value) {
            _set(2, Integer.valueOf(_value));
        }

        public static ChannelMessageContents mode(SessionModeSetter _value) {
            return new ChannelMessageContents(3, _value);
        }

        public SessionModeSetter getMode() {
            _assertTag(3);
            return (SessionModeSetter) this._value;
        }

        public void setMode(SessionModeSetter _value) {
            _set(3, _value);
        }

        public static ChannelMessageContents workDuration(WorkDurationFixedV1 _value) {
            return new ChannelMessageContents(4, _value);
        }

        public WorkDurationFixedV1 getWorkDuration() {
            _assertTag(4);
            return (WorkDurationFixedV1) this._value;
        }

        public void setWorkDuration(WorkDurationFixedV1 _value) {
            _set(4, _value);
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
                    _aidl_parcel.writeFixedArray(getReserved(), _aidl_flag, 16);
                    break;
                case 1:
                    _aidl_parcel.writeLong(getTargetDuration());
                    break;
                case 2:
                    _aidl_parcel.writeInt(getHint());
                    break;
                case 3:
                    _aidl_parcel.writeTypedObject(getMode(), _aidl_flag);
                    break;
                case 4:
                    _aidl_parcel.writeTypedObject(getWorkDuration(), _aidl_flag);
                    break;
            }
        }

        public void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_tag = _aidl_parcel.readInt();
            switch (_aidl_tag) {
                case 0:
                    long[] _aidl_value = (long[]) _aidl_parcel.createFixedArray(long[].class, 16);
                    _set(_aidl_tag, _aidl_value);
                    return;
                case 1:
                    long _aidl_value2 = _aidl_parcel.readLong();
                    _set(_aidl_tag, Long.valueOf(_aidl_value2));
                    return;
                case 2:
                    int _aidl_value3 = _aidl_parcel.readInt();
                    _set(_aidl_tag, Integer.valueOf(_aidl_value3));
                    return;
                case 3:
                    SessionModeSetter _aidl_value4 =
                            (SessionModeSetter)
                                    _aidl_parcel.readTypedObject(SessionModeSetter.CREATOR);
                    _set(_aidl_tag, _aidl_value4);
                    return;
                case 4:
                    WorkDurationFixedV1 _aidl_value5 =
                            (WorkDurationFixedV1)
                                    _aidl_parcel.readTypedObject(WorkDurationFixedV1.CREATOR);
                    _set(_aidl_tag, _aidl_value5);
                    return;
                default:
                    throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            switch (getTag()) {
                case 3:
                    int _mask = 0 | describeContents(getMode());
                    return _mask;
                case 4:
                    int _mask2 = 0 | describeContents(getWorkDuration());
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
                    return "reserved";
                case 1:
                    return "targetDuration";
                case 2:
                    return "hint";
                case 3:
                    return "mode";
                case 4:
                    return "workDuration";
                default:
                    throw new IllegalStateException("unknown field: " + _tag);
            }
        }

        private void _set(int _tag, Object _value) {
            this._tag = _tag;
            this._value = _value;
        }

        public static class SessionModeSetter implements Parcelable {
            public static final Parcelable.Creator<SessionModeSetter> CREATOR =
                    new Parcelable.Creator<SessionModeSetter>() { // from class:
                        // android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public SessionModeSetter createFromParcel(Parcel _aidl_source) {
                            SessionModeSetter _aidl_out = new SessionModeSetter();
                            _aidl_out.readFromParcel(_aidl_source);
                            return _aidl_out;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
                        public SessionModeSetter[] newArray(int _aidl_size) {
                            return new SessionModeSetter[_aidl_size];
                        }
                    };
            public boolean enabled = false;
            public int modeInt;

            @Override // android.os.Parcelable
            public final int getStability() {
                return 1;
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
                int _aidl_start_pos = _aidl_parcel.dataPosition();
                _aidl_parcel.writeInt(0);
                _aidl_parcel.writeInt(this.modeInt);
                _aidl_parcel.writeBoolean(this.enabled);
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
                    this.modeInt = _aidl_parcel.readInt();
                    if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    } else {
                        this.enabled = _aidl_parcel.readBoolean();
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
                return 0;
            }
        }
    }
}
