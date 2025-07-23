package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LceDataInfo implements Parcelable {
    public static final Parcelable.Creator<LceDataInfo> CREATOR = new Parcelable.Creator<LceDataInfo>() { // from class: android.hardware.radio.network.LceDataInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LceDataInfo createFromParcel(Parcel parcel) {
            LceDataInfo lceDataInfo = new LceDataInfo();
            lceDataInfo.readFromParcel(parcel);
            return lceDataInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LceDataInfo[] newArray(int i) {
            return new LceDataInfo[i];
        }
    };
    public int lastHopCapacityKbps = 0;
    public byte confidenceLevel = 0;
    public boolean lceSuspended = false;

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
        parcel.writeInt(this.lastHopCapacityKbps);
        parcel.writeByte(this.confidenceLevel);
        parcel.writeBoolean(this.lceSuspended);
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
                this.lastHopCapacityKbps = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.confidenceLevel = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.lceSuspended = parcel.readBoolean();
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
        stringJoiner.add("lastHopCapacityKbps: " + this.lastHopCapacityKbps);
        stringJoiner.add("confidenceLevel: " + ((int) this.confidenceLevel));
        stringJoiner.add("lceSuspended: " + this.lceSuspended);
        return "LceDataInfo" + stringJoiner.toString();
    }
}
