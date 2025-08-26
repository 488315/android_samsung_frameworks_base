package vendor.samsung.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehRrcStateInfo implements Parcelable {
    public static final Parcelable.Creator<SehRrcStateInfo> CREATOR = new Parcelable.Creator<SehRrcStateInfo>() { // from class: vendor.samsung.hardware.radio.data.SehRrcStateInfo.1
        @Override // android.os.Parcelable.Creator
        public SehRrcStateInfo createFromParcel(Parcel parcel) {
            SehRrcStateInfo sehRrcStateInfo = new SehRrcStateInfo();
            sehRrcStateInfo.readFromParcel(parcel);
            return sehRrcStateInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehRrcStateInfo[] newArray(int i) {
            return new SehRrcStateInfo[i];
        }
    };
    public byte rat = 0;
    public int state = 0;

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
        parcel.writeByte(this.rat);
        parcel.writeInt(this.state);
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
                this.rat = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.state = parcel.readInt();
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
