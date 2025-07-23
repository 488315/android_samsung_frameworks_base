package android.gui;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class StalledTransactionInfo implements Parcelable {
    public static final Parcelable.Creator<StalledTransactionInfo> CREATOR = new Parcelable.Creator<StalledTransactionInfo>() { // from class: android.gui.StalledTransactionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StalledTransactionInfo createFromParcel(Parcel parcel) {
            StalledTransactionInfo stalledTransactionInfo = new StalledTransactionInfo();
            stalledTransactionInfo.readFromParcel(parcel);
            return stalledTransactionInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StalledTransactionInfo[] newArray(int i) {
            return new StalledTransactionInfo[i];
        }
    };
    public long bufferId = 0;
    public long frameNumber = 0;
    public String layerName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.layerName);
        parcel.writeLong(this.bufferId);
        parcel.writeLong(this.frameNumber);
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
                this.layerName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bufferId = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.frameNumber = parcel.readLong();
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
