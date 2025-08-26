package android.hardware.input;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class IKeyboardBacklightState implements Parcelable {
    public static final Parcelable.Creator<IKeyboardBacklightState> CREATOR = new Parcelable.Creator<IKeyboardBacklightState>() { // from class: android.hardware.input.IKeyboardBacklightState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IKeyboardBacklightState createFromParcel(Parcel parcel) {
            IKeyboardBacklightState iKeyboardBacklightState = new IKeyboardBacklightState();
            iKeyboardBacklightState.readFromParcel(parcel);
            return iKeyboardBacklightState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IKeyboardBacklightState[] newArray(int i) {
            return new IKeyboardBacklightState[i];
        }
    };
    public int brightnessLevel = 0;
    public int maxBrightnessLevel = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.brightnessLevel);
        parcel.writeInt(this.maxBrightnessLevel);
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
                this.brightnessLevel = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.maxBrightnessLevel = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IKeyboardBacklightState)) {
            return false;
        }
        IKeyboardBacklightState iKeyboardBacklightState = (IKeyboardBacklightState) obj;
        return Objects.deepEquals(Integer.valueOf(this.brightnessLevel), Integer.valueOf(iKeyboardBacklightState.brightnessLevel)) && Objects.deepEquals(Integer.valueOf(this.maxBrightnessLevel), Integer.valueOf(iKeyboardBacklightState.maxBrightnessLevel));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.brightnessLevel), Integer.valueOf(this.maxBrightnessLevel)).toArray());
    }
}
