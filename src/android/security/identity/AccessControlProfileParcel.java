package android.security.identity;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class AccessControlProfileParcel implements Parcelable {
    public static final Parcelable.Creator<AccessControlProfileParcel> CREATOR = new Parcelable.Creator<AccessControlProfileParcel>() { // from class: android.security.identity.AccessControlProfileParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessControlProfileParcel createFromParcel(Parcel parcel) {
            AccessControlProfileParcel accessControlProfileParcel = new AccessControlProfileParcel();
            accessControlProfileParcel.readFromParcel(parcel);
            return accessControlProfileParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessControlProfileParcel[] newArray(int i) {
            return new AccessControlProfileParcel[i];
        }
    };
    public byte[] readerCertificate;
    public int id = 0;
    public boolean userAuthenticationRequired = false;
    public long userAuthenticationTimeoutMillis = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeByteArray(this.readerCertificate);
        parcel.writeBoolean(this.userAuthenticationRequired);
        parcel.writeLong(this.userAuthenticationTimeoutMillis);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.readerCertificate = parcel.createByteArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.userAuthenticationRequired = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.userAuthenticationTimeoutMillis = parcel.readLong();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
