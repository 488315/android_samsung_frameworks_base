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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.subscriberId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeString(this.mNiddApn);
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
                this.subscriberId = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mCarrierId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mNiddApn = parcel.readString();
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
