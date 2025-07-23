package android.hardware.radio.config;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SimTypeInfo implements Parcelable {
    public static final Parcelable.Creator<SimTypeInfo> CREATOR = new Parcelable.Creator<SimTypeInfo>() { // from class: android.hardware.radio.config.SimTypeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimTypeInfo createFromParcel(Parcel parcel) {
            SimTypeInfo simTypeInfo = new SimTypeInfo();
            simTypeInfo.readFromParcel(parcel);
            return simTypeInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimTypeInfo[] newArray(int i) {
            return new SimTypeInfo[i];
        }
    };
    public int currentSimType = 0;
    public int supportedSimTypes = 0;

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
        parcel.writeInt(this.currentSimType);
        parcel.writeInt(this.supportedSimTypes);
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
                this.currentSimType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.supportedSimTypes = parcel.readInt();
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
        stringJoiner.add("currentSimType: " + SimType$$.toString(this.currentSimType));
        stringJoiner.add("supportedSimTypes: " + this.supportedSimTypes);
        return "SimTypeInfo" + stringJoiner.toString();
    }
}
