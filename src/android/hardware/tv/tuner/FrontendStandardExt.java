package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendStandardExt implements Parcelable {
    public static final Parcelable.Creator<FrontendStandardExt> CREATOR = new Parcelable.Creator<FrontendStandardExt>() { // from class: android.hardware.tv.tuner.FrontendStandardExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendStandardExt createFromParcel(Parcel parcel) {
            return new FrontendStandardExt(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendStandardExt[] newArray(int i) {
            return new FrontendStandardExt[i];
        }
    };
    public static final int dvbsStandardExt = 0;
    public static final int dvbtStandardExt = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int dvbsStandardExt = 0;
        public static final int dvbtStandardExt = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendStandardExt() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private FrontendStandardExt(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendStandardExt(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendStandardExt dvbsStandardExt(byte b) {
        return new FrontendStandardExt(0, Byte.valueOf(b));
    }

    public byte getDvbsStandardExt() {
        _assertTag(0);
        return ((Byte) this._value).byteValue();
    }

    public void setDvbsStandardExt(byte b) {
        _set(0, Byte.valueOf(b));
    }

    public static FrontendStandardExt dvbtStandardExt(byte b) {
        return new FrontendStandardExt(1, Byte.valueOf(b));
    }

    public byte getDvbtStandardExt() {
        _assertTag(1);
        return ((Byte) this._value).byteValue();
    }

    public void setDvbtStandardExt(byte b) {
        _set(1, Byte.valueOf(b));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeByte(getDvbsStandardExt());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeByte(getDvbtStandardExt());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Byte.valueOf(parcel.readByte()));
        } else if (i == 1) {
            _set(i, Byte.valueOf(parcel.readByte()));
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
            return "dvbsStandardExt";
        }
        if (i == 1) {
            return "dvbtStandardExt";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
