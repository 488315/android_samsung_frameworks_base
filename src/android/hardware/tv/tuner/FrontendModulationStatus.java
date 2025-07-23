package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendModulationStatus implements Parcelable {
    public static final Parcelable.Creator<FrontendModulationStatus> CREATOR = new Parcelable.Creator<FrontendModulationStatus>() { // from class: android.hardware.tv.tuner.FrontendModulationStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendModulationStatus createFromParcel(Parcel parcel) {
            return new FrontendModulationStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendModulationStatus[] newArray(int i) {
            return new FrontendModulationStatus[i];
        }
    };
    public static final int dvbc = 0;
    public static final int dvbs = 1;
    public static final int isdbs = 2;
    public static final int isdbs3 = 3;
    public static final int isdbt = 4;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int dvbc = 0;
        public static final int dvbs = 1;
        public static final int isdbs = 2;
        public static final int isdbs3 = 3;
        public static final int isdbt = 4;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendModulationStatus() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendModulationStatus(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendModulationStatus(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendModulationStatus dvbc(int i) {
        return new FrontendModulationStatus(0, Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendModulationStatus dvbs(int i) {
        return new FrontendModulationStatus(1, Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendModulationStatus isdbs(int i) {
        return new FrontendModulationStatus(2, Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static FrontendModulationStatus isdbs3(int i) {
        return new FrontendModulationStatus(3, Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static FrontendModulationStatus isdbt(int i) {
        return new FrontendModulationStatus(4, Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(4, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getDvbc());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getDvbs());
            return;
        }
        if (i2 == 2) {
            parcel.writeInt(getIsdbs());
        } else if (i2 == 3) {
            parcel.writeInt(getIsdbs3());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeInt(getIsdbt());
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
            return "dvbc";
        }
        if (i == 1) {
            return "dvbs";
        }
        if (i == 2) {
            return "isdbs";
        }
        if (i == 3) {
            return "isdbs3";
        }
        if (i == 4) {
            return "isdbt";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
