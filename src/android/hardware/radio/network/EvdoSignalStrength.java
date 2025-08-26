package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EvdoSignalStrength implements Parcelable {
    public static final Parcelable.Creator<EvdoSignalStrength> CREATOR = new Parcelable.Creator<EvdoSignalStrength>() { // from class: android.hardware.radio.network.EvdoSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EvdoSignalStrength createFromParcel(Parcel parcel) {
            EvdoSignalStrength evdoSignalStrength = new EvdoSignalStrength();
            evdoSignalStrength.readFromParcel(parcel);
            return evdoSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EvdoSignalStrength[] newArray(int i) {
            return new EvdoSignalStrength[i];
        }
    };

    @Deprecated
    public int dbm = 0;

    @Deprecated
    public int ecio = 0;

    @Deprecated
    public int signalNoiseRatio = 0;

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
        parcel.writeInt(this.dbm);
        parcel.writeInt(this.ecio);
        parcel.writeInt(this.signalNoiseRatio);
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
                this.dbm = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.ecio = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.signalNoiseRatio = parcel.readInt();
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
        stringJoiner.add("dbm: " + this.dbm);
        stringJoiner.add("ecio: " + this.ecio);
        stringJoiner.add("signalNoiseRatio: " + this.signalNoiseRatio);
        return "EvdoSignalStrength" + stringJoiner.toString();
    }
}
