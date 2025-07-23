package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaLineControlInfoRecord implements Parcelable {
    public static final Parcelable.Creator<CdmaLineControlInfoRecord> CREATOR = new Parcelable.Creator<CdmaLineControlInfoRecord>() { // from class: android.hardware.radio.voice.CdmaLineControlInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaLineControlInfoRecord createFromParcel(Parcel parcel) {
            CdmaLineControlInfoRecord cdmaLineControlInfoRecord = new CdmaLineControlInfoRecord();
            cdmaLineControlInfoRecord.readFromParcel(parcel);
            return cdmaLineControlInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaLineControlInfoRecord[] newArray(int i) {
            return new CdmaLineControlInfoRecord[i];
        }
    };

    @Deprecated
    public byte lineCtrlPolarityIncluded = 0;

    @Deprecated
    public byte lineCtrlToggle = 0;

    @Deprecated
    public byte lineCtrlReverse = 0;

    @Deprecated
    public byte lineCtrlPowerDenial = 0;

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
        parcel.writeByte(this.lineCtrlPolarityIncluded);
        parcel.writeByte(this.lineCtrlToggle);
        parcel.writeByte(this.lineCtrlReverse);
        parcel.writeByte(this.lineCtrlPowerDenial);
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
                this.lineCtrlPolarityIncluded = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.lineCtrlToggle = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.lineCtrlReverse = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.lineCtrlPowerDenial = parcel.readByte();
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
        stringJoiner.add("lineCtrlPolarityIncluded: " + ((int) this.lineCtrlPolarityIncluded));
        stringJoiner.add("lineCtrlToggle: " + ((int) this.lineCtrlToggle));
        stringJoiner.add("lineCtrlReverse: " + ((int) this.lineCtrlReverse));
        stringJoiner.add("lineCtrlPowerDenial: " + ((int) this.lineCtrlPowerDenial));
        return "CdmaLineControlInfoRecord" + stringJoiner.toString();
    }
}
