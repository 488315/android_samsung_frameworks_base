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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.totalCount);
        parcel.writeInt(this.usedCount);
        parcel.writeInt(this.firstIndex);
        parcel.writeInt(this.maxTextLength);
        parcel.writeInt(this.maxNumberLength);
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
                this.totalCount = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.usedCount = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.firstIndex = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxTextLength = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.maxNumberLength = parcel.readInt();
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
