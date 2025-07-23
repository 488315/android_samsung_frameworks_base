package vendor.samsung.hardware.radio.satelliteservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehAntennaDirection implements Parcelable {
    public static final Parcelable.Creator<SehAntennaDirection> CREATOR = new Parcelable.Creator<SehAntennaDirection>() { // from class: vendor.samsung.hardware.radio.satelliteservice.SehAntennaDirection.1
        @Override // android.os.Parcelable.Creator
        public SehAntennaDirection createFromParcel(Parcel parcel) {
            SehAntennaDirection sehAntennaDirection = new SehAntennaDirection();
            sehAntennaDirection.readFromParcel(parcel);
            return sehAntennaDirection;
        }

        @Override // android.os.Parcelable.Creator
        public SehAntennaDirection[] newArray(int i) {
            return new SehAntennaDirection[i];
        }
    };
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;

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
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
        parcel.writeFloat(this.z);
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
                this.x = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.y = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.z = parcel.readFloat();
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
}
