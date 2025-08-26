package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSimLockInfo implements Parcelable {
    public static final Parcelable.Creator<SehSimLockInfo> CREATOR = new Parcelable.Creator<SehSimLockInfo>() { // from class: vendor.samsung.hardware.radio.sim.SehSimLockInfo.1
        @Override // android.os.Parcelable.Creator
        public SehSimLockInfo createFromParcel(Parcel parcel) {
            SehSimLockInfo sehSimLockInfo = new SehSimLockInfo();
            sehSimLockInfo.readFromParcel(parcel);
            return sehSimLockInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehSimLockInfo[] newArray(int i) {
            return new SehSimLockInfo[i];
        }
    };
    public int numberOfLockTypes = 0;
    public int lockType = 0;
    public int lockKey = 0;
    public int numberOfRetry = 0;

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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.numberOfLockTypes);
        parcel.writeInt(this.lockType);
        parcel.writeInt(this.lockKey);
        parcel.writeInt(this.numberOfRetry);
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
                this.numberOfLockTypes = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.lockType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.lockKey = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.numberOfRetry = parcel.readInt();
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
}
