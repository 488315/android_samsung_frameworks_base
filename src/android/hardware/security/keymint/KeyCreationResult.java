package android.hardware.security.keymint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class KeyCreationResult implements Parcelable {
    public static final Parcelable.Creator<KeyCreationResult> CREATOR = new Parcelable.Creator<KeyCreationResult>() { // from class: android.hardware.security.keymint.KeyCreationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationResult createFromParcel(Parcel parcel) {
            KeyCreationResult keyCreationResult = new KeyCreationResult();
            keyCreationResult.readFromParcel(parcel);
            return keyCreationResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationResult[] newArray(int i) {
            return new KeyCreationResult[i];
        }
    };
    public Certificate[] certificateChain;
    public byte[] keyBlob;
    public KeyCharacteristics[] keyCharacteristics;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.keyBlob);
        parcel.writeTypedArray(this.keyCharacteristics, i);
        parcel.writeTypedArray(this.certificateChain, i);
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
                this.keyBlob = parcel.createByteArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.keyCharacteristics = (KeyCharacteristics[]) parcel.createTypedArray(KeyCharacteristics.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.certificateChain = (Certificate[]) parcel.createTypedArray(Certificate.CREATOR);
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.certificateChain) | describeContents(this.keyCharacteristics);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
