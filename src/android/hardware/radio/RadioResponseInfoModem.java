package android.hardware.radio;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RadioResponseInfoModem implements Parcelable {
    public static final Parcelable.Creator<RadioResponseInfoModem> CREATOR = new Parcelable.Creator<RadioResponseInfoModem>() { // from class: android.hardware.radio.RadioResponseInfoModem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioResponseInfoModem createFromParcel(Parcel parcel) {
            RadioResponseInfoModem radioResponseInfoModem = new RadioResponseInfoModem();
            radioResponseInfoModem.readFromParcel(parcel);
            return radioResponseInfoModem;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioResponseInfoModem[] newArray(int i) {
            return new RadioResponseInfoModem[i];
        }
    };
    public int type = 0;
    public int serial = 0;
    public int error = 0;
    public boolean isEnabled = false;

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
        parcel.writeInt(this.type);
        parcel.writeInt(this.serial);
        parcel.writeInt(this.error);
        parcel.writeBoolean(this.isEnabled);
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
                    this.serial = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.error = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isEnabled = parcel.readBoolean();
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
        stringJoiner.add("type: " + RadioResponseType$$.toString(this.type));
        stringJoiner.add("serial: " + this.serial);
        stringJoiner.add("error: " + RadioError$$.toString(this.error));
        stringJoiner.add("isEnabled: " + this.isEnabled);
        return "RadioResponseInfoModem" + stringJoiner.toString();
    }
}
