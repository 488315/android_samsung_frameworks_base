package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatIotRegState implements Parcelable {
    public static final Parcelable.Creator<SehSatIotRegState> CREATOR = new Parcelable.Creator<SehSatIotRegState>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatIotRegState.1
        @Override // android.os.Parcelable.Creator
        public SehSatIotRegState createFromParcel(Parcel parcel) {
            SehSatIotRegState sehSatIotRegState = new SehSatIotRegState();
            sehSatIotRegState.readFromParcel(parcel);
            return sehSatIotRegState;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatIotRegState[] newArray(int i) {
            return new SehSatIotRegState[i];
        }
    };
    public int reserved = 0;
    public int state = 0;
    public int regType = 0;

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
        parcel.writeInt(this.reserved);
        parcel.writeInt(this.state);
        parcel.writeInt(this.regType);
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
                this.reserved = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.state = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.regType = parcel.readInt();
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
        stringJoiner.add("reserved: " + this.reserved);
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("regType: " + this.regType);
        return "SehSatIotRegState" + stringJoiner.toString();
    }
}
