package android.media.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.p009os.BadParcelableException;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhraseRecognitionEventSys implements Parcelable {
    public static final Parcelable.Creator<PhraseRecognitionEventSys> CREATOR = new Parcelable.Creator<PhraseRecognitionEventSys>() { // from class: android.media.soundtrigger_middleware.PhraseRecognitionEventSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEventSys createFromParcel(Parcel _aidl_source) {
            PhraseRecognitionEventSys _aidl_out = new PhraseRecognitionEventSys();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEventSys[] newArray(int _aidl_size) {
            return new PhraseRecognitionEventSys[_aidl_size];
        }
    };
    public long halEventReceivedMillis = -1;
    public PhraseRecognitionEvent phraseRecognitionEvent;
    public IBinder token;

    @Override // android.p009os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeTypedObject(this.phraseRecognitionEvent, _aidl_flag);
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
            this.phraseRecognitionEvent = (PhraseRecognitionEvent) _aidl_parcel.readTypedObject(PhraseRecognitionEvent.CREATOR);
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
        _aidl_sj.add("phraseRecognitionEvent: " + Objects.toString(this.phraseRecognitionEvent));
        _aidl_sj.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        _aidl_sj.add("token: " + Objects.toString(this.token));
        return "android.media.soundtrigger_middleware.PhraseRecognitionEventSys" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof PhraseRecognitionEventSys)) {
            return false;
        }
        PhraseRecognitionEventSys that = (PhraseRecognitionEventSys) other;
        if (Objects.deepEquals(this.phraseRecognitionEvent, that.phraseRecognitionEvent) && Objects.deepEquals(Long.valueOf(this.halEventReceivedMillis), Long.valueOf(that.halEventReceivedMillis)) && Objects.deepEquals(this.token, that.token)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.phraseRecognitionEvent, Long.valueOf(this.halEventReceivedMillis), this.token).toArray());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.phraseRecognitionEvent);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null || !(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }
}
