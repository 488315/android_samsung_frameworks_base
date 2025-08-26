package android.security.identity;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SecurityHardwareInfoParcel implements Parcelable {
    public static final Parcelable.Creator<SecurityHardwareInfoParcel> CREATOR = new Parcelable.Creator<SecurityHardwareInfoParcel>() { // from class: android.security.identity.SecurityHardwareInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityHardwareInfoParcel createFromParcel(Parcel parcel) {
            SecurityHardwareInfoParcel securityHardwareInfoParcel = new SecurityHardwareInfoParcel();
            securityHardwareInfoParcel.readFromParcel(parcel);
            return securityHardwareInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityHardwareInfoParcel[] newArray(int i) {
            return new SecurityHardwareInfoParcel[i];
        }
    };
    public boolean directAccess = false;
    public String[] supportedDocTypes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.directAccess);
        parcel.writeStringArray(this.supportedDocTypes);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.directAccess = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.supportedDocTypes = parcel.createStringArray();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
