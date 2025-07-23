package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CellularIdentifierDisclosure implements Parcelable {
    public static final Parcelable.Creator<CellularIdentifierDisclosure> CREATOR = new Parcelable.Creator<CellularIdentifierDisclosure>() { // from class: android.hardware.radio.network.CellularIdentifierDisclosure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularIdentifierDisclosure createFromParcel(Parcel parcel) {
            CellularIdentifierDisclosure cellularIdentifierDisclosure = new CellularIdentifierDisclosure();
            cellularIdentifierDisclosure.readFromParcel(parcel);
            return cellularIdentifierDisclosure;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularIdentifierDisclosure[] newArray(int i) {
            return new CellularIdentifierDisclosure[i];
        }
    };
    public String plmn;
    public int identifier = 0;
    public int protocolMessage = 0;
    public boolean isEmergency = false;

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
        parcel.writeString(this.plmn);
        parcel.writeInt(this.identifier);
        parcel.writeInt(this.protocolMessage);
        parcel.writeBoolean(this.isEmergency);
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
                this.plmn = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.identifier = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.protocolMessage = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isEmergency = parcel.readBoolean();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("plmn: " + Objects.toString(this.plmn));
        stringJoiner.add("identifier: " + CellularIdentifier$$.toString(this.identifier));
        stringJoiner.add("protocolMessage: " + NasProtocolMessage$$.toString(this.protocolMessage));
        stringJoiner.add("isEmergency: " + this.isEmergency);
        return "CellularIdentifierDisclosure" + stringJoiner.toString();
    }
}
