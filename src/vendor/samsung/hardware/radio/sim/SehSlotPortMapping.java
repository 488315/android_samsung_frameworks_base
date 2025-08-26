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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.physicalSlotId);
        parcel.writeInt(this.portId);
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
                this.physicalSlotId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.portId = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("physicalSlotId: " + this.physicalSlotId);
        stringJoiner.add("portId: " + this.portId);
        return "SehSlotPortMapping" + stringJoiner.toString();
    }
}
