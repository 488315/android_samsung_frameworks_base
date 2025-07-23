package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehEriInfo implements Parcelable {
    public static final Parcelable.Creator<SehEriInfo> CREATOR = new Parcelable.Creator<SehEriInfo>() { // from class: vendor.samsung.hardware.radio.network.SehEriInfo.1
        @Override // android.os.Parcelable.Creator
        public SehEriInfo createFromParcel(Parcel parcel) {
            SehEriInfo sehEriInfo = new SehEriInfo();
            sehEriInfo.readFromParcel(parcel);
            return sehEriInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehEriInfo[] newArray(int i) {
            return new SehEriInfo[i];
        }
    };
    public String eriText;
    public byte roamingIndicator = 0;
    public byte iconIndex = 0;
    public byte iconMode = 0;

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
        parcel.writeByte(this.roamingIndicator);
        parcel.writeByte(this.iconIndex);
        parcel.writeByte(this.iconMode);
        parcel.writeString(this.eriText);
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
                this.roamingIndicator = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.iconIndex = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.iconMode = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.eriText = parcel.readString();
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
        stringJoiner.add("roamingIndicator: " + ((int) this.roamingIndicator));
        stringJoiner.add("iconIndex: " + ((int) this.iconIndex));
        stringJoiner.add("iconMode: " + ((int) this.iconMode));
        stringJoiner.add("eriText: " + Objects.toString(this.eriText));
        return "SehEriInfo" + stringJoiner.toString();
    }
}
