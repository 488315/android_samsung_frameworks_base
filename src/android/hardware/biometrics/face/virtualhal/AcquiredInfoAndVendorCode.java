package android.hardware.biometrics.face.virtualhal;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AcquiredInfoAndVendorCode implements Parcelable {
    public static final Parcelable.Creator<AcquiredInfoAndVendorCode> CREATOR = new Parcelable.Creator<AcquiredInfoAndVendorCode>() { // from class: android.hardware.biometrics.face.virtualhal.AcquiredInfoAndVendorCode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AcquiredInfoAndVendorCode createFromParcel(Parcel parcel) {
            return new AcquiredInfoAndVendorCode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AcquiredInfoAndVendorCode[] newArray(int i) {
            return new AcquiredInfoAndVendorCode[i];
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

    private AcquiredInfoAndVendorCode(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AcquiredInfoAndVendorCode(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AcquiredInfoAndVendorCode acquiredInfo(byte b) {
        return new AcquiredInfoAndVendorCode(0, Byte.valueOf(b));
    }

    public byte getAcquiredInfo() {
        _assertTag(0);
        return ((Byte) this._value).byteValue();
    }

    public void setAcquiredInfo(byte b) {
        _set(0, Byte.valueOf(b));
    }

    public static AcquiredInfoAndVendorCode vendorCode(int i) {
        return new AcquiredInfoAndVendorCode(1, Integer.valueOf(i));
    }

    public int getVendorCode() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setVendorCode(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeByte(getAcquiredInfo());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getVendorCode());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Byte.valueOf(parcel.readByte()));
        } else if (readInt == 1) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "acquiredInfo";
        }
        if (i == 1) {
            return "vendorCode";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
