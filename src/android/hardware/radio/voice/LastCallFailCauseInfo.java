package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LastCallFailCauseInfo implements Parcelable {
    public static final Parcelable.Creator<LastCallFailCauseInfo> CREATOR = new Parcelable.Creator<LastCallFailCauseInfo>() { // from class: android.hardware.radio.voice.LastCallFailCauseInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LastCallFailCauseInfo createFromParcel(Parcel parcel) {
            LastCallFailCauseInfo lastCallFailCauseInfo = new LastCallFailCauseInfo();
            lastCallFailCauseInfo.readFromParcel(parcel);
            return lastCallFailCauseInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LastCallFailCauseInfo[] newArray(int i) {
            return new LastCallFailCauseInfo[i];
        }
    };
    public int causeCode = 0;
    public String vendorCause;

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
        parcel.writeInt(this.causeCode);
        parcel.writeString(this.vendorCause);
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
                this.causeCode = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.vendorCause = parcel.readString();
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
        stringJoiner.add("causeCode: " + LastCallFailCause$$.toString(this.causeCode));
        stringJoiner.add("vendorCause: " + Objects.toString(this.vendorCause));
        return "LastCallFailCauseInfo" + stringJoiner.toString();
    }
}
