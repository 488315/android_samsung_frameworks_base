package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class QosFilterIpsecSpi implements Parcelable {
    public static final Parcelable.Creator<QosFilterIpsecSpi> CREATOR = new Parcelable.Creator<QosFilterIpsecSpi>() { // from class: android.hardware.radio.data.QosFilterIpsecSpi.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterIpsecSpi createFromParcel(Parcel parcel) {
            return new QosFilterIpsecSpi(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterIpsecSpi[] newArray(int i) {
            return new QosFilterIpsecSpi[i];
        }
    };
    public static final int noinit = 0;
    public static final int value = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int noinit = 0;
        public static final int value = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public QosFilterIpsecSpi() {
        this._tag = 0;
        this._value = false;
    }

    private QosFilterIpsecSpi(Parcel parcel) {
        readFromParcel(parcel);
    }

    private QosFilterIpsecSpi(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static QosFilterIpsecSpi noinit(boolean z) {
        return new QosFilterIpsecSpi(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static QosFilterIpsecSpi value(int i) {
        return new QosFilterIpsecSpi(1, Integer.valueOf(i));
    }

    public int getValue() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setValue(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getValue());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
        } else if (i == 1) {
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
            return "QosFilterIpsecSpi.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "QosFilterIpsecSpi.value(" + getValue() + NavigationBarInflaterView.KEY_CODE_END;
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
            return "value";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
