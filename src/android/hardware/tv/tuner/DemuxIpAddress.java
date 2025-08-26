package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxIpAddress implements Parcelable {
    public static final Parcelable.Creator<DemuxIpAddress> CREATOR = new Parcelable.Creator<DemuxIpAddress>() { // from class: android.hardware.tv.tuner.DemuxIpAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpAddress createFromParcel(Parcel parcel) {
            DemuxIpAddress demuxIpAddress = new DemuxIpAddress();
            demuxIpAddress.readFromParcel(parcel);
            return demuxIpAddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpAddress[] newArray(int i) {
            return new DemuxIpAddress[i];
        }
    };
    public DemuxIpAddressIpAddress dstIpAddress;
    public DemuxIpAddressIpAddress srcIpAddress;
    public int srcPort = 0;
    public int dstPort = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.srcIpAddress, i);
        parcel.writeTypedObject(this.dstIpAddress, i);
        parcel.writeInt(this.srcPort);
        parcel.writeInt(this.dstPort);
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
                this.srcIpAddress = (DemuxIpAddressIpAddress) parcel.readTypedObject(DemuxIpAddressIpAddress.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dstIpAddress = (DemuxIpAddressIpAddress) parcel.readTypedObject(DemuxIpAddressIpAddress.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.srcPort = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.dstPort = parcel.readInt();
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
        return describeContents(this.dstIpAddress) | describeContents(this.srcIpAddress);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
