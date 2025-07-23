package android.hardware.power;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CpuHeadroomResult implements Parcelable {
    public static final Parcelable.Creator<CpuHeadroomResult> CREATOR = new Parcelable.Creator<CpuHeadroomResult>() { // from class: android.hardware.power.CpuHeadroomResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomResult createFromParcel(Parcel parcel) {
            return new CpuHeadroomResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CpuHeadroomResult[] newArray(int i) {
            return new CpuHeadroomResult[i];
        }
    };
    public static final int globalHeadroom = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int globalHeadroom = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public CpuHeadroomResult() {
        this._tag = 0;
        this._value = Float.valueOf(0.0f);
    }

    private CpuHeadroomResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    private CpuHeadroomResult(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static CpuHeadroomResult globalHeadroom(float f) {
        return new CpuHeadroomResult(0, Float.valueOf(f));
    }

    public float getGlobalHeadroom() {
        _assertTag(0);
        return ((Float) this._value).floatValue();
    }

    public void setGlobalHeadroom(float f) {
        _set(0, Float.valueOf(f));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        if (this._tag != 0) {
            return;
        }
        parcel.writeFloat(getGlobalHeadroom());
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Float.valueOf(parcel.readFloat()));
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
        if (this._tag == 0) {
            return "CpuHeadroomResult.globalHeadroom(" + getGlobalHeadroom() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CpuHeadroomResult)) {
            return false;
        }
        CpuHeadroomResult cpuHeadroomResult = (CpuHeadroomResult) obj;
        return this._tag == cpuHeadroomResult._tag && Objects.deepEquals(this._value, cpuHeadroomResult._value);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this._tag), this._value).toArray());
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "globalHeadroom";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
