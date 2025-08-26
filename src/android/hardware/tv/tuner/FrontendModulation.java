package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendModulation implements Parcelable {
    public static final Parcelable.Creator<FrontendModulation> CREATOR = new Parcelable.Creator<FrontendModulation>() { // from class: android.hardware.tv.tuner.FrontendModulation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendModulation createFromParcel(Parcel parcel) {
            return new FrontendModulation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendModulation[] newArray(int i) {
            return new FrontendModulation[i];
        }
    };
    public static final int atsc = 6;
    public static final int atsc3 = 7;
    public static final int dtmb = 8;
    public static final int dvbc = 0;
    public static final int dvbs = 1;
    public static final int dvbt = 2;
    public static final int isdbs = 3;
    public static final int isdbs3 = 4;
    public static final int isdbt = 5;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int atsc = 6;
        public static final int atsc3 = 7;
        public static final int dtmb = 8;
        public static final int dvbc = 0;
        public static final int dvbs = 1;
        public static final int dvbt = 2;
        public static final int isdbs = 3;
        public static final int isdbs3 = 4;
        public static final int isdbt = 5;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendModulation() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendModulation(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendModulation(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendModulation dvbc(int i) {
        return new FrontendModulation(0, Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendModulation dvbs(int i) {
        return new FrontendModulation(1, Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendModulation dvbt(int i) {
        return new FrontendModulation(2, Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static FrontendModulation isdbs(int i) {
        return new FrontendModulation(3, Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static FrontendModulation isdbs3(int i) {
        return new FrontendModulation(4, Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
        _set(4, Integer.valueOf(i));
    }

    public static FrontendModulation isdbt(int i) {
        return new FrontendModulation(5, Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(5);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(5, Integer.valueOf(i));
    }

    public static FrontendModulation atsc(int i) {
        return new FrontendModulation(6, Integer.valueOf(i));
    }

    public int getAtsc() {
        _assertTag(6);
        return ((Integer) this._value).intValue();
    }

    public void setAtsc(int i) {
        _set(6, Integer.valueOf(i));
    }

    public static FrontendModulation atsc3(int i) {
        return new FrontendModulation(7, Integer.valueOf(i));
    }

    public int getAtsc3() {
        _assertTag(7);
        return ((Integer) this._value).intValue();
    }

    public void setAtsc3(int i) {
        _set(7, Integer.valueOf(i));
    }

    public static FrontendModulation dtmb(int i) {
        return new FrontendModulation(8, Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(8);
        return ((Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(8, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeInt(getDvbc());
                break;
            case 1:
                parcel.writeInt(getDvbs());
                break;
            case 2:
                parcel.writeInt(getDvbt());
                break;
            case 3:
                parcel.writeInt(getIsdbs());
                break;
            case 4:
                parcel.writeInt(getIsdbs3());
                break;
            case 5:
                parcel.writeInt(getIsdbt());
                break;
            case 6:
                parcel.writeInt(getAtsc());
                break;
            case 7:
                parcel.writeInt(getAtsc3());
                break;
            case 8:
                parcel.writeInt(getDtmb());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        switch (i) {
            case 0:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 1:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 5:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 8:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            default:
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
        switch (i) {
            case 0:
                return "dvbc";
            case 1:
                return "dvbs";
            case 2:
                return "dvbt";
            case 3:
                return "isdbs";
            case 4:
                return "isdbs3";
            case 5:
                return "isdbt";
            case 6:
                return "atsc";
            case 7:
                return "atsc3";
            case 8:
                return "dtmb";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
