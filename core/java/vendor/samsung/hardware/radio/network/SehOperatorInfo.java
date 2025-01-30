package vendor.samsung.hardware.radio.network;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehOperatorInfo implements Parcelable {
    public static final Parcelable.Creator<SehOperatorInfo> CREATOR = new Parcelable.Creator<SehOperatorInfo>() { // from class: vendor.samsung.hardware.radio.network.SehOperatorInfo.1
        @Override // android.os.Parcelable.Creator
        public SehOperatorInfo createFromParcel(Parcel _aidl_source) {
            SehOperatorInfo _aidl_out = new SehOperatorInfo();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehOperatorInfo[] newArray(int _aidl_size) {
            return new SehOperatorInfo[_aidl_size];
        }
    };
    public static final int STATUS_AVAILABLE = 1;
    public static final int STATUS_CURRENT = 2;
    public static final int STATUS_FORBIDDEN = 3;
    public static final int STATUS_UNKNOWN = 0;
    public String alphaLong;
    public String alphaShort;
    public String lac;
    public String operatorNumeric;
    public String rat;
    public int status = 0;

    @Override // android.p009os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.p009os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeString(this.alphaLong);
        _aidl_parcel.writeString(this.alphaShort);
        _aidl_parcel.writeString(this.operatorNumeric);
        _aidl_parcel.writeInt(this.status);
        _aidl_parcel.writeString(this.rat);
        _aidl_parcel.writeString(this.lac);
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
            this.alphaLong = _aidl_parcel.readString();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.alphaShort = _aidl_parcel.readString();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.operatorNumeric = _aidl_parcel.readString();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.status = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.rat = _aidl_parcel.readString();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.lac = _aidl_parcel.readString();
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
        _aidl_sj.add("alphaLong: " + Objects.toString(this.alphaLong));
        _aidl_sj.add("alphaShort: " + Objects.toString(this.alphaShort));
        _aidl_sj.add("operatorNumeric: " + Objects.toString(this.operatorNumeric));
        _aidl_sj.add("status: " + this.status);
        _aidl_sj.add("rat: " + Objects.toString(this.rat));
        _aidl_sj.add("lac: " + Objects.toString(this.lac));
        return "vendor.samsung.hardware.radio.network.SehOperatorInfo" + _aidl_sj.toString();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
