package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Qos implements Parcelable {
    public static final Parcelable.Creator<Qos> CREATOR = new Parcelable.Creator<Qos>() { // from class: android.hardware.radio.data.Qos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Qos createFromParcel(Parcel parcel) {
            return new Qos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Qos[] newArray(int i) {
            return new Qos[i];
        }
    };
    public static final int eps = 1;
    public static final int noinit = 0;
    public static final int nr = 2;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int eps = 1;
        public static final int noinit = 0;
        public static final int nr = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public Qos() {
        this._tag = 0;
        this._value = false;
    }

    private Qos(Parcel parcel) {
        readFromParcel(parcel);
    }

    private Qos(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static Qos noinit(boolean z) {
        return new Qos(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static Qos eps(EpsQos epsQos) {
        return new Qos(1, epsQos);
    }

    public EpsQos getEps() {
        _assertTag(1);
        return (EpsQos) this._value;
    }

    public void setEps(EpsQos epsQos) {
        _set(1, epsQos);
    }

    public static Qos nr(NrQos nrQos) {
        return new Qos(2, nrQos);
    }

    public NrQos getNr() {
        _assertTag(2);
        return (NrQos) this._value;
    }

    public void setNr(NrQos nrQos) {
        _set(2, nrQos);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
        } else if (i2 == 1) {
            parcel.writeTypedObject(getEps(), i);
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeTypedObject(getNr(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, (EpsQos) parcel.readTypedObject(EpsQos.CREATOR));
        } else if (i == 2) {
            _set(i, (NrQos) parcel.readTypedObject(NrQos.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 1) {
            return describeContents(getEps());
        }
        if (tag != 2) {
            return 0;
        }
        return describeContents(getNr());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public String toString() {
        int i = this._tag;
        if (i == 0) {
            return "Qos.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "Qos.eps(" + Objects.toString(getEps()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "Qos.nr(" + Objects.toString(getNr()) + NavigationBarInflaterView.KEY_CODE_END;
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
            return "eps";
        }
        if (i == 2) {
            return "nr";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
