package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendTransmissionMode implements Parcelable {
    public static final Parcelable.Creator<FrontendTransmissionMode> CREATOR = new Parcelable.Creator<FrontendTransmissionMode>() { // from class: android.hardware.tv.tuner.FrontendTransmissionMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendTransmissionMode createFromParcel(Parcel parcel) {
            return new FrontendTransmissionMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendTransmissionMode[] newArray(int i) {
            return new FrontendTransmissionMode[i];
        }
    };
    public static final int dtmb = 2;
    public static final int dvbt = 0;
    public static final int isdbt = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int dtmb = 2;
        public static final int dvbt = 0;
        public static final int isdbt = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendTransmissionMode() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendTransmissionMode(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendTransmissionMode(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendTransmissionMode dvbt(int i) {
        return new FrontendTransmissionMode(0, Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static FrontendTransmissionMode isdbt(int i) {
        return new FrontendTransmissionMode(1, Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static FrontendTransmissionMode dtmb(int i) {
        return new FrontendTransmissionMode(2, Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(2, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getDvbt());
        } else if (i2 == 1) {
            parcel.writeInt(getIsdbt());
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(getDtmb());
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
            return "dvbt";
        }
        if (i == 1) {
            return "isdbt";
        }
        if (i == 2) {
            return "dtmb";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
