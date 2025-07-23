package android.system.keystore2;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyDescriptor implements Parcelable {
    public static final Parcelable.Creator<KeyDescriptor> CREATOR = new Parcelable.Creator<KeyDescriptor>() { // from class: android.system.keystore2.KeyDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyDescriptor createFromParcel(Parcel parcel) {
            KeyDescriptor keyDescriptor = new KeyDescriptor();
            keyDescriptor.readFromParcel(parcel);
            return keyDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyDescriptor[] newArray(int i) {
            return new KeyDescriptor[i];
        }
    };
    public String alias;
    public byte[] blob;
    public int domain = 0;
    public long nspace = 0;

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
        parcel.writeInt(this.domain);
        parcel.writeLong(this.nspace);
        parcel.writeString(this.alias);
        parcel.writeByteArray(this.blob);
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
                this.domain = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.nspace = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.alias = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.blob = parcel.createByteArray();
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
