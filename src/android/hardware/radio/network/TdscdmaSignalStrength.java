package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class TdscdmaSignalStrength implements Parcelable {
    public static final Parcelable.Creator<TdscdmaSignalStrength> CREATOR = new Parcelable.Creator<TdscdmaSignalStrength>() { // from class: android.hardware.radio.network.TdscdmaSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TdscdmaSignalStrength createFromParcel(Parcel parcel) {
            TdscdmaSignalStrength tdscdmaSignalStrength = new TdscdmaSignalStrength();
            tdscdmaSignalStrength.readFromParcel(parcel);
            return tdscdmaSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TdscdmaSignalStrength[] newArray(int i) {
            return new TdscdmaSignalStrength[i];
        }
    };
    public int signalStrength = 0;
    public int bitErrorRate = 0;
    public int rscp = 0;

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
        parcel.writeInt(this.signalStrength);
        parcel.writeInt(this.bitErrorRate);
        parcel.writeInt(this.rscp);
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
                this.signalStrength = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bitErrorRate = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.rscp = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("signalStrength: " + this.signalStrength);
        stringJoiner.add("bitErrorRate: " + this.bitErrorRate);
        stringJoiner.add("rscp: " + this.rscp);
        return "TdscdmaSignalStrength" + stringJoiner.toString();
    }
}
