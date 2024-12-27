package android.content;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.List;

public class UriRelativeFilterGroupParcel implements Parcelable {
    public static final Parcelable.Creator<UriRelativeFilterGroupParcel> CREATOR =
            new Parcelable.Creator<UriRelativeFilterGroupParcel>() { // from class:
                // android.content.UriRelativeFilterGroupParcel.1
                @Override // android.os.Parcelable.Creator
                public UriRelativeFilterGroupParcel createFromParcel(Parcel _aidl_source) {
                    UriRelativeFilterGroupParcel _aidl_out = new UriRelativeFilterGroupParcel();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                @Override // android.os.Parcelable.Creator
                public UriRelativeFilterGroupParcel[] newArray(int _aidl_size) {
                    return new UriRelativeFilterGroupParcel[_aidl_size];
                }
            };
    public int action = 0;
    public List<UriRelativeFilterParcel> filters;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeInt(this.action);
        _aidl_parcel.writeTypedList(this.filters, _aidl_flag);
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
            this.action = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.filters = _aidl_parcel.createTypedArrayList(UriRelativeFilterParcel.CREATOR);
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
        int _mask = 0 | describeContents(this.filters);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null) {
            return 0;
        }
        if (_v instanceof Collection) {
            int _mask = 0;
            for (Object o : (Collection) _v) {
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
