package vendor.samsung.hardware.radio.satelliteservice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehSystemSelectionSpecifier implements Parcelable {
    public static final Parcelable.Creator<SehSystemSelectionSpecifier> CREATOR = new Parcelable.Creator<SehSystemSelectionSpecifier>() { // from class: vendor.samsung.hardware.radio.satelliteservice.SehSystemSelectionSpecifier.1
        @Override // android.os.Parcelable.Creator
        public SehSystemSelectionSpecifier createFromParcel(Parcel parcel) {
            SehSystemSelectionSpecifier sehSystemSelectionSpecifier = new SehSystemSelectionSpecifier();
            sehSystemSelectionSpecifier.readFromParcel(parcel);
            return sehSystemSelectionSpecifier;
        }

        @Override // android.os.Parcelable.Creator
        public SehSystemSelectionSpecifier[] newArray(int i) {
            return new SehSystemSelectionSpecifier[i];
        }
    };
    public int[] bands;
    public int[] earfcs;
    public String mccMnc;

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
        parcel.writeString(this.mccMnc);
        parcel.writeIntArray(this.bands);
        parcel.writeIntArray(this.earfcs);
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
                this.mccMnc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bands = parcel.createIntArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.earfcs = parcel.createIntArray();
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
