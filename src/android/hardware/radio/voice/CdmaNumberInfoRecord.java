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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.number);
        parcel.writeByte(this.numberType);
        parcel.writeByte(this.numberPlan);
        parcel.writeByte(this.pi);
        parcel.writeByte(this.si);
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
                    this.numberType = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.numberPlan = parcel.readByte();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.pi = parcel.readByte();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.si = parcel.readByte();
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
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("numberType: " + ((int) this.numberType));
        stringJoiner.add("numberPlan: " + ((int) this.numberPlan));
        stringJoiner.add("pi: " + ((int) this.pi));
        stringJoiner.add("si: " + ((int) this.si));
        return "CdmaNumberInfoRecord" + stringJoiner.toString();
    }
}
