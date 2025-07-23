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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iSecurityLevel);
        parcel.writeTypedObject(this.metadata, i);
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
                this.iSecurityLevel = IKeystoreSecurityLevel.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.metadata = (KeyMetadata) parcel.readTypedObject(KeyMetadata.CREATOR);
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
