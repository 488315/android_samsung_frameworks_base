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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mccMnc);
        parcel.writeIntArray(this.bands);
        parcel.writeIntArray(this.earfcs);
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
                this.mccMnc = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bands = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.earfcs = parcel.createIntArray();
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
