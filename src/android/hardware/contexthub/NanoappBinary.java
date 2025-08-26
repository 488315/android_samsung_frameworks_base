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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.nanoappVersion);
        parcel.writeInt(this.flags);
        parcel.writeByte(this.targetChreApiMajorVersion);
        parcel.writeByte(this.targetChreApiMinorVersion);
        parcel.writeByteArray(this.customBinary);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.nanoappVersion = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.flags = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.targetChreApiMajorVersion = parcel.readByte();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.targetChreApiMinorVersion = parcel.readByte();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.customBinary = parcel.createByteArray();
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
}
