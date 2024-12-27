package android.media.soundtrigger_middleware;

import android.media.soundtrigger.RecognitionEvent;
import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class RecognitionEventSys implements Parcelable {
    public static final Parcelable.Creator<RecognitionEventSys> CREATOR =
            new Parcelable.Creator<RecognitionEventSys>() { // from class:
                // android.media.soundtrigger_middleware.RecognitionEventSys.1
                @Override // android.os.Parcelable.Creator
                public RecognitionEventSys createFromParcel(Parcel _aidl_source) {
                    RecognitionEventSys _aidl_out = new RecognitionEventSys();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                @Override // android.os.Parcelable.Creator
                public RecognitionEventSys[] newArray(int _aidl_size) {
                    return new RecognitionEventSys[_aidl_size];
                }
            };
    public long halEventReceivedMillis = -1;
    public RecognitionEvent recognitionEvent;
    public IBinder token;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeTypedObject(this.recognitionEvent, _aidl_flag);
        _aidl_parcel.writeLong(this.halEventReceivedMillis);
        _aidl_parcel.writeStrongBinder(this.token);
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
            this.recognitionEvent =
                    (RecognitionEvent) _aidl_parcel.readTypedObject(RecognitionEvent.CREATOR);
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.halEventReceivedMillis = _aidl_parcel.readLong();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.token = _aidl_parcel.readStrongBinder();
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

    public String toString() {
        StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
        _aidl_sj.add("recognitionEvent: " + Objects.toString(this.recognitionEvent));
        _aidl_sj.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        _aidl_sj.add("token: " + Objects.toString(this.token));
        return "RecognitionEventSys" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof RecognitionEventSys)) {
            return false;
        }
        RecognitionEventSys that = (RecognitionEventSys) other;
        if (Objects.deepEquals(this.recognitionEvent, that.recognitionEvent)
                && Objects.deepEquals(
                        Long.valueOf(this.halEventReceivedMillis),
                        Long.valueOf(that.halEventReceivedMillis))
                && Objects.deepEquals(this.token, that.token)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.recognitionEvent,
                                Long.valueOf(this.halEventReceivedMillis),
                                this.token)
                        .toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.recognitionEvent);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null || !(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }
}
