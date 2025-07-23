package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaT53AudioControlInfoRecord implements Parcelable {
    public static final Parcelable.Creator<CdmaT53AudioControlInfoRecord> CREATOR = new Parcelable.Creator<CdmaT53AudioControlInfoRecord>() { // from class: android.hardware.radio.voice.CdmaT53AudioControlInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaT53AudioControlInfoRecord createFromParcel(Parcel parcel) {
            CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = new CdmaT53AudioControlInfoRecord();
            cdmaT53AudioControlInfoRecord.readFromParcel(parcel);
            return cdmaT53AudioControlInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaT53AudioControlInfoRecord[] newArray(int i) {
            return new CdmaT53AudioControlInfoRecord[i];
        }
    };

    @Deprecated
    public byte upLink = 0;

    @Deprecated
    public byte downLink = 0;

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
        parcel.writeByte(this.upLink);
        parcel.writeByte(this.downLink);
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
                this.upLink = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.downLink = parcel.readByte();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("upLink: " + ((int) this.upLink));
        stringJoiner.add("downLink: " + ((int) this.downLink));
        return "CdmaT53AudioControlInfoRecord" + stringJoiner.toString();
    }
}
