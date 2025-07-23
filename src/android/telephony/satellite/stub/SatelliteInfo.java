package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SatelliteInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteInfo> CREATOR = new Parcelable.Creator<SatelliteInfo>() { // from class: android.telephony.satellite.stub.SatelliteInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteInfo createFromParcel(Parcel parcel) {
            SatelliteInfo satelliteInfo = new SatelliteInfo();
            satelliteInfo.readFromParcel(parcel);
            return satelliteInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteInfo[] newArray(int i) {
            return new SatelliteInfo[i];
        }
    };
    public int[] bands;
    public EarfcnRange[] earfcnRanges;
    public UUID id;
    public SatellitePosition position;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.id, i);
        parcel.writeTypedObject(this.position, i);
        parcel.writeIntArray(this.bands);
        parcel.writeTypedArray(this.earfcnRanges, i);
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
                this.id = (UUID) parcel.readTypedObject(UUID.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.position = (SatellitePosition) parcel.readTypedObject(SatellitePosition.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bands = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.earfcnRanges = (EarfcnRange[]) parcel.createTypedArray(EarfcnRange.CREATOR);
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
        return describeContents(this.earfcnRanges) | describeContents(this.id) | describeContents(this.position);
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
