package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class NanoappBinary implements Parcelable {
    public static final Parcelable.Creator<NanoappBinary> CREATOR = new Parcelable.Creator<NanoappBinary>() { // from class: android.hardware.contexthub.NanoappBinary.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoappBinary createFromParcel(Parcel parcel) {
            NanoappBinary nanoappBinary = new NanoappBinary();
            nanoappBinary.readFromParcel(parcel);
            return nanoappBinary;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoappBinary[] newArray(int i) {
            return new NanoappBinary[i];
        }
    };
    public static final int FLAG_ENCRYPTED = 2;
    public static final int FLAG_SIGNED = 1;
    public static final int FLAG_TCM_CAPABLE = 4;
    public byte[] customBinary;
    public long nanoappId = 0;
    public int nanoappVersion = 0;
    public int flags = 0;
    public byte targetChreApiMajorVersion = 0;
    public byte targetChreApiMinorVersion = 0;

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
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.nanoappVersion);
        parcel.writeInt(this.flags);
        parcel.writeByte(this.targetChreApiMajorVersion);
        parcel.writeByte(this.targetChreApiMinorVersion);
        parcel.writeByteArray(this.customBinary);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.nanoappVersion = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.flags = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.targetChreApiMajorVersion = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.targetChreApiMinorVersion = parcel.readByte();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.customBinary = parcel.createByteArray();
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
}
