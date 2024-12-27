package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CarrierRestrictions implements Parcelable {
    public static final Parcelable.Creator<CarrierRestrictions> CREATOR =
            new Parcelable.Creator<CarrierRestrictions>() { // from class:
                // android.hardware.radio.sim.CarrierRestrictions.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CarrierRestrictions createFromParcel(Parcel _aidl_source) {
                    CarrierRestrictions _aidl_out = new CarrierRestrictions();
                    _aidl_out.readFromParcel(_aidl_source);
                    return _aidl_out;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CarrierRestrictions[] newArray(int _aidl_size) {
                    return new CarrierRestrictions[_aidl_size];
                }
            };

    @Deprecated public Carrier[] allowedCarriers;

    @Deprecated public Carrier[] excludedCarriers;
    public int status;
    public boolean allowedCarriersPrioritized = false;
    public CarrierInfo[] allowedCarrierInfoList = new CarrierInfo[0];
    public CarrierInfo[] excludedCarrierInfoList = new CarrierInfo[0];

    public @interface CarrierRestrictionStatus {
        public static final int NOT_RESTRICTED = 1;
        public static final int RESTRICTED = 2;
        public static final int UNKNOWN = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeTypedArray(this.allowedCarriers, _aidl_flag);
        _aidl_parcel.writeTypedArray(this.excludedCarriers, _aidl_flag);
        _aidl_parcel.writeBoolean(this.allowedCarriersPrioritized);
        _aidl_parcel.writeInt(this.status);
        _aidl_parcel.writeTypedArray(this.allowedCarrierInfoList, _aidl_flag);
        _aidl_parcel.writeTypedArray(this.excludedCarrierInfoList, _aidl_flag);
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
            this.allowedCarriers = (Carrier[]) _aidl_parcel.createTypedArray(Carrier.CREATOR);
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.excludedCarriers = (Carrier[]) _aidl_parcel.createTypedArray(Carrier.CREATOR);
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.allowedCarriersPrioritized = _aidl_parcel.readBoolean();
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
            this.allowedCarrierInfoList =
                    (CarrierInfo[]) _aidl_parcel.createTypedArray(CarrierInfo.CREATOR);
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.excludedCarrierInfoList =
                        (CarrierInfo[]) _aidl_parcel.createTypedArray(CarrierInfo.CREATOR);
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
        _aidl_sj.add("allowedCarriers: " + Arrays.toString(this.allowedCarriers));
        _aidl_sj.add("excludedCarriers: " + Arrays.toString(this.excludedCarriers));
        _aidl_sj.add("allowedCarriersPrioritized: " + this.allowedCarriersPrioritized);
        _aidl_sj.add("status: " + this.status);
        _aidl_sj.add("allowedCarrierInfoList: " + Arrays.toString(this.allowedCarrierInfoList));
        _aidl_sj.add("excludedCarrierInfoList: " + Arrays.toString(this.excludedCarrierInfoList));
        return "CarrierRestrictions" + _aidl_sj.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.allowedCarriers);
        return _mask
                | describeContents(this.excludedCarriers)
                | describeContents(this.allowedCarrierInfoList)
                | describeContents(this.excludedCarrierInfoList);
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
