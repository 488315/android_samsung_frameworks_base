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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.isEndcAvailable);
        parcel.writeBoolean(this.isDcNrRestricted);
        parcel.writeBoolean(this.isNrAvailable);
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
                this.isEndcAvailable = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isDcNrRestricted = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isNrAvailable = parcel.readBoolean();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("isEndcAvailable: " + this.isEndcAvailable);
        stringJoiner.add("isDcNrRestricted: " + this.isDcNrRestricted);
        stringJoiner.add("isNrAvailable: " + this.isNrAvailable);
        return "NrIndicators" + stringJoiner.toString();
    }
}
