package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendRollOff implements Parcelable {
    public static final Parcelable.Creator<FrontendRollOff> CREATOR = new Parcelable.Creator<FrontendRollOff>() { // from class: android.hardware.tv.tuner.FrontendRollOff.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendRollOff createFromParcel(Parcel parcel) {
            return new FrontendRollOff(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendRollOff[] newArray(int i) {
            return new FrontendRollOff[i];
        }
    };
    public static final int dvbs = 0;
    public static final int isdbs = 1;
    public static final int isdbs3 = 2;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int dvbs = 0;
        public static final int isdbs = 1;
        public static final int isdbs3 = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendRollOff() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendRollOff(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendRollOff(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendRollOff dvbs(int i) {
        return new FrontendRollOff(0, Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendRollOff isdbs(int i) {
        return new FrontendRollOff(1, Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendRollOff isdbs3(int i) {
        return new FrontendRollOff(2, Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
        _set(2, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getDvbs());
        } else if (i2 == 1) {
            parcel.writeInt(getIsdbs());
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(getIsdbs3());
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
        } else if (readInt == 2) {
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
            return "dvbs";
        }
        if (i == 1) {
            return "isdbs";
        }
        if (i == 2) {
            return "isdbs3";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
