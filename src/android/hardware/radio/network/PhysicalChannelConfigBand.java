package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfigBand implements Parcelable {
    public static final Parcelable.Creator<PhysicalChannelConfigBand> CREATOR = new Parcelable.Creator<PhysicalChannelConfigBand>() { // from class: android.hardware.radio.network.PhysicalChannelConfigBand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfigBand createFromParcel(Parcel parcel) {
            return new PhysicalChannelConfigBand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalChannelConfigBand[] newArray(int i) {
            return new PhysicalChannelConfigBand[i];
        }
    };
    public static final int eutranBand = 3;
    public static final int geranBand = 1;
    public static final int ngranBand = 4;
    public static final int noinit = 0;
    public static final int utranBand = 2;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int eutranBand = 3;
        public static final int geranBand = 1;
        public static final int ngranBand = 4;
        public static final int noinit = 0;
        public static final int utranBand = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public PhysicalChannelConfigBand() {
        this._tag = 0;
        this._value = false;
    }

    private PhysicalChannelConfigBand(Parcel parcel) {
        readFromParcel(parcel);
    }

    private PhysicalChannelConfigBand(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static PhysicalChannelConfigBand noinit(boolean z) {
        return new PhysicalChannelConfigBand(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static PhysicalChannelConfigBand geranBand(int i) {
        return new PhysicalChannelConfigBand(1, Integer.valueOf(i));
    }

    public int getGeranBand() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setGeranBand(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static PhysicalChannelConfigBand utranBand(int i) {
        return new PhysicalChannelConfigBand(2, Integer.valueOf(i));
    }

    public int getUtranBand() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setUtranBand(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static PhysicalChannelConfigBand eutranBand(int i) {
        return new PhysicalChannelConfigBand(3, Integer.valueOf(i));
    }

    public int getEutranBand() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setEutranBand(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static PhysicalChannelConfigBand ngranBand(int i) {
        return new PhysicalChannelConfigBand(4, Integer.valueOf(i));
    }

    public int getNgranBand() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setNgranBand(int i) {
        _set(4, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getGeranBand());
            return;
        }
        if (i2 == 2) {
            parcel.writeInt(getUtranBand());
        } else if (i2 == 3) {
            parcel.writeInt(getEutranBand());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeInt(getNgranBand());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 2) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 3) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 4) {
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

    public String toString() {
        int i = this._tag;
        if (i == 0) {
            return "PhysicalChannelConfigBand.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "PhysicalChannelConfigBand.geranBand(" + GeranBands$$.toString(getGeranBand()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "PhysicalChannelConfigBand.utranBand(" + UtranBands$$.toString(getUtranBand()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "PhysicalChannelConfigBand.eutranBand(" + EutranBands$$.toString(getEutranBand()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "PhysicalChannelConfigBand.ngranBand(" + NgranBands$$.toString(getNgranBand()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "noinit";
        }
        if (i == 1) {
            return "geranBand";
        }
        if (i == 2) {
            return "utranBand";
        }
        if (i == 3) {
            return "eutranBand";
        }
        if (i == 4) {
            return "ngranBand";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
