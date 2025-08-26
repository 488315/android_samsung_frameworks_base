package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.satellite.AntennaPosition;

/* loaded from: classes4.dex */
public class SatelliteCapabilities implements Parcelable {
    public static final Parcelable.Creator<SatelliteCapabilities> CREATOR = new Parcelable.Creator<SatelliteCapabilities>() { // from class: android.telephony.satellite.stub.SatelliteCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities createFromParcel(Parcel parcel) {
            SatelliteCapabilities satelliteCapabilities = new SatelliteCapabilities();
            satelliteCapabilities.readFromParcel(parcel);
            return satelliteCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities[] newArray(int i) {
            return new SatelliteCapabilities[i];
        }
    };
    public int[] antennaPositionKeys;
    public AntennaPosition[] antennaPositionValues;
    public boolean isPointingRequired = false;
    public int maxBytesPerOutgoingDatagram = 0;
    public int[] supportedRadioTechnologies;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.supportedRadioTechnologies);
        parcel.writeBoolean(this.isPointingRequired);
        parcel.writeInt(this.maxBytesPerOutgoingDatagram);
        parcel.writeIntArray(this.antennaPositionKeys);
        parcel.writeTypedArray(this.antennaPositionValues, i);
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
                    this.isPointingRequired = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxBytesPerOutgoingDatagram = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.antennaPositionKeys = parcel.createIntArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.antennaPositionValues = (AntennaPosition[]) parcel.createTypedArray(AntennaPosition.CREATOR);
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
        return describeContents(this.antennaPositionValues);
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
