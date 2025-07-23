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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.numberOfLockTypes);
        parcel.writeInt(this.lockType);
        parcel.writeInt(this.lockKey);
        parcel.writeInt(this.numberOfRetry);
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
                this.numberOfLockTypes = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.lockType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.lockKey = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.numberOfRetry = parcel.readInt();
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
}
