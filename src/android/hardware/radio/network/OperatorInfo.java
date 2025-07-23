package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class OperatorInfo implements Parcelable {
    public static final Parcelable.Creator<OperatorInfo> CREATOR = new Parcelable.Creator<OperatorInfo>() { // from class: android.hardware.radio.network.OperatorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperatorInfo createFromParcel(Parcel parcel) {
            OperatorInfo operatorInfo = new OperatorInfo();
            operatorInfo.readFromParcel(parcel);
            return operatorInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperatorInfo[] newArray(int i) {
            return new OperatorInfo[i];
        }
    };
    public static final int STATUS_AVAILABLE = 1;
    public static final int STATUS_CURRENT = 2;
    public static final int STATUS_FORBIDDEN = 3;
    public static final int STATUS_UNKNOWN = 0;
    public String alphaLong;
    public String alphaShort;
    public String operatorNumeric;
    public int status = 0;

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
        parcel.writeString(this.alphaLong);
        parcel.writeString(this.alphaShort);
        parcel.writeString(this.operatorNumeric);
        parcel.writeInt(this.status);
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
                this.alphaLong = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.alphaShort = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.operatorNumeric = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.status = parcel.readInt();
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
        stringJoiner.add("alphaLong: " + Objects.toString(this.alphaLong));
        stringJoiner.add("alphaShort: " + Objects.toString(this.alphaShort));
        stringJoiner.add("operatorNumeric: " + Objects.toString(this.operatorNumeric));
        stringJoiner.add("status: " + this.status);
        return "OperatorInfo" + stringJoiner.toString();
    }
}
