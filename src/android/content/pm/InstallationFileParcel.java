package android.content.pm;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class InstallationFileParcel implements Parcelable {
    public static final Parcelable.Creator<InstallationFileParcel> CREATOR = new Parcelable.Creator<InstallationFileParcel>() { // from class: android.content.pm.InstallationFileParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallationFileParcel createFromParcel(Parcel parcel) {
            InstallationFileParcel installationFileParcel = new InstallationFileParcel();
            installationFileParcel.readFromParcel(parcel);
            return installationFileParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstallationFileParcel[] newArray(int i) {
            return new InstallationFileParcel[i];
        }
    };
    public byte[] metadata;
    public String name;
    public byte[] signature;
    public int location = -1;
    public long size = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeInt(this.location);
        parcel.writeLong(this.size);
        parcel.writeByteArray(this.metadata);
        parcel.writeByteArray(this.signature);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.location = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.size = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.metadata = parcel.createByteArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.signature = parcel.createByteArray();
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
