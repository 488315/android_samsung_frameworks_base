package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaSignalInfoRecord implements Parcelable {
    public static final Parcelable.Creator<CdmaSignalInfoRecord> CREATOR = new Parcelable.Creator<CdmaSignalInfoRecord>() { // from class: android.hardware.radio.voice.CdmaSignalInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSignalInfoRecord createFromParcel(Parcel parcel) {
            CdmaSignalInfoRecord cdmaSignalInfoRecord = new CdmaSignalInfoRecord();
            cdmaSignalInfoRecord.readFromParcel(parcel);
            return cdmaSignalInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSignalInfoRecord[] newArray(int i) {
            return new CdmaSignalInfoRecord[i];
        }
    };

    @Deprecated
    public boolean isPresent = false;

    @Deprecated
    public byte signalType = 0;

    @Deprecated
    public byte alertPitch = 0;

    @Deprecated
    public byte signal = 0;

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
        parcel.writeBoolean(this.isPresent);
        parcel.writeByte(this.signalType);
        parcel.writeByte(this.alertPitch);
        parcel.writeByte(this.signal);
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
                this.isPresent = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.signalType = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.alertPitch = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.signal = parcel.readByte();
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
        stringJoiner.add("isPresent: " + this.isPresent);
        stringJoiner.add("signalType: " + ((int) this.signalType));
        stringJoiner.add("alertPitch: " + ((int) this.alertPitch));
        stringJoiner.add("signal: " + ((int) this.signal));
        return "CdmaSignalInfoRecord" + stringJoiner.toString();
    }
}
