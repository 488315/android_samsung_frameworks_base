package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class WcdmaSignalStrength implements Parcelable {
    public static final Parcelable.Creator<WcdmaSignalStrength> CREATOR = new Parcelable.Creator<WcdmaSignalStrength>() { // from class: android.hardware.radio.network.WcdmaSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WcdmaSignalStrength createFromParcel(Parcel parcel) {
            WcdmaSignalStrength wcdmaSignalStrength = new WcdmaSignalStrength();
            wcdmaSignalStrength.readFromParcel(parcel);
            return wcdmaSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WcdmaSignalStrength[] newArray(int i) {
            return new WcdmaSignalStrength[i];
        }
    };
    public int signalStrength = 0;
    public int bitErrorRate = 0;
    public int rscp = 0;
    public int ecno = 0;

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
        parcel.writeInt(this.signalStrength);
        parcel.writeInt(this.bitErrorRate);
        parcel.writeInt(this.rscp);
        parcel.writeInt(this.ecno);
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
                this.signalStrength = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bitErrorRate = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.rscp = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.ecno = parcel.readInt();
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
        stringJoiner.add("signalStrength: " + this.signalStrength);
        stringJoiner.add("bitErrorRate: " + this.bitErrorRate);
        stringJoiner.add("rscp: " + this.rscp);
        stringJoiner.add("ecno: " + this.ecno);
        return "WcdmaSignalStrength" + stringJoiner.toString();
    }
}
