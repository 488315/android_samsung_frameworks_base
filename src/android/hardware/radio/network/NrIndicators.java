package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class NrIndicators implements Parcelable {
    public static final Parcelable.Creator<NrIndicators> CREATOR = new Parcelable.Creator<NrIndicators>() { // from class: android.hardware.radio.network.NrIndicators.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrIndicators createFromParcel(Parcel parcel) {
            NrIndicators nrIndicators = new NrIndicators();
            nrIndicators.readFromParcel(parcel);
            return nrIndicators;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrIndicators[] newArray(int i) {
            return new NrIndicators[i];
        }
    };
    public boolean isEndcAvailable = false;
    public boolean isDcNrRestricted = false;
    public boolean isNrAvailable = false;

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
        parcel.writeBoolean(this.isEndcAvailable);
        parcel.writeBoolean(this.isDcNrRestricted);
        parcel.writeBoolean(this.isNrAvailable);
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
                this.isEndcAvailable = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isDcNrRestricted = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isNrAvailable = parcel.readBoolean();
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
        stringJoiner.add("isEndcAvailable: " + this.isEndcAvailable);
        stringJoiner.add("isDcNrRestricted: " + this.isDcNrRestricted);
        stringJoiner.add("isNrAvailable: " + this.isNrAvailable);
        return "NrIndicators" + stringJoiner.toString();
    }
}
