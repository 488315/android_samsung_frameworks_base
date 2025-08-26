package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehPhonebookInfo implements Parcelable {
    public static final Parcelable.Creator<SehPhonebookInfo> CREATOR = new Parcelable.Creator<SehPhonebookInfo>() { // from class: vendor.samsung.hardware.radio.sim.SehPhonebookInfo.1
        @Override // android.os.Parcelable.Creator
        public SehPhonebookInfo createFromParcel(Parcel parcel) {
            SehPhonebookInfo sehPhonebookInfo = new SehPhonebookInfo();
            sehPhonebookInfo.readFromParcel(parcel);
            return sehPhonebookInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehPhonebookInfo[] newArray(int i) {
            return new SehPhonebookInfo[i];
        }
    };
    public int totalCount = 0;
    public int usedCount = 0;
    public int firstIndex = 0;
    public int maxTextLength = 0;
    public int maxNumberLength = 0;

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
        parcel.writeInt(this.totalCount);
        parcel.writeInt(this.usedCount);
        parcel.writeInt(this.firstIndex);
        parcel.writeInt(this.maxTextLength);
        parcel.writeInt(this.maxNumberLength);
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
                this.totalCount = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.usedCount = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.firstIndex = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxTextLength = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxNumberLength = parcel.readInt();
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
