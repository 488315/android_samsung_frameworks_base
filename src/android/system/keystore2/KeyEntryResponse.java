package android.system.keystore2;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.keystore2.IKeystoreSecurityLevel;

/* loaded from: classes3.dex */
public class KeyEntryResponse implements Parcelable {
    public static final Parcelable.Creator<KeyEntryResponse> CREATOR = new Parcelable.Creator<KeyEntryResponse>() { // from class: android.system.keystore2.KeyEntryResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyEntryResponse createFromParcel(Parcel parcel) {
            KeyEntryResponse keyEntryResponse = new KeyEntryResponse();
            keyEntryResponse.readFromParcel(parcel);
            return keyEntryResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyEntryResponse[] newArray(int i) {
            return new KeyEntryResponse[i];
        }
    };
    public IKeystoreSecurityLevel iSecurityLevel;
    public KeyMetadata metadata;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iSecurityLevel);
        parcel.writeTypedObject(this.metadata, i);
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
                this.iSecurityLevel = IKeystoreSecurityLevel.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.metadata = (KeyMetadata) parcel.readTypedObject(KeyMetadata.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.metadata);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
