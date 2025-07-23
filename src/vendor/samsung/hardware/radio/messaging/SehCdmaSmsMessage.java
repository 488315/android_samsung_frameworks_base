package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCdmaSmsMessage implements Parcelable {
    public static final Parcelable.Creator<SehCdmaSmsMessage> CREATOR = new Parcelable.Creator<SehCdmaSmsMessage>() { // from class: vendor.samsung.hardware.radio.messaging.SehCdmaSmsMessage.1
        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsMessage createFromParcel(Parcel parcel) {
            SehCdmaSmsMessage sehCdmaSmsMessage = new SehCdmaSmsMessage();
            sehCdmaSmsMessage.readFromParcel(parcel);
            return sehCdmaSmsMessage;
        }

        @Override // android.os.Parcelable.Creator
        public SehCdmaSmsMessage[] newArray(int i) {
            return new SehCdmaSmsMessage[i];
        }
    };
    public SehCdmaSmsAddress address;
    public byte[] bearerData;
    public SehCdmaSmsSubaddress subAddress;
    public int teleserviceId = 0;
    public boolean isServicePresent = false;
    public int serviceCategory = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.teleserviceId);
        parcel.writeBoolean(this.isServicePresent);
        parcel.writeInt(this.serviceCategory);
        parcel.writeTypedObject(this.address, i);
        parcel.writeTypedObject(this.subAddress, i);
        parcel.writeByteArray(this.bearerData);
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
                this.teleserviceId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isServicePresent = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.serviceCategory = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.address = (SehCdmaSmsAddress) parcel.readTypedObject(SehCdmaSmsAddress.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.subAddress = (SehCdmaSmsSubaddress) parcel.readTypedObject(SehCdmaSmsSubaddress.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.bearerData = parcel.createByteArray();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("teleserviceId: " + this.teleserviceId);
        stringJoiner.add("isServicePresent: " + this.isServicePresent);
        stringJoiner.add("serviceCategory: " + this.serviceCategory);
        stringJoiner.add("address: " + Objects.toString(this.address));
        stringJoiner.add("subAddress: " + Objects.toString(this.subAddress));
        stringJoiner.add("bearerData: " + Arrays.toString(this.bearerData));
        return "SehCdmaSmsMessage" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.subAddress) | describeContents(this.address);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
