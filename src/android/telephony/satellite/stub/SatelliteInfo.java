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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.id, i);
        parcel.writeTypedObject(this.position, i);
        parcel.writeIntArray(this.bands);
        parcel.writeTypedArray(this.earfcnRanges, i);
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
                this.id = (UUID) parcel.readTypedObject(UUID.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.position = (SatellitePosition) parcel.readTypedObject(SatellitePosition.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.bands = parcel.createIntArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.earfcnRanges = (EarfcnRange[]) parcel.createTypedArray(EarfcnRange.CREATOR);
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
        return describeContents(this.earfcnRanges) | describeContents(this.id) | describeContents(this.position);
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
