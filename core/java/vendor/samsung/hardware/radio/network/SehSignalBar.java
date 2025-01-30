package vendor.samsung.hardware.radio.network;

import android.p009os.BadParcelableException;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSignalBar implements Parcelable {
    public static final Parcelable.Creator<SehSignalBar> CREATOR = new Parcelable.Creator<SehSignalBar>() { // from class: vendor.samsung.hardware.radio.network.SehSignalBar.1
        @Override // android.os.Parcelable.Creator
        public SehSignalBar createFromParcel(Parcel _aidl_source) {
            SehSignalBar _aidl_out = new SehSignalBar();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SehSignalBar[] newArray(int _aidl_size) {
            return new SehSignalBar[_aidl_size];
        }
    };
    public int cdmaLevel;
    public int evdoLevel;
    public int gsmLevel;
    public int lteLevel;
    public int nrLevel;
    public int tdscdmaLevel;
    public int wcdmaLevel;

    @Override // android.p009os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.p009os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeInt(this.cdmaLevel);
        _aidl_parcel.writeInt(this.evdoLevel);
        _aidl_parcel.writeInt(this.gsmLevel);
        _aidl_parcel.writeInt(this.wcdmaLevel);
        _aidl_parcel.writeInt(this.tdscdmaLevel);
        _aidl_parcel.writeInt(this.lteLevel);
        _aidl_parcel.writeInt(this.nrLevel);
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
            this.cdmaLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.evdoLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.gsmLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.wcdmaLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.tdscdmaLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.lteLevel = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.nrLevel = _aidl_parcel.readInt();
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
        _aidl_sj.add("cdmaLevel: " + SehSignalLevel$$.toString(this.cdmaLevel));
        _aidl_sj.add("evdoLevel: " + SehSignalLevel$$.toString(this.evdoLevel));
        _aidl_sj.add("gsmLevel: " + SehSignalLevel$$.toString(this.gsmLevel));
        _aidl_sj.add("wcdmaLevel: " + SehSignalLevel$$.toString(this.wcdmaLevel));
        _aidl_sj.add("tdscdmaLevel: " + SehSignalLevel$$.toString(this.tdscdmaLevel));
        _aidl_sj.add("lteLevel: " + SehSignalLevel$$.toString(this.lteLevel));
        _aidl_sj.add("nrLevel: " + SehSignalLevel$$.toString(this.nrLevel));
        return "vendor.samsung.hardware.radio.network.SehSignalBar" + _aidl_sj.toString();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
