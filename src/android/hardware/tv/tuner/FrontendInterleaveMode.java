package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendInterleaveMode implements Parcelable {
    public static final Parcelable.Creator<FrontendInterleaveMode> CREATOR = new Parcelable.Creator<FrontendInterleaveMode>() { // from class: android.hardware.tv.tuner.FrontendInterleaveMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendInterleaveMode createFromParcel(Parcel parcel) {
            return new FrontendInterleaveMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendInterleaveMode[] newArray(int i) {
            return new FrontendInterleaveMode[i];
        }
    };
    public static final int atsc3 = 0;
    public static final int dtmb = 2;
    public static final int dvbc = 1;
    public static final int isdbt = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int atsc3 = 0;
        public static final int dtmb = 2;
        public static final int dvbc = 1;
        public static final int isdbt = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendInterleaveMode() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendInterleaveMode(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendInterleaveMode(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendInterleaveMode atsc3(int i) {
        return new FrontendInterleaveMode(0, Integer.valueOf(i));
    }

    public int getAtsc3() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setAtsc3(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendInterleaveMode dvbc(int i) {
        return new FrontendInterleaveMode(1, Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendInterleaveMode dtmb(int i) {
        return new FrontendInterleaveMode(2, Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static FrontendInterleaveMode isdbt(int i) {
        return new FrontendInterleaveMode(3, Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(3, Integer.valueOf(i));
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
        } else if (i2 == 2) {
            parcel.writeInt(getDtmb());
        } else {
            if (i2 != 3) {
                return;
            }
            parcel.writeInt(getIsdbt());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 2) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 3) {
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
            return "atsc3";
        }
        if (i == 1) {
            return "dvbc";
        }
        if (i == 2) {
            return "dtmb";
        }
        if (i == 3) {
            return "isdbt";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
