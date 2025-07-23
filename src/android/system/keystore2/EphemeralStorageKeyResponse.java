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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.ephemeralKey);
        parcel.writeByteArray(this.upgradedBlob);
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
                this.ephemeralKey = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.upgradedBlob = parcel.createByteArray();
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
