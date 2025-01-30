package vendor.samsung.hardware.khdm;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SehDeviceInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: vendor.samsung.hardware.khdm.SehDeviceInfo.1
        @Override // android.os.Parcelable.Creator
        public SehDeviceInfo createFromParcel(Parcel parcel) {
            SehDeviceInfo sehDeviceInfo = new SehDeviceInfo();
            sehDeviceInfo.readFromParcel(parcel);
            return sehDeviceInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehDeviceInfo[] newArray(int i) {
            return new SehDeviceInfo[i];
        }
    };
    public byte[] hashedImei;
    public byte[] imei0;
    public boolean isWrappedKey = false;
    public byte[] macAddr;
    public byte[] serialNumber;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.serialNumber);
        parcel.writeByteArray(this.imei0);
        parcel.writeByteArray(this.hashedImei);
        parcel.writeByteArray(this.macAddr);
        parcel.writeBoolean(this.isWrappedKey);
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
                this.serialNumber = parcel.createByteArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.imei0 = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.hashedImei = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.macAddr = parcel.createByteArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.isWrappedKey = parcel.readBoolean();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                                parcel.setDataPosition(dataPosition + readInt);
                                return;
                            }
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
