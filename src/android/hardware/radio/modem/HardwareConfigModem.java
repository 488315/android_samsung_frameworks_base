package android.hardware.radio.modem;

import android.hardware.radio.RadioTechnology$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class HardwareConfigModem implements Parcelable {
    public static final Parcelable.Creator<HardwareConfigModem> CREATOR = new Parcelable.Creator<HardwareConfigModem>() { // from class: android.hardware.radio.modem.HardwareConfigModem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareConfigModem createFromParcel(Parcel parcel) {
            HardwareConfigModem hardwareConfigModem = new HardwareConfigModem();
            hardwareConfigModem.readFromParcel(parcel);
            return hardwareConfigModem;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareConfigModem[] newArray(int i) {
            return new HardwareConfigModem[i];
        }
    };
    public int rilModel = 0;
    public int rat = 0;
    public int maxVoiceCalls = 0;
    public int maxDataCalls = 0;
    public int maxStandby = 0;

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
        parcel.writeInt(this.rilModel);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.maxVoiceCalls);
        parcel.writeInt(this.maxDataCalls);
        parcel.writeInt(this.maxStandby);
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
                this.rilModel = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.rat = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxVoiceCalls = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxDataCalls = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxStandby = parcel.readInt();
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
        stringJoiner.add("rilModel: " + this.rilModel);
        stringJoiner.add("rat: " + RadioTechnology$$.toString(this.rat));
        stringJoiner.add("maxVoiceCalls: " + this.maxVoiceCalls);
        stringJoiner.add("maxDataCalls: " + this.maxDataCalls);
        stringJoiner.add("maxStandby: " + this.maxStandby);
        return "HardwareConfigModem" + stringJoiner.toString();
    }
}
