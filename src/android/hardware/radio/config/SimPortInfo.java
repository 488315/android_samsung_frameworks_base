package android.hardware.radio.config;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SimPortInfo implements Parcelable {
    public static final Parcelable.Creator<SimPortInfo> CREATOR = new Parcelable.Creator<SimPortInfo>() { // from class: android.hardware.radio.config.SimPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimPortInfo createFromParcel(Parcel parcel) {
            SimPortInfo simPortInfo = new SimPortInfo();
            simPortInfo.readFromParcel(parcel);
            return simPortInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimPortInfo[] newArray(int i) {
            return new SimPortInfo[i];
        }
    };
    public String iccId;
    public int logicalSlotId = 0;
    public boolean portActive = false;

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
        parcel.writeString(this.iccId);
        parcel.writeInt(this.logicalSlotId);
        parcel.writeBoolean(this.portActive);
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
                this.iccId = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.logicalSlotId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.portActive = parcel.readBoolean();
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
        stringJoiner.add("iccId: " + Objects.toString(this.iccId));
        stringJoiner.add("logicalSlotId: " + this.logicalSlotId);
        stringJoiner.add("portActive: " + this.portActive);
        return "SimPortInfo" + stringJoiner.toString();
    }
}
