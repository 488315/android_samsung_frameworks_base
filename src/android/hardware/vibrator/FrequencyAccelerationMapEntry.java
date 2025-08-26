package android.hardware.vibrator;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrequencyAccelerationMapEntry implements Parcelable {
    public static final Parcelable.Creator<FrequencyAccelerationMapEntry> CREATOR = new Parcelable.Creator<FrequencyAccelerationMapEntry>() { // from class: android.hardware.vibrator.FrequencyAccelerationMapEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrequencyAccelerationMapEntry createFromParcel(Parcel parcel) {
            FrequencyAccelerationMapEntry frequencyAccelerationMapEntry = new FrequencyAccelerationMapEntry();
            frequencyAccelerationMapEntry.readFromParcel(parcel);
            return frequencyAccelerationMapEntry;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrequencyAccelerationMapEntry[] newArray(int i) {
            return new FrequencyAccelerationMapEntry[i];
        }
    };
    public float frequencyHz = 0.0f;
    public float maxOutputAccelerationGs = 0.0f;

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
        parcel.writeFloat(this.frequencyHz);
        parcel.writeFloat(this.maxOutputAccelerationGs);
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
                this.frequencyHz = parcel.readFloat();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.maxOutputAccelerationGs = parcel.readFloat();
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
}
