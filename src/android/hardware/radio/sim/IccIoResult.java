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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sw1);
        parcel.writeInt(this.sw2);
        parcel.writeString(this.simResponse);
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
                this.sw1 = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sw2 = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.simResponse = parcel.readString();
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
        stringJoiner.add("sw1: " + this.sw1);
        stringJoiner.add("sw2: " + this.sw2);
        stringJoiner.add("simResponse: " + Objects.toString(this.simResponse));
        return "IccIoResult" + stringJoiner.toString();
    }
}
