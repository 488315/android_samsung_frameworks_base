package android.hardware.radio.network;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SignalThresholdInfo implements Parcelable {
    public static final Parcelable.Creator<SignalThresholdInfo> CREATOR = new Parcelable.Creator<SignalThresholdInfo>() { // from class: android.hardware.radio.network.SignalThresholdInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalThresholdInfo createFromParcel(Parcel parcel) {
            SignalThresholdInfo signalThresholdInfo = new SignalThresholdInfo();
            signalThresholdInfo.readFromParcel(parcel);
            return signalThresholdInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalThresholdInfo[] newArray(int i) {
            return new SignalThresholdInfo[i];
        }
    };
    public static final int SIGNAL_MEASUREMENT_TYPE_ECNO = 9;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSCP = 2;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRP = 3;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRQ = 4;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSI = 1;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSNR = 5;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRP = 6;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRQ = 7;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSSINR = 8;
    public int[] thresholds;
    public int signalMeasurement = 0;
    public int hysteresisMs = 0;
    public int hysteresisDb = 0;
    public boolean isEnabled = false;
    public int ran = 0;

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
        parcel.writeInt(this.signalMeasurement);
        parcel.writeInt(this.hysteresisMs);
        parcel.writeInt(this.hysteresisDb);
        parcel.writeIntArray(this.thresholds);
        parcel.writeBoolean(this.isEnabled);
        parcel.writeInt(this.ran);
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
                this.signalMeasurement = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.hysteresisMs = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.hysteresisDb = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.thresholds = parcel.createIntArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.isEnabled = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.ran = parcel.readInt();
                                    if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("signalMeasurement: " + this.signalMeasurement);
        stringJoiner.add("hysteresisMs: " + this.hysteresisMs);
        stringJoiner.add("hysteresisDb: " + this.hysteresisDb);
        stringJoiner.add("thresholds: " + Arrays.toString(this.thresholds));
        stringJoiner.add("isEnabled: " + this.isEnabled);
        stringJoiner.add("ran: " + AccessNetwork$$.toString(this.ran));
        return "SignalThresholdInfo" + stringJoiner.toString();
    }
}
