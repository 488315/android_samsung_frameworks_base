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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.usedCount);
        parcel.writeInt(this.totalCount);
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
                this.usedCount = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.totalCount = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
