package android.hardware.radio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RadioConst implements Parcelable {
    public static final int CARD_MAX_APPS = 8;
    public static final Parcelable.Creator<RadioConst> CREATOR = new Parcelable.Creator<RadioConst>() { // from class: android.hardware.radio.RadioConst.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioConst createFromParcel(Parcel parcel) {
            RadioConst radioConst = new RadioConst();
            radioConst.readFromParcel(parcel);
            return radioConst;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioConst[] newArray(int i) {
            return new RadioConst[i];
        }
    };
    public static final int MAX_RILDS = 3;
    public static final int MAX_UUID_LENGTH = 64;
    public static final int P2_CONSTANT_NO_P2 = -1;
    public static final int VALUE_UNAVAILABLE = Integer.MAX_VALUE;
    public static final byte VALUE_UNAVAILABLE_BYTE = -1;
    public static final long VALUE_UNAVAILABLE_LONG = Long.MAX_VALUE;

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
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt >= 4) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } else {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public String toString() {
        return "RadioConst" + new StringJoiner(", ", "{", "}").toString();
    }
}
