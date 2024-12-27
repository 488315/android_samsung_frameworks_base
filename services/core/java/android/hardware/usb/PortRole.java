package android.hardware.usb;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PortRole implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int _tag = 0;
    public Object _value = (byte) 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.usb.PortRole$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            PortRole portRole = new PortRole();
            int readInt = parcel.readInt();
            if (readInt == 0) {
                Byte valueOf = Byte.valueOf(parcel.readByte());
                portRole._tag = readInt;
                portRole._value = valueOf;
            } else if (readInt == 1) {
                Byte valueOf2 = Byte.valueOf(parcel.readByte());
                portRole._tag = readInt;
                portRole._value = valueOf2;
            } else {
                if (readInt != 2) {
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    readInt, "union: unknown tag: "));
                }
                Byte valueOf3 = Byte.valueOf(parcel.readByte());
                portRole._tag = readInt;
                portRole._value = valueOf3;
            }
            return portRole;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PortRole[i];
        }
    }

    public static String _tagString(int i) {
        if (i == 0) {
            return "powerRole";
        }
        if (i == 1) {
            return "dataRole";
        }
        if (i == 2) {
            return "mode";
        }
        throw new IllegalStateException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown field: "));
    }

    public final void _assertTag(int i) {
        if (this._tag == i) {
            return;
        }
        throw new IllegalStateException(
                "bad access: " + _tagString(i) + ", " + _tagString(this._tag) + " is available.");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            _assertTag(0);
            parcel.writeByte(((Byte) this._value).byteValue());
        } else if (i2 == 1) {
            _assertTag(1);
            parcel.writeByte(((Byte) this._value).byteValue());
        } else {
            if (i2 != 2) {
                return;
            }
            _assertTag(2);
            parcel.writeByte(((Byte) this._value).byteValue());
        }
    }
}
