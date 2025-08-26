package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendScanMessageStandard implements Parcelable {
    public static final Parcelable.Creator<FrontendScanMessageStandard> CREATOR = new Parcelable.Creator<FrontendScanMessageStandard>() { // from class: android.hardware.tv.tuner.FrontendScanMessageStandard.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessageStandard createFromParcel(Parcel parcel) {
            return new FrontendScanMessageStandard(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessageStandard[] newArray(int i) {
            return new FrontendScanMessageStandard[i];
        }
    };
    public static final int sStd = 0;
    public static final int sifStd = 2;
    public static final int tStd = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int sStd = 0;
        public static final int sifStd = 2;
        public static final int tStd = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendScanMessageStandard() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private FrontendScanMessageStandard(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendScanMessageStandard(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendScanMessageStandard sStd(byte b) {
        return new FrontendScanMessageStandard(0, Byte.valueOf(b));
    }

    public byte getSStd() {
        _assertTag(0);
        return ((Byte) this._value).byteValue();
    }

    public void setSStd(byte b) {
        _set(0, Byte.valueOf(b));
    }

    public static FrontendScanMessageStandard tStd(byte b) {
        return new FrontendScanMessageStandard(1, Byte.valueOf(b));
    }

    public byte getTStd() {
        _assertTag(1);
        return ((Byte) this._value).byteValue();
    }

    public void setTStd(byte b) {
        _set(1, Byte.valueOf(b));
    }

    public static FrontendScanMessageStandard sifStd(int i) {
        return new FrontendScanMessageStandard(2, Integer.valueOf(i));
    }

    public int getSifStd() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setSifStd(int i) {
        _set(2, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeByte(getSStd());
        } else if (i2 == 1) {
            parcel.writeByte(getTStd());
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(getSifStd());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Byte.valueOf(parcel.readByte()));
            return;
        }
        if (i == 1) {
            _set(i, Byte.valueOf(parcel.readByte()));
        } else if (i == 2) {
            _set(i, Integer.valueOf(parcel.readInt()));
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
            return "sStd";
        }
        if (i == 1) {
            return "tStd";
        }
        if (i == 2) {
            return "sifStd";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
