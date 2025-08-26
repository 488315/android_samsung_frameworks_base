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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        if (i >= 4) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } else {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }

    public String toString() {
        return "RadioConst" + new StringJoiner(", ", "{", "}").toString();
    }
}
