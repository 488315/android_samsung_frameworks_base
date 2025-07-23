package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSmsMessage implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsMessage> CREATOR = new Parcelable.Creator<CdmaSmsMessage>() { // from class: android.hardware.radio.messaging.CdmaSmsMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsMessage createFromParcel(Parcel parcel) {
            CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
            cdmaSmsMessage.readFromParcel(parcel);
            return cdmaSmsMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsMessage[] newArray(int i) {
            return new CdmaSmsMessage[i];
        }
    };

    @Deprecated
    public CdmaSmsAddress address;

    @Deprecated
    public byte[] bearerData;

    @Deprecated
    public CdmaSmsSubaddress subAddress;

    @Deprecated
    public int teleserviceId = 0;

    @Deprecated
    public boolean isServicePresent = false;

    @Deprecated
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
                            this.address = (CdmaSmsAddress) parcel.readTypedObject(CdmaSmsAddress.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.subAddress = (CdmaSmsSubaddress) parcel.readTypedObject(CdmaSmsSubaddress.CREATOR);
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
        return "CdmaSmsMessage" + stringJoiner.toString();
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
