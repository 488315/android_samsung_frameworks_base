package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhonebookRecordInfo implements Parcelable {
    public static final Parcelable.Creator<PhonebookRecordInfo> CREATOR = new Parcelable.Creator<PhonebookRecordInfo>() { // from class: android.hardware.radio.sim.PhonebookRecordInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookRecordInfo createFromParcel(Parcel parcel) {
            PhonebookRecordInfo phonebookRecordInfo = new PhonebookRecordInfo();
            phonebookRecordInfo.readFromParcel(parcel);
            return phonebookRecordInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhonebookRecordInfo[] newArray(int i) {
            return new PhonebookRecordInfo[i];
        }
    };
    public String[] additionalNumbers;
    public String[] emails;
    public String name;
    public String number;
    public int recordId = 0;

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
        parcel.writeInt(this.recordId);
        parcel.writeString(this.name);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.emails);
        parcel.writeStringArray(this.additionalNumbers);
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
                this.recordId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.name = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.number = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.emails = parcel.createStringArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.additionalNumbers = parcel.createStringArray();
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
        stringJoiner.add("recordId: " + this.recordId);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("emails: " + Arrays.toString(this.emails));
        stringJoiner.add("additionalNumbers: " + Arrays.toString(this.additionalNumbers));
        return "PhonebookRecordInfo" + stringJoiner.toString();
    }
}
