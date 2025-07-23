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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.reserved);
        parcel.writeInt(this.state);
        parcel.writeInt(this.regType);
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
                this.reserved = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.state = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.regType = parcel.readInt();
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
        stringJoiner.add("reserved: " + this.reserved);
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("regType: " + this.regType);
        return "SehSatIotRegState" + stringJoiner.toString();
    }
}
