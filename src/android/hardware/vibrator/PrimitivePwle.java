package android.hardware.vibrator;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PrimitivePwle implements Parcelable {
    public static final Parcelable.Creator<PrimitivePwle> CREATOR = new Parcelable.Creator<PrimitivePwle>() { // from class: android.hardware.vibrator.PrimitivePwle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrimitivePwle createFromParcel(Parcel parcel) {
            return new PrimitivePwle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrimitivePwle[] newArray(int i) {
            return new PrimitivePwle[i];
        }
    };
    public static final int active = 0;
    public static final int braking = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int active = 0;
        public static final int braking = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public PrimitivePwle() {
        this._tag = 0;
        this._value = null;
    }

    private PrimitivePwle(Parcel parcel) {
        readFromParcel(parcel);
    }

    private PrimitivePwle(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static PrimitivePwle active(ActivePwle activePwle) {
        return new PrimitivePwle(0, activePwle);
    }

    public ActivePwle getActive() {
        _assertTag(0);
        return (ActivePwle) this._value;
    }

    public void setActive(ActivePwle activePwle) {
        _set(0, activePwle);
    }

    public static PrimitivePwle braking(BrakingPwle brakingPwle) {
        return new PrimitivePwle(1, brakingPwle);
    }

    public BrakingPwle getBraking() {
        _assertTag(1);
        return (BrakingPwle) this._value;
    }

    public void setBraking(BrakingPwle brakingPwle) {
        _set(1, brakingPwle);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getActive(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getBraking(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (ActivePwle) parcel.readTypedObject(ActivePwle.CREATOR));
        } else if (i == 1) {
            _set(i, (BrakingPwle) parcel.readTypedObject(BrakingPwle.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getActive());
        }
        if (tag != 1) {
            return 0;
        }
        return describeContents(getBraking());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
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
            return "active";
        }
        if (i == 1) {
            return "braking";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
