package android.hardware.radio.modem;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RadioCapability implements Parcelable {
    public static final Parcelable.Creator<RadioCapability> CREATOR = new Parcelable.Creator<RadioCapability>() { // from class: android.hardware.radio.modem.RadioCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioCapability createFromParcel(Parcel parcel) {
            RadioCapability radioCapability = new RadioCapability();
            radioCapability.readFromParcel(parcel);
            return radioCapability;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioCapability[] newArray(int i) {
            return new RadioCapability[i];
        }
    };
    public static final int PHASE_APPLY = 2;
    public static final int PHASE_CONFIGURED = 0;
    public static final int PHASE_FINISH = 4;
    public static final int PHASE_START = 1;
    public static final int PHASE_UNSOL_RSP = 3;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_SUCCESS = 1;
    public String logicalModemUuid;
    public int session = 0;
    public int phase = 0;
    public int raf = 0;
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
        parcel.writeInt(this.session);
        parcel.writeInt(this.phase);
        parcel.writeInt(this.raf);
        parcel.writeString(this.logicalModemUuid);
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
                this.session = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.phase = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.raf = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.logicalModemUuid = parcel.readString();
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
        stringJoiner.add("session: " + this.session);
        stringJoiner.add("phase: " + this.phase);
        stringJoiner.add("raf: " + this.raf);
        stringJoiner.add("logicalModemUuid: " + Objects.toString(this.logicalModemUuid));
        stringJoiner.add("status: " + this.status);
        return "RadioCapability" + stringJoiner.toString();
    }
}
