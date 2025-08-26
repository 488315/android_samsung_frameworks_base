package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EmergencyNumber implements Parcelable {
    public static final Parcelable.Creator<EmergencyNumber> CREATOR = new Parcelable.Creator<EmergencyNumber>() { // from class: android.hardware.radio.voice.EmergencyNumber.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyNumber createFromParcel(Parcel parcel) {
            EmergencyNumber emergencyNumber = new EmergencyNumber();
            emergencyNumber.readFromParcel(parcel);
            return emergencyNumber;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyNumber[] newArray(int i) {
            return new EmergencyNumber[i];
        }
    };
    public static final int SOURCE_DEFAULT = 8;
    public static final int SOURCE_MODEM_CONFIG = 4;
    public static final int SOURCE_NETWORK_SIGNALING = 1;
    public static final int SOURCE_SIM = 2;
    public String mcc;
    public String mnc;
    public String number;
    public String[] urns;
    public int categories = 0;
    public int sources = 0;

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
        parcel.writeString(this.number);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeInt(this.categories);
        parcel.writeStringArray(this.urns);
        parcel.writeInt(this.sources);
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
                this.number = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mcc = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mnc = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.categories = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.urns = parcel.createStringArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.sources = parcel.readInt();
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
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("categories: " + this.categories);
        stringJoiner.add("urns: " + Arrays.toString(this.urns));
        stringJoiner.add("sources: " + this.sources);
        return "EmergencyNumber" + stringJoiner.toString();
    }
}
