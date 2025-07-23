package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableHolder;

/* loaded from: classes2.dex */
public final class AuthenticateReason implements Parcelable {
    public static final Parcelable.Creator<AuthenticateReason> CREATOR = new Parcelable.Creator<AuthenticateReason>() { // from class: android.hardware.biometrics.common.AuthenticateReason.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticateReason createFromParcel(Parcel parcel) {
            return new AuthenticateReason(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticateReason[] newArray(int i) {
            return new AuthenticateReason[i];
        }
    };
    public static final int faceAuthenticateReason = 1;
    public static final int fingerprintAuthenticateReason = 2;
    public static final int vendorAuthenticateReason = 0;
    private int _tag;
    private Object _value;

    public @interface Face {
        public static final int ALTERNATE_BIOMETRIC_BOUNCER_SHOWN = 4;
        public static final int ASSISTANT_VISIBLE = 3;
        public static final int NOTIFICATION_PANEL_CLICKED = 5;
        public static final int OCCLUDING_APP_REQUESTED = 6;
        public static final int PICK_UP_GESTURE_TRIGGERED = 7;
        public static final int PRIMARY_BOUNCER_SHOWN = 2;
        public static final int QS_EXPANDED = 8;
        public static final int STARTED_WAKING_UP = 1;
        public static final int SWIPE_UP_ON_BOUNCER = 9;
        public static final int UDFPS_POINTER_DOWN = 10;
        public static final int UNKNOWN = 0;
    }

    public @interface Fingerprint {
        public static final int UNKNOWN = 0;
    }

    public @interface Tag {
        public static final int faceAuthenticateReason = 1;
        public static final int fingerprintAuthenticateReason = 2;
        public static final int vendorAuthenticateReason = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AuthenticateReason() {
        this._tag = 0;
        this._value = null;
    }

    private AuthenticateReason(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AuthenticateReason(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AuthenticateReason vendorAuthenticateReason(Vendor vendor2) {
        return new AuthenticateReason(0, vendor2);
    }

    public Vendor getVendorAuthenticateReason() {
        _assertTag(0);
        return (Vendor) this._value;
    }

    public void setVendorAuthenticateReason(Vendor vendor2) {
        _set(0, vendor2);
    }

    public static AuthenticateReason faceAuthenticateReason(int i) {
        return new AuthenticateReason(1, Integer.valueOf(i));
    }

    public int getFaceAuthenticateReason() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setFaceAuthenticateReason(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static AuthenticateReason fingerprintAuthenticateReason(int i) {
        return new AuthenticateReason(2, Integer.valueOf(i));
    }

    public int getFingerprintAuthenticateReason() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setFingerprintAuthenticateReason(int i) {
        _set(2, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getVendorAuthenticateReason(), i);
        } else if (i2 == 1) {
            parcel.writeInt(getFaceAuthenticateReason());
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeInt(getFingerprintAuthenticateReason());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, (Vendor) parcel.readTypedObject(Vendor.CREATOR));
            return;
        }
        if (readInt == 1) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else if (readInt == 2) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (getTag() != 0) {
            return 0;
        }
        return describeContents(getVendorAuthenticateReason());
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
            return "vendorAuthenticateReason";
        }
        if (i == 1) {
            return "faceAuthenticateReason";
        }
        if (i == 2) {
            return "fingerprintAuthenticateReason";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class Vendor implements Parcelable {
        public static final Parcelable.Creator<Vendor> CREATOR = new Parcelable.Creator<Vendor>() { // from class: android.hardware.biometrics.common.AuthenticateReason.Vendor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Vendor createFromParcel(Parcel parcel) {
                Vendor vendor2 = new Vendor();
                vendor2.readFromParcel(parcel);
                return vendor2;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Vendor[] newArray(int i) {
                return new Vendor[i];
            }
        };
        public final ParcelableHolder extension = new ParcelableHolder(1);

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.extension, 0);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    if (parcel.readInt() != 0) {
                        this.extension.readFromParcel(parcel);
                    }
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.extension);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
