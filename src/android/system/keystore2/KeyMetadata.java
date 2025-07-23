package android.system.keystore2;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyMetadata implements Parcelable {
    public static final Parcelable.Creator<KeyMetadata> CREATOR = new Parcelable.Creator<KeyMetadata>() { // from class: android.system.keystore2.KeyMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMetadata createFromParcel(Parcel parcel) {
            KeyMetadata keyMetadata = new KeyMetadata();
            keyMetadata.readFromParcel(parcel);
            return keyMetadata;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMetadata[] newArray(int i) {
            return new KeyMetadata[i];
        }
    };
    public Authorization[] authorizations;
    public byte[] certificate;
    public byte[] certificateChain;
    public KeyDescriptor key;
    public int keySecurityLevel = 0;
    public long modificationTimeMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.key, i);
        parcel.writeInt(this.keySecurityLevel);
        parcel.writeTypedArray(this.authorizations, i);
        parcel.writeByteArray(this.certificate);
        parcel.writeByteArray(this.certificateChain);
        parcel.writeLong(this.modificationTimeMs);
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
                this.key = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.keySecurityLevel = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.authorizations = (Authorization[]) parcel.createTypedArray(Authorization.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.certificate = parcel.createByteArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.certificateChain = parcel.createByteArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.modificationTimeMs = parcel.readLong();
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
        return describeContents(this.authorizations) | describeContents(this.key);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
