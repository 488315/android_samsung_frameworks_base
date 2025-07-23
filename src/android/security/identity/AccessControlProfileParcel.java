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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeByteArray(this.readerCertificate);
        parcel.writeBoolean(this.userAuthenticationRequired);
        parcel.writeLong(this.userAuthenticationTimeoutMillis);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.readerCertificate = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.userAuthenticationRequired = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.userAuthenticationTimeoutMillis = parcel.readLong();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
}
