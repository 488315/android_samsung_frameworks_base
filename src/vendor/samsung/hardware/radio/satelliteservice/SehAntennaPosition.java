package vendor.samsung.hardware.radio.satelliteservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehAntennaPosition implements Parcelable {
    public static final Parcelable.Creator<SehAntennaPosition> CREATOR = new Parcelable.Creator<SehAntennaPosition>() { // from class: vendor.samsung.hardware.radio.satelliteservice.SehAntennaPosition.1
        @Override // android.os.Parcelable.Creator
        public SehAntennaPosition createFromParcel(Parcel parcel) {
            SehAntennaPosition sehAntennaPosition = new SehAntennaPosition();
            sehAntennaPosition.readFromParcel(parcel);
            return sehAntennaPosition;
        }

        @Override // android.os.Parcelable.Creator
        public SehAntennaPosition[] newArray(int i) {
            return new SehAntennaPosition[i];
        }
    };
    public SehAntennaDirection antennaDirection;
    public int key = 0;
    public int suggestedHoldPosition = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.key);
        parcel.writeTypedObject(this.antennaDirection, i);
        parcel.writeInt(this.suggestedHoldPosition);
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
                this.key = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.antennaDirection = (SehAntennaDirection) parcel.readTypedObject(SehAntennaDirection.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.suggestedHoldPosition = parcel.readInt();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.antennaDirection);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
