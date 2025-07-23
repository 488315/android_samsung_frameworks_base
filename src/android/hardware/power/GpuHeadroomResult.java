package android.hardware.power;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GpuHeadroomResult implements Parcelable {
    public static final Parcelable.Creator<GpuHeadroomResult> CREATOR = new Parcelable.Creator<GpuHeadroomResult>() { // from class: android.hardware.power.GpuHeadroomResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomResult createFromParcel(Parcel parcel) {
            return new GpuHeadroomResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GpuHeadroomResult[] newArray(int i) {
            return new GpuHeadroomResult[i];
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

    public GpuHeadroomResult() {
        this._tag = 0;
        this._value = Float.valueOf(0.0f);
    }

    private GpuHeadroomResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    private GpuHeadroomResult(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static GpuHeadroomResult globalHeadroom(float f) {
        return new GpuHeadroomResult(0, Float.valueOf(f));
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
            return "GpuHeadroomResult.globalHeadroom(" + getGlobalHeadroom() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GpuHeadroomResult)) {
            return false;
        }
        GpuHeadroomResult gpuHeadroomResult = (GpuHeadroomResult) obj;
        return this._tag == gpuHeadroomResult._tag && Objects.deepEquals(this._value, gpuHeadroomResult._value);
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
