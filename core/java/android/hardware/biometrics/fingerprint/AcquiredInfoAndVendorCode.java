package android.hardware.biometrics.fingerprint;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AcquiredInfoAndVendorCode implements Parcelable {
    public static final Parcelable.Creator<AcquiredInfoAndVendorCode> CREATOR =
            new Parcelable.Creator<AcquiredInfoAndVendorCode>() { // from class:
                // android.hardware.biometrics.fingerprint.AcquiredInfoAndVendorCode.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AcquiredInfoAndVendorCode createFromParcel(Parcel _aidl_source) {
                    return new AcquiredInfoAndVendorCode(_aidl_source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AcquiredInfoAndVendorCode[] newArray(int _aidl_size) {
                    return new AcquiredInfoAndVendorCode[_aidl_size];
                }
            };
    public static final int acquiredInfo = 0;
    public static final int vendorCode = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int acquiredInfo = 0;
        public static final int vendorCode = 1;
    }

    public AcquiredInfoAndVendorCode() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private AcquiredInfoAndVendorCode(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private AcquiredInfoAndVendorCode(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static AcquiredInfoAndVendorCode acquiredInfo(byte _value) {
        return new AcquiredInfoAndVendorCode(0, Byte.valueOf(_value));
    }

    public byte getAcquiredInfo() {
        _assertTag(0);
        return ((Byte) this._value).byteValue();
    }

    public void setAcquiredInfo(byte _value) {
        _set(0, Byte.valueOf(_value));
    }

    public static AcquiredInfoAndVendorCode vendorCode(int _value) {
        return new AcquiredInfoAndVendorCode(1, Integer.valueOf(_value));
    }

    public int getVendorCode() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setVendorCode(int _value) {
        _set(1, Integer.valueOf(_value));
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
                _aidl_parcel.writeByte(getAcquiredInfo());
                break;
            case 1:
                _aidl_parcel.writeInt(getVendorCode());
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                byte _aidl_value = _aidl_parcel.readByte();
                _set(_aidl_tag, Byte.valueOf(_aidl_value));
                return;
            case 1:
                int _aidl_value2 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value2));
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
                return "acquiredInfo";
            case 1:
                return "vendorCode";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
