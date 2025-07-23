package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaNumberInfoRecord implements Parcelable {

    @Deprecated
    public static final int CDMA_NUMBER_INFO_BUFFER_LENGTH = 81;
    public static final Parcelable.Creator<CdmaNumberInfoRecord> CREATOR = new Parcelable.Creator<CdmaNumberInfoRecord>() { // from class: android.hardware.radio.voice.CdmaNumberInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaNumberInfoRecord createFromParcel(Parcel parcel) {
            CdmaNumberInfoRecord cdmaNumberInfoRecord = new CdmaNumberInfoRecord();
            cdmaNumberInfoRecord.readFromParcel(parcel);
            return cdmaNumberInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaNumberInfoRecord[] newArray(int i) {
            return new CdmaNumberInfoRecord[i];
        }
    };

    @Deprecated
    public String number;

    @Deprecated
    public byte numberType = 0;

    @Deprecated
    public byte numberPlan = 0;

    @Deprecated
    public byte pi = 0;

    @Deprecated
    public byte si = 0;

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
        parcel.writeString(this.number);
        parcel.writeByte(this.numberType);
        parcel.writeByte(this.numberPlan);
        parcel.writeByte(this.pi);
        parcel.writeByte(this.si);
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
                this.number = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.numberType = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.numberPlan = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.pi = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.si = parcel.readByte();
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
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("numberType: " + ((int) this.numberType));
        stringJoiner.add("numberPlan: " + ((int) this.numberPlan));
        stringJoiner.add("pi: " + ((int) this.pi));
        stringJoiner.add("si: " + ((int) this.si));
        return "CdmaNumberInfoRecord" + stringJoiner.toString();
    }
}
