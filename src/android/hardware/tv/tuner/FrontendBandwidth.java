package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendBandwidth implements Parcelable {
    public static final Parcelable.Creator<FrontendBandwidth> CREATOR = new Parcelable.Creator<FrontendBandwidth>() { // from class: android.hardware.tv.tuner.FrontendBandwidth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendBandwidth createFromParcel(Parcel parcel) {
            return new FrontendBandwidth(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendBandwidth[] newArray(int i) {
            return new FrontendBandwidth[i];
        }
    };
    public static final int atsc3 = 0;
    public static final int dtmb = 4;
    public static final int dvbc = 1;
    public static final int dvbt = 2;
    public static final int isdbt = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int atsc3 = 0;
        public static final int dtmb = 4;
        public static final int dvbc = 1;
        public static final int dvbt = 2;
        public static final int isdbt = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendBandwidth() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendBandwidth(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendBandwidth(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendBandwidth atsc3(int i) {
        return new FrontendBandwidth(0, Integer.valueOf(i));
    }

    public int getAtsc3() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setAtsc3(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendBandwidth dvbc(int i) {
        return new FrontendBandwidth(1, Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendBandwidth dvbt(int i) {
        return new FrontendBandwidth(2, Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static FrontendBandwidth isdbt(int i) {
        return new FrontendBandwidth(3, Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static FrontendBandwidth dtmb(int i) {
        return new FrontendBandwidth(4, Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(4, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getAtsc3());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getDvbc());
            return;
        }
        if (i2 == 2) {
            parcel.writeInt(getDvbt());
        } else if (i2 == 3) {
            parcel.writeInt(getIsdbt());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeInt(getDtmb());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 1) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 2) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 3) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else if (readInt == 4) {
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
            return "atsc3";
        }
        if (i == 1) {
            return "dvbc";
        }
        if (i == 2) {
            return "dvbt";
        }
        if (i == 3) {
            return "isdbt";
        }
        if (i == 4) {
            return "dtmb";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
