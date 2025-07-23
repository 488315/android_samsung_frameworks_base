package android.hardware.radio.ims;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ConnectionFailureInfo implements Parcelable {
    public static final Parcelable.Creator<ConnectionFailureInfo> CREATOR = new Parcelable.Creator<ConnectionFailureInfo>() { // from class: android.hardware.radio.ims.ConnectionFailureInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionFailureInfo createFromParcel(Parcel parcel) {
            ConnectionFailureInfo connectionFailureInfo = new ConnectionFailureInfo();
            connectionFailureInfo.readFromParcel(parcel);
            return connectionFailureInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConnectionFailureInfo[] newArray(int i) {
            return new ConnectionFailureInfo[i];
        }
    };
    public int failureReason = 0;
    public int causeCode = 0;
    public int waitTimeMillis = 0;

    public @interface ConnectionFailureReason {
        public static final int INVALID = 0;
        public static final int REASON_ACCESS_DENIED = 1;
        public static final int REASON_NAS_FAILURE = 2;
        public static final int REASON_NO_SERVICE = 7;
        public static final int REASON_PDN_NOT_AVAILABLE = 8;
        public static final int REASON_RACH_FAILURE = 3;
        public static final int REASON_RF_BUSY = 9;
        public static final int REASON_RLC_FAILURE = 4;
        public static final int REASON_RRC_REJECT = 5;
        public static final int REASON_RRC_TIMEOUT = 6;
        public static final int REASON_UNSPECIFIED = 65535;
    }

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
        parcel.writeInt(this.failureReason);
        parcel.writeInt(this.causeCode);
        parcel.writeInt(this.waitTimeMillis);
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
                this.failureReason = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.causeCode = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.waitTimeMillis = parcel.readInt();
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
        stringJoiner.add("failureReason: " + this.failureReason);
        stringJoiner.add("causeCode: " + this.causeCode);
        stringJoiner.add("waitTimeMillis: " + this.waitTimeMillis);
        return "ConnectionFailureInfo" + stringJoiner.toString();
    }
}
