package vendor.samsung.hardware.khdm;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public final class SehDeviceInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public byte[] hashedImei;
    public byte[] imei0;
    public boolean isWrappedKey = false;
    public byte[] macAddr;
    public byte[] serialNumber;

    /* renamed from: vendor.samsung.hardware.khdm.SehDeviceInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            SehDeviceInfo sehDeviceInfo = new SehDeviceInfo();
            sehDeviceInfo.readFromParcel(parcel);
            return sehDeviceInfo;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SehDeviceInfo[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
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
        parcel.writeByteArray(this.serialNumber);
        parcel.writeByteArray(this.imei0);
        parcel.writeByteArray(this.hashedImei);
        parcel.writeByteArray(this.macAddr);
        parcel.writeBoolean(this.isWrappedKey);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
