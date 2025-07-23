package vendor.samsung.hardware.radio.satelliteservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSatelliteCapabilities implements Parcelable {
    public static final Parcelable.Creator<SehSatelliteCapabilities> CREATOR = new Parcelable.Creator<SehSatelliteCapabilities>() { // from class: vendor.samsung.hardware.radio.satelliteservice.SehSatelliteCapabilities.1
        @Override // android.os.Parcelable.Creator
        public SehSatelliteCapabilities createFromParcel(Parcel parcel) {
            SehSatelliteCapabilities sehSatelliteCapabilities = new SehSatelliteCapabilities();
            sehSatelliteCapabilities.readFromParcel(parcel);
            return sehSatelliteCapabilities;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatelliteCapabilities[] newArray(int i) {
            return new SehSatelliteCapabilities[i];
        }
    };
    public SehAntennaPosition[] antennaPositionMap;
    public int isPointingRequired = 0;
    public int maxBytesPerOutgoingDatagram = 0;
    public int[] supportedRadioTechnologies;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.supportedRadioTechnologies);
        parcel.writeInt(this.isPointingRequired);
        parcel.writeInt(this.maxBytesPerOutgoingDatagram);
        parcel.writeTypedArray(this.antennaPositionMap, i);
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
                this.supportedRadioTechnologies = parcel.createIntArray();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isPointingRequired = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxBytesPerOutgoingDatagram = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.antennaPositionMap = (SehAntennaPosition[]) parcel.createTypedArray(SehAntennaPosition.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.antennaPositionMap);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
