package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehArfcnInfo implements Parcelable {
    public static final Parcelable.Creator<SehArfcnInfo> CREATOR = new Parcelable.Creator<SehArfcnInfo>() { // from class: vendor.samsung.hardware.radio.network.SehArfcnInfo.1
        @Override // android.os.Parcelable.Creator
        public SehArfcnInfo createFromParcel(Parcel parcel) {
            SehArfcnInfo sehArfcnInfo = new SehArfcnInfo();
            sehArfcnInfo.readFromParcel(parcel);
            return sehArfcnInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehArfcnInfo[] newArray(int i) {
            return new SehArfcnInfo[i];
        }
    };
    public byte rat = 0;
    public int band = 0;
    public int arfcn = 0;

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
        parcel.writeByte(this.rat);
        parcel.writeInt(this.band);
        parcel.writeInt(this.arfcn);
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
                this.rat = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.band = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.arfcn = parcel.readInt();
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
        stringJoiner.add("rat: " + ((int) this.rat));
        stringJoiner.add("band: " + this.band);
        stringJoiner.add("arfcn: " + this.arfcn);
        return "SehArfcnInfo" + stringJoiner.toString();
    }
}
