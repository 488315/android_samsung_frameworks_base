package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.StringJoiner;

public class HeadTracking implements Parcelable {
    public static final Parcelable.Creator<HeadTracking> CREATOR =
            new Parcelable.Creator<
                    HeadTracking>() { // from class: android.media.audio.common.HeadTracking.1
                @Override // android.os.Parcelable.Creator
                public HeadTracking createFromParcel(Parcel _aidl_source) {
                    HeadTracking _aidl_out = new HeadTracking();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                @Override // android.os.Parcelable.Creator
                public HeadTracking[] newArray(int _aidl_size) {
                    return new HeadTracking[_aidl_size];
                }
            };

    public @interface ConnectionMode {
        public static final byte DIRECT_TO_SENSOR_SW = 1;
        public static final byte DIRECT_TO_SENSOR_TUNNEL = 2;
        public static final byte FRAMEWORK_PROCESSED = 0;
    }

    public @interface Mode {
        public static final byte DISABLED = 1;
        public static final byte OTHER = 0;
        public static final byte RELATIVE_SCREEN = 3;
        public static final byte RELATIVE_WORLD = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        int _aidl_end_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.setDataPosition(_aidl_start_pos);
        _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
        _aidl_parcel.setDataPosition(_aidl_end_pos);
    }

    public final void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        int _aidl_parcelable_size = _aidl_parcel.readInt();
        if (_aidl_parcelable_size < 4) {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }
        if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
            throw new BadParcelableException("Overflow in the size of parcelable");
        }
        _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
    }

    public String toString() {
        StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
        return "HeadTracking" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof HeadTracking)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(new Object[0]).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class SensorData implements Parcelable {
        public static final Parcelable.Creator<SensorData> CREATOR =
                new Parcelable.Creator<SensorData>() { // from class:
                    // android.media.audio.common.HeadTracking.SensorData.1
                    @Override // android.os.Parcelable.Creator
                    public SensorData createFromParcel(Parcel _aidl_source) {
                        return new SensorData(_aidl_source);
                    }

                    @Override // android.os.Parcelable.Creator
                    public SensorData[] newArray(int _aidl_size) {
                        return new SensorData[_aidl_size];
                    }
                };
        public static final int headToStage = 0;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final int headToStage = 0;
        }

        public SensorData() {
            float[] _value = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            this._tag = 0;
            this._value = _value;
        }

        private SensorData(Parcel _aidl_parcel) {
            readFromParcel(_aidl_parcel);
        }

        private SensorData(int _tag, Object _value) {
            this._tag = _tag;
            this._value = _value;
        }

        public int getTag() {
            return this._tag;
        }

        public static SensorData headToStage(float[] _value) {
            return new SensorData(0, _value);
        }

        public float[] getHeadToStage() {
            _assertTag(0);
            return (float[]) this._value;
        }

        public void setHeadToStage(float[] _value) {
            _set(0, _value);
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
                    _aidl_parcel.writeFixedArray(getHeadToStage(), _aidl_flag, 6);
                    break;
            }
        }

        public void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_tag = _aidl_parcel.readInt();
            switch (_aidl_tag) {
                case 0:
                    float[] _aidl_value = (float[]) _aidl_parcel.createFixedArray(float[].class, 6);
                    _set(_aidl_tag, _aidl_value);
                    return;
                default:
                    throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            getTag();
            return 0;
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
                    return "headToStage";
                default:
                    throw new IllegalStateException("unknown field: " + _tag);
            }
        }

        private void _set(int _tag, Object _value) {
            this._tag = _tag;
            this._value = _value;
        }
    }
}
