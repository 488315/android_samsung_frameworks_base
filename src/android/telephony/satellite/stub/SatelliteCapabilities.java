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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.supportedRadioTechnologies);
        parcel.writeBoolean(this.isPointingRequired);
        parcel.writeInt(this.maxBytesPerOutgoingDatagram);
        parcel.writeIntArray(this.antennaPositionKeys);
        parcel.writeTypedArray(this.antennaPositionValues, i);
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
                    this.isPointingRequired = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxBytesPerOutgoingDatagram = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.antennaPositionKeys = parcel.createIntArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.antennaPositionValues = (AntennaPosition[]) parcel.createTypedArray(AntennaPosition.CREATOR);
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
        return describeContents(this.antennaPositionValues);
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
