package android.media.soundtrigger;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhraseRecognitionExtra implements Parcelable {
    public static final Parcelable.Creator<PhraseRecognitionExtra> CREATOR = new Parcelable.Creator<PhraseRecognitionExtra>() { // from class: android.media.soundtrigger.PhraseRecognitionExtra.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionExtra createFromParcel(Parcel _aidl_source) {
            PhraseRecognitionExtra _aidl_out = new PhraseRecognitionExtra();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionExtra[] newArray(int _aidl_size) {
            return new PhraseRecognitionExtra[_aidl_size];
        }
    };
    public ConfidenceLevel[] levels;

    /* renamed from: id */
    public int f311id = 0;
    public int recognitionModes = 0;
    public int confidenceLevel = 0;

    @Override // android.p009os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.p009os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeInt(this.f311id);
        _aidl_parcel.writeInt(this.recognitionModes);
        _aidl_parcel.writeInt(this.confidenceLevel);
        _aidl_parcel.writeTypedArray(this.levels, _aidl_flag);
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
            this.f311id = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.recognitionModes = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.confidenceLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.levels = (ConfidenceLevel[]) _aidl_parcel.createTypedArray(ConfidenceLevel.CREATOR);
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
        _aidl_sj.add("id: " + this.f311id);
        _aidl_sj.add("recognitionModes: " + this.recognitionModes);
        _aidl_sj.add("confidenceLevel: " + this.confidenceLevel);
        _aidl_sj.add("levels: " + Arrays.toString(this.levels));
        return "android.media.soundtrigger.PhraseRecognitionExtra" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof PhraseRecognitionExtra)) {
            return false;
        }
        PhraseRecognitionExtra that = (PhraseRecognitionExtra) other;
        if (Objects.deepEquals(Integer.valueOf(this.f311id), Integer.valueOf(that.f311id)) && Objects.deepEquals(Integer.valueOf(this.recognitionModes), Integer.valueOf(that.recognitionModes)) && Objects.deepEquals(Integer.valueOf(this.confidenceLevel), Integer.valueOf(that.confidenceLevel)) && Objects.deepEquals(this.levels, that.levels)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.f311id), Integer.valueOf(this.recognitionModes), Integer.valueOf(this.confidenceLevel), this.levels).toArray());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.levels);
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
}
