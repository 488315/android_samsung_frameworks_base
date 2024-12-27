package android.net;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class InterfaceConfigurationParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String[] flags;
    public String hwAddr;
    public String ifName;
    public String ipv4Addr;
    public int prefixLength = 0;

    /* renamed from: android.net.InterfaceConfigurationParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            InterfaceConfigurationParcel interfaceConfigurationParcel =
                    new InterfaceConfigurationParcel();
            interfaceConfigurationParcel.readFromParcel(parcel);
            return interfaceConfigurationParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new InterfaceConfigurationParcel[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.ifName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.hwAddr = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.ipv4Addr = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.prefixLength = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.flags = parcel.createStringArray();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                                parcel.setDataPosition(dataPosition + readInt);
                                return;
                            }
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.ifName);
        parcel.writeString(this.hwAddr);
        parcel.writeString(this.ipv4Addr);
        parcel.writeInt(this.prefixLength);
        parcel.writeStringArray(this.flags);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
