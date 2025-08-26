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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.supportedRadioTechnologies);
        parcel.writeInt(this.isPointingRequired);
        parcel.writeInt(this.maxBytesPerOutgoingDatagram);
        parcel.writeTypedArray(this.antennaPositionMap, i);
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
                this.supportedRadioTechnologies = parcel.createIntArray();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isPointingRequired = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxBytesPerOutgoingDatagram = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.antennaPositionMap = (SehAntennaPosition[]) parcel.createTypedArray(SehAntennaPosition.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.antennaPositionMap);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
