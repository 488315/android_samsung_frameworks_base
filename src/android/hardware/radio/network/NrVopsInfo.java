package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class NrVopsInfo implements Parcelable {
    public static final Parcelable.Creator<NrVopsInfo> CREATOR = new Parcelable.Creator<NrVopsInfo>() { // from class: android.hardware.radio.network.NrVopsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrVopsInfo createFromParcel(Parcel parcel) {
            NrVopsInfo nrVopsInfo = new NrVopsInfo();
            nrVopsInfo.readFromParcel(parcel);
            return nrVopsInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrVopsInfo[] newArray(int i) {
            return new NrVopsInfo[i];
        }
    };
    public static final byte EMC_INDICATOR_BOTH_NR_EUTRA_CONNECTED_TO_5GCN = 3;
    public static final byte EMC_INDICATOR_EUTRA_CONNECTED_TO_5GCN = 2;
    public static final byte EMC_INDICATOR_NOT_SUPPORTED = 0;
    public static final byte EMC_INDICATOR_NR_CONNECTED_TO_5GCN = 1;
    public static final byte EMF_INDICATOR_BOTH_NR_EUTRA_CONNECTED_TO_5GCN = 3;
    public static final byte EMF_INDICATOR_EUTRA_CONNECTED_TO_5GCN = 2;
    public static final byte EMF_INDICATOR_NOT_SUPPORTED = 0;
    public static final byte EMF_INDICATOR_NR_CONNECTED_TO_5GCN = 1;
    public static final byte VOPS_INDICATOR_VOPS_NOT_SUPPORTED = 0;
    public static final byte VOPS_INDICATOR_VOPS_OVER_3GPP = 1;
    public static final byte VOPS_INDICATOR_VOPS_OVER_NON_3GPP = 2;
    public byte vopsSupported = 0;
    public byte emcSupported = 0;
    public byte emfSupported = 0;

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
        parcel.writeByte(this.vopsSupported);
        parcel.writeByte(this.emcSupported);
        parcel.writeByte(this.emfSupported);
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
                this.vopsSupported = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.emcSupported = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.emfSupported = parcel.readByte();
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
        stringJoiner.add("vopsSupported: " + ((int) this.vopsSupported));
        stringJoiner.add("emcSupported: " + ((int) this.emcSupported));
        stringJoiner.add("emfSupported: " + ((int) this.emfSupported));
        return "NrVopsInfo" + stringJoiner.toString();
    }
}
