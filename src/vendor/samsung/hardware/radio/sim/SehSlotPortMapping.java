package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSlotPortMapping implements Parcelable {
    public static final Parcelable.Creator<SehSlotPortMapping> CREATOR = new Parcelable.Creator<SehSlotPortMapping>() { // from class: vendor.samsung.hardware.radio.sim.SehSlotPortMapping.1
        @Override // android.os.Parcelable.Creator
        public SehSlotPortMapping createFromParcel(Parcel parcel) {
            SehSlotPortMapping sehSlotPortMapping = new SehSlotPortMapping();
            sehSlotPortMapping.readFromParcel(parcel);
            return sehSlotPortMapping;
        }

        @Override // android.os.Parcelable.Creator
        public SehSlotPortMapping[] newArray(int i) {
            return new SehSlotPortMapping[i];
        }
    };
    public int physicalSlotId = 0;
    public int portId = 0;

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
        parcel.writeInt(this.physicalSlotId);
        parcel.writeInt(this.portId);
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
                this.physicalSlotId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.portId = parcel.readInt();
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
        stringJoiner.add("physicalSlotId: " + this.physicalSlotId);
        stringJoiner.add("portId: " + this.portId);
        return "SehSlotPortMapping" + stringJoiner.toString();
    }
}
