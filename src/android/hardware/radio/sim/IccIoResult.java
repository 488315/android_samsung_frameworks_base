package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class IccIoResult implements Parcelable {
    public static final Parcelable.Creator<IccIoResult> CREATOR = new Parcelable.Creator<IccIoResult>() { // from class: android.hardware.radio.sim.IccIoResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccIoResult createFromParcel(Parcel parcel) {
            IccIoResult iccIoResult = new IccIoResult();
            iccIoResult.readFromParcel(parcel);
            return iccIoResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccIoResult[] newArray(int i) {
            return new IccIoResult[i];
        }
    };
    public String simResponse;
    public int sw1 = 0;
    public int sw2 = 0;

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
        parcel.writeInt(this.sw1);
        parcel.writeInt(this.sw2);
        parcel.writeString(this.simResponse);
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
                this.sw1 = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sw2 = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.simResponse = parcel.readString();
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
        stringJoiner.add("sw1: " + this.sw1);
        stringJoiner.add("sw2: " + this.sw2);
        stringJoiner.add("simResponse: " + Objects.toString(this.simResponse));
        return "IccIoResult" + stringJoiner.toString();
    }
}
