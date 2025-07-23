package android.hardware.cas;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class Status implements Parcelable {
    public static final int BAD_VALUE = 6;
    public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator<Status>() { // from class: android.hardware.cas.Status.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Status createFromParcel(Parcel parcel) {
            Status status = new Status();
            status.readFromParcel(parcel);
            return status;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Status[] newArray(int i) {
            return new Status[i];
        }
    };
    public static final int ERROR_CAS_BLACKOUT = 20;
    public static final int ERROR_CAS_CANNOT_HANDLE = 4;
    public static final int ERROR_CAS_CARD_INVALID = 19;
    public static final int ERROR_CAS_CARD_MUTE = 18;
    public static final int ERROR_CAS_DECRYPT = 13;
    public static final int ERROR_CAS_DECRYPT_UNIT_NOT_INITIALIZED = 12;
    public static final int ERROR_CAS_DEVICE_REVOKED = 11;
    public static final int ERROR_CAS_INSUFFICIENT_OUTPUT_PROTECTION = 9;
    public static final int ERROR_CAS_INVALID_STATE = 5;
    public static final int ERROR_CAS_LICENSE_EXPIRED = 2;
    public static final int ERROR_CAS_NEED_ACTIVATION = 15;
    public static final int ERROR_CAS_NEED_PAIRING = 16;
    public static final int ERROR_CAS_NOT_PROVISIONED = 7;
    public static final int ERROR_CAS_NO_CARD = 17;
    public static final int ERROR_CAS_NO_LICENSE = 1;
    public static final int ERROR_CAS_REBOOTING = 21;
    public static final int ERROR_CAS_RESOURCE_BUSY = 8;
    public static final int ERROR_CAS_SESSION_NOT_OPENED = 3;
    public static final int ERROR_CAS_TAMPER_DETECTED = 10;
    public static final int ERROR_CAS_UNKNOWN = 14;
    public static final int OK = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt >= 4) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } else {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }
}
