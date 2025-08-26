package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxIpAddressIpAddress implements Parcelable {
    public static final Parcelable.Creator<DemuxIpAddressIpAddress> CREATOR = new Parcelable.Creator<DemuxIpAddressIpAddress>() { // from class: android.hardware.tv.tuner.DemuxIpAddressIpAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpAddressIpAddress createFromParcel(Parcel parcel) {
            return new DemuxIpAddressIpAddress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpAddressIpAddress[] newArray(int i) {
            return new DemuxIpAddressIpAddress[i];
        }
    };
    public static final int v4 = 0;
    public static final int v6 = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int v4 = 0;
        public static final int v6 = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxIpAddressIpAddress() {
        this._tag = 0;
        this._value = new byte[0];
    }

    private DemuxIpAddressIpAddress(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxIpAddressIpAddress(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxIpAddressIpAddress v4(byte[] bArr) {
        return new DemuxIpAddressIpAddress(0, bArr);
    }

    public byte[] getV4() {
        _assertTag(0);
        return (byte[]) this._value;
    }

    public void setV4(byte[] bArr) {
        _set(0, bArr);
    }

    public static DemuxIpAddressIpAddress v6(byte[] bArr) {
        return new DemuxIpAddressIpAddress(1, bArr);
    }

    public byte[] getV6() {
        _assertTag(1);
        return (byte[]) this._value;
    }

    public void setV6(byte[] bArr) {
        _set(1, bArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeByteArray(getV4());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeByteArray(getV6());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, parcel.createByteArray());
        } else if (i == 1) {
            _set(i, parcel.createByteArray());
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
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
            return "v4";
        }
        if (i == 1) {
            return "v6";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
