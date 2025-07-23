package android.hardware.radio.modem;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class HardwareConfig implements Parcelable {
    public static final Parcelable.Creator<HardwareConfig> CREATOR = new Parcelable.Creator<HardwareConfig>() { // from class: android.hardware.radio.modem.HardwareConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareConfig createFromParcel(Parcel parcel) {
            HardwareConfig hardwareConfig = new HardwareConfig();
            hardwareConfig.readFromParcel(parcel);
            return hardwareConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareConfig[] newArray(int i) {
            return new HardwareConfig[i];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 0;
    public static final int STATE_STANDBY = 1;
    public static final int TYPE_MODEM = 0;
    public static final int TYPE_SIM = 1;
    public HardwareConfigModem[] modem;
    public HardwareConfigSim[] sim;
    public String uuid;
    public int type = 0;
    public int state = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.uuid);
        parcel.writeInt(this.state);
        parcel.writeTypedArray(this.modem, i);
        parcel.writeTypedArray(this.sim, i);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uuid = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.state = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.modem = (HardwareConfigModem[]) parcel.createTypedArray(HardwareConfigModem.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.sim = (HardwareConfigSim[]) parcel.createTypedArray(HardwareConfigSim.CREATOR);
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("uuid: " + Objects.toString(this.uuid));
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("modem: " + Arrays.toString(this.modem));
        stringJoiner.add("sim: " + Arrays.toString(this.sim));
        return "HardwareConfig" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sim) | describeContents(this.modem);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
