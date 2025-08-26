package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class Carrier implements Parcelable {
    public static final Parcelable.Creator<Carrier> CREATOR = new Parcelable.Creator<Carrier>() { // from class: android.hardware.radio.sim.Carrier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Carrier createFromParcel(Parcel parcel) {
            Carrier carrier = new Carrier();
            carrier.readFromParcel(parcel);
            return carrier;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Carrier[] newArray(int i) {
            return new Carrier[i];
        }
    };
    public static final int MATCH_TYPE_ALL = 0;
    public static final int MATCH_TYPE_GID1 = 3;
    public static final int MATCH_TYPE_GID2 = 4;
    public static final int MATCH_TYPE_IMSI_PREFIX = 2;
    public static final int MATCH_TYPE_SPN = 1;
    public String matchData;
    public int matchType = 0;
    public String mcc;
    public String mnc;

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
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeInt(this.matchType);
        parcel.writeString(this.matchData);
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
                this.mcc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mnc = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.matchType = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.matchData = parcel.readString();
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
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("matchType: " + this.matchType);
        stringJoiner.add("matchData: " + Objects.toString(this.matchData));
        return "Carrier" + stringJoiner.toString();
    }
}
