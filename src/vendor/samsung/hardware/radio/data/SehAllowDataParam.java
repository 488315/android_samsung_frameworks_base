package vendor.samsung.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehAllowDataParam implements Parcelable {
    public static final Parcelable.Creator<SehAllowDataParam> CREATOR = new Parcelable.Creator<SehAllowDataParam>() { // from class: vendor.samsung.hardware.radio.data.SehAllowDataParam.1
        @Override // android.os.Parcelable.Creator
        public SehAllowDataParam createFromParcel(Parcel parcel) {
            SehAllowDataParam sehAllowDataParam = new SehAllowDataParam();
            sehAllowDataParam.readFromParcel(parcel);
            return sehAllowDataParam;
        }

        @Override // android.os.Parcelable.Creator
        public SehAllowDataParam[] newArray(int i) {
            return new SehAllowDataParam[i];
        }
    };
    public int defaultDataPhoneId = 0;

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
        parcel.writeInt(this.defaultDataPhoneId);
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
                this.defaultDataPhoneId = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
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
