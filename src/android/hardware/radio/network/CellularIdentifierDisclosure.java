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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.plmn);
        parcel.writeInt(this.identifier);
        parcel.writeInt(this.protocolMessage);
        parcel.writeBoolean(this.isEmergency);
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
                this.plmn = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.identifier = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.protocolMessage = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isEmergency = parcel.readBoolean();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
