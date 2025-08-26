package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifierBands implements Parcelable {
    public static final Parcelable.Creator<RadioAccessSpecifierBands> CREATOR = new Parcelable.Creator<RadioAccessSpecifierBands>() { // from class: android.hardware.radio.network.RadioAccessSpecifierBands.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifierBands createFromParcel(Parcel parcel) {
            return new RadioAccessSpecifierBands(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifierBands[] newArray(int i) {
            return new RadioAccessSpecifierBands[i];
        }
    };
    public static final int eutranBands = 3;
    public static final int geranBands = 1;
    public static final int ngranBands = 4;
    public static final int noinit = 0;
    public static final int utranBands = 2;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int eutranBands = 3;
        public static final int geranBands = 1;
        public static final int ngranBands = 4;
        public static final int noinit = 0;
        public static final int utranBands = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public RadioAccessSpecifierBands() {
        this._tag = 0;
        this._value = false;
    }

    private RadioAccessSpecifierBands(Parcel parcel) {
        readFromParcel(parcel);
    }

    private RadioAccessSpecifierBands(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static RadioAccessSpecifierBands noinit(boolean z) {
        return new RadioAccessSpecifierBands(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static RadioAccessSpecifierBands geranBands(int[] iArr) {
        return new RadioAccessSpecifierBands(1, iArr);
    }

    public int[] getGeranBands() {
        _assertTag(1);
        return (int[]) this._value;
    }

    public void setGeranBands(int[] iArr) {
        _set(1, iArr);
    }

    public static RadioAccessSpecifierBands utranBands(int[] iArr) {
        return new RadioAccessSpecifierBands(2, iArr);
    }

    public int[] getUtranBands() {
        _assertTag(2);
        return (int[]) this._value;
    }

    public void setUtranBands(int[] iArr) {
        _set(2, iArr);
    }

    public static RadioAccessSpecifierBands eutranBands(int[] iArr) {
        return new RadioAccessSpecifierBands(3, iArr);
    }

    public int[] getEutranBands() {
        _assertTag(3);
        return (int[]) this._value;
    }

    public void setEutranBands(int[] iArr) {
        _set(3, iArr);
    }

    public static RadioAccessSpecifierBands ngranBands(int[] iArr) {
        return new RadioAccessSpecifierBands(4, iArr);
    }

    public int[] getNgranBands() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setNgranBands(int[] iArr) {
        _set(4, iArr);
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
            parcel.writeIntArray(getGeranBands());
            return;
        }
        if (i2 == 2) {
            parcel.writeIntArray(getUtranBands());
        } else if (i2 == 3) {
            parcel.writeIntArray(getEutranBands());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeIntArray(getNgranBands());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, parcel.createIntArray());
            return;
        }
        if (i == 2) {
            _set(i, parcel.createIntArray());
            return;
        }
        if (i == 3) {
            _set(i, parcel.createIntArray());
        } else if (i == 4) {
            _set(i, parcel.createIntArray());
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
            return "RadioAccessSpecifierBands.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "RadioAccessSpecifierBands.geranBands(" + GeranBands$$.arrayToString(getGeranBands()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "RadioAccessSpecifierBands.utranBands(" + UtranBands$$.arrayToString(getUtranBands()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "RadioAccessSpecifierBands.eutranBands(" + EutranBands$$.arrayToString(getEutranBands()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "RadioAccessSpecifierBands.ngranBands(" + NgranBands$$.arrayToString(getNgranBands()) + NavigationBarInflaterView.KEY_CODE_END;
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
            return "geranBands";
        }
        if (i == 2) {
            return "utranBands";
        }
        if (i == 3) {
            return "eutranBands";
        }
        if (i == 4) {
            return "ngranBands";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
