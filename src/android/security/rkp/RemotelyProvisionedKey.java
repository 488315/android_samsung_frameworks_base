package android.security.rkp;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class RemotelyProvisionedKey implements Parcelable {
    public static final Parcelable.Creator<RemotelyProvisionedKey> CREATOR = new Parcelable.Creator<RemotelyProvisionedKey>() { // from class: android.security.rkp.RemotelyProvisionedKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemotelyProvisionedKey createFromParcel(Parcel parcel) {
            RemotelyProvisionedKey remotelyProvisionedKey = new RemotelyProvisionedKey();
            remotelyProvisionedKey.readFromParcel(parcel);
            return remotelyProvisionedKey;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemotelyProvisionedKey[] newArray(int i) {
            return new RemotelyProvisionedKey[i];
        }
    };
    public byte[] encodedCertChain;
    public byte[] keyBlob;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.keyBlob);
        parcel.writeByteArray(this.encodedCertChain);
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
                this.keyBlob = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.encodedCertChain = parcel.createByteArray();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
