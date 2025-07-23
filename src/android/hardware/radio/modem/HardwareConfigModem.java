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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.rilModel);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.maxVoiceCalls);
        parcel.writeInt(this.maxDataCalls);
        parcel.writeInt(this.maxStandby);
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
                this.rilModel = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.rat = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxVoiceCalls = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxDataCalls = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.maxStandby = parcel.readInt();
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
        stringJoiner.add("rilModel: " + this.rilModel);
        stringJoiner.add("rat: " + RadioTechnology$$.toString(this.rat));
        stringJoiner.add("maxVoiceCalls: " + this.maxVoiceCalls);
        stringJoiner.add("maxDataCalls: " + this.maxDataCalls);
        stringJoiner.add("maxStandby: " + this.maxStandby);
        return "HardwareConfigModem" + stringJoiner.toString();
    }
}
