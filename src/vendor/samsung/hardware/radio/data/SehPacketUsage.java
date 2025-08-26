package vendor.samsung.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehPacketUsage implements Parcelable {
    public static final Parcelable.Creator<SehPacketUsage> CREATOR = new Parcelable.Creator<SehPacketUsage>() { // from class: vendor.samsung.hardware.radio.data.SehPacketUsage.1
        @Override // android.os.Parcelable.Creator
        public SehPacketUsage createFromParcel(Parcel parcel) {
            SehPacketUsage sehPacketUsage = new SehPacketUsage();
            sehPacketUsage.readFromParcel(parcel);
            return sehPacketUsage;
        }

        @Override // android.os.Parcelable.Creator
        public SehPacketUsage[] newArray(int i) {
            return new SehPacketUsage[i];
        }
    };
    public long rxBytes = 0;
    public long txBytes = 0;

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
        parcel.writeLong(this.rxBytes);
        parcel.writeLong(this.txBytes);
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
                this.rxBytes = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.txBytes = parcel.readLong();
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
