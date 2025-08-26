package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SatelliteSubscriberInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriberInfo> CREATOR = new Parcelable.Creator<SatelliteSubscriberInfo>() { // from class: android.telephony.satellite.stub.SatelliteSubscriberInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberInfo createFromParcel(Parcel parcel) {
            SatelliteSubscriberInfo satelliteSubscriberInfo = new SatelliteSubscriberInfo();
            satelliteSubscriberInfo.readFromParcel(parcel);
            return satelliteSubscriberInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberInfo[] newArray(int i) {
            return new SatelliteSubscriberInfo[i];
        }
    };
    public int mCarrierId = 0;
    public String mNiddApn;
    public String subscriberId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.subscriberId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeString(this.mNiddApn);
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
                this.subscriberId = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mCarrierId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mNiddApn = parcel.readString();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
