package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class QosFilterTypeOfService implements Parcelable {
    public static final Parcelable.Creator<QosFilterTypeOfService> CREATOR = new Parcelable.Creator<QosFilterTypeOfService>() { // from class: android.hardware.radio.data.QosFilterTypeOfService.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterTypeOfService createFromParcel(Parcel parcel) {
            return new QosFilterTypeOfService(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterTypeOfService[] newArray(int i) {
            return new QosFilterTypeOfService[i];
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

    public QosFilterTypeOfService() {
        this._tag = 0;
        this._value = false;
    }

    private QosFilterTypeOfService(Parcel parcel) {
        readFromParcel(parcel);
    }

    private QosFilterTypeOfService(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static QosFilterTypeOfService noinit(boolean z) {
        return new QosFilterTypeOfService(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static QosFilterTypeOfService value(byte b) {
        return new QosFilterTypeOfService(1, Byte.valueOf(b));
    }

    public byte getValue() {
        _assertTag(1);
        return ((Byte) this._value).byteValue();
    }

    public void setValue(byte b) {
        _set(1, Byte.valueOf(b));
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
            parcel.writeByte(getValue());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Boolean.valueOf(parcel.readBoolean()));
        } else if (readInt == 1) {
            _set(readInt, Byte.valueOf(parcel.readByte()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
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
            return "QosFilterTypeOfService.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "QosFilterTypeOfService.value(" + ((int) getValue()) + NavigationBarInflaterView.KEY_CODE_END;
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
