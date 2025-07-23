package vendor.samsung.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehCallDetails implements Parcelable {
    public static final Parcelable.Creator<SehCallDetails> CREATOR = new Parcelable.Creator<SehCallDetails>() { // from class: vendor.samsung.hardware.radio.network.SehCallDetails.1
        @Override // android.os.Parcelable.Creator
        public SehCallDetails createFromParcel(Parcel parcel) {
            SehCallDetails sehCallDetails = new SehCallDetails();
            sehCallDetails.readFromParcel(parcel);
            return sehCallDetails;
        }

        @Override // android.os.Parcelable.Creator
        public SehCallDetails[] newArray(int i) {
            return new SehCallDetails[i];
        }
    };
    public int callType;
    public String[] extras;

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
        parcel.writeInt(this.callType);
        parcel.writeStringArray(this.extras);
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
                this.callType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.extras = parcel.createStringArray();
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
        stringJoiner.add("callType: " + SehCallType$$.toString(this.callType));
        stringJoiner.add("extras: " + Arrays.toString(this.extras));
        return "SehCallDetails" + stringJoiner.toString();
    }
}
