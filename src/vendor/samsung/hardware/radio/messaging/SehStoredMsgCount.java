package vendor.samsung.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehStoredMsgCount implements Parcelable {
    public static final Parcelable.Creator<SehStoredMsgCount> CREATOR = new Parcelable.Creator<SehStoredMsgCount>() { // from class: vendor.samsung.hardware.radio.messaging.SehStoredMsgCount.1
        @Override // android.os.Parcelable.Creator
        public SehStoredMsgCount createFromParcel(Parcel parcel) {
            SehStoredMsgCount sehStoredMsgCount = new SehStoredMsgCount();
            sehStoredMsgCount.readFromParcel(parcel);
            return sehStoredMsgCount;
        }

        @Override // android.os.Parcelable.Creator
        public SehStoredMsgCount[] newArray(int i) {
            return new SehStoredMsgCount[i];
        }
    };
    public int usedCount = 0;
    public int totalCount = 0;

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
        parcel.writeInt(this.usedCount);
        parcel.writeInt(this.totalCount);
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
                this.usedCount = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.totalCount = parcel.readInt();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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
