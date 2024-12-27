package vendor.samsung.frameworks.codecsolution;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 8dcf0dbe0f67a13a743f09df378775af1adb9bb1e787ba2899bc963e7059e86a */
/* loaded from: classes.dex */
public class SehDisplaySize implements Parcelable {
    public static final Parcelable.Creator<SehDisplaySize> CREATOR = new Parcelable.Creator<SehDisplaySize>() { // from class: vendor.samsung.frameworks.codecsolution.SehDisplaySize.1
        @Override // android.os.Parcelable.Creator
        public SehDisplaySize createFromParcel(Parcel parcel) {
            SehDisplaySize sehDisplaySize = new SehDisplaySize();
            sehDisplaySize.readFromParcel(parcel);
            return sehDisplaySize;
        }

        @Override // android.os.Parcelable.Creator
        public SehDisplaySize[] newArray(int i) {
            return new SehDisplaySize[i];
        }
    };
    public int width = 0;
    public int height = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.width = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.height = parcel.readInt();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
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

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
