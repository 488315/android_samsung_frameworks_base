package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaDisplayInfoRecord implements Parcelable {

    @Deprecated
    public static final int CDMA_ALPHA_INFO_BUFFER_LENGTH = 64;
    public static final Parcelable.Creator<CdmaDisplayInfoRecord> CREATOR = new Parcelable.Creator<CdmaDisplayInfoRecord>() { // from class: android.hardware.radio.voice.CdmaDisplayInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaDisplayInfoRecord createFromParcel(Parcel parcel) {
            CdmaDisplayInfoRecord cdmaDisplayInfoRecord = new CdmaDisplayInfoRecord();
            cdmaDisplayInfoRecord.readFromParcel(parcel);
            return cdmaDisplayInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaDisplayInfoRecord[] newArray(int i) {
            return new CdmaDisplayInfoRecord[i];
        }
    };

    @Deprecated
    public String alphaBuf;

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
        parcel.writeString(this.alphaBuf);
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
                this.alphaBuf = parcel.readString();
                if (iDataPosition > Integer.MAX_VALUE - i) {
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("alphaBuf: " + Objects.toString(this.alphaBuf));
        return "CdmaDisplayInfoRecord" + stringJoiner.toString();
    }
}
