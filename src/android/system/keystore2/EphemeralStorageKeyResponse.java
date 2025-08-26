package android.system.keystore2;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class EphemeralStorageKeyResponse implements Parcelable {
    public static final Parcelable.Creator<EphemeralStorageKeyResponse> CREATOR = new Parcelable.Creator<EphemeralStorageKeyResponse>() { // from class: android.system.keystore2.EphemeralStorageKeyResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EphemeralStorageKeyResponse createFromParcel(Parcel parcel) {
            EphemeralStorageKeyResponse ephemeralStorageKeyResponse = new EphemeralStorageKeyResponse();
            ephemeralStorageKeyResponse.readFromParcel(parcel);
            return ephemeralStorageKeyResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EphemeralStorageKeyResponse[] newArray(int i) {
            return new EphemeralStorageKeyResponse[i];
        }
    };
    public byte[] ephemeralKey;
    public byte[] upgradedBlob;

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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.ephemeralKey);
        parcel.writeByteArray(this.upgradedBlob);
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
                this.ephemeralKey = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.upgradedBlob = parcel.createByteArray();
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
